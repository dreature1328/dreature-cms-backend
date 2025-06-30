package xyz.dreature.cms.content.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import xyz.dreature.cms.common.entity.Category;
import xyz.dreature.cms.common.entity.Post;
import xyz.dreature.cms.common.entity.PostBrief;
import xyz.dreature.cms.common.util.MapperUtil;
import xyz.dreature.cms.common.util.PrefixKey;
import xyz.dreature.cms.common.vo.TableResult;
import xyz.dreature.cms.content.mapper.ContentMapper;

import java.util.List;

@Service
public class ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private Jedis jedis;

    private ObjectMapper mapper = MapperUtil.MP;

    //-----前台文章与类别相关-----
    //查询文章总数
    public Integer queryTotalOfPost() {
        return contentMapper.queryTotalOfPost();
    }

    //查询某个类别下的文章总数
    public Integer queryTotalOfPostInCategory(Integer categoryId) {
        if (categoryId == 0) return contentMapper.queryTotalOfPost();
        else return contentMapper.queryTotalOfPostInCategory(categoryId);
    }

    //不借助redis，无缓存功能的通过id查询文章函数
    public Post queryPostById(Integer postId) {
        try {
            Post Post = contentMapper.queryPostById(postId);
            return Post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //借助redis实现缓存功能的通过id查询文章函数
    public Post queryPostByIdWithRedis(Integer postId) {
        String PostKey = PrefixKey.POST_QUERY_PREFIX + postId;
        String lock = PrefixKey.POST_UPDATE_PREFIX + postId + ".lock";
        try {
            // 先判断缓存中是否有锁
            if (jedis.exists(lock))
                return contentMapper.queryPostById(postId);
            // 判断缓存是否存在数据
            if (jedis.exists(PostKey)) {
                String PostJson = jedis.get(PostKey);
                Post Post = mapper.readValue(PostJson, Post.class);
                return Post;
            } else {
                Post Post = contentMapper.queryPostById(postId);
                String PostJson = mapper.writeValueAsString(Post);
                jedis.setex(PostKey, 60 * 60 * 24 * 2, PostJson);
                return Post;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //按页查询文章
    public TableResult queryPostByPage(Integer page, Integer length) {
        // 封装数据到 TableResult 对象
        // 1.创建一个返回的对象,将查询数据set进去然后返回
        TableResult result = new TableResult();
        // 2封装第一个属性 total 的数量
        Integer total = queryTotalOfPost();
        // 3封装第二个属性 List<Post> pList
        // 根据页数计算起始位置
        Integer start = (page - 1) * length;
        //page指页数，length指每页长度（能容纳多少个对象）
        List<Post> pList = contentMapper.queryPostByPage(start, length);
        // 4封装对象属性
        result.setTotal(total);
        result.setItems(pList);


        return result;
    }

    //按id查询文章简略信息
    public TableResult queryPostBriefByPostId(Integer postId) {
        // 封装数据到 TableResult 对象
        // 1.创建一个返回的对象,将查询数据set进去然后返回
        TableResult result = new TableResult();
        // 2封装第一个属性 total 的数量
        Integer total = contentMapper.queryPostBriefTotalByPostId(postId);
        // 3封装第二个属性 List<PostBrief> pList
        List<PostBrief> pList = contentMapper.queryPostBriefByPostId(postId);
        // 4封装对象属性
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    //查询所有类别（所有信息）
    public TableResult queryAllCategory() {
        TableResult result = new TableResult();
        Integer total = contentMapper.queryTotalOfCategory();
        List<Category> pList = contentMapper.queryAllCategory();
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    //根据类别以及热度排序展示文章热榜
    public TableResult listHotRankByCategoryIdAndPage(Integer categoryId, Integer page, Integer length) {

        TableResult result = new TableResult();
        Integer total;
        Integer start = (page - 1) * length;
        List<Post> pList;

        //如果categoryid为0，则罗列所有分类的文章
        if (categoryId == 0) {
            total = contentMapper.queryTotalOfPost();
            pList = contentMapper.listHotRankByPage(start, length);
        } else {
            total = contentMapper.queryTotalOfPostInCategory(categoryId);
            pList = contentMapper.listHotRankByCategoryIdAndPage(categoryId, start, length);
        }

        result.setTotal(total);
        result.setItems(pList);

        return result;
    }

    //根据字符串搜索文章
    public TableResult searchPostByQAndPage(String q, Integer page, Integer length) {
        TableResult result = new TableResult();
        q = '%' + q + '%';
        Integer start = (page - 1) * length;
        Integer total = contentMapper.queryTotalOfSearchResultByQ(q);
        List<Post> pList = contentMapper.searchPostByQAndPage(q, start, length);
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    //------后台文章管理------
    //后台发表文章
    public void postPost(Post Post) {
        contentMapper.postPost(Post);
    }

    //后台更新文章
    public void updatePost(Post Post) {
        contentMapper.updatePost(Post);
    }

    //后台更新文章，借助redis
    public void updatePostWithRedis(Post Post) {
        // 更新前先删除缓存
        // 删除缓存成功，才能进行商品更新，否则用户查询到旧数据
        String lock = PrefixKey.POST_UPDATE_PREFIX + Post.getPostId() + ".lock";
        Long leftTime = jedis.ttl(PrefixKey.POST_QUERY_PREFIX + Post.getPostId());
        //缺陷：如果没有人查过该商品，redis里没有缓存，返回的时间是-2
        if (leftTime > 0) {
            jedis.setex(lock, Integer.parseInt(leftTime + ""), "");
            jedis.del(PrefixKey.POST_QUERY_PREFIX + Post.getPostId());
        }
        contentMapper.updatePost(Post);
        //解锁
        jedis.del(lock);
    }

    //后台删除文章，不借助redis
    //stringIds指一个字符串，各id用逗号隔开
    //stringIdArr[]指用逗号切割stringIds，获取String类型的id存为数组
    //integerIdArr[]指用将stringIdArr转为Integer类型
    public void deletePost(String stringIds) {
        String stringIdArr[] = stringIds.split(",");
        Integer integerIdArr[] = new Integer[stringIdArr.length];

        //String类型的id数组转成integer类型
        for (int i = 0; i < stringIdArr.length; i++) {
            integerIdArr[i] = Integer.valueOf(stringIdArr[i]);
        }

        contentMapper.deletePost(integerIdArr);
    }

    //后台删除文章，借助redis
    public void deletePostWithRedis(String stringIds) {
        String stringIdArr[] = stringIds.split(",");
        Integer integerIdArr[] = new Integer[stringIdArr.length];

        for (String stringId : stringIdArr) {
            Post del_Post = new Post();
            del_Post.setPostId(Integer.valueOf(stringId));

            String PostKey = PrefixKey.POST_QUERY_PREFIX + del_Post.getPostId();
            String lock = PrefixKey.POST_UPDATE_PREFIX + del_Post.getPostId() + ".lock";
            // 判断缓存是否存在数据
            if (jedis.exists(PostKey)) {
                jedis.del(PostKey);
            }
            // 判断缓存中是否有锁
            if (jedis.exists(lock))
                jedis.del(lock);
        }

        //String类型的id数组转成Integer类型
        for (int i = 0; i < stringIdArr.length; i++) {
            integerIdArr[i] = Integer.valueOf(stringIdArr[i]);
        }

        contentMapper.deletePost(integerIdArr);
    }

    //后台隐藏文章
    public void hidePost(String stringIds) {
        String stringIdArr[] = stringIds.split(",");
        Integer integerIdArr[] = new Integer[stringIdArr.length];
        Integer integerId;

        //String类型的id数组转成integer类型
        for (int i = 0; i < stringIdArr.length; i++) {
            integerId = Integer.valueOf(stringIdArr[i]);
            integerIdArr[i] = integerId;

            Post hiddenPost = new Post();
            hiddenPost.setPostId(integerId);
        }

        contentMapper.hidePost(integerIdArr);
    }

    //后台隐藏文章，借助redis
    public void hidePostWithRedis(String stringIds) {
        String stringIdArr[] = stringIds.split(",");
        Integer integerIdArr[] = new Integer[stringIdArr.length];

        for (String stringId : stringIdArr) {
            Post hiddenPost = new Post();
            hiddenPost.setPostId(Integer.valueOf(stringId));

            String PostKey = PrefixKey.POST_QUERY_PREFIX + hiddenPost.getPostId();
            String lock = PrefixKey.POST_UPDATE_PREFIX + hiddenPost.getPostId() + ".lock";
            // 判断缓存是否存在数据
            if (jedis.exists(PostKey)) {
                jedis.del(PostKey);
            }
            // 判断缓存中是否有锁
            if (jedis.exists(lock))
                jedis.del(lock);
        }

        //String类型的id数组转成Integer类型
        for (int i = 0; i < stringIdArr.length; i++) {
            integerIdArr[i] = Integer.valueOf(stringIdArr[i]);
        }
        contentMapper.hidePost(integerIdArr);
    }

    //后台显示文章
    public void showPost(String stringIds) {
        String stringIdArr[] = stringIds.split(",");
        Integer integerIdArr[] = new Integer[stringIdArr.length];

        //String类型的id数组转成Integer类型
        for (int i = 0; i < stringIdArr.length; i++) {
            integerIdArr[i] = Integer.valueOf(stringIdArr[i]);
        }

        contentMapper.showPost(integerIdArr);
    }

    //-----文章阅读量、热度值相关-----
    //增加文章阅读量
    public void addPostView(Integer postId) {
        contentMapper.addPostView(postId);
    }

    //直接对热度值进行增加
    public void updatePostHot(Integer postId, Integer addedValue) {
        contentMapper.addPostHot(postId, addedValue);
    }

    //按算式重新计算热度值
    public void updatePostHot(Integer postId) {
        Post Post = contentMapper.queryPostById(postId);
        Integer newValue = Post.getPostView()
                + Post.getPostLike() * 5
                + Post.getPostFavorite() * 10
                + Post.getPostRepost() * 20;
        contentMapper.resetPostHot(postId, newValue);
    }

}
