package com.smallc.xiwenlejian.common.core.vo;

import java.io.Serializable;

public class ResultVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private Object data;

    public ResultVO(Integer code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
