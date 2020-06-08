package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.entity.TeamAlbum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/3
 * @Time:16:33
 */
public interface TeamAlbumService extends IService<TeamAlbum> {
    int addTeamAlbum(String teamid , String albumname , String introduction , MultipartFile cover);

    List<Object> getAlbumInfo(String teamid);

    boolean isAlbumExist(String teamid, String albumname);

    TeamAlbum findTeamAlbumByTeamIdAndAlbumName(String teamid , String albumname);

    List<TeamAlbum> findTeamAlbumByTeamId(String teamId);

    TeamAlbum findTeamAlbumByAlbumId(String albumid);

    byte[] getTeamAlbumCover(String albumid);

    byte[] getCompressTeamAlbumCover(String albumid);
}
