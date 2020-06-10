package com.sbzze.travelfriend.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.TeamMember;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/10
 * @Time:17:22
 */
public interface TeamMemberMapper extends BaseMapper<TeamMember> {
    List<TeamMember> findMemberByTeamId(@Param("teamid") String teamid);
    List<TeamMember> findMemberByMembername(@Param("membername") String membername);
    TeamMember finMemberByMemberNameAndTeamId(@Param("membername") String membername , @Param("teamid") String teamid);
}
