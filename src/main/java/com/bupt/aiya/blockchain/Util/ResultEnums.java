package com.bupt.aiya.blockchain.Util;

/**
 * Created by aiya on 2019/8/25 下午10:07
 */
public enum  ResultEnums {
    SUCCESS(0, "成功"),
    ERROR(-1, "失败"),
    RESULT_IS_NULL(-2, "结果为空"),
    INSERT_FAIL(-3, "插入失败");
    private  Integer code;
    private String msg;

    ResultEnums(){

    }
    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
