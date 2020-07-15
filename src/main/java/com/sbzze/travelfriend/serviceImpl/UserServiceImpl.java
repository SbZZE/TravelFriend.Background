package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.UserMapper;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    // 增
    @Override
    public int insertUser( String username, String password, String nickname ) {
        User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setUsername(username);
        user.setPassword(password);
        if ( nickname.isEmpty() ) {
            user.setNickname(username);
        } else {
            user.setNickname(nickname);
        }
        user.setGender(WOMAN);

        return baseMapper.insert( user );
    }

    // 查
    @Override
    public User findUserByName( String username ) { return baseMapper.findUserByName( username ); }

    @Override
    public User findUserById( String id ) {
        return baseMapper.findUserById( id );
    }

    @Override
    public UserDto.UserInfoWithOutAvatarDto findUserInfoByName( String username ) {
        User user = baseMapper.findUserByName( username );
        UserDto.UserInfoWithOutAvatarDto userInfo = new UserDto.UserInfoWithOutAvatarDto();

        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setSignature(user.getSignature());
        userInfo.setGender(user.getGender());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setAddress(user.getAddress());

        return userInfo;
    }

    // 改
    @Override
    public int updateUserInfoWithOutAvatar( UserDto.UserInfoWithOutAvatarDto updateDto ) {
        User user = baseMapper.findUserByName( updateDto.getUsername() );

        user.setNickname(updateDto.getNickname());
        user.setGender(updateDto.getGender());
        user.setSignature(updateDto.getSignature());
        user.setBirthday(updateDto.getBirthday());
        user.setAddress(updateDto.getAddress());

        return baseMapper.updateById(user);
    }

    @Override
    public int updateUserAvatar( String username, MultipartFile file ) {
        String originalFileName = file.getOriginalFilename();
        String changedFileName = FileNameUtil.getFileName(originalFileName);
        //String filePath = ROOT_PATH + SON_PATH + "/" + AVATAR + "/" + username + "/";
        String signName = USER + "/" + AVATAR;
        // /ROOT_PATH/SON_PATH/user/avatar/${username}/
        String filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName, username);

        if ( !FileUtil.uploadByDeleteExistFile(filePath, file, changedFileName) ) {
            log.error("用户头像更新失败");
            return -1;
        }

        //压缩图片并上传
       if ( !FileUtil.compressFile(file, filePath, changedFileName, PREFIX, 0.5f, 0.2f) ) {
           log.error("用户压缩头像更新失败");
           return -1;
       }

        //String insertFileName = "http://" + host + ":" + POST + SON_PATH + "/" + AVATAR + "/" + username + "/" + changedFileName;
        //String insertCompressFileName = "http://" + host + ":" + POST + SON_PATH + "/" + AVATAR + "/" + username + "/" + PREFIX + changedFileName;
        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName, username);
        String insertFileName = urlPath + changedFileName;
        String insertCompressFileName = urlPath + PREFIX + changedFileName;

        User user = baseMapper.findUserByName(username);
        user.setAvatar(insertFileName);
        user.setCompressAvatar(insertCompressFileName);
        return baseMapper.updateById(user);
    }

    // 查
    @Override
    public byte[] getAvatar( String username ) {
        User user = baseMapper.findUserByName(username);
        if ( null == user ) {
            return null;
        }
        String avatarUrl = user.getAvatar();

        byte[] fileBytes = FileUtil.downloadFileBytes(avatarUrl);

        return fileBytes;
    }


    @Override
    public byte[] getCompressAvatar(String username, String width, String height) {
        User user = baseMapper.findUserByName(username);

        if ( user == null || user.getCompressAvatar() == null ) {
            return null;
        }
        String compressAvatarUrl = user.getCompressAvatar();

        return FileUtil.downloadFileBytesByWidthAndHeight(compressAvatarUrl, Integer.valueOf(width), Integer.valueOf(height));


    }

    //删
    public int deleteUser(String username){
        User user = baseMapper.findUserByName(username);

        return baseMapper.deleteById(user.getId());
    }
}
