package com.example.yourtask.model;

import com.google.gson.annotations.SerializedName;

public class RequestResult {
    @SerializedName("code")
    public int code;
    @SerializedName("id")
    public int id;
    @SerializedName("message")
    public String message;

    public RequestResult(int code, int id, String message) {
        this.code = code;
        this.id = id;
        this.message = message;
    }
}
