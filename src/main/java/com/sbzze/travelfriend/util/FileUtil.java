package com.sbzze.travelfriend.util;


import com.sbzze.travelfriend.dto.FileChunkDto;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;


/**
 * 文件工具类
 *
 **/
@Slf4j
@Component
public class FileUtil {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static FileUtil fileUtil;

    @PostConstruct  //用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void init() {
        fileUtil = this;
        fileUtil.redisTemplate = this.redisTemplate;
    }

    /**
     * 上传文件(流式上传)
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
        } catch (IOException e) {
            log.error("文件上传失败", LogsUtil.getStackTrace(e));
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

            File tempFile = File.createTempFile("temp", ".png");
            ImageIO.write(ImageIO.read(httpUrl), "PNG", tempFile);
            byte[] bytes = FileUtils.readFileToByteArray(tempFile);

            tempFile.delete();
            return bytes;
        } catch (IOException e) {
            log.error("文件下载失败", LogsUtil.getStackTrace(e));
           return null;
        }


    }

    /**
     * 下载指定宽高的文件流
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static byte[] downloadFileBytesByWidthAndHeight( String url , int width, int height ) {
        try {
            File tempFile = File.createTempFile("temp", ".png");
            URL httpUrl = new URL(url);
            ImageIO.write(ImageIO.read(httpUrl), "PNG", tempFile);

            Thumbnails.of(tempFile)
                    .size(width, height)
                    .keepAspectRatio(false)
                    .toFile(tempFile);

            byte[] bytes = FileUtils.readFileToByteArray(tempFile);

            tempFile.delete();
            return bytes;
        } catch (IOException e) {
            log.error("文件下载失败", LogsUtil.getStackTrace(e));
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
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            Thumbnails.of(file.getInputStream())
                      .scale(scale)
                      .outputQuality(outputQuality)
                      .toFile(tempFile);

            file.getInputStream().close();
            return true;
        } catch (IOException e) {
            log.error("图片压缩上传失败", LogsUtil.getStackTrace(e));
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


    /**
     * 分片上传
     * @param filePath
     * @param fileChunkDto
     * @return
     */
    public static File sliceUpload( String filePath, FileChunkDto fileChunkDto ) {
        return uploadByMappedByteBuffer(filePath, fileChunkDto);
    }


    // mapped上传方式
    public static File uploadByMappedByteBuffer( String filePath, FileChunkDto fileChunkDto ) {
        boolean isFinish = false;
        File file = null;
        String tempFileName = filePath + "temp_" + fileChunkDto.getFilename();
        File tempFile = new File(tempFileName);

        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }

        try {
            RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rw");
            FileChannel fileChannel = tempRaf.getChannel();

            //偏移量
            long offset = fileChunkDto.getChunksize() * fileChunkDto.getChunknumber();
            //每片字节大小
            byte[] fileData = fileChunkDto.getFilechunk().getBytes();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
            mappedByteBuffer.put(fileData);

            //释放
            freedMappedByteBuffer(mappedByteBuffer);
            fileChannel.close();
            tempRaf.close();

            isFinish = checkAndSetUploadProgress(fileChunkDto, filePath);

            if (isFinish) {
                 file = FileNameUtil.renameFile(tempFile, fileChunkDto.getFilename());
                 tempFile.delete();
            }
        } catch (IOException e) {
            log.error("mappedByteBuffer上传失败",LogsUtil.getStackTrace(e));
        }

        return file;
    }

    // 检查并修改文件上传进度
    private static boolean checkAndSetUploadProgress( FileChunkDto fileChunkDto, String filePath ) {
        boolean isFinish = false;
        String fileName = FileNameUtil.getNameWithOutSuffix(fileChunkDto.getFilename());
        String confFileName = filePath + fileName + ".conf";
        File confFile = new File(confFileName);

        try {
            RandomAccessFile accessFile = new RandomAccessFile(confFile, "rw");
            System.out.println("N0." + fileChunkDto.getChunknumber() + " completed");

            //创建conf文件文件长度为总分片数，每上传一个分块即向conf文件中写入一个127，那么没上传的位置就是默认0,已上传的就是Byte.MAX_VALUE 127
            accessFile.setLength(fileChunkDto.getTotalchunks());
            accessFile.seek(fileChunkDto.getChunknumber());
            accessFile.write(Byte.MAX_VALUE);


            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            byte isComplete = Byte.MAX_VALUE;
            for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
                //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
                isComplete = (byte) (isComplete & completeList[i]);
                System.out.println("check part " + i + " complete?:" + completeList[i]);
            }

            accessFile.close();

            isFinish = setUploadProgress2Redis(fileChunkDto, filePath, confFile, isComplete);

        } catch (IOException e) {
            log.error("检查/修改上传文件进度失败",LogsUtil.getStackTrace(e));
        }

        return isFinish;
    }

    // 把上传进度信息存进redis
    private static boolean setUploadProgress2Redis( FileChunkDto fileChunkDto, String filePath, File confFile, byte isComplete ) {
        String filename = fileChunkDto.getFilename();
        String confFileName = FileNameUtil.getNameWithOutSuffix(filename);
        // 上传完成
        if (isComplete == Byte.MAX_VALUE) {
            fileUtil.redisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, fileChunkDto.getIdentifier(), "true");
            fileUtil.redisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + fileChunkDto.getIdentifier(), filePath + filename);
            //删除缓存
            fileUtil.redisTemplate.delete(Constants.FILE_MD5_KEY + fileChunkDto.getIdentifier());
            confFile.delete();
            return true;
        } else {
            // 上传失败，更新Redis信息
            if (!fileUtil.redisTemplate.opsForHash().hasKey(Constants.FILE_UPLOAD_STATUS, fileChunkDto.getIdentifier())) {
                fileUtil.redisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, fileChunkDto.getIdentifier(), "false");
                fileUtil.redisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + fileChunkDto.getIdentifier(),
                        filePath + confFileName + ".conf");
            }

            return false;
        }
    }

    // 释放mapped线程
    public static void freedMappedByteBuffer(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }

            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
                                new Object[0]);
                        cleaner.clean();
                    } catch (Exception e) {
                        log.error("释放mapped线程失败", LogsUtil.getStackTrace(e));
                    }
                    log.info("释放mapped线程成功");
                    return null;
                }
            });

        } catch (Exception e) {
            log.error("释放mapped线程失败", LogsUtil.getStackTrace(e));
        }
    }


    /**
     * MultipartFile转File
     * @param file
     * @return
     */
    public static MultipartFile transferMultipartFileToFile( File file ) {
        MultipartFile multipartFile = null;
        try {
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
            input.close();
        } catch (IOException e) {
            log.error("MultipartFile转File失败",LogsUtil.getStackTrace(e));
        }
        return multipartFile;
    }

    /**
     * 生成视频缩略图
     * @param videoName
     * @param ffmpegPath
     * @param length
     * @param width
     * @return compressName / null
     */
    public static boolean compressVideoToImage( String videoName, String ffmpegPath, String length, String width, String prefix ) {
        File file = new File(videoName);
        // 原地址 + 前缀 + 文件名.jpg
        String compressName = file.getParent() + "/" + prefix + file.getName().substring(0, file.getName().indexOf(".")) + ".jpg";
        if (!file.exists()) {
            log.warn("文件" + videoName + "不存在");
            return false;
        }
        List<String> commands = new java.util.ArrayList<String>();
        String scale = length + "*" + width;

        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoName);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("1");//这个参数是设置截取视频多少秒时的画面
        //commands.add("-t");
        //commands.add("0.001");
        commands.add("-s");
        commands.add(scale);
        commands.add(compressName);

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            log.info("视频缩略图截取成功");
        } catch (Exception e) {
            log.error("视频缩略图截取失败", LogsUtil.getStackTrace(e));
            return false;
        }
        return true;
    }

}
