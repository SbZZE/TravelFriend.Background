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
    public String addAlbum( UserDto.UserAlbumDto userAlbumDto ) {

        String originalFileName = userAlbumDto.getCover().getOriginalFilename();
        String changedFileName = FileNameUtil.getFileName(originalFileName);
        String signName = USER + "/" + ALBUM;

        String albumId = UUIDUtil.getUUID();
        String tagName = userAlbumDto.getUsername() + "/" + albumId + "/" + COVER;

        // /ROOT_PATH/SON_PATH/user/album/${username}/${albumid}/cover/
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName, tagName);

        if ( !FileUtil.compressFile(userAlbumDto.getCover(), filePath, changedFileName, PREFIX, 0.5f, 0.5f) ) {
            //TODO 状态码修改
            return null;
        }

        UserAlbum album = new UserAlbum();
        album.setId(albumId);
        album.setUserId(userService.findUserByName(userAlbumDto.getUsername()).getId());
        album.setAlbumname(userAlbumDto.getAlbumname());
        album.setIntroduction(userAlbumDto.getIntroduction());

        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName, tagName);
        String insertFileName = urlPath + PREFIX + changedFileName;
        album.setCover(insertFileName);
        if ( baseMapper.insert(album) <= 0 ){
            return null;
        } else {
            return albumId;
        }

    }

    @Override
    public List<Object> getAlbumInfo( String username ) {
        User user = userService.findUserByName(username);
        List<UserAlbum> albums = findAlbumByUserId(user.getId());


        UserDto.UserAlbumInfoDto userAlbumInfoDto = new UserDto.UserAlbumInfoDto();
        List<Object> userAlbumInfoDtos = new ArrayList<>();
        for (UserAlbum album : albums) {
            userAlbumInfoDto.setAlbumid(album.getId());
            userAlbumInfoDto.setAlbumname(album.getAlbumname());
            userAlbumInfoDto.setIntroduction(album.getIntroduction());

            userAlbumInfoDtos.add(userAlbumInfoDto);
        }

        return userAlbumInfoDtos;
    }

    @Override
    public byte[] getAlbumCover( String albumid ) {
        UserAlbum userAlbum = baseMapper.selectById(albumid);

        if ( null == userAlbum ) {
            return null;
        }

        String coverUrl = userAlbum.getCover();

        byte[] fileBytes = FileUtil.downloadFileBytes(coverUrl);
        return fileBytes;

    }



}
