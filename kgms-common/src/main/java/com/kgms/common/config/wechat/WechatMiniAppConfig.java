package com.kgms.common.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置
 * 用于微信登录、模板消息推送等
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatMiniAppConfig {

    /** AppID(应用ID) */
    private String appId;

    /** AppSecret(应用密钥) */
    private String appSecret;

    /** 消息模板ID - 成长记录通知 */
    private String templateRecord;

    /** 消息模板ID - 月度报告通知 */
    private String templateReport;

    /** 消息模板ID - 成长视频通知 */
    private String templateVideo;

    /** 消息模板ID - 系统通知 */
    private String templateNotice;

    /** 消息模板ID - 食谱通知 */
    private String templateFood;

    /** 授权回调URL */
    private String redirectUri;

    /** 是否启用微信登录 */
    private boolean loginEnabled = true;

    /** 是否启用消息推送 */
    private boolean pushEnabled = true;

    /**
     * 获取AccessToken
     * 需要周期性刷新
     */
    public String getAccessTokenUrl() {
        return String.format(
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
            appId, appSecret
        );
    }

    /**
     * 获取用户信息URL
     */
    public String getUserInfoUrl(String accessToken, String openId) {
        return String.format(
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",
            accessToken, openId
        );
    }

    /**
     * 发送模板消息URL
     */
    public String getSendTemplateUrl(String accessToken) {
        return String.format(
            "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s",
            accessToken
        );
    }

    /**
     * 生成小程序码URL
     */
    public String getWxaCodeUrl(String accessToken) {
        return String.format(
            "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s",
            accessToken
        );
    }

    /**
     * 校验配置是否有效
     */
    public boolean isValid() {
        return appId != null && !appId.isEmpty()
            && appSecret != null && !appSecret.isEmpty();
    }
}