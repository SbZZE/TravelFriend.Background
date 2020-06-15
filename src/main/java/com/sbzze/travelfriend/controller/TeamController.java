package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.TeamDto;
import com.sbzze.travelfriend.entity.Team;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.service.TeamMemberService;
import com.sbzze.travelfriend.service.TeamService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.service.UserTokenService;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.TeamResultViewModelUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:团队功能
 * @Date:2020/6/2
 * @Time:13:48
 */

@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private String teams[] = null;

    //获取该用户的所有团队
    @UserLoginToken
    @GetMapping("/teams")
    public Object teams(String username){
        User userForBase = userService.findUserByName(username);
        List<Object> teamForBase = teamService.getTeamInfo(userForBase.getId());
        if (null == teamForBase){
            return TeamResultViewModelUtil.getTeamInfoError(null);
        }else {
            return TeamResultViewModelUtil.getTeamInfoSuccess(teamForBase);
        }

    }

    //获取团队头像
    @UserLoginToken
    @GetMapping("/avatar")
    @ResponseBody
    public byte[] getTeamAvatar(String teamid , String isCompress){
        if (isCompress.equals("true")) {
            return teamService.getCompressTeamAvatar(teamid);
        } else if (isCompress.equals("false")){
            return teamService.getTeamAvatar(teamid);
        } else {
            return null;
        }
    }

    //创建团队
    @UserLoginToken
    @PostMapping("/create")
    public Object createTeam(@RequestBody TeamDto.TeamCreateDto teamCreateDto){
        User userForBase = userService.findUserByName(teamCreateDto.getUsername());
        List<Team> teamForBase = teamService.findTeamByUserId(userForBase.getId());
        teams = new String[teamForBase.size()];

        for(int i = 0 ; i<teamForBase.size() ; i++){
            teams[i] = teamForBase.get(i).getTeamName();
            if (teams[i].equals(teamCreateDto.getTeamname())){
                return TeamResultViewModelUtil.CreateErrorByExit();

            }
        }
        int flag = teamService.insertTeam(teamCreateDto.getUsername() , teamCreateDto.getTeamname() , teamCreateDto.getIntroduction());
        if (flag <= 0){
            return TeamResultViewModelUtil.CreateErrorByExit();
        }else {

            return TeamResultViewModelUtil.CreateSuccess();
        }

    }

    //修改团队信息不包括头像
    @UserLoginToken
    @PostMapping("/teaminfo")
    public Object updateTeamInfoWithOutAvatar(@RequestBody TeamDto.TeamInfoWithOutAvatarDto teamInfoWithOutAvatarDto){
        Team teamForBase = teamService.findTeamByTeamId(teamInfoWithOutAvatarDto.getTeamid());
        if (null == teamForBase){
            return TeamResultViewModelUtil.updateTeamInfoError();
        }
        int flag = teamService.updateTeamInfoWithOutAvatar(teamInfoWithOutAvatarDto);
        if (flag <= 0){
            return TeamResultViewModelUtil.updateTeamInfoError();
        }else {
//            String content = "changed";
//            rabbitTemplate.convertAndSend("fanoutExchange", null, MsgUtil.setMsg(teamInfoWithOutAvatarDto.getTeamname(), MsgUtil.Type.INFO, content));
            return TeamResultViewModelUtil.updateTeamInfoSuccess();
        }

    }

    //上传团队头像
    @UserLoginToken
    @PostMapping("/avatar")
    public Object updateTeamAvatar(@RequestParam String teamid , @RequestParam MultipartFile avatar){
        if ( avatar.isEmpty() ) {
            return TeamResultViewModelUtil.updateTeamAvatarError();
        }

        if ( !FileUtil.isImage(avatar) ) {
            return TeamResultViewModelUtil.updateTeamAvatarError();
        }
        Team teamForbase = teamService.findTeamByTeamId(teamid);
        if (null == teamForbase){
            return TeamResultViewModelUtil.updateTeamAvatarError();
        }
        int flag = teamService.updateTeamAvatar(teamid , avatar);
        if (flag<=0){
            return TeamResultViewModelUtil.updateTeamAvatarError();
        }else {
//            String content = "changed";
//            rabbitTemplate.convertAndSend("fanoutExchange", null, MsgUtil.setMsg(teamid, MsgUtil.Type.AVATAR, content));
            return TeamResultViewModelUtil.updateTeamAvatarSuccess();
        }

    }

    //获取团队成员
    @UserLoginToken
    @GetMapping("/member")
    public Object getTeamMember(String teamid){


        return TeamResultViewModelUtil.getTeamMemberSuccess(teamService.getTeamMember(teamid));
    }

}
