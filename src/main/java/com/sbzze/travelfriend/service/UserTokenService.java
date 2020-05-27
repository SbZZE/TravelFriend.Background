package com.sbzze.travelfriend.service;

import com.baomidou.mybatisplus.service.IService;
import com.sbzze.travelfriend.entity.UserToken;

public interface UserTokenService extends IService<UserToken> {
    int insertUserToken(String userId, String userToken);

    UserToken findUserTokenByUserId(String userId);

    int deleteUserToken(String userId);

    int updateUserToken(String userId, String userToken);
}
