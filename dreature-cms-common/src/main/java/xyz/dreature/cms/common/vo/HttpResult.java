package xyz.dreature.cms.common.vo;

public class HttpResult {

    // 响应码
    private Integer code;
    private String msg;
    private Object data;

    public HttpResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult success() {
        return success("", null);
    }

    public static HttpResult success(Object data) {
        return success("", data);
    }

    public static HttpResult success(String msg, Object data) {
        Integer code = 20000;
        return new HttpResult(code, msg, data);
    }

    public static HttpResult fail() {
        return fail("");
    }

    public static HttpResult fail(String msg) {
        Integer code = 50000;
        return new HttpResult(code, msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
