package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.model.ResultView;
import com.sbzze.travelfriend.service.TeamAlbumImagesService;
import com.sbzze.travelfriend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/breakpoint")
public class BreakPointController {

    @Autowired
    private TeamAlbumImagesService teamAlbumImagesService;


    @UserLoginToken
    @PostMapping("/upload")
    public Object upload(FileChunkDto fileChunkDto, @RequestParam MultipartFile file) {
        fileChunkDto.setFilechunk(file);
        Object obj = null;

        // 团队
        if (fileChunkDto.getAlbumtype() == Constants.TEAM) {
            String fileId = teamAlbumImagesService.uploadImagesOrVideos(fileChunkDto);
            System.out.println(fileId);
            if ( fileId == null ) {
                return new ResultView<>(201,"文件块传输失败");
            } else {
                return new ResultView<>(200,"文件块传输成功", fileId);
            }

        }
        // 个人
        if (fileChunkDto.getAlbumtype() == Constants.USER) {

        }
        return obj;

    }

}
