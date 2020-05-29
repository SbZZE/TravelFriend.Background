package com.sbzze.travelfriend.serviceImpl;

import com.sbzze.travelfriend.entity.UserToken;
import com.sbzze.travelfriend.mapper.UserTokenMapper;
import com.sbzze.travelfriend.service.UserTokenService;
import com.sbzze.travelfriend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenServiceImpl extends BaseServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {

    @Autowired
    UserTokenMapper userTokenMapper;

    @Override
    public int insertUserToken( String userId, String userToken ) {
        UserToken uToken = new UserToken();
        uToken.setId(UUIDUtil.getUUID());
        uToken.setUserId(userId);
        uToken.setToken(userToken);

        return baseMapper.insert(uToken);
    }

    @Override
    public UserToken findUserTokenByUserId( String userId ) {
        return userTokenMapper.findUserTokenByUserId(userId);
    }

    @Override
    public int deleteUserToken( String userId ) {
        UserToken uToken = findUserTokenByUserId(userId);
        return baseMapper.deleteById(uToken.getId());
    }

    @Override
    public int updateUserToken( String userId, String userToken ) {
        UserToken uToken = findUserTokenByUserId(userId);
        uToken.setToken(userToken);
        return baseMapper.updateById(uToken);
    }
}
