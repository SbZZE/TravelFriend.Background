package com.sbzze.travelfriend.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.TeamAlbum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/6/2
 * @Time:15:46
 */
@Mapper
public interface TeamAlbumMapper extends BaseMapper<TeamAlbum> {
    TeamAlbum findTeamAlbumByTeamIdAndAlbumName(@Param("teamid") String teamid ,@Param("albumname") String albumname);
    List<TeamAlbum> findTeamAlbumByTeamId(@Param("teamid") String teamid);
    TeamAlbum findTeamAlbumByAlbumId(@Param("id") String albumid);

}
