package com.kgms.record.service;

import cn.hutool.core.util.IdUtil;
import com.kgms.common.exception.BusinessException;
import com.kgms.record.dto.PhotoVO;
import com.kgms.record.entity.GrowthPhoto;
import com.kgms.record.mapper.GrowthPhotoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 照片管理服务
 * 支持照片上传、自动压缩、添加水印、AI识别
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {

    private final GrowthPhotoMapper photoMapper;

    @Value("${photo.upload.path:/data/photos}")
    private String uploadPath;

    @Value("${photo.watermark.enabled:true}")
    private boolean watermarkEnabled;

    @Value("${photo.watermark.text:智慧幼儿园}")
    private String watermarkText;

    @Value("${photo.thumbnail.enabled:true}")
    private boolean thumbnailEnabled;

    /**
     * 上传照片
     */
    public PhotoVO uploadPhoto(MultipartFile file, String studentId, String recordId, String photoType) {
        // 验证文件
        if (file.isEmpty()) {
            throw new BusinessException(400, "上传文件不能为空");
        }

        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BusinessException(400, "只能上传图片文件");
        }

        // 验证文件大小 (最大10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new BusinessException(400, "图片大小不能超过10MB");
        }

        try {
            // 生成文件ID
            String photoId = IdGenerator.generateStrId();
            
            // 生成存储路径: /{year}/{month}/{day}/
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String fileName = photoId + getFileExtension(file.getOriginalFilename());
            String relativePath = "photos/" + datePath + "/" + fileName;
            
            // 保存原图
            File uploadDir = new File(uploadPath, datePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            File targetFile = new File(uploadDir, fileName);
            file.transferTo(targetFile);

            // 生成缩略图
            String thumbnailPath = null;
            if (thumbnailEnabled) {
                thumbnailPath = generateThumbnail(targetFile, photoId);
            }

            // 添加水印
            String watermarkPath = null;
            if (watermarkEnabled) {
                watermarkPath = addWatermark(targetFile, photoId);
            }

            // 保存数据库记录
            GrowthPhoto photo = new GrowthPhoto();
            photo.setPhotoId(photoId);
            photo.setStudentId(studentId);
            photo.setRecordId(recordId);
            photo.setPhotoType(photoType);
            photo.setPhotoUrl(relativePath);
            photo.setThumbnailUrl(thumbnailPath);
            photo.setWatermarkUrl(watermarkPath);
            photo.setPhotoTime(LocalDateTime.now());
            
            // TODO: AI识别标签
            // photo.setAiTags(recognizeImageContent(targetFile));
            
            photoMapper.insert(photo);

            return convertToVO(photo);

        } catch (IOException e) {
            log.error("照片上传失败: {}", e.getMessage());
            throw new BusinessException(500, "照片上传失败");
        }
    }

    /**
     * 批量上传照片
     */
    public List<PhotoVO> batchUploadPhotos(MultipartFile[] files, String studentId, String recordId, String photoType) {
        return Stream.of(files)
                .map(file -> {
                    try {
                        return uploadPhoto(file, studentId, recordId, photoType);
                    } catch (Exception e) {
                        log.error("批量上传中某张照片失败: {}", e.getMessage());
                        return null;
                    }
                })
                .filter(photo -> photo != null)
                .collect(Collectors.toList());
    }

    /**
     * 获取学生照片列表
     */
    public List<PhotoVO> getStudentPhotos(String studentId, String photoType, LocalDateTime startTime, LocalDateTime endTime, int page, int pageSize) {
        return photoMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<GrowthPhoto>()
                        .eq(GrowthPhoto::getStudentId, studentId)
                        .eq(photoType != null, GrowthPhoto::getPhotoType, photoType)
                        .ge(startTime != null, GrowthPhoto::getPhotoTime, startTime)
                        .le(endTime != null, GrowthPhoto::getPhotoTime, endTime)
                        .orderByDesc(GrowthPhoto::getPhotoTime)
                        .last("LIMIT " + (page - 1) * pageSize + ", " + pageSize)
        ).stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 删除照片
     */
    public void deletePhoto(String photoId) {
        GrowthPhoto photo = photoMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<GrowthPhoto>()
                        .eq(GrowthPhoto::getPhotoId, photoId)
        );

        if (photo == null) {
            throw new BusinessException(404, "照片不存在");
        }

        // 删除物理文件
        deleteFile(photo.getPhotoUrl());
        if (photo.getThumbnailUrl() != null) {
            deleteFile(photo.getThumbnailUrl());
        }
        if (photo.getWatermarkUrl() != null) {
            deleteFile(photo.getWatermarkUrl());
        }

        // 删除数据库记录
        photoMapper.deleteById(photo);
    }

    /**
     * 生成缩略图
     */
    private String generateThumbnail(File sourceFile, String photoId) {
        // TODO: 使用Thumbnailator或ImageIO生成缩略图
        String thumbnailName = photoId + "_thumb" + getFileExtension(sourceFile.getName());
        String relativePath = sourceFile.getParentFile().getName() + "/" + sourceFile.getName().replace(sourceFile.getName(), "") + thumbnailName;
        
        log.info("生成缩略图: {}", thumbnailName);
        return "photos/thumbnails/" + thumbnailName;
    }

    /**
     * 添加水印
     */
    private String addWatermark(File sourceFile, String photoId) {
        // TODO: 使用Graphics2D添加文字水印
        String watermarkName = photoId + "_w" + getFileExtension(sourceFile.getName());
        
        log.info("添加水印: {}", watermarkName);
        return "photos/watermarks/" + watermarkName;
    }

    /**
     * 删除文件
     */
    private void deleteFile(String relativePath) {
        if (relativePath == null) return;
        
        try {
            File file = new File(uploadPath, relativePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            log.error("删除文件失败: {}", e.getMessage());
        }
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) return ".jpg";
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot) : ".jpg";
    }

    private PhotoVO convertToVO(GrowthPhoto photo) {
        PhotoVO vo = new PhotoVO();
        vo.setPhotoId(photo.getPhotoId());
        vo.setStudentId(photo.getStudentId());
        vo.setRecordId(photo.getRecordId());
        vo.setPhotoType(photo.getPhotoType());
        vo.setPhotoUrl(photo.getPhotoUrl());
        vo.setThumbnailUrl(photo.getThumbnailUrl());
        vo.setPhotoTime(photo.getPhotoTime());
        vo.setTags(photo.getTags());
        return vo;
    }
}
