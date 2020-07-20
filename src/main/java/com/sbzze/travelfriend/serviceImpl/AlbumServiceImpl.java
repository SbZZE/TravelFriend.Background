package com.sbzze.travelfriend.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sbzze.travelfriend.dto.AlbumDto;
import com.sbzze.travelfriend.entity.Album;
import com.sbzze.travelfriend.mapper.AlbumMapper;
import com.sbzze.travelfriend.service.AlbumService;
import com.sbzze.travelfriend.util.Constants;
import com.sbzze.travelfriend.util.FileNameUtil;
import com.sbzze.travelfriend.util.FileUtil;
import com.sbzze.travelfriend.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class AlbumServiceImpl extends BaseServiceImpl<AlbumMapper, Album> implements AlbumService {

    /**
     * 判断相册是否存在
     * @param targetId
     * @param albumName
     * @return
     */
    @Override
    public boolean isAlbumExist(String targetId, String albumName) {
        Album album = baseMapper.findAlbumByTypeIdAndAlbumName(targetId, albumName);

        if ( null == album ) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 判断相册是否存在
     * @param id
     * @return
     */
    @Override
    public boolean isAlbumExist(String id) {
        Album album = baseMapper.selectById(id);

        if ( null == album ) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 新建相册
     * @param albumAddDto
     * @return
     */
    @Override
    public String addAlbum( AlbumDto.AlbumAddDto albumAddDto) {
        String signName = null;
        String tagName;
        String filePath;
        Album album = new Album();
        String originalFileName = albumAddDto.getCover().getOriginalFilename();
        String changedFileName = FileNameUtil.getFileName(originalFileName);

        String albumId = UUIDUtil.getUUID();

        if (Integer.valueOf(albumAddDto.getTarget()) == Constants.USER_INT) {
            album.setType(Constants.USER);
            signName = Constants.USER + "/" + Constants.ALBUM;
        }
        if (Integer.valueOf(albumAddDto.getTarget()) == Constants.TEAM_INT) {
            album.setType(TEAM);
            signName = Constants.TEAM + "/" + Constants.ALBUM;
        }
        tagName = albumAddDto.getTargetid() + "/" + albumId + "/" + Constants.COVER;

        // /ROOT_PATH/SON_PATH/${type}/album/${typeid}/${albumid}/cover/
        filePath = FileNameUtil.getFilePath(ROOT_PATH, SON_PATH, signName, tagName);

        if ( !FileUtil.compressFile(albumAddDto.getCover(), filePath, changedFileName, PREFIX) ) {
            log.error("相册封面上传失败");
            return null;
        }

        String urlPath = FileNameUtil.getUrlPath(POST, SON_PATH, signName, tagName);
        String insertFileName = urlPath + PREFIX + changedFileName;

        album.setId(albumId);
        album.setAlbumname(albumAddDto.getAlbumname());
        album.setIntroduction(albumAddDto.getIntroduction());
        album.setTypeId(albumAddDto.getTargetid());
        album.setCover(insertFileName);

        if ( baseMapper.insert(album) <= 0 ){
            log.error("新增相册插入数据库失败");
            return null;
        } else {
            return albumId;
        }
    }

    /**
     * 根据相册ID获取相册封面
     * @param albumid
     * @return
     */
    @Override
    public byte[] getAlbumCover(String albumid) {
        Album album = baseMapper.selectById(albumid);

        if ( null == album || null == album.getCover()) {
            return null;
        }

        String coverUrl = album.getCover();

        byte[] fileBytes = FileUtil.downloadFileBytes(coverUrl);
        return fileBytes;
    }

    /**
     * 修改相册信息(不含相册封面)
     * @param id
     * @param albumname
     * @param introduction
     * @return
     */
    @Override
    public int updateAlbumInfo(String id, String albumname, String introduction) {
        Album album = baseMapper.selectById(id);
        album.setAlbumname(albumname);
        album.setIntroduction(introduction);

        return baseMapper.updateById(album);
    }

    /**
     * 获取相册信息
     * @param targetId
     * @param target
     * @return
     */
    @Override
    public List<AlbumDto.AlbumInfoWithCountDto> getAlbums(String targetId, String target ) {
        Wrapper<Album> wrapper = new EntityWrapper<>();
        String type = null;

        if (Integer.valueOf(target) == Constants.USER_INT) {
            type = Constants.USER;
        }
        if (Integer.valueOf(target) == Constants.TEAM_INT) {
            type = Constants.TEAM;
        }
        wrapper.eq("type", type);
        wrapper.eq("type_id", targetId);

        List<Album> albums = baseMapper.selectList(wrapper);

        List<AlbumDto.AlbumInfoWithCountDto> dtos = new ArrayList<>();

        for (Album album : albums) {
            AlbumDto.AlbumInfoWithCountDto dto = new AlbumDto.AlbumInfoWithCountDto();
            dto.setAlbumid(album.getId());
            dto.setAlbumname(album.getAlbumname());
            dto.setCount(album.getCount());
            dto.setIntroduction(album.getIntroduction());

            dtos.add(dto);
            dto = null;
        }

        return dtos;
    }

}
