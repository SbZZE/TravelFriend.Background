package com.sbzze.travelfriend.controller;


import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.AccountValidatorUtil;
import com.sbzze.travelfriend.util.JwtUtil;
import com.sbzze.travelfriend.util.ResultViewModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录
    @PostMapping("/login")
    public Object login( @RequestBody String username, String password ){

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
    @GetMapping("/register")
    public Object register( UserDto userDto ) {
        User userForBase = userService.findUserByName(userDto.getUsername());
        if ( null != userForBase) {
            return ResultViewModelUtil.registerErrorByExist();
        }

        //验证是否为邮箱或手机号
        if ( !AccountValidatorUtil.isEmail(userDto.getUsername()) && !AccountValidatorUtil.isMobile(userDto.getUsername()) ) {
            return ResultViewModelUtil.registerErrorByUsername();
        }


        int flag = userService.insertUser( userDto.getUsername(), userDto.getPassword(), userDto.getNickname(), userDto.getSignature() );
        if ( flag <= 0 ) {
            return ResultViewModelUtil.registerErrorByInsert();
        }
        else {
            return ResultViewModelUtil.registerSuccess();
        }

    }
}
