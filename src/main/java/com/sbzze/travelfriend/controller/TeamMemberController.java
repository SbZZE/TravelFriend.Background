package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.entity.TeamMember;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.service.TeamMemberService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.service.UserTokenService;
import com.sbzze.travelfriend.util.CancelModelUtil;
import com.sbzze.travelfriend.util.TeamResultViewModelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/10
 * @Time:22:20
 */

@RestController
@RequestMapping("/api/team/member")
public class TeamMemberController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private TeamMemberService teamMemberService;

    //邀请团队成员
    @ApiOperation("邀请团队成员")
    @UserLoginToken
    @PostMapping
    public Object addTeamMember(String teamid , String username){
        TeamMember teamMemberForBase = teamMemberService.findMemberByMemberNameAndTeamId(username , teamid);
        if (null != teamMemberForBase ){
            return TeamResultViewModelUtil.addTeamMemberErrorByExit();
        }
        User userForBase = userService.findUserByName(username);
        if (null == userForBase){
            return TeamResultViewModelUtil.addTeamMemberErrorByNotExit();
        }
        int flag = teamMemberService.addTeamMember(teamid , username);
        if (flag <= 0){
            return TeamResultViewModelUtil.addTeamMemberError();
        }else {
            return TeamResultViewModelUtil.addTeamMemberSuccess();
        }
    }

    //踢出团队成员
    @ApiOperation("踢出团队成员")
    @UserLoginToken
    @DeleteMapping
    public Object deleteUser(String teamid , String username){
        TeamMember teamMemberForBase = teamMemberService.findMemberByMemberNameAndTeamId(username , teamid);
        if (null == teamMemberForBase){
            return CancelModelUtil.cancelMemberErrorByNotExist();
        }
        int flag = teamMemberService.deleteUser(teamid , username);
        if (flag <= 0){
            return CancelModelUtil.cancelMemberErrorByDelete();
        }else {
            return CancelModelUtil.cancelMemberSuccess();
        }

    }


}
