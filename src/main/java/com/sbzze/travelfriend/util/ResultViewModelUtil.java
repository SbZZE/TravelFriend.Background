package com.sbzze.travelfriend.util;

import com.sbzze.travelfriend.model.ResultViewModel;

/**
 * 响应数据封装类
 *
 * @author TJ
 */
public class ResultViewModelUtil {


    public static ResultViewModel loginSuccess( Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(200);
        resultViewModel.setMessage("登录成功");
        resultViewModel.setToken(object);

        return resultViewModel;
    }

    public static ResultViewModel loginErrorByPwd( Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(201);
        resultViewModel.setMessage("密码错误");
        resultViewModel.setToken(object);

        return resultViewModel;
    }

    public static ResultViewModel loginErrorByNotRegister( Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(202);
        resultViewModel.setMessage("用户不存在");
        resultViewModel.setToken(object);

        return resultViewModel;
    }
}
