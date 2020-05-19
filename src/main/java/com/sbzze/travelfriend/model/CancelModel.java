package com.sbzze.travelfriend.model;

import lombok.Data;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/5/20
 * @Time:0:21
 */
public class CancelModel {

    @Data
    public static class normalModel {
        private Integer code;
        private String message;
    }

}
