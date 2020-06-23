package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.entity.UserAlbumImages;
import com.sbzze.travelfriend.entity.UserAlbum;
import com.sbzze.travelfriend.mapper.UserAlbumImagesMapper;
import com.sbzze.travelfriend.service.UserAlbumImagesService;
import com.sbzze.travelfriend.service.UserAlbumService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserAlbumImagesServiceImpl extends BaseServiceImpl<UserAlbumImagesMapper, UserAlbumImages> implements UserAlbumImagesService {

    //TODO 待修改

    @Autowired
    UserService userService;

    @Autowired
    UserAlbumService userAlbumService;

    @Override
    public List<UserAlbumImages> findImagesByAlbumId(String albumId) {
        return baseMapper.findImagesByAlbumId(albumId);
    }

    //TODO 更新用户相册图片
    @Override
    public int updateUserAlbum(String username, MultipartFile[] files, String albumname ) {
        String signName = USER + "/" + ALBUM;
        // TODO 修改保存地址
        // /ROOT_PATH/SON_PATH/user/album/${username}/${albumname}/分照片和视频的文件夹？
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName, username);
        String originalFilePath = filePath + "orginal" + "/";
        String compressFilePath = filePath + "compress" + "/";
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String changedFileName = FileNameUtil.getFileName(originalFileName);
            FileUtil.uploadAlbum(originalFilePath, file, changedFileName);
            FileUtil.compressFile(file, compressFilePath, changedFileName, PREFIX, 1f, 0.2f);
        }

        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName, username);
        String insertFilePath = urlPath + "original" + "/" ;
        String insertCompressFilePath = urlPath +"compress" + "/";

        String userId = userService.findUserByName(username).getId();
        UserAlbum album  = userAlbumService.findAlbumByUserIdAndAlbumName(userId, albumname);

        if ( album != null ) {
            /*
            UserAlbumImages userAlbumImages = findImagesByAlbumId(album.getId());
            userAlbumImages.setImage(insertFilePath);
            userAlbumImages.setCompressImage(insertCompressFilePath);

            return baseMapper.updateById(userAlbumImages);
            */
        } else {
            //TODO 返回码待修改
            return -1;
        }
        //TODO
        return 1;

    }
}
