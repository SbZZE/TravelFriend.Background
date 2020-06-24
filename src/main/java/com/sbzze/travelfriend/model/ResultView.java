package com.sbzze.travelfriend.model;

import lombok.Data;

@Data
public class ResultView<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultView( Integer code, String msg ) {
        this.code = code;
        this.message = msg;
        this.data = null;
    }

    public ResultView( Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

}
