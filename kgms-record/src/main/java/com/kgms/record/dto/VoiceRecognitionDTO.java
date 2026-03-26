package com.kgms.record.dto;

import lombok.Data;

/**
 * 语音识别结果DTO
 */
@Data
public class VoiceRecognitionDTO {

    /** 原始语音URL */
    private String voiceUrl;
    /** 识别文本 */
    private String recognizedText;
    /** 语音时长(秒) */
    private Integer duration;
    /** 置信度 */
    private Double confidence;
    /** 识别状态 */
    private String status;
}