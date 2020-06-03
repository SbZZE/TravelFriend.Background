package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.TeamDto;
import com.sbzze.travelfriend.entity.Team;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.TeamMapper;
import com.sbzze.travelfriend.service.TeamService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:15:29
 */
@Service
public class TeamServiceImpl extends BaseServiceImpl<TeamMapper, Team> implements TeamService {
    @Autowired
    UserService userService;
    //创建团队
    @Override
    public int insertTeam(String username, String teamname, String introduction){
        Team team = new Team();
        User user = userService.findUserByName(username);
        team.setId(UUIDUtil.getUUID());
        team.setUserId(user.getId());
        team.setTeamName(teamname);
        team.setIntroduction(introduction);
        return baseMapper.insert(team);
    }
    //查
    @Override
    public List<Team> findTeamByUserId(String userId){
        return baseMapper.findTeamByUserId(userId);
    }
    @Override
    public Team findTeamByTeamId(String id){
        return baseMapper.findTeamById(id);
    }
    @Override
    public byte[] getTeamAvatar(String teamid){
        Team team = baseMapper.findTeamById(teamid);
        if (null == team){
            return null;
        }
        String avatarUrl = team.getAvatar();
        byte[] fileBytes = FileUtil.downloadFileBytes(avatarUrl);
        return fileBytes;
    }

    //获取某用户所有的团队信息
    @Override
    public List<Object> getTeamInfo(String userid){
        List<Team> teams = findTeamByUserId(userid);
        TeamDto.TeamInfoWithOutAvatarDto teamInfoWithOutAvatarDto = new TeamDto.TeamInfoWithOutAvatarDto();
        List<Object> teamInfoWithOutAvatarDtos = new ArrayList<>();
        for (Team team : teams){
            teamInfoWithOutAvatarDto.setTeamid(team.getId());
            teamInfoWithOutAvatarDto.setTeamname(team.getTeamName());
            teamInfoWithOutAvatarDto.setIntroduction(team.getIntroduction());
            teamInfoWithOutAvatarDto.setIsleader(true);
        }
        return teamInfoWithOutAvatarDtos;

    }

    //修改团队信息不包括头像
    @Override
    public int updateTeamInfoWithOutAvatar(TeamDto.TeamInfoWithOutAvatarDto updateDto){
        Team team = baseMapper.findTeamById(updateDto.getTeamid());
        team.setTeamName(updateDto.getTeamname());
        team.setIntroduction(updateDto.getIntroduction());
        return baseMapper.updateById(team);
    }

    //修改团队头像
    public int updateTeamAvatar(String teamid, MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        String changedFileName = FileNameUtil.getFileName(originalFileName);
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, AVATAR, teamid);
        if ( !FileUtil.uploadByDeleteExistFile(filePath, file, changedFileName) ) {
            return -1;
        }

        //压缩图片并上传
        if ( !FileUtil.compressFile(file, filePath, changedFileName, PREFIX, 1f, 0.2f) ) {
            return -1;
        }
        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, AVATAR, teamid);
        String insertFileName = urlPath + changedFileName;
        String insertCompressFileName = urlPath + PREFIX + changedFileName;
        Team team = baseMapper.findTeamById(teamid);
        team.setAvatar(insertFileName);
        team.setCompressAvatar(insertCompressFileName);
        return baseMapper.updateById(team);
    }

    //获取某团队所有成员
    public List<Team> getTeamMember(String teamid){
        Team team = baseMapper.findTeamById(teamid);
        TeamDto.TeamMemberInfoDto teamMemberInfoDto = new TeamDto.TeamMemberInfoDto();
        char members[] = team.getMember();
        List<Team> teamMemberInfoDtos = new ArrayList<>();
        for (int i = members.length ; i>=0 ; i--){
            teamMemberInfoDto.setUsername(teamMemberInfoDto.getUsername());
            teamMemberInfoDto.setNickname(teamMemberInfoDto.getNickname());
            teamMemberInfoDto.setIsleader(false);
        }
        return teamMemberInfoDtos;


    }

    }

