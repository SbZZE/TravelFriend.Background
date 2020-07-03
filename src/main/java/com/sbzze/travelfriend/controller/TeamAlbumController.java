package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.entity.TeamAlbum;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.service.TeamAlbumService;
import com.sbzze.travelfriend.service.TeamService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.service.UserTokenService;
import com.sbzze.travelfriend.util.TeamResultViewModelUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:16:29
 */
@RestController
@RequestMapping("/api/team/album")
public class TeamAlbumController {

    @Autowired
    private TeamAlbumService teamAlbumService;

    @Autowired
    RabbitTemplate rabbitTemplate;


    //获取某团队所有相册
    @UserLoginToken
    @GetMapping
    public Object getTeamAlbum(String teamid){
        return TeamResultViewModelUtil.getTeamAlbumInfoSuccess(teamAlbumService.getAlbumInfo(teamid));

    }

    //新建团队相册
    @UserLoginToken
    @PostMapping
    public Object addTeamAlbum(@RequestParam String teamid , @RequestParam String albumname , @RequestParam String introduction , @RequestParam MultipartFile cover){

        if (teamAlbumService.isAlbumExist(teamid , albumname)){
            return TeamResultViewModelUtil.addTeamAlbumErrorByExit();
        }
        if(teamAlbumService.addTeamAlbum(teamid , albumname , introduction , cover) <= 0){
            return TeamResultViewModelUtil.addTeamAlbumError();
        }else {
            TeamAlbum teamAlbum = teamAlbumService.findTeamAlbumByTeamIdAndAlbumName(teamid , albumname);
            return TeamResultViewModelUtil.addTeamAlbumSuccess(teamAlbum.getId());
        }

    }


}
