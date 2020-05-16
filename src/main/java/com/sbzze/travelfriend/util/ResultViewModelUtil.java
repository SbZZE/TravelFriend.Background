package com.sbzze.travelfriend.util;

import com.sbzze.travelfriend.model.ResultViewModel;

/**
 * 响应数据封装类
 *
 * @author TJ
 */
public class ResultViewModelUtil {

    // TODO
    public static ResultViewModel.normalModel tokenError() {
        ResultViewModel.normalModel getUserInfoModel = new ResultViewModel.normalModel();
        getUserInfoModel.setCode(401);
        getUserInfoModel.setMessage("身份验证失败");


        return getUserInfoModel;
    }

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

    public static ResultViewModel.normalModel registerSuccess() {
        ResultViewModel.normalModel registerModel = new ResultViewModel.normalModel();
        registerModel.setCode(200);
        registerModel.setMessage("注册成功");

        return registerModel;
    }

    public static ResultViewModel.normalModel registerErrorByExist() {
        ResultViewModel.normalModel registerModel = new ResultViewModel.normalModel();
        registerModel.setCode(201);
        registerModel.setMessage("用户已存在");

        return registerModel;
    }

    public static ResultViewModel.normalModel registerErrorByUsername() {
        ResultViewModel.normalModel registerModel = new ResultViewModel.normalModel();
        registerModel.setCode(202);
        registerModel.setMessage("邮箱或手机号不正确");

        return registerModel;
    }

    public static ResultViewModel.normalModel registerErrorByInsert() {
        ResultViewModel.normalModel registerModel = new ResultViewModel.normalModel();
        registerModel.setCode(203);
        registerModel.setMessage("注册失败");

        return registerModel;
    }

    public static ResultViewModel.normalWithDataModel getUserInfoSuccess( Object object ) {
        ResultViewModel.normalWithDataModel getUserInfoModel = new ResultViewModel.normalWithDataModel();
        getUserInfoModel.setCode(200);
        getUserInfoModel.setMessage("获取成功");
        getUserInfoModel.setData(object);

        return getUserInfoModel;
    }

    public static ResultViewModel.normalWithDataModel getUserInfoErrorByUserName( Object object ) {
        ResultViewModel.normalWithDataModel getUserInfoModel = new ResultViewModel.normalWithDataModel();
        getUserInfoModel.setCode(201);
        getUserInfoModel.setMessage("用户名不存在");
        getUserInfoModel.setData(object);

        return getUserInfoModel;
    }

    public static ResultViewModel.normalModel updateUserInfoSuccess() {
        ResultViewModel.normalModel updateUserInfoModel = new ResultViewModel.normalModel();
        updateUserInfoModel.setCode(200);
        updateUserInfoModel.setMessage("修改成功");

        return updateUserInfoModel;
    }

    public static ResultViewModel.normalModel updateUserErrorByUpdate() {
        ResultViewModel.normalModel updateUserInfoModel = new ResultViewModel.normalModel();
        updateUserInfoModel.setCode(201);
        updateUserInfoModel.setMessage("修改失败");

        return updateUserInfoModel;
    }

    public static ResultViewModel.normalWithDataModel updateUserInfoErrorByUserName( Object object ) {
        ResultViewModel.normalWithDataModel updateUserInfoModel = new ResultViewModel.normalWithDataModel();
        updateUserInfoModel.setCode(202);
        updateUserInfoModel.setMessage("用户名不存在");
        updateUserInfoModel.setData(object);

        return updateUserInfoModel;
    }

    public static ResultViewModel.normalModel updateUserAvatarSuccess() {
        ResultViewModel.normalModel updateAvatarModel = new ResultViewModel.normalModel();
        updateAvatarModel.setCode(200);
        updateAvatarModel.setMessage("修改成功");

        return updateAvatarModel;
    }

    public static ResultViewModel.normalModel updateUserAvatarErrorByUsername() {
        ResultViewModel.normalModel updateAvatarModel = new ResultViewModel.normalModel();
        updateAvatarModel.setCode(201);
        updateAvatarModel.setMessage("用户名不存在");

        return updateAvatarModel;
    }


    public static ResultViewModel.normalModel updateUserAvatarErrorByPicType() {
        ResultViewModel.normalModel updateAvatarModel = new ResultViewModel.normalModel();
        updateAvatarModel.setCode(202);
        updateAvatarModel.setMessage("文件格式错误");

        return updateAvatarModel;
    }

    public static ResultViewModel.normalModel updateUserAvatarErrorByUpdate() {
        ResultViewModel.normalModel updateAvatarModel = new ResultViewModel.normalModel();
        updateAvatarModel.setCode(203);
        updateAvatarModel.setMessage("修改失败");

        return updateAvatarModel;
    }

    public static ResultViewModel.normalWithDataModel getUserAvatarSuccess( Object object ) {
        ResultViewModel.normalWithDataModel getAvatarModel = new ResultViewModel.normalWithDataModel();
        getAvatarModel.setCode(200);
        getAvatarModel.setMessage("获取成功");
        getAvatarModel.setData(object);

        return getAvatarModel;
    }

    public static ResultViewModel.normalWithDataModel getUserAvatarErrorByUsername( Object object ) {
        ResultViewModel.normalWithDataModel getAvatarModel = new ResultViewModel.normalWithDataModel();
        getAvatarModel.setCode(201);
        getAvatarModel.setMessage("用户名不存在");
        getAvatarModel.setData(object);

        return getAvatarModel;
    }

    public static ResultViewModel.normalWithDataModel getUserAvatarErrorByGet( Object object ) {
        ResultViewModel.normalWithDataModel getAvatarModel = new ResultViewModel.normalWithDataModel();
        getAvatarModel.setCode(202);
        getAvatarModel.setMessage("获取失败");
        getAvatarModel.setData(object);

        return getAvatarModel;
    }
}
