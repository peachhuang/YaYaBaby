package com.yaya.baby.bean;

/**
 * @author : Darcy
 * @package com.yaya.baby.bean
 * @Date 2018-11-7 18:06
 * @Description 基本数据的实体类
 */
public class ResultEntity {

    private int code;
    private String message;
    private Object datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }
}
