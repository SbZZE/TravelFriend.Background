package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.model.ResultView;
import com.sbzze.travelfriend.service.TeamAlbumImagesService;
import com.sbzze.travelfriend.service.UserAlbumImagesService;
import com.sbzze.travelfriend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/breakpoint")
public class BreakPointController {

    @Autowired
    private TeamAlbumImagesService teamAlbumImagesService;
    @Autowired
    private UserAlbumImagesService userAlbumImagesService;


    @UserLoginToken
    @PostMapping("/upload")
    public Object upload(FileChunkDto fileChunkDto, @RequestParam MultipartFile file) {
        fileChunkDto.setFilechunk(file);
        String fileId = null;
        // 团队
        if (fileChunkDto.getAlbumtype() == Constants.TEAM) {
            fileId = teamAlbumImagesService.uploadImagesOrVideos(fileChunkDto);
            return new ResultView<>(200,"文件块传输成功", fileId);
        }
        // 个人
        if (fileChunkDto.getAlbumtype() == Constants.USER) {
            fileId = userAlbumImagesService.uploadImagesOrVideos(fileChunkDto);
            return new ResultView<>(200, "文件块传输成功", fileId);
        }

        return new ResultView<>(201, "文件传输失败", fileId);
    }

}
