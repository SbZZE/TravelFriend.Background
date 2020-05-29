package com.sbzze.travelfriend.util;


import com.alibaba.fastjson.JSON;
import com.sbzze.travelfriend.Wrapper.RequestWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求内容处理工具
 * @author TJ
 */
public class RequestHandleUtil {
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 获取请求参数
     * @param req
     * @return 请求参数格式key-value
     */
    public static Map<String, Object> getReqParam(HttpServletRequest req){
        String method = req.getMethod();
        Map<String, Object> reqMap;

        if ( METHOD_GET.equals(method) ) {
            reqMap = doGet(req);
        } else if ( METHOD_POST.equals(method) ) {
            reqMap = doPost(req);
        } else {
            return null;//其他请求方式暂不处理
        }

        return reqMap;
    }

    private static Map<String, Object> doGet(HttpServletRequest req) {
        String param = req.getQueryString();

        return paramsToMap(param);
    }

    private static Map<String, Object> doPost(HttpServletRequest req){
        String contentType = req.getContentType();
        Map<String,Object> params = new HashMap<String, Object>();

        try {
            // 保存request请求
            RequestWrapper requestWrapper = new RequestWrapper(req);
            String body = requestWrapper.getBody();

            if (CONTENT_TYPE_JSON.equals(contentType)) {
                if (StringUtils.isNotEmpty(body)) {
                    params = JSON.parseObject(body,Map.class);
                }

                return params;
            } else {
                //其他内容格式的请求暂不处理
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> paramsToMap(String params) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(params)) {
            String[] array = params.split("&");
            for (String pair : array) {
                if ("=".equals(pair.trim())) {
                    continue;
                }
                String[] entity = pair.split("=");
                if (entity.length == 1) {
                    map.put(decode(entity[0]), null);
                } else {
                    map.put(decode(entity[0]), decode(entity[1]));
                }
            }
        }
        return map;
    }



    /**
     * 编码格式转换
     * @param content
     * @return
     */
    public static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
