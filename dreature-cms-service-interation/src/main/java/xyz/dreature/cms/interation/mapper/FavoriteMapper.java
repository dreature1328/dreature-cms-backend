package xyz.dreature.cms.interation.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.dreature.cms.common.entity.Favorite;
import xyz.dreature.cms.common.entity.Post;

import java.util.List;

public interface FavoriteMapper {

    public Integer queryTotalOfFavoriteOfUser(Integer userId);

    public List<Post> queryFavoriteByUserIdAndPage(@Param("userId") Integer userId, @Param("start") Integer start, @Param("length") Integer length);

    public Favorite queryFavoriteByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);

    public void addFavorite(Favorite favorite);

    public void cancelFavorite(Favorite favorite);

    public void addPostHot(@Param("postId") Integer postId,
                           @Param("addedValue") Integer addedValue);

    public List<Post> showFavoriteChart();
}
