package com.sbzze.travelfriend.util;


import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件上传工具类
 *
 **/
public class FileUtil {

    /**
     * 上传照片
     * @param file
     * @param path
     * @param newFileName
     * @return
     */
    public static boolean upload( MultipartFile file, String path, String newFileName ){

        // 生成新的文件名
        String realPath = path + "/" + newFileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
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

    public static void delFile( String dirPath ){
        File file = new File(dirPath);

        if( file.isFile() ){
            file.delete();
        } else {
            File[] files = file.listFiles();
            if( files != null ){
                for (int i = 0; i < files.length; i++){
                    delFile(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

    public static boolean uploadAvatar( String rootPath, String sonPath, String username, MultipartFile file, String changedFileName) {

        String filePath = rootPath + sonPath;
        String newFilePath = filePath + "/" + username + "/";
        String newFileName = newFilePath + changedFileName;
        File dest = new File(newFileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        } else {
            FileUtil.delFile(newFilePath);
            dest.getParentFile().mkdirs();
        }
        try {
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(dest);
            byte[] bytes = new byte[1024];
            int res = 0;
            while ((res = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, res);
            }
            inputStream.close();
            outputStream.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static byte[] downloadFileBytes( String url, String fileName ) {

        if ( url.isEmpty() ) {
            return null;
        }
        try {

            URL httpUrl = new URL(url);
            fileName = fileName.substring(0, fileName.indexOf("."));
            File tempFile = File.createTempFile(fileName, ".JPG");

            FileUtils.copyURLToFile(httpUrl, tempFile);
            tempFile.deleteOnExit();

            FileInputStream inputStream = new FileInputStream(tempFile);
            byte[] bytes = new byte[inputStream.available()];

            inputStream.read(bytes, 0, inputStream.available());

            return bytes;

        } catch (IOException e) {
           e.printStackTrace();
           return null;
        }
    }
}
