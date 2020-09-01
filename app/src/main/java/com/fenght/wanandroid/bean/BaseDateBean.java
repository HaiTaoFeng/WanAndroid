package com.fenght.wanandroid.bean;

import java.util.List;

public class BaseDateBean {
    /**
     * data : []
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<?> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
