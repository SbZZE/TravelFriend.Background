package com.sbzze.travelfriend.util;


import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 文件上传工具类
 *
 **/
public class FileUtil {

    /**
     * 上传文件
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

    /**
     * 判断是否为图片
     * @param multipartFile
     * @return
     */
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

    /**
     * 删除文件夹下所有文件（不包含该文件夹）
     * @param dirPath 文件目录
     */
    public static void delFile( String dirPath ){

        File file = new File(dirPath);
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (dirPath.endsWith(File.separator)) {
                temp = new File(dirPath + tempList[i]);
            } else {
                temp = new File(dirPath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delFile(dirPath + "/" + tempList[i]);// 删除文件夹里面的文件
            }
        }

    }

    /**
     * 上传头像
     * @param filePath        文件路径
     * @param file            文件
     * @param changedFileName 新文件名
     * @return
     */
    public static boolean uploadAvatar( String filePath, MultipartFile file, String changedFileName) {

        String fileName = filePath + changedFileName;
        File dest = new File(fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        } else {
            FileUtil.delFile(filePath);
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

    /**
     * 下载文件流
     * @param url  地址
     * @return
     */
    public static byte[] downloadFileBytes( String url) {

        if ( url.isEmpty() ) {
            return null;
        }
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            InputStream inputStream = conn.getInputStream();

            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());

            inputStream.close();
            conn.disconnect();

            return bytes;
        } catch (IOException e) {
           e.printStackTrace();
           return null;
        }

    }

    /**
     * 压缩上传图片
     * @param file
     * @param filePath
     * @param changedFileName
     * @param prefix
     * @return
     */
    public static boolean compressFile( MultipartFile file, String filePath, String changedFileName, String prefix ) {

        String fileName = filePath + prefix + changedFileName;

        File tempFile = new File(fileName);
        try {
            Thumbnails.of(file.getInputStream())
                      .scale(1f)
                      .outputQuality(0.2f)
                      .toFile(tempFile);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
