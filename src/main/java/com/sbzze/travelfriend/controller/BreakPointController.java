package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.FileChunkDto;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.model.ResultView;
import com.sbzze.travelfriend.service.AlbumFileService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/breakpoint")
public class BreakPointController {

    @Autowired
    private AlbumFileService albumFileService;

    @ApiOperation("分片上传")
    @UserLoginToken
    @PostMapping("/upload")
    public Object upload(FileChunkDto fileChunkDto, @RequestParam MultipartFile file) {
        fileChunkDto.setFilechunk(file);
        Pair<Integer, String> pair = albumFileService.uploadImagesOrVideos(fileChunkDto);
        String fileId = pair.getRight();
        int chunkNumber = pair.getLeft();
        if ( fileId == null ) {
            log.info("第" + chunkNumber + "文件块上传完成，共" + fileChunkDto.getTotalchunks() + "块");
            return new ResultView<>(201,"第" + chunkNumber + "文件块上传完成，共" + fileChunkDto.getTotalchunks() + "块");
        } else {
            return new ResultView<>(200, "文件传输成功", fileId);
        }
    }

}
