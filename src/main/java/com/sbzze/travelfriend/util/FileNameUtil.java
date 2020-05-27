package com.sbzze.travelfriend.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description: 生成文件名工具类
 * @author: yuxiang
 * @create: 2019-12-02 15:53
 **/
public class FileNameUtil {

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUIDUtil.getUUID() + FileNameUtil.getSuffix(fileOriginName);
    }

    /**
     * 生成文件路径
     * @param rootPath
     * @param sonPath
     * @param signName
     * @param username
     * @return
     */
    public static String getFilePath(String rootPath, String sonPath, String signName, String username) {
        return rootPath + sonPath + "/" + signName + "/" + username + "/";
    }

    /**
     * 生成url路径
     * @param post
     * @param sonPath
     * @param signName
     * @param username
     * @return
     */
    public static String getUrlPath(String post, String sonPath, String signName, String username) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String url = "http://" + host + ":" + post + sonPath + "/" + signName + "/" + username + "/";
        return url;
    }
}
