package cn.gathub.uilts;

import java.io.Serializable;

public class JsonResult implements Serializable {

    private static final long serialVersionUID = 812376774103405857L;
    private int state;//0:代表成功;1或其他值代表处理失败
    private Object data;//接收返回的数据
    private String message;//定义提示信息

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;


    public JsonResult() {
    }

    public JsonResult(int state, Throwable e) {
        this.state = state;
        data = null;
        message = e.getMessage();
    }

    public JsonResult(int state, Object data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;

    }

    public JsonResult(Throwable e) {
        state = ERROR;
        data = null;
        message = e.getMessage();
    }

    public JsonResult(Object data) {
        state = SUCCESS;
        this.data = data;
        message = "";
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JsonResult [state=" + state + ", data=" + data + ", message=" + message + "]";
    }
}







