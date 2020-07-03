package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.AlbumDto;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.model.ResultView;
import com.sbzze.travelfriend.service.TeamAlbumService;
import com.sbzze.travelfriend.service.UserAlbumService;
import com.sbzze.travelfriend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private UserAlbumService userAlbumService;
    @Autowired
    private TeamAlbumService teamAlbumService;

    // 获取相册封面
    @UserLoginToken
    @GetMapping("/cover")
    @ResponseBody
    public Object getAlbumCover( String albumid, String target, String width, String height ) {
        if ( Integer.valueOf(target) == Constants.USER ) {
            return new ResultView<>(200, "获取相册封面成功", userAlbumService.getAlbumCover(albumid, width, height));
        }
        if ( Integer.valueOf(target) == Constants.USER ) {
            return new ResultView<>(200, "获取相册封面成功", teamAlbumService.getCompressTeamAlbumCover(albumid, width, height));
        }
        return new ResultView<>(201,"获取相册封面失败");

    }

    // 修改相册信息
    @UserLoginToken
    @PostMapping("/info")
    public Object updateAlbumInfo( @RequestBody AlbumDto.AlbumInfoDto albumInfoDto) {
        if ( Integer.valueOf(albumInfoDto.getTarget()) == Constants.USER ) {
            userAlbumService.updateAlbumInfo(albumInfoDto.getAlbumid(), albumInfoDto.getAlbumname(), albumInfoDto.getIntroduction());
            return new ResultView<>(200, "修改相册封面成功");
        }
        if ( Integer.valueOf(albumInfoDto.getTarget()) == Constants.USER ) {
            teamAlbumService.updateAlbumInfo(albumInfoDto.getAlbumid(), albumInfoDto.getAlbumname(), albumInfoDto.getIntroduction());
            return new ResultView<>(200, "修改相册封面成功");
        }
        return new ResultView<>(201,"修改相册信息失败");
    }

    // 获取相册文件列表

    // 获取文件缩略图

}
