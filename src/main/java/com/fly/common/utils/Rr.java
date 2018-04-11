package com.fly.common.utils;


import org.apache.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * @author liufei
 */
public class Rr extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Rr() {
        put("code", 0);
        put("msg", "success");
    }

    public static Rr error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常,请联系管理员");
    }

    public static Rr error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);

    }

    public static Rr error(int code) {

        //获取对应错误码的错误信息
        String msg = MessageContainer.getMsg(code);

        if (!StringUtils.isEmpty(msg)) {
            return Rr.error(code, msg);
        }
        return null;
    }

    public static Rr error(int code, String msg) {
        Rr rr = new Rr();
        rr.put("code", code);
        rr.put("msg", msg);
        return rr;

    }

    public static Rr ok() {
        return new Rr();
    }

    public static Rr ok(String msg) {
        Rr rr = new Rr();
        rr.put("msg", msg);
        return rr;
    }

    public static Rr ok(Map<String, Object> map) {
        Rr rr = new Rr();
        rr.putAll(map);
        return rr;
    }

    @Override
    public Rr put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}