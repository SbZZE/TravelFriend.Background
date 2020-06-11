package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.entity.TeamMember;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/10
 * @Time:17:33
 */
public interface TeamMemberService extends IService<TeamMember> {
    List<TeamMember> findMemberByTeamId(String teamid);

    List<TeamMember> findMemberByMembername(String membername);

    TeamMember finMemberByMemberNameAndTeamId(String membername , String teamid);

    int addTeamMember(String teamid , String username);

    int deleteUser(String teamid , String username);

    int addTeamLeader(String teamid , String username);

}
