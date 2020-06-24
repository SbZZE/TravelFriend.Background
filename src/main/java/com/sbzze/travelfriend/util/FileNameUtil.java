package com.sbzze.travelfriend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description: 生成文件名工具类
 * @author: TJ
 **/
@Slf4j
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
     * 获取文件名
     * @param fileName
     * @return
     */
    public static String getNameWithOutSuffix(String fileName) {
        return fileName.substring(fileName.indexOf("."));
    }

    /**
     * 生成新的文件名(使用UUID)
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
     * @param tagName
     * @return
     */
    public static String getFilePath(String rootPath, String sonPath, String signName, String tagName) {
        return rootPath + sonPath + "/" + signName + "/" + tagName + "/";
    }

    /**
     * 生成url路径
     * @param post
     * @param sonPath
     * @param signName
     * @param tagName
     * @return
     */
    public static String getUrlPath(String post, String sonPath, String signName, String tagName) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String url = "http://" + host + ":" + post + sonPath + "/" + signName + "/" + tagName + "/";
        return url;
    }

    /**
     * 文件重命名
     *
     * @param toBeRenamed   将要修改名字的文件
     * @param toFileNewName 新的名字
     * @return
     */
    public static File renameFile(File toBeRenamed, String toFileNewName) {

        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            log.info("File does not exist: " + toBeRenamed.getName());
            return null;
        }

        String filePath = toBeRenamed.getParent();
        File newFile = new File(filePath + File.separatorChar + toFileNewName);
        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            return newFile;
        } else {
            return null;
        }

    }
}
