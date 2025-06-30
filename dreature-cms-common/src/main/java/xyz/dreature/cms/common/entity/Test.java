package xyz.dreature.cms.common.entity;

public class Test {
    // 根据驼峰命名定义属性
    private Integer status;
    private String time;
    private String message;
    private String source;

    public Test() {

    }

    public Test(Test test) {
        this.status = test.getStatus();
        this.time = test.getTime();
        this.message = test.getMessage();
        this.source = test.getSource();

    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    // getter setter
    @Override
    public String toString() {
        return
                "test [status=" + status
                        + ", time=" + time
                        + ", message=" + message
                        + ", source=" + source
                        + "]";

    }
}
