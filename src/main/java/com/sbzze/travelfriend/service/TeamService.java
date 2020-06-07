package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.dto.TeamDto;
import com.sbzze.travelfriend.entity.Team;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:14:20
 */
public interface TeamService extends IService<Team> {
    int insertTeam(String username,String teamname,String introduction); //创建团队

    List<Team> findTeamByUserId(String userid);//查找某用户的团队

    Team findTeamByTeamId(String id);

    //List<User> findUserByTeamId(String teamid); //查找某团队所有的用户

    List<Object> getTeamInfo(String userid);//获取某用户所有的团队信息

    int updateTeamInfoWithOutAvatar(TeamDto.TeamInfoWithOutAvatarDto updateDto);//修改团队信息不包括头像

    int updateTeamAvatar(String teamid,MultipartFile file);//修改团队头像

    byte[] getTeamAvatar(String teamid);//获取团队头像

    byte[] getCompressTeamAvatar(String teamid);//获取团头像缩略图

    List<Object> getTeamMember(String teamid);//获取团队成员


}
