package com.sbzze.travelfriend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.service.UserService;
import com.sbzze.travelfriend.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor extends BaseInterceptor {
    @Autowired
    UserService userService;

    @Autowired
    UserTokenService userTokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }


        //检查是否有passtoken注释，有则跳过认证
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }


        //检查有没有需要用户权限的注解
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token


        if (method.isAnnotationPresent(UserLoginToken.class)) {

            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);

            if (userLoginToken.required()) {
                // token认证
                if ( token.isEmpty() ) {
                    setResponse(httpServletRequest, httpServletResponse, "400","无token，请登录");
                    return false;
                }

                // 获取token中的userId
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    setResponse(httpServletRequest, httpServletResponse, "401","token有误");
                    return false;
                }

                // 验证 token
                User user = userService.findUserById(userId);

                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    setResponse(httpServletRequest, httpServletResponse, "401","token有误");
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}