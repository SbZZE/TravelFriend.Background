//package com.sbzze.travelfriend.serviceImpl;
//
//import com.baomidou.mybatisplus.mapper.BaseMapper;
//import com.sbzze.travelfriend.entity.Team;
//import com.sbzze.travelfriend.entity.TeamAlbum;
//import com.sbzze.travelfriend.entity.User;
//import com.sbzze.travelfriend.entity.UserAlbum;
//import com.sbzze.travelfriend.mapper.TeamAlbumMapper;
//import com.sbzze.travelfriend.service.TeamAlbumService;
//import com.sbzze.travelfriend.service.TeamService;
//import com.sbzze.travelfriend.util.FileNameUtil;
//import com.sbzze.travelfriend.util.FileUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
///**
// * @Author:Zzq
// * @Description:
// * @Date:2020/6/3
// * @Time:16:39
// */
//@Service
//public class TeamAlbumServiceImpl extends BaseMapper<TeamAlbumMapper , TeamAlbum> implements TeamAlbumService {
//    @Autowired
//    TeamService teamService;
//
//    //新建团队相册
//    @Override
//    public int addTeamAlbum(String teamname , String albumname , MultipartFile cover){
//        String originalFileName = cover.getOriginalFilename();
//        String changedFileName = FileNameUtil.getFileName(originalFileName);
//        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, ALBUM + "/" + COVER, teamname);
//
//        if ( !FileUtil.compressFile(cover, filePath, changedFileName, PREFIX, 0.5f, 0.5f) ) {
//            //TODO 状态码修改
//            return -1;
//        }
//
//    }
//
//    //获取团队所有相册信息
//    @Override
//    public List<Object> getAlbumInfo(String teamid){
//
//    }
//
//    //相册是否已经存在
//    @Override
//    public boolean isAlbumExist(String teamid, String albumname){
//        System.out.println(teamid);
//        Team team = teamService.findTeamByTeamId(teamid);
//        System.out.println(team);
//        TeamAlbum teamalbum = findTeamAlbumByTeamIdAndAlbumName(team.getId(), albumname);
//        if ( null == teamalbum ) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }
//}
