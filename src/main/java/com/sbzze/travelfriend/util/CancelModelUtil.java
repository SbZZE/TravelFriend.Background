package com.sbzze.travelfriend.util;

import com.sbzze.travelfriend.model.CancelModel;

/**
 * @Author:Zzq
 * @Description:
 * @Date:2020/5/20
 * @Time:0:17
 */
public class CancelModelUtil {
    //TODO
    public static CancelModel.normalModel cancelErrorByNotExist(){
        CancelModel.normalModel cancelModel = new CancelModel.normalModel();
        cancelModel.setCode(201);
        cancelModel.setMessage("用户不存在");

        return cancelModel;

    }

    public static CancelModel.normalModel cancelSuccess(){
        CancelModel.normalModel canceModel = new CancelModel.normalModel();
        canceModel.setCode(200);
        canceModel.setMessage("注销成功");
        return canceModel;
    }
    public static CancelModel.normalModel cancelErrorByDelete(){
        CancelModel.normalModel canceModel = new CancelModel.normalModel();
        canceModel.setCode(202);
        canceModel.setMessage("用户注销失败");
        return canceModel;
    }

    public static CancelModel.normalModel cancelMemberErrorByNotExist(){
        CancelModel.normalModel cancelModel = new CancelModel.normalModel();
        cancelModel.setCode(201);
        cancelModel.setMessage("队友不存在");

        return cancelModel;

    }

    public static CancelModel.normalModel cancelMemberSuccess(){
        CancelModel.normalModel canceModel = new CancelModel.normalModel();
        canceModel.setCode(200);
        canceModel.setMessage("踢出成功");
        return canceModel;
    }
    public static CancelModel.normalModel cancelMemberErrorByDelete(){
        CancelModel.normalModel canceModel = new CancelModel.normalModel();
        canceModel.setCode(202);
        canceModel.setMessage("踢出失败");
        return canceModel;
    }
}
