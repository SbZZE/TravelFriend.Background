package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.entity.TeamMember;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.TeamMemberMapper;
import com.sbzze.travelfriend.service.TeamMemberService;
import com.sbzze.travelfriend.service.TeamService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/10
 * @Time:17:42
 */
@Service
public class TeamMemberServiceImpl extends BaseServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {
    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;

    //查
    @Override
    public List<TeamMember> findMemberByTeamId(String teamid){
        return baseMapper.findMemberByTeamId(teamid);
    }

    @Override
    public List<TeamMember> findMemberByMemberName(String membername){
        return baseMapper.findMemberByMemberName(membername);
    }

    @Override
    public TeamMember findMemberByMemberNameAndTeamId(String membername , String teamid){
        return baseMapper.findMemberByMemberNameAndTeamId(membername , teamid);
    }
    public List<TeamMember> findMemberByMemberNameNotLeader(String membername){
        return baseMapper.findMemberByMemberNameNotLeader(membername);
    }

    //新增团队成员
    public int addTeamMember(String teamid , String username){
        User user = userService.findUserByName(username);
        TeamMember teamMember = new TeamMember();
        teamMember.setId(UUIDUtil.getUUID());
        teamMember.setTeamid(teamid);
        teamMember.setMembername(username);
        teamMember.setMembernickname(user.getNickname());
        teamMember.setIsleader(false);

        return baseMapper.insert(teamMember);
    }
    //默认创建团队的用户为队长
    public int addTeamLeader(String teamid , String username){
        User user = userService.findUserByName(username);
        TeamMember teamMember = new TeamMember();
        teamMember.setId(UUIDUtil.getUUID());
        teamMember.setTeamid(teamid);
        teamMember.setMembername(username);
        teamMember.setMembernickname(user.getNickname());
        teamMember.setIsleader(true);

        return baseMapper.insert(teamMember);
    }

    //删除团队成员
    public int deleteUser(String teamid , String username){
        TeamMember teamMember = baseMapper.findMemberByMemberNameAndTeamId(username , teamid);

        return baseMapper.deleteById(teamMember.getId());
    }

}
