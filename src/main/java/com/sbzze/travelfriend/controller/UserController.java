package com.sbzze.travelfriend.controller;


import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.filter.PassToken;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.AccountValidatorUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.JwtUtil;
import com.sbzze.travelfriend.util.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录
    @PassToken
    @GetMapping("/login")
    public Object login( String username, String password ){

        User userForBase = userService.findUserByName(username);

        if( null == userForBase  ) {
            return ResultViewModelUtil.loginErrorByNotRegister(null);
        }
        if ( !userForBase.getPassword().equals(password) ){
            return ResultViewModelUtil.loginErrorByPwd(null);
        } else {
            return ResultViewModelUtil.loginSuccess(JwtUtil.getToken(userForBase));
        }
    }

    // 注册
    @PassToken
    @PostMapping("/register")
    public Object register( @RequestBody UserDto.UserRegisterDto userRegisterDto ) {
        User userForBase = userService.findUserByName(userRegisterDto.getUsername());
        if ( null != userForBase) {
            return ResultViewModelUtil.registerErrorByExist();
        }

        //验证是否为邮箱或手机号
        if ( !AccountValidatorUtil.isEmail(userRegisterDto.getUsername()) && !AccountValidatorUtil.isMobile(userRegisterDto.getUsername()) ) {
            return ResultViewModelUtil.registerErrorByUsername();
        }

        int flag = userService.insertUser( userRegisterDto.getUsername(), userRegisterDto.getPassword(),
                                           userRegisterDto.getNickname(), userRegisterDto.getSignature() );
        if ( flag <= 0 ) {
            return ResultViewModelUtil.registerErrorByInsert();
        }
        else {
            return ResultViewModelUtil.registerSuccess();
        }

    }


    // 获取个人资料(不包含头像)
    @UserLoginToken
    @GetMapping("/userInfo")
    public Object getUserInfoWithOutAvatar( String username ) {

        User userForBase = userService.findUserByName(username);

        if ( null == userForBase ) {
            return ResultViewModelUtil.getUserInfoErrorByUserName(null);
        } else {
            return ResultViewModelUtil.getUserInfoSuccess(userService.findUserInfoByName(username));
        }
    }


    // 修改个人资料 (不包含头像)
    @UserLoginToken
    @PostMapping("/userInfo")
    public Object updateUserInfoWithOutAvatar( @RequestBody UserDto.UserInfoWithOutAvatarDto userInfoWithOutAvatarDto ) {

        User userForBase = userService.findUserByName(userInfoWithOutAvatarDto.getUsername());
        if ( null == userForBase ) {
            return ResultViewModelUtil.updateUserInfoErrorByUserName(null);
        }

        int flag = userService.updateUserInfoWithOutAvatar(userInfoWithOutAvatarDto);
        if ( flag <= 0 ) {
            return ResultViewModelUtil.updateUserErrorByUpdate();
        } else {
            return ResultViewModelUtil.updateUserInfoSuccess();
        }
    }


    // 修改头像
    @UserLoginToken
    @PostMapping("/avatar")
    public Object updateUserAdatar(@RequestParam String username, @RequestParam MultipartFile avatar ) {

        if ( avatar.isEmpty() ) {
            return ResultViewModelUtil.updateUserAvatarErrorByPicType();
        }


        if ( !FileUtil.isImage(avatar) ) {
            return ResultViewModelUtil.updateUserAvatarErrorByPicType();
        }

        User userForBase = userService.findUserByName(username);
        if ( null == userForBase ) {
            return ResultViewModelUtil.updateUserAvatarErrorByUsername();
        }

        int flag = userService.updateUserAvatar(username, avatar);
        if ( flag <= 0) {
            return ResultViewModelUtil.updateUserAvatarErrorByUpdate();
        } else {
            return ResultViewModelUtil.updateUserAvatarSuccess();
        }
    }


    // 获取头像
    @UserLoginToken
    @GetMapping("/avatar")
    public Object getUserAvatar( String username ) {

        User userForBase = userService.findUserByName(username);
        if ( null == userForBase ) {
            return ResultViewModelUtil.getUserAvatarErrorByUsername(null);
        }

        Object obj = userService.downloadAvatar(username);

        if ( null == obj) {
            return ResultViewModelUtil.getUserAvatarErrorByGet(null);
        } else {
            return ResultViewModelUtil.getUserAvatarSuccess(obj);
        }

    }

}
