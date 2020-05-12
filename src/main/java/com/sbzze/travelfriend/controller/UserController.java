package com.sbzze.travelfriend.controller;


import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.service.UserService;
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
        } else {
            if ( !userForBase.getPassword().equals(password) ){
                return ResultViewModelUtil.loginErrorByPwd(null);
            } else {
                return ResultViewModelUtil.loginSuccess(JwtUtil.getToken(userForBase));
            }
        }
    }

    // 注册
}
