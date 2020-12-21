package com.xp.test.common.returnValue;

import java.util.Map;

/**
 * 用做接口请求响应结果
 * 
 * @author qguan
 *
 */
public class Result {

    public int status;
    public String response;
    public String message;
    public Map<String,Object> value;

    public Result() {
        // TODO Auto-generated constructor stub
        super();
    }

    public Result(int status, String message) {
        super();
        this.status = status;
        this.message = message;

    }

    public Result(int status, String message,Map<String,Object> map) {
        super();
        this.status = status;
        this.message = message;
        this.value=map;

    }

    public Result(int status, String message, String response) {
        super();
        this.status = status;
        this.message = message;
        this.response = response;

    }
}
