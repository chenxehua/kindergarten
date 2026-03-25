package com.kgms.common.config.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatMiniAppConfig {

    private String appId;
    private String appSecret;
    private boolean loginEnabled = true;
    private boolean pushEnabled = true;

    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }
    public String getAppSecret() { return appSecret; }
    public void setAppSecret(String appSecret) { this.appSecret = appSecret; }
    public boolean isLoginEnabled() { return loginEnabled; }
    public void setLoginEnabled(boolean loginEnabled) { this.loginEnabled = loginEnabled; }
    public boolean isPushEnabled() { return pushEnabled; }
    public void setPushEnabled(boolean pushEnabled) { this.pushEnabled = pushEnabled; }
    
    public boolean isValid() {
        return appId != null && !appId.isEmpty() && appSecret != null && !appSecret.isEmpty();
    }
}
