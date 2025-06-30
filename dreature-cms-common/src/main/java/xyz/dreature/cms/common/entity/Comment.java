package xyz.dreature.cms.common.entity;

import java.util.List;

public class Comment {
    private Integer commentId;                //评论id
    private Integer commentCommenterId;        //评论人id
    private String commentCommenterNickname;    //评论人名称
    private String commentCommenterProfilePictureUrl;    //评论人头像url
    private Integer commentPostId;        //评论的文章id
    private Integer commentParentId;        //评论的父亲评论id，最顶级默认为0
    private Integer commentDepth;            //评论层级（深度），只有0或1
    private String commentContent;            //评论内容
    private Long commentDate;                //评论时间（时间戳）
    private Integer commentStatus;            //评论状态（显示隐藏）

    private List<Comment> childComments;    //子评论

    public Comment() {

    }

    public Comment(Integer commentCommenterId, String commentCommenterNickname, String commentCommenterProfilePictureUrl,
                   Integer commentPostId, Integer commentParentId, Integer commentDepth, String commentContent, Long commentDate,
                   Integer commentStatus) {
        super();
        this.commentCommenterId = commentCommenterId;
        this.commentCommenterNickname = commentCommenterNickname;
        this.commentCommenterProfilePictureUrl = commentCommenterProfilePictureUrl;
        this.commentPostId = commentPostId;
        this.commentParentId = commentParentId;
        this.commentDepth = commentDepth;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.commentStatus = commentStatus;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentCommenterId() {
        return commentCommenterId;
    }

    public void setCommentCommenterId(Integer commentCommenterId) {
        this.commentCommenterId = commentCommenterId;
    }

    public String getCommentCommenterNickname() {
        return commentCommenterNickname;
    }

    public void setCommentCommenterNickname(String commentCommenterNickname) {
        this.commentCommenterNickname = commentCommenterNickname;
    }

    public String getCommentCommenterProfilePictureUrl() {
        return commentCommenterProfilePictureUrl;
    }

    public void setCommentCommenterProfilePictureUrl(String commentCommenterProfilePictureUrl) {
        this.commentCommenterProfilePictureUrl = commentCommenterProfilePictureUrl;
    }

    public Integer getCommentPostId() {
        return commentPostId;
    }

    public void setCommentPostId(Integer commentPostId) {
        this.commentPostId = commentPostId;
    }

    public Integer getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Integer commentParentId) {
        this.commentParentId = commentParentId;
    }

    public Integer getCommentDepth() {
        return commentDepth;
    }

    public void setCommentDepth(Integer commentDepth) {
        this.commentDepth = commentDepth;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Long commentDate) {
        this.commentDate = commentDate;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }


}
