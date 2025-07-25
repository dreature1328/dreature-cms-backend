package xyz.dreature.cms.common.entity;

public class Category {
    private Integer categoryId;
    private String categoryName;


    public Category() {

    }

    public Category(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return
                "category [categoryId=" + categoryId
                        + ", categoryName=" + categoryName
                        + "]";

    }
}
