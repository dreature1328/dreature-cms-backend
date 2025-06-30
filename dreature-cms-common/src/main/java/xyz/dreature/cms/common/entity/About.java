package xyz.dreature.cms.common.entity;

public class About {
    private Integer about_id;
    private String heading;
    private String content;
    private String btn_text;
    private String image_path1;
    private String image_path2;

    public About(
            Integer about_id,
            String heading,
            String content,
            String btn_text,
            String image_path1,
            String image_path2
    ) {
        this.about_id = about_id;
        this.heading = heading;
        this.content = content;
        this.btn_text = btn_text;
        this.image_path1 = image_path1;
        this.image_path2 = image_path2;
    }

    public About(About about) {
        this.about_id = about.getAbout_id();
        this.heading = about.getHeading();
        this.content = about.getContent();
        this.btn_text = about.getBtn_text();
        this.image_path1 = about.getImage_path1();
        this.image_path2 = about.getImage_path2();
    }

    public Integer getAbout_id() {
        return about_id;
    }

    public void setAbout_id(Integer about_id) {
        this.about_id = about_id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBtn_text() {
        return btn_text;
    }

    public void setBtn_text(String btn_text) {
        this.btn_text = btn_text;
    }

    public String getImage_path1() {
        return image_path1;
    }

    public void setImage_path1(String image_path1) {
        this.image_path1 = image_path1;
    }

    public String getImage_path2() {
        return image_path2;
    }

    public void setImage_path2(String image_path2) {
        this.image_path2 = image_path2;
    }

    @Override
    public String toString() {
        return
                "About["
                        + "about_id=" + about_id + ", "
                        + "heading=" + heading + ", "
                        + "content=" + content + ", "
                        + "btn_text=" + btn_text + ", "
                        + "image_path1=" + image_path1 + ", "
                        + "image_path2=" + image_path2
                        + "]";
    }
}
