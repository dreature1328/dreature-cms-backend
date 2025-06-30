package xyz.dreature.cms.comment.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.dreature.cms.common.entity.Comment;

import java.util.List;

public interface CommentMapper {

    List<Comment> queryCommentByPostId(Integer postId);

    void addComment(Comment comment);

    void hideComment(String[] comment_ids);

    void showComment(String[] comment_ids);

    Integer queryTotal();

    List<Comment> queryAllComments(@Param("start") Integer start, @Param("rows") Integer rows);

}
