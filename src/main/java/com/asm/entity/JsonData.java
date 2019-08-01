package com.asm.entity;

import com.google.appengine.repackaged.com.google.gson.Gson;

public class JsonData {
    private int status;
    private Object data;
    private String message;
    private static Gson gson = new Gson();
    public JsonData(){

    }
    public JsonData(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public JsonData setStatus(int status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonData setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonData setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toJsonString() {
        return gson.toJson(this);
    }
}
