package com.sbzze.travelfriend.serviceImpl;


import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.entity.UserAlbum;
import com.sbzze.travelfriend.mapper.UserAlbumMapper;
import com.sbzze.travelfriend.service.UserAlbumService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAlbumServiceImpl extends BaseServiceImpl<UserAlbumMapper, UserAlbum> implements UserAlbumService {

    @Autowired
    UserService userService;

    @Override
    public UserAlbum findAlbumByUserIdAndAlbumName( String userId, String albumname ) {
        return baseMapper.findAlbumByUserIdAndAlbumName(userId, albumname);
    }

    @Override
    public List<UserAlbum> findAlbumByUserId( String userId ) {
        return baseMapper.findAlbumByUserId(userId);
    }

    @Override
    public boolean isAlbumExist( String username, String albumname ) {
        User user = userService.findUserByName(username);
        UserAlbum album = findAlbumByUserIdAndAlbumName(user.getId(), albumname);
        if ( null == album ) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public int addAlbum( String username, String albumname, String introduction, MultipartFile cover ) {

        String originalFileName = cover.getOriginalFilename();
        String changedFileName = FileNameUtil.getFileName(originalFileName);
        String signName = USER + "/" + ALBUM;
        // TODO 修改保存地址
        // 封面
        // /ROOT_PATH/SON_PATH/user/album/${username}/${albumname}/avatar/
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName + "/" + COVER, username);

        if ( !FileUtil.compressFile(cover, filePath, changedFileName, PREFIX, 0.5f, 0.5f) ) {
            //TODO 状态码修改
            return -1;
        }

        UserAlbum album = new UserAlbum();
        album.setId(UUIDUtil.getUUID());
        album.setUserId(userService.findUserByName(username).getId());
        album.setAlbumname(albumname);
        album.setIntroduction(introduction);

        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName + "/" + COVER, username);
        String insertFileName = urlPath + PREFIX + changedFileName;
        album.setCover(insertFileName);

        return baseMapper.insert(album);

    }

    @Override
    public List<Object> getAlbumInfo( String username ) {
        User user = userService.findUserByName(username);
        List<UserAlbum> albums = findAlbumByUserId(user.getId());


        UserDto.UserAlbumInfoDto userAlbumInfoDto = new UserDto.UserAlbumInfoDto();
        List<Object> userAlbumInfoDtos = new ArrayList<>();
        for (UserAlbum album : albums) {
            userAlbumInfoDto.setAlbumname(album.getAlbumname());
            userAlbumInfoDto.setIntroduction(album.getIntroduction());

            userAlbumInfoDtos.add(userAlbumInfoDto);
        }

        return userAlbumInfoDtos;
    }

    @Override
    public byte[] getAlbumCover( String username, String albumname ) {
        UserAlbum userAlbum = findAlbumByUserIdAndAlbumName(userService.findUserByName(username).getId(), albumname);

        if ( null == userAlbum ) {
            return null;
        }

        String coverUrl = userAlbum.getCover();

        byte[] fileBytes = FileUtil.downloadFileBytes(coverUrl);
        return fileBytes;

    }



}
