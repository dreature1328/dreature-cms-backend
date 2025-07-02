package xyz.dreature.cms.content.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.dreature.cms.common.entity.Category;
import xyz.dreature.cms.common.entity.Post;
import xyz.dreature.cms.common.entity.PostBrief;

import java.util.List;

public interface ContentMapper {

    //=====数量查询=====
    public Integer queryTotalOfPost();

    public Integer queryTotalOfCategory();

    public Integer queryPostBriefTotalByPostId(Integer postId);

    public Integer queryTotalOfPostInCategory(Integer categoryId);

    public Integer queryTotalOfSearchResultByQ(String q);

    //=====Post类、PostBrief类和Category类查询=====
    public Post queryPostById(Integer postId);

    // 交给sql语句的参数一般只有一个，两个还能否使用#{}
    // 多个参数传递给SQLSession根据映射文件#{}拼接数据时，可以用@Param注解定义参数变量名称
    public List<Post> queryPostByPage(@Param("start") Integer start, @Param("length") Integer length);

    public List<Post> listHotRankByPage(@Param("start") Integer start, @Param("length") Integer length);

    public List<Post> listHotRankByCategoryIdAndPage(@Param("categoryId") Integer categoryId, @Param("start") Integer start, @Param("length") Integer length);

    public List<PostBrief> queryPostBriefByPostId(Integer postId);

    public List<Category> queryAllCategory();

    public List<Post> searchPostByQAndPage(@Param("q") String q, @Param("start") Integer start, @Param("length") Integer length);

    //=====文章阅读量、热度值计算=====
    public void addPostView(Integer postId);

    public void addPostHot(@Param("postId") Integer postId,
                           @Param("addedValue") Integer addedValue);

    public void resetPostHot(@Param("postId") Integer postId,
                             @Param("newValue") Integer newValue);

    //=====后台文章管理=====
    public void postPost(Post post);

    public void updatePost(Post post);

    public void deletePost(Integer[] postIdArr);

    public void hidePost(Integer[] postIdArr);

    public void showPost(Integer[] postIdArr);
}
