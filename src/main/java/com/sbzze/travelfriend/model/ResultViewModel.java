package com.sbzze.travelfriend.model;

import lombok.Data;

/**
 * 登录响应模型
 *
 * @param <T>
 */
@Data
public class ResultViewModel<T> {
    private Integer code;
    private String message;
    private T token;
}
