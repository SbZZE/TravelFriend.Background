package com.sbzze.travelfriend.model;

import lombok.Data;

/**
 * 响应模型
 *
 * @author TJ
 */

public class ResultViewModel {

    @Data
    public static class loginModel {
        private Integer code;
        private String message;
        private String token;
    }

    @Data
    public static class registerModel {
        private Integer code;
        private String message;
    }
}
