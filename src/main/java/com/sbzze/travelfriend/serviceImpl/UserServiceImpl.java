package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.dto.UserDto;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.mapper.UserMapper;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {


    @Value("${web.upload-path}")
    private String path;


    // 增
    @Override
    public int insertUser( String username, String password, String nickname, String signature ) {
        User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setUsername(username);
        user.setPassword(password);
        if (null == nickname) {
            user.setNickname(username);
        } else {
            user.setNickname(nickname);
        }
        user.setSignature(signature);

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
        try {
            String localPath = path;
            String fileName = file.getOriginalFilename();
            String realPath = new File(localPath).getAbsolutePath() + "\\" + username + "\\" +FileNameUtil.getFileName(fileName);
            File dest = new File(realPath);

            if ( !dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            /*
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(dest);

            byte[] bytes = new byte[1024];
            int res = 0;
            while ((res = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, res);
            }
             */
            User user = baseMapper.findUserByName(username);
            user.setAvatar(dest.getPath());

            return baseMapper.updateById(user);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
