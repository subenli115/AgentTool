package com.moxi.agenttool.entity;


/**
 * @Package: com.benwunet.sign.ui.bean
 * @ClassName: codeResult
 * @Description: String返回格式
 * @Author: feng wen jun
 * @Since: 2020/10/20 0020 18:05
 * @Version: 1.0
 */


public class StringDataBean {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
    private String data;
    private String msg;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                '}';
    }
}
