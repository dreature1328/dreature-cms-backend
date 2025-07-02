package xyz.dreature.cms.interaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.dreature.cms.common.entity.Like;
import xyz.dreature.cms.common.entity.Post;
import xyz.dreature.cms.common.vo.TableResult;
import xyz.dreature.cms.interaction.mapper.LikeMapper;

import java.util.List;

@Service
public class LikeService extends BaseInteractionService {
    @Autowired
    private LikeMapper likeMapper;

//	//通过postID获取对应的收藏数
//	public int getLikeNumByPostId(String postId) {
//		return likeMapper.getLikeNumByPostId(postId);
//	}

    //查询某用户的收藏文章总数
    public Integer queryTotalOfLikeOfUser(Integer userId) {
        return likeMapper.queryTotalOfLikeOfUser(userId);
    }

    public boolean checkIfLiked(Integer userId, Integer postId) {
        Like exist = likeMapper.queryLikeByUserIdAndPostId(userId, postId);    //判断是否已收藏该文章
        if (exist != null) {
            return true;
        }
        return false;
    }

    //按页数查询某用户的收藏文章
    public TableResult queryLikeByUserIdAndPage(Integer userId, Integer page, Integer length) {

        TableResult result = new TableResult();
        Integer total = likeMapper.queryTotalOfLikeOfUser(userId);
        Integer start = (page - 1) * length;
//
        List<Post> pList = likeMapper.queryLikeByUserIdAndPage(userId, start, length);
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    public void addLike(Integer userId, Integer postId) {
        if (checkIfLiked(userId, postId)) {
//			exist.setNum(exist.getNum()+like.getNum());
//			likeMapper.updateNum(exist);
            System.out.println("点赞已存在");
        } else {
            Like like = new Like();
            like.setLikeLikerId(userId);
            like.setLikePostId(postId);
            //不存在数据，新增前要补充完整所有信息
            //发起响商品中心调用服务的代码
//			Post post=restTemplate.getForObject("http://productservice/product/admin/item/"
//					+like.getLikePostId(),Post.class);
//			like.setPostPrice(post.getPostLikenumc());
//			like.setPostName(post.getPostName());
//			like.setPostImage(post.getPostImgurl());
            likeMapper.addLike(like);
//			likeMapper.addlike(like.getLikePostId());
        }
    }

    //
////	public void updateNum(Like like) {
////		likeMapper.updateNum(like);
////	}
//
    public void cancelLike(Integer userId, Integer postId) {
        if (!checkIfLiked(userId, postId)) {
//			exist.setNum(exist.getNum()+like.getNum());
//			likeMapper.updateNum(exist);
            System.out.println("点赞不存在");
        } else {
            Like like = new Like();
            like.setLikeLikerId(userId);
            like.setLikePostId(postId);
            likeMapper.cancelLike(like);
        }
    }

    //增加热度值
    public void updatePostHot(Integer postId, Integer addedValue) {
        likeMapper.addPostHot(postId, addedValue);
    }

    public String showLikeChart() {
        List<Post> posts = likeMapper.showLikeChart();
        StringBuffer name = new StringBuffer("");
        StringBuffer num = new StringBuffer("");
        StringBuffer fdata = new StringBuffer("");

        for (Post post : posts) {
            name.append(post.getPostTitle()).append(',');
            num.append(post.getPostLike()).append(",");
        }

        name.deleteCharAt(name.length() - 1);
        num.deleteCharAt(num.length() - 1);

        String postJson = "{\"name\":\"" + name + "\",\"num\":\"" + num + "\"}";

        return postJson;

    }

}
