package com.sbzze.travelfriend.controller;

import com.sbzze.travelfriend.dto.AlbumDto;
import com.sbzze.travelfriend.entity.AlbumFile;
import com.sbzze.travelfriend.filter.UserLoginToken;
import com.sbzze.travelfriend.model.ResultView;
import com.sbzze.travelfriend.service.*;
import com.sbzze.travelfriend.util.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumFileService albumFileService;

    // 新建相册
    @ApiOperation("新建相册")
    @UserLoginToken
    @PostMapping("/create")
    public Object addAlbum( AlbumDto.AlbumAddDto albumAddDto, @RequestParam MultipartFile cover ) {
        albumAddDto.setCover(cover);
        if ( albumService.isAlbumExist(albumAddDto.getTargetid(), albumAddDto.getAlbumname()) ) {
            return new ResultView<>(201,"相册已存在");
        }
        String albumId = albumService.addAlbum(albumAddDto);
        if ( albumId == null ) {
            log.error("新建相册失败");
            return new ResultView<>(202,"新建相册失败");
        } else {
            return new ResultView<>(200,"新建相册成功", albumId);
        }
    }

    // 获取相册封面
    @ApiOperation("获取相册封面")
    @UserLoginToken
    @GetMapping("/cover")
    @ResponseBody
    public byte[] getAlbumCover( @RequestParam String albumid ) {
        if ( !albumService.isAlbumExist(albumid) ) {
            return null;
        }
        byte[] file = albumService.getAlbumCover(albumid);
        if ( file == null ) {
            log.error("获取相册封面失败");
            return null;
        } else {
            return file;
        }
    }

    // 修改相册信息(不含相册封面)
    @ApiOperation("修改相册信息(不含相册封面)")
    @UserLoginToken
    @PostMapping("/info")
    public Object updateAlbumInfo( @RequestBody AlbumDto.AlbumInfoDto albumInfoDto) {
        if ( !albumService.isAlbumExist(albumInfoDto.getAlbumid()) ) {
            return new ResultView<>(201, "相册不存在");
        }
        if ( albumService.updateAlbumInfo(albumInfoDto.getAlbumid(), albumInfoDto.getAlbumname(), albumInfoDto.getIntroduction()) <= 0 ) {
            log.error("修改相册信息失败");
            return new ResultView<>(202, "修改相册信息失败");
        } else {
            return new ResultView<>(200, "修改相册封面成功");
        }
    }

    // 获取相册文件列表
    @ApiOperation("获取相册文件列表")
    @UserLoginToken
    @GetMapping("/file/list")
    @ResponseBody
    public Object getAlbumFileList( @RequestParam String albumid ) {
        if ( !albumService.isAlbumExist(albumid) ) {
            return new ResultView<>(201, "相册不存在");
        }
        List<AlbumFile> albumFiles = albumFileService.getAlbumFileList(albumid);
        if (albumFiles.size() <= 0) {
            log.error("获取相册文件列表失败");
            return new ResultView<>(202, "获取相册文件列表失败");
        }
        List<AlbumDto.AlbumFileInfoDto> dtos = new ArrayList<>();

        for (AlbumFile albumFile : albumFiles) {
            AlbumDto.AlbumFileInfoDto dto = new AlbumDto.AlbumFileInfoDto();
            dto.setFileid(albumFile.getId());
            if (albumFile.getType().equals(Constants.IMAGE)){
                dto.setType(Constants.IMAGE_INT);
            }
            if (albumFile.getType().equals(Constants.VIDEO)){
                dto.setType(Constants.VIDEO_INT);
            }
            dtos.add(dto);
        }
        return new ResultView<>(200, "获取相册文件列表成功", dtos);
    }

    // 获取文件缩略图
    @ApiOperation("获取文件缩略图")
    @UserLoginToken
    @GetMapping("/file/thumb")
    @ResponseBody
    public byte[] getCompressFile( @RequestParam String fileid ) {
        byte[] compressFile = albumFileService.getCompressFile(fileid);
        if ( null == compressFile ) {
            log.error("获取文件缩略图失败");
            return null;
        } else {
            return compressFile;
        }
    }

    // 获取所有相册
    @ApiOperation("获取所有相册")
    @UserLoginToken
    @GetMapping("/list")
    @ResponseBody
    public Object getAlbums( @RequestParam String targetid, @RequestParam String target ) {
        List<AlbumDto.AlbumInfoWithCountDto> albums;
        albums = albumService.getAlbums(targetid, target);
        if ( albums.size() <= 0 ) {
            return new ResultView<>(201, "获取所有相册失败");
        } else {
            return new ResultView<>(200, "获取所有相册成功", albums);
        }
    }
}
