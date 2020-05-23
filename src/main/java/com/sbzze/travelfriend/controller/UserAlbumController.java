package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.filter.UserLoginToken;
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
    public Object addAlbum( @RequestParam String username, @RequestParam String albumname,
                            @RequestParam String introduction, @RequestParam MultipartFile cover ) {
        if ( userAlbumService.isAlbumExist(username, albumname) ) {
            return ResultViewModelUtil.addAlbumErrorByAlbumExist();
        }
        if ( userAlbumService.addAlbum(username, albumname, introduction, cover) <= 0 ) {
            return ResultViewModelUtil.addAlbumErrorByCover();
        } else {
            return ResultViewModelUtil.addAlbumSuccess();
        }

    }

    // 获取相册信息
    @UserLoginToken
    @GetMapping("/info")
    public Object getAlbumInfo( String username ) {
        return ResultViewModelUtil.getAlbumInfoSuccess(userAlbumService.getAlbumInfo(username));
    }

    // 获取相册封面
    @UserLoginToken
    @GetMapping("/cover")
    @ResponseBody
    public byte[] getAlbumCover( String username, String albumname ) {
        return userAlbumService.getAlbumCover(username, albumname);
    }

}
