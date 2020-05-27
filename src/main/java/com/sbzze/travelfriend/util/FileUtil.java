package com.sbzze.travelfriend.util;


import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * 文件上传工具类
 *
 **/
public class FileUtil {

    /**
     * 上传文件
     * @param file 源文件
     * @param dest 目标文件
     * @return
     */
    public static boolean upload( MultipartFile file, File dest ){

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
     * 通过删除已存在文件夹的方式上传图片（适用于只需保存一张图片的情况）
     * @param filePath        文件路径
     * @param file            文件
     * @param changedFileName 新文件名
     * @return
     */
    public static boolean uploadByDeleteExistFile( String filePath, MultipartFile file, String changedFileName) {

        String fileName = filePath + changedFileName;
        File dest = new File(fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        } else {
            FileUtil.delFile(filePath);
            dest.getParentFile().mkdirs();
        }

        return upload(file, dest);

    }

    /**
     * 下载文件流
     * @param url  地址
     * @return
     */
    public static byte[] downloadFileBytes( String url ) {

        if (url.isEmpty()) {
            return null;
        }

        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            //ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);

            byte[] bytes = new byte[conn.getContentLength()];
            //int res = 0;
            while (inputStream.read(bytes, 0, conn.getContentLength()) != -1);
            //inputStream.read(bytes, 0, conn.getContentLength());

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
    public static boolean compressFile( MultipartFile file, String filePath, String changedFileName, String prefix, float scale, float outputQuality ) {

        String fileName = filePath + prefix + changedFileName;

        File tempFile = new File(fileName);
        try {
            Thumbnails.of(file.getInputStream())
                      .scale(scale)
                      .outputQuality(outputQuality)
                      .toFile(tempFile);

            file.getInputStream().close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 上传相册
     * @param filePath
     * @param file
     * @param changedFileName
     * @return
     */
    public static boolean uploadAlbum( String filePath, MultipartFile file, String changedFileName ) {
        String fileName = filePath + changedFileName;
        File dest = new File(fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        return upload(file, dest);
    }
}
