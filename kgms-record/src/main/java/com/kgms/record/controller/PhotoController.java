package com.kgms.record.controller;

import com.kgms.common.result.Result;
import com.kgms.record.dto.PhotoVO;
import com.kgms.record.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/record/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    /**
     * 上传照片
     */
    @PostMapping("/upload")
    public Result<PhotoVO> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam String studentId,
            @RequestParam(required = false) String recordId,
            @RequestParam(required = false, defaultValue = "其他") String photoType) {
        
        PhotoVO photo = photoService.uploadPhoto(file, studentId, recordId, photoType);
        return Result.success(photo);
    }

    /**
     * 批量上传照片
     */
    @PostMapping("/upload/batch")
    public Result<List<PhotoVO>> batchUpload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam String studentId,
            @RequestParam(required = false) String recordId,
            @RequestParam(required = false, defaultValue = "其他") String photoType) {
        
        List<PhotoVO> photos = photoService.batchUploadPhotos(files, studentId, recordId, photoType);
        return Result.success(photos);
    }

    /**
     * 获取学生照片列表
     */
    @GetMapping("/list")
    public Result<List<PhotoVO>> getPhotoList(
            @RequestParam String studentId,
            @RequestParam(required = false) String photoType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        
        List<PhotoVO> photos = photoService.getStudentPhotos(studentId, photoType, startTime, endTime, page, pageSize);
        return Result.success(photos);
    }

    /**
     * 删除照片
     */
    @DeleteMapping("/delete")
    public Result<Void> deletePhoto(@RequestParam String photoId) {
        photoService.deletePhoto(photoId);
        return Result.success();
    }
}
