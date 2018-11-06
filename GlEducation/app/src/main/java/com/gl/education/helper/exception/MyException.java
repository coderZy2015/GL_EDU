package com.gl.education.helper.exception;

import com.gl.education.helper.SimpleResponse;
import com.google.gson.Gson;

/**
 * Created by zy on 2018/6/7.
 */

public class MyException extends IllegalStateException {

    private SimpleResponse errorBean;

    public MyException(String s) {
        super(s);
        errorBean = new Gson().fromJson(s, SimpleResponse.class);
    }

    public SimpleResponse getErrorBean() {
        return errorBean;
    }
}
