package com.sbzze.travelfriend.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:15:33
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    List<Team> findTeamByUserId(@Param("userId") String userId);
    Team findTeamById(@Param("id") String id);
}
