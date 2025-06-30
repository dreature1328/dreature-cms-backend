package xyz.dreature.cms.common.entity;

public class Post {

    private Integer postId;
    private Integer postAuthorId;
    private String postDate;
    private String postTitle;
    private String postExcerpt;
    private String postCoverUrl;
    private String postContent;
    private Integer postView;
    private Integer postLike;
    private Integer postFavorite;
    private Integer postRepost;
    private Integer postHot;
    private Integer postCategoryId;
    private Integer postStatus;

    public Post() {

    }

    public Post(Post post) {
        this.postId = post.getPostId();
        this.postAuthorId = post.getPostAuthorId();
        this.postDate = post.getPostDate();
        this.postTitle = post.getPostTitle();
        this.postExcerpt = post.getPostExcerpt();
        this.postCoverUrl = post.getPostCoverUrl();
        this.postContent = post.getPostContent();
        this.postView = post.getPostView();
        this.postLike = post.getPostLike();
        this.postFavorite = post.getPostFavorite();
        this.postRepost = post.getPostRepost();
        this.postHot = post.getPostHot();
        this.postCategoryId = post.getPostCategoryId();
        this.postStatus = post.getPostStatus();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(Integer postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostCoverUrl() {
        return postCoverUrl;
    }

    public void setPostCoverUrl(String postCoverUrl) {
        this.postCoverUrl = postCoverUrl;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Integer getPostView() {
        return postView;
    }

    public void setPostView(Integer postView) {
        this.postView = postView;
    }

    public Integer getPostLike() {
        return postLike;
    }

    public void setPostLike(Integer postLike) {
        this.postLike = postLike;
    }

    public Integer getPostFavorite() {
        return postFavorite;
    }

    public void setPostFavorite(Integer postFavorite) {
        this.postFavorite = postFavorite;
    }

    public Integer getPostRepost() {
        return postRepost;
    }

    public void setPostRepost(Integer postRepost) {
        this.postRepost = postRepost;
    }

    public Integer getPostHot() {
        return postHot;
    }

    public void setPostHot(Integer postHot) {
        this.postHot = postHot;
    }

    public Integer getPostCategoryId() {
        return postCategoryId;
    }

    public void setPostCategoryId(Integer postCategoryId) {
        this.postCategoryId = postCategoryId;
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    // getter setter
    @Override
    public String toString() {
        return
                "post [postId=" + postId
                        + ", postAuthorId=" + postAuthorId
                        + ", postDate=" + postDate
                        + ", postTitle=" + postTitle
                        + ", postExcerpt=" + postExcerpt
                        + ", postCoverUrl=" + postCoverUrl
                        + ", postContent=" + postContent
                        + ", postView=" + postView
                        + ", postLike=" + postLike
                        + ", postFavorite=" + postFavorite
                        + ", postRepost=" + postRepost
                        + ", postHot=" + postHot
                        + ", postCategoryId=" + postCategoryId
                        + ", postStatus=" + postStatus
                        + "]";

    }
}
