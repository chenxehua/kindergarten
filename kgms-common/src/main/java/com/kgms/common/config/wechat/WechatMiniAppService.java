package com.kgms.common.config.wechat;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信小程序服务
 * 处理登录、用户信息、模板消息等
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WechatMiniAppService {

    private final WechatMiniAppConfig config;
    private final RestTemplate restTemplate;

    // AccessToken缓存
    private static final Map<String, TokenInfo> TOKEN_CACHE = new ConcurrentHashMap<>();

    /**
     * 小程序登录
     * @param code 小程序wx.login返回的code
     * @return 登录结果
     */
    public WechatLoginResult login(String code) {
        log.info("微信小程序登录: code={}", code);

        if (!config.isValid()) {
            log.error("微信配置无效: appId={}", config.getAppId());
            throw new RuntimeException("微信配置未正确设置");
        }

        try {
            // 1. 通过code获取session_key和openid
            String sessionUrl = config.getAccessTokenUrl().replace("grant_type=client_credential", "grant_type=authorization_code");
            // 这里使用code2Session接口
            String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                config.getAppId(), config.getAppSecret(), code
            );

            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = JSON.parseObject(response);

            if (json.containsKey("errcode")) {
                log.error("微信登录失败: {}", response);
                throw new RuntimeException("微信登录失败: " + json.getString("errmsg"));
            }

            // 2. 返回登录结果
            WechatLoginResult result = new WechatLoginResult();
            result.setOpenId(json.getString("openid"));
            result.setSessionKey(json.getString("session_key"));
            result.setUnionId(json.getString("unionid"));

            log.info("微信登录成功: openId={}", result.getOpenId());
            return result;

        } catch (Exception e) {
            log.error("微信登录异常: {}", e.getMessage());
            throw new RuntimeException("微信登录异常: " + e.getMessage());
        }
    }

    /**
     * 获取AccessToken
     * 建议缓存，有效期2小时
     */
    public String getAccessToken() {
        // 检查缓存
        TokenInfo cached = TOKEN_CACHE.get("access_token");
        if (cached != null && !cached.isExpired()) {
            return cached.getToken();
        }

        // 重新获取
        try {
            String url = config.getAccessTokenUrl();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = JSON.parseObject(response);

            if (json.containsKey("access_token")) {
                String token = json.getString("access_token");
                int expiresIn = json.getIntValue("expires_in", 7200);

                // 缓存
                TOKEN_CACHE.put("access_token", new TokenInfo(token, expiresIn - 300));

                log.info("获取微信AccessToken成功");
                return token;
            } else {
                log.error("获取AccessToken失败: {}", response);
                return null;
            }
        } catch (Exception e) {
            log.error("获取AccessToken异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 发送模板消息
     * @param openId 接收者OpenID
     * @param templateId 模板ID
     * @param data 模板数据
     * @param page 点击跳转页面
     * @return 是否成功
     */
    public boolean sendTemplateMessage(String openId, String templateId, Map<String, TemplateItem> data, String page) {
        log.info("发送模板消息: openId={}, templateId={}", openId, templateId);

        if (!config.isPushEnabled()) {
            log.warn("微信消息推送未启用");
            return false;
        }

        String accessToken = getAccessToken();
        if (accessToken == null) {
            log.error("获取AccessToken失败，无法发送消息");
            return false;
        }

        try {
            String url = config.getSendTemplateUrl(accessToken);

            Map<String, Object> request = new HashMap<>();
            request.put("touser", openId);
            request.put("template_id", templateId);
            request.put("data", data);
            if (page != null) {
                request.put("page", page);
            }

            String response = restTemplate.postForObject(url, request, String.class);
            JSONObject json = JSON.parseObject(response);

            if (json.getIntValue("errcode") == 0) {
                log.info("模板消息发送成功");
                return true;
            } else {
                log.error("模板消息发送失败: {}", response);
                return false;
            }
        } catch (Exception e) {
            log.error("模板消息发送异常: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 发送成长记录通知
     */
    public boolean sendRecordNotification(String openId, String studentName, String date, String emotion) {
        if (config.getTemplateRecord() == null) {
            log.warn("成长记录模板ID未配置");
            return false;
        }

        Map<String, TemplateItem> data = new HashMap<>();
        data.put("first", new TemplateItem("今日成长记录已发布"));
        data.put("keyword1", new TemplateItem(studentName));
        data.put("keyword2", new TemplateItem(date));
        data.put("keyword3", new TemplateItem(emotion));
        data.put("remark", new TemplateItem("点击查看详情"));

        return sendTemplateMessage(openId, config.getTemplateRecord(), data, "pages/record/detail");
    }

    /**
     * 发送月度报告通知
     */
    public boolean sendReportNotification(String openId, String studentName, String month) {
        if (config.getTemplateReport() == null) {
            log.warn("月度报告模板ID未配置");
            return false;
        }

        Map<String, TemplateItem> data = new HashMap<>();
        data.put("first", new TemplateItem(month + "成长报告已生成"));
        data.put("keyword1", new TemplateItem(studentName));
        data.put("keyword2", new TemplateItem(month));
        data.put("remark", new TemplateItem("点击查看详细报告"));

        return sendTemplateMessage(openId, config.getTemplateReport(), data, "pages/report/detail");
    }

    /**
     * 发送视频生成通知
     */
    public boolean sendVideoNotification(String openId, String studentName, String month, String duration) {
        if (config.getTemplateVideo() == null) {
            log.warn("视频模板ID未配置");
            return false;
        }

        Map<String, TemplateItem> data = new HashMap<>();
        data.put("first", new TemplateItem("成长视频已生成"));
        data.put("keyword1", new TemplateItem(studentName));
        data.put("keyword2", new TemplateItem(month));
        data.put("keyword3", new TemplateItem(duration));
        data.put("remark", new TemplateItem("点击观看孩子的精彩瞬间"));

        return sendTemplateMessage(openId, config.getTemplateVideo(), data, "pages/video/detail");
    }

    // =================== 内部类 ===================

    @lombok.Data
    public static class WechatLoginResult {
        private String openId;
        private String sessionKey;
        private String unionId;
    }

    @lombok.Data
    public static class TemplateItem {
        private String value;
        private String color;

        public TemplateItem() {}
        public TemplateItem(String value) {
            this.value = value;
        }
        public TemplateItem(String value, String color) {
            this.value = value;
            this.color = color;
        }
    }

    @lombok.Data
    private static class TokenInfo {
        private String token;
        private long expireTime;

        TokenInfo(String token, int expiresInSeconds) {
            this.token = token;
            this.expireTime = System.currentTimeMillis() + expiresInSeconds * 1000L;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
}