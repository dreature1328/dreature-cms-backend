package xyz.dreature.cms.common.entity;

import java.io.Serializable;

public class Like implements Serializable {
    private Integer likeId;
    private Integer likeLikerId;
    private Integer likePostId;
    private String likeDate;

    public Like() {

    }

    public Like(Like like) {
        this.likeId = like.getLikeId();
        this.likeLikerId = like.getLikeLikerId();
        this.likePostId = like.getLikePostId();
        this.likeDate = like.getLikeDate();

    }

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Integer getLikeLikerId() {
        return likeLikerId;
    }

    public void setLikeLikerId(Integer likeLikerId) {
        this.likeLikerId = likeLikerId;
    }

    public Integer getLikePostId() {
        return likePostId;
    }

    public void setLikePostId(Integer likePostId) {
        this.likePostId = likePostId;
    }

    public String getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(String likeDate) {
        this.likeDate = likeDate;
    }


    /*
     * id INT(11) (NULL) NO PRI (NULL) AUTO_INCREMENT
     * SELECT,INSERT,UPDATE,REFERENCES user_id VARCHAR(100) utf8_general_ci YES
     * (NULL) SELECT,INSERT,UPDATE,REFERENCES product_id VARCHAR(100)
     * utf8_general_ci YES (NULL) SELECT,INSERT,UPDATE,REFERENCES product_image
     * VARCHAR(500) utf8_general_ci YES (NULL) SELECT,INSERT,UPDATE,REFERENCES
     * product_name VARCHAR(100) utf8_general_ci YES (NULL)
     * SELECT,INSERT,UPDATE,REFERENCES product_price DOUBLE (NULL) YES (NULL)
     * SELECT,INSERT,UPDATE,REFERENCES num INT(11) (NULL) YES (NULL)
     * SELECT,INSERT,UPDATE,REFERENCES
     *
     */

    @Override
    public String toString() {
        return "Like{wwwwwwwwwwwwwwwwwwwwwwwwwww" +
                "likeId=" + likeId +
                ", likeLikerId=" + likeLikerId +
                ", likePostId=" + likePostId +
                ", likeDate='" + likeDate + '\'' +
                '}';
    }
}
