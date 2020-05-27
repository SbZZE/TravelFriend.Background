package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.entity.AlbumImages;
import com.sbzze.travelfriend.entity.UserAlbum;
import com.sbzze.travelfriend.mapper.AlbumImagesMapper;
import com.sbzze.travelfriend.service.AlbumImagesService;
import com.sbzze.travelfriend.service.UserAlbumService;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumImagesServiceImpl extends BaseServiceImpl<AlbumImagesMapper, AlbumImages> implements AlbumImagesService {

    @Autowired
    UserService userService;

    @Autowired
    UserAlbumService userAlbumService;

    @Override
    public AlbumImages findImagesByAlbumId(String albumId) {
        return baseMapper.findImagesByAlbumId(albumId);
    }

    //TODO 更新用户相册图片
    @Override
    public int updateUserAlbum(String username, MultipartFile[] files, String albumname ) {
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, ALBUM, username);
        String originalFilePath = filePath + "orginal" + "/";
        String compressFilePath = filePath + "compress" + "/";
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String changedFileName = FileNameUtil.getFileName(originalFileName);
            FileUtil.uploadAlbum(originalFilePath, file, changedFileName);
            FileUtil.compressFile(file, compressFilePath, changedFileName, PREFIX, 1f, 0.2f);
        }

        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, ALBUM, username);
        String insertFilePath = urlPath + "original" + "/" ;
        String insertCompressFilePath = urlPath +"compress" + "/";

        String userId = userService.findUserByName(username).getId();
        UserAlbum album  = userAlbumService.findAlbumByUserIdAndAlbumName(userId, albumname);

        if ( album != null ) {
            AlbumImages albumImages = findImagesByAlbumId(album.getId());
            albumImages.setImage(insertFilePath);
            albumImages.setCompressImage(insertCompressFilePath);

            return baseMapper.updateById(albumImages);
        } else {
            //TODO 返回码待修改
            return -1;
        }

    }
}
