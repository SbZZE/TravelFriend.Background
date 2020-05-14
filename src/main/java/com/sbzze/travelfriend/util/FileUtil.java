package com.sbzze.travelfriend.util;


import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 *
 **/
public class FileUtil {

    /**
     *
     * @param file              文件
     * @param path              文件存放路径
     * @param originalFileName  原文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String originalFileName){

        // 生成新的文件名
        String realPath = path + FileNameUtil.getFileName(originalFileName);

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isImage( MultipartFile multipartFile ) {

        File file = new File(multipartFile.getOriginalFilename());
        BufferedImage image;
        boolean flag;

        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            image = ImageIO.read(file);
            if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
                flag = false;
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        // 会在本地产生临时文件，用完后需要删除
        if (file.exists()) {
            file.delete();
        }

        return flag;
    }

}
