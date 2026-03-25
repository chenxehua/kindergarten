package com.kgms.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotoVO {

    private String photoId;
    private String studentId;
    private String recordId;
    private String photoType;
    private String photoUrl;
    private String thumbnailUrl;
    private String watermarkUrl;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime photoTime;
    
    private String tags;
    private String aiTags;
}
