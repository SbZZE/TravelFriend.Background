package com.sbzze.travelfriend.util;

import com.sbzze.travelfriend.model.ResultViewModel;

/**
 * 响应数据封装类
 *
 * @author TJ
 */
public class ResultViewModelUtil {


    public static ResultViewModel.loginModel loginSuccess( String token ) {
        ResultViewModel.loginModel loginModel = new ResultViewModel.loginModel();
        loginModel.setCode(200);
        loginModel.setMessage("登录成功");
        loginModel.setToken(token);

        return loginModel;
    }

    public static ResultViewModel.loginModel loginErrorByPwd( String token ) {
        ResultViewModel.loginModel loginModel = new ResultViewModel.loginModel();
        loginModel.setCode(201);
        loginModel.setMessage("密码错误");
        loginModel.setToken(token);

        return loginModel;
    }

    public static ResultViewModel.loginModel loginErrorByNotRegister( String token ) {
        ResultViewModel.loginModel loginModel = new ResultViewModel.loginModel();
        loginModel.setCode(202);
        loginModel.setMessage("用户不存在");
        loginModel.setToken(token);

        return loginModel;
    }

    public static ResultViewModel.registerModel registerSuccess() {
        ResultViewModel.registerModel registerModel = new ResultViewModel.registerModel();
        registerModel.setCode(200);
        registerModel.setMessage("注册成功");

        return registerModel;
    }

    public static ResultViewModel.registerModel registerErrorByExist() {
        ResultViewModel.registerModel registerModel = new ResultViewModel.registerModel();
        registerModel.setCode(201);
        registerModel.setMessage("用户已存在");

        return registerModel;
    }

    public static ResultViewModel.registerModel registerErrorByUsername() {
        ResultViewModel.registerModel registerModel = new ResultViewModel.registerModel();
        registerModel.setCode(202);
        registerModel.setMessage("邮箱或手机号不正确");

        return registerModel;
    }

    public static ResultViewModel.registerModel registerErrorByInsert() {
        ResultViewModel.registerModel registerModel = new ResultViewModel.registerModel();
        registerModel.setCode(203);
        registerModel.setMessage("注册失败");

        return registerModel;
    }
}
