package xyz.dreature.cms.common.entity;

import java.io.Serializable;

public class Favorite implements Serializable {
    private Integer favoriteId;
    private Integer favoriteFavoriterId;
    private Integer favoritePostId;
    private String favoriteDate;

    public Favorite() {

    }

    public Favorite(Favorite favorite) {
        this.favoriteId = favorite.getFavoriteId();
        this.favoriteFavoriterId = favorite.getFavoriteFavoriterId();
        this.favoritePostId = favorite.getFavoritePostId();
        this.favoriteDate = favorite.getFavoriteDate();

    }

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Integer getFavoriteFavoriterId() {
        return favoriteFavoriterId;
    }

    public void setFavoriteFavoriterId(Integer favoriteFavoriterId) {
        this.favoriteFavoriterId = favoriteFavoriterId;
    }

    public Integer getFavoritePostId() {
        return favoritePostId;
    }

    public void setFavoritePostId(Integer favoritePostId) {
        this.favoritePostId = favoritePostId;
    }

    public String getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(String favoriteDate) {
        this.favoriteDate = favoriteDate;
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
}
