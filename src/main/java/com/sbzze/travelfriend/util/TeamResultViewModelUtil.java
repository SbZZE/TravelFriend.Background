package com.sbzze.travelfriend.util;

import com.sbzze.travelfriend.model.TeamResultViewModel;

/**
 * @Author:Zzq
 * @Description:响应数据封装
 * @Date:2020/6/3
 * @Time:14:37
 */
public class TeamResultViewModelUtil {
    //TODO
    public static TeamResultViewModel.normalModel CreateSuccess() {
        TeamResultViewModel.normalModel CreateModel = new TeamResultViewModel.normalModel();
        CreateModel.setCode(200);
        CreateModel.setMessage("创建团队成功");

        return CreateModel;
    }

    public static TeamResultViewModel.normalModel CreateErrorByExit() {
        TeamResultViewModel.normalModel CreateModel = new TeamResultViewModel.normalModel();
        CreateModel.setCode(201);
        CreateModel.setMessage("团队已经存在");

        return CreateModel;
    }

    public static TeamResultViewModel.noramlWithTeamsModel getTeamInfoSuccess(Object object) {
        TeamResultViewModel.noramlWithTeamsModel getTeamInfoModel = new TeamResultViewModel.noramlWithTeamsModel();
        getTeamInfoModel.setCode(200);
        getTeamInfoModel.setMessage("获取成功");
        getTeamInfoModel.setTeams(object);

        return getTeamInfoModel;
    }

    public static TeamResultViewModel.normalWithDataModel getTeamInfoError(Object object) {
        TeamResultViewModel.normalWithDataModel getTeamInfoModel = new TeamResultViewModel.normalWithDataModel();
        getTeamInfoModel.setCode(201);
        getTeamInfoModel.setMessage("获取失败");
        getTeamInfoModel.setData(object);

        return getTeamInfoModel;
    }

    public static TeamResultViewModel.normalModel updateTeamInfoSuccess() {
        TeamResultViewModel.normalModel updateTeamInfoModel = new TeamResultViewModel.normalModel();
        updateTeamInfoModel.setCode(200);
        updateTeamInfoModel.setMessage("修改成功");

        return updateTeamInfoModel;
    }

    public static TeamResultViewModel.normalModel updateTeamInfoError() {
        TeamResultViewModel.normalModel updateTeamInfoModel = new TeamResultViewModel.normalModel();
        updateTeamInfoModel.setCode(201);
        updateTeamInfoModel.setMessage("修改失败");

        return updateTeamInfoModel;
    }

    public static TeamResultViewModel.normalModel updateTeamAvatarSuccess() {
        TeamResultViewModel.normalModel updateAvatarModel = new TeamResultViewModel.normalModel();
        updateAvatarModel.setCode(200);
        updateAvatarModel.setMessage("修改成功");

        return updateAvatarModel;
    }

    public static TeamResultViewModel.normalModel updateTeamAvatarError() {
        TeamResultViewModel.normalModel updateAvatarModel = new TeamResultViewModel.normalModel();
        updateAvatarModel.setCode(201);
        updateAvatarModel.setMessage("修改失败");

        return updateAvatarModel;
    }

    public static TeamResultViewModel.noramlWithMembersModel getTeamMemberSuccess(Object object) {
        TeamResultViewModel.noramlWithMembersModel getTeamMemberModel = new TeamResultViewModel.noramlWithMembersModel();
        getTeamMemberModel.setCode(200);
        getTeamMemberModel.setMessage("获取成功");
        getTeamMemberModel.setMembers(object);

        return getTeamMemberModel;
    }

    public static TeamResultViewModel.normalModel getTeamMemberError() {
        TeamResultViewModel.normalModel getTeamMemberModel = new TeamResultViewModel.normalModel();
        getTeamMemberModel.setCode(201);
        getTeamMemberModel.setMessage("获取失败");

        return getTeamMemberModel;
    }

    public static TeamResultViewModel.noramlWithAlbumsModel getTeamAlbumInfoSuccess(Object object) {
        TeamResultViewModel.noramlWithAlbumsModel getTeamAlbumInfoModel = new TeamResultViewModel.noramlWithAlbumsModel();
        getTeamAlbumInfoModel.setCode(200);
        getTeamAlbumInfoModel.setMessage("成功");
        getTeamAlbumInfoModel.setAlbums(object);

        return getTeamAlbumInfoModel;

    }

    public static TeamResultViewModel.noramlWithAlbumsModel getTeamAlbumInfoError(Object object) {
        TeamResultViewModel.noramlWithAlbumsModel getTeamAlbumInfoModel = new TeamResultViewModel.noramlWithAlbumsModel();
        getTeamAlbumInfoModel.setCode(201);
        getTeamAlbumInfoModel.setMessage("失败");
        getTeamAlbumInfoModel.setAlbums(object);

        return getTeamAlbumInfoModel;

    }

    public static TeamResultViewModel.noramlWithTeamAlbumIdModel addTeamAlbumSuccess(String albumid){
        TeamResultViewModel.noramlWithTeamAlbumIdModel addTeamAlbumModel = new TeamResultViewModel.noramlWithTeamAlbumIdModel();
        addTeamAlbumModel.setCode(200);
        addTeamAlbumModel.setMessage("新建相册成功");
        addTeamAlbumModel.setAlbumid(albumid);

        return addTeamAlbumModel;
    }

    public static TeamResultViewModel.normalModel addTeamAlbumErrorByExit(){
        TeamResultViewModel.normalModel addTeamAlbumModel = new TeamResultViewModel.normalModel();
        addTeamAlbumModel.setCode(201);
        addTeamAlbumModel.setMessage("新建相册失败，相册已存在");

        return addTeamAlbumModel;
    }

    public static TeamResultViewModel.normalModel addTeamAlbumError(){
        TeamResultViewModel.normalModel addTeamAlbumModel = new TeamResultViewModel.normalModel();
        addTeamAlbumModel.setCode(201);
        addTeamAlbumModel.setMessage("新建相册失败");

        return addTeamAlbumModel;
    }
}
