package xyz.dreature.cms.common.vo;

import xyz.dreature.cms.common.entity.Post;

import java.util.List;

/**
 * @author admin
 */
public class Page {
    // 总页数
    private Integer totalPage;
    // 当前页数
    private Integer currentPage;
    // 查询分页结果
    private List<Post> Posts;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Post> getPosts() {
        return Posts;
    }

    public void setPosts(List<Post> Posts) {
        this.Posts = Posts;
    }

}
