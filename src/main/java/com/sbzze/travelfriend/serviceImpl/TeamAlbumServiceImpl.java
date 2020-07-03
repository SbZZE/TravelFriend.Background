package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.TeamDto;
import com.sbzze.travelfriend.entity.Team;
import com.sbzze.travelfriend.entity.TeamAlbum;
import com.sbzze.travelfriend.mapper.TeamAlbumMapper;
import com.sbzze.travelfriend.service.TeamAlbumService;
import com.sbzze.travelfriend.service.TeamService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/3
 * @Time:16:39
 */
@Slf4j
@Service
public class TeamAlbumServiceImpl extends BaseServiceImpl<TeamAlbumMapper , TeamAlbum> implements TeamAlbumService {
    @Autowired
    TeamService teamService;

    @Override
    public TeamAlbum findTeamAlbumByTeamIdAndAlbumName(String teamid , String albumname){
        return baseMapper.findTeamAlbumByTeamIdAndAlbumName(teamid , albumname);
    }

    @Override
    public  List<TeamAlbum> findTeamAlbumByTeamId(String teamid){
        return baseMapper.findTeamAlbumByTeamId(teamid);
    }

    @Override
    public TeamAlbum findTeamAlbumByAlbumId(String albumid){
        return baseMapper.findTeamAlbumByAlbumId(albumid);
    }

    //新建团队相册
    @Override
    public int addTeamAlbum(String teamid , String albumname , String introduction , MultipartFile cover){
        String originalFileName = cover.getOriginalFilename();
        String changedFileName = FileNameUtil.getFileName(originalFileName);
        String signName = TEAM + "/" + ALBUM;
        String filePath = FileNameUtil.getFilePath(ROOT_PATH , SON_PATH , signName  , teamid);
        if (!FileUtil.uploadByDeleteExistFile(filePath , cover , changedFileName)){
            log.error("团队封面上传失败");
            return -1;
        }

        if (!FileUtil.compressFile(cover, filePath, changedFileName, PREFIX, 1f, 0.2f)) {
            log.error("团队压缩封面上传失败");
            return -1;
        }
        TeamAlbum album = new TeamAlbum();
        album.setId(UUIDUtil.getUUID());
        album.setTeamid(teamid);
        album.setAlbumname(albumname);
        album.setIntroduction(introduction);
        album.setCount("1");

        String urlPath = FileNameUtil.getUrlPath(POST , SON_PATH , signName , teamid + "/" + album.getId());
        String insertCompressFileName = urlPath + PREFIX + changedFileName;
        String insertFileName = urlPath + changedFileName;
        album.setCompressCover(insertCompressFileName);
        album.setCover(insertFileName);

        return baseMapper.insert(album);
    }

    //获取团队所有相册信息
    @Override
    public List<Object> getAlbumInfo(String teamid){
        List<TeamAlbum> albums = findTeamAlbumByTeamId(teamid);
        TeamDto.TeamAlbumInfoDto teamAlbumInfoDto = new TeamDto.TeamAlbumInfoDto();
        List<Object> teamAlbumInfoDtos = new ArrayList<>();
        for (TeamAlbum album : albums){
            teamAlbumInfoDto = new TeamDto.TeamAlbumInfoDto();
            teamAlbumInfoDto.setAlbumid(album.getId());
            teamAlbumInfoDto.setAlbumname(album.getAlbumname());
            teamAlbumInfoDto.setCount(album.getCount());

            teamAlbumInfoDtos.add(teamAlbumInfoDto);
        }

        return teamAlbumInfoDtos;

    }

    //相册是否已经存在
    @Override
    public boolean isAlbumExist(String teamid, String albumname){
        Team team = teamService.findTeamByTeamId(teamid);
        TeamAlbum teamalbum = findTeamAlbumByTeamIdAndAlbumName(team.getId(), albumname);
        if ( null == teamalbum ) {
            return false;
        } else {
            return true;
        }

    }

    //获取团队相册封面
    @Override
    public byte[] getTeamAlbumCover(String albumid){
      TeamAlbum teamAlbum = findTeamAlbumByAlbumId(albumid);
      if (null == teamAlbum){
          return null;
      }
      String coverUrl = teamAlbum.getCover();
      byte[] fileBytes = FileUtil.downloadFileBytes(coverUrl);
      return fileBytes;
    }

    @Override
    public byte[] getCompressTeamAlbumCover(String albumid){
        TeamAlbum teamAlbum = findTeamAlbumByAlbumId(albumid);
        if (null == teamAlbum){
            return null;
        }
        String coverUrl = teamAlbum.getCompressCover();
        byte[] fileBytes = FileUtil.downloadFileBytes(coverUrl);
        return fileBytes;
    }
}
