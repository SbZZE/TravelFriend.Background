package com.sbzze.travelfriend.model;

import lombok.Data;

/**
 * @Author:Zzq
 * @Description:团队响应模型
 * @Date:2020/6/3
 * @Time:14:36
 */
public class TeamResultViewModel {
    @Data
    public static class normalModel {
        private Integer code;
        private String message;
    }

    @Data
    public static class normalWithDataModel {
        private Integer code;
        private String message;
        private Object data;
    }
    @Data
    public static class noramlWithTeamsModel {
        private Integer code;
        private String message;
        private Object teams;
    }
    @Data
    public static class noramlWithMembersModel{
        private Integer code;
        private String message;
        private Object members;
    }
    @Data
    public static class noramlWithAlbumsModel{
        private Integer code;
        private String message;
        private Object albums;
    }
    @Data
    public static class noramlWithTeamAlbumIdModel{
        private Integer code;
        private String message;
        private String albumid;
    }
    @Data
    public static class noramlWithTeamAlbumCoverModel{
        private Integer code;
        private String message;
        private Object cover;
    }
}
