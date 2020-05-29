package com.sbzze.travelfriend.mapper;



import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sbzze.travelfriend.entity.UserToken;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {
    UserToken findUserTokenByUserId(@Param("userId") String userId);
}