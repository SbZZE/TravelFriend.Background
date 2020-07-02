package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.model.ResultView;
import com.sbzze.travelfriend.service.UserAlbumService;
import com.sbzze.travelfriend.util.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/user/album")
public class UserAlbumController {

    @Autowired
    private UserAlbumService userAlbumService;

    // 添加相册
    @UserLoginToken
    @PostMapping
    public Object addAlbum( UserDto.UserAlbumDto userAlbumDto, @RequestParam MultipartFile cover ) {
        userAlbumDto.setCover(cover);
        if ( userAlbumService.isAlbumExist(userAlbumDto.getUsername(), userAlbumDto.getAlbumname()) ) {
            return ResultViewModelUtil.addAlbumErrorByAlbumExist();
        }
        String albumId = userAlbumService.addAlbum(userAlbumDto);
        if ( albumId == null ) {
            return ResultViewModelUtil.addAlbumErrorByCover();
        } else {
            return new ResultView<>(200,"添加相册成功", albumId);
        }

    }

    // 获取相册信息
    @UserLoginToken
    @GetMapping("/info")
    public Object getAlbumInfo( String username ) {
        return new ResultView<>(200,"获取成功", userAlbumService.getAlbumInfo(username));
    }

    // 获取相册封面
    @UserLoginToken
    @GetMapping("/cover")
    @ResponseBody
    public byte[] getAlbumCover( String albumid, String width, String height ) {
        return userAlbumService.getAlbumCover(albumid, width, height);
    }

}
