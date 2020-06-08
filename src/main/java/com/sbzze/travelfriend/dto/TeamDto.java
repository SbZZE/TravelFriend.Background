package com.sbzze.travelfriend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    @Data
    public static class TeamAlbumDto {
        private String teamname;
        private String albumname;
        private String introduction;
        private MultipartFile cover;
    }

    @Data
    public static class TeamAlbumInfoDto {
        private String albumid;
        private String albumname;
        private String count;
    }

}
