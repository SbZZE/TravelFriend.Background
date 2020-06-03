//package com.sbzze.travelfriend.controller;
//
//import com.sbzze.travelfriend.filter.UserLoginToken;
//import com.sbzze.travelfriend.service.TeamService;
//import com.sbzze.travelfriend.service.UserService;
//import com.sbzze.travelfriend.service.UserTokenService;
//import com.sbzze.travelfriend.util.TeamResultViewModelUtil;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * @Author:Zzq
// * @Description:
// * @Date:2020/6/2
// * @Time:16:29
// */
//@RestController
//@RequestMapping("/api/team")
//public class TeamAlbumController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TeamService teamService;
//
//    @Autowired
//    private UserTokenService userTokenService;
//
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//
//    //获取某团队所有相册
//    @UserLoginToken
//    @GetMapping("/album")
//    public Object getTeamAlbum(String teamid){
//        return TeamResultViewModelUtil.getTeamAlbumInfoSuccess(teamService.findTeamByTeamId(teamid));
//
//    }
//
//    //新建团队相册
//    @UserLoginToken
//    @PostMapping("/album")
//    public Object addTeamAlbum(@RequestParam String teamid , @RequestParam String albumname, @RequestParam MultipartFile cover){
//
//    }
//
//}
