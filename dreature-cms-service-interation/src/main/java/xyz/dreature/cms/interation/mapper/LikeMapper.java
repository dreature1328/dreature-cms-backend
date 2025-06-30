package xyz.dreature.cms.interation.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.dreature.cms.common.entity.Like;
import xyz.dreature.cms.common.entity.Post;

import java.util.List;

public interface LikeMapper {

    public Integer queryTotalOfLikeOfUser(Integer userId);

    public List<Post> queryLikeByUserIdAndPage(@Param("userId") Integer userId, @Param("start") Integer start, @Param("length") Integer length);

    public Like queryLikeByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);

    public void addLike(Like like);

    public void cancelLike(Like like);

    public void addPostHot(@Param("postId") Integer postId,
                           @Param("addedValue") Integer addedValue);

    public List<Post> showLikeChart();
}
