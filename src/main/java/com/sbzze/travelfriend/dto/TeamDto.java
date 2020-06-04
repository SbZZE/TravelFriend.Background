package com.sbzze.travelfriend.dto;

import lombok.Data;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:14:05
 */
public class TeamDto {

    @Data
    public static class TeamCreateDto{
        private String username;
        private String teamname;
        private String introduction;
        private boolean isleader;
    }
    @Data
    public static class TeamInfoWithOutAvatarDto{
        private String teamid;
        private String teamname;
        private String introduction;
        private boolean isleader;
    }
    @Data
    public static class TeamMemberInfoDto{
        private String username;
        private String nickname;
        private boolean isleader;
    }

}
