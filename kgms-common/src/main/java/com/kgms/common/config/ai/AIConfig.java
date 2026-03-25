package com.kgms.common.config.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AI服务配置
 * 支持视频生成、图像识别等AI能力
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AIConfig {

    /** 是否启用AI服务 */
    private boolean enabled = false;

    /** AI服务提供商: baidu/alibaba/openai */
    private String provider = "baidu";

    // ========== 百度AI配置 ==========
    private String baiduApiKey;
    private String baiduSecretKey;
    private String baiduEndpoint = "https://aip.baidubce.com";

    // ========== 阿里AI配置 ==========
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    private String aliyunEndpoint = "https://arms.aliyuncs.com";

    // ========== 视频生成配置 ==========
    /** 视频生成超时时间(秒) */
    private int videoGenerateTimeout = 300;

    /** 视频生成最大重试次数 */
    private int videoMaxRetries = 3;

    /** 默认视频模板 */
    private String defaultVideoTemplate = "template_v1";

    /** 视频输出分辨率 */
    private String videoResolution = "1920x1080";

    /** 视频帧率 */
    private int videoFps = 30;

    // ========== 图像识别配置 ==========
    /** 图像识别启用 */
    private boolean imageRecognitionEnabled = true;

    /** 人脸识别启用 */
    private boolean faceRecognitionEnabled = true;

    /** 场景识别启用 */
    private boolean sceneRecognitionEnabled = true;

    /**
     * 获取百度AccessToken
     */
    public String getBaiduAccessToken() {
        // TODO: 实现获取百度AccessToken的逻辑
        return null;
    }

    /**
     * 检查AI服务是否可用
     */
    public boolean isAvailable() {
        if (!enabled) {
            return false;
        }
        switch (provider) {
            case "baidu":
                return baiduApiKey != null && !baiduApiKey.isEmpty();
            case "aliyun":
                return aliyunAccessKeyId != null && !aliyunAccessKeyId.isEmpty();
            default:
                return false;
        }
    }
}