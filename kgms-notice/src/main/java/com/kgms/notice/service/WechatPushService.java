package com.kgms.notice.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信推送服务
 * 用于向家长发送微信模板消息通知
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WechatPushService {

    @Value("${wechat.appid:wx123456789}")
    private String appid;

    @Value("${wechat.secret:xxxxx}")
    private String secret;

    @Value("${wechat.template.notice:xxxxx}")
    private String noticeTemplateId;

    @Value("${wechat.template.record:xxxxx}")
    private String recordTemplateId;

    @Value("${wechat.template.report:xxxxx}")
    private String reportTemplateId;

    @Value("${wechat.template.video:xxxxx}")
    private String videoTemplateId;

    private final RestTemplate restTemplate;

    /**
     * 获取微信AccessToken
     */
    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        
        try {
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = JSON.parseObject(response);
            
            if (json.containsKey("access_token")) {
                return json.getString("access_token");
            } else {
                log.error("获取微信AccessToken失败: {}", response);
                return null;
            }
        } catch (Exception e) {
            log.error("获取微信AccessToken异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 发送通知公告模板消息
     */
    public boolean sendNoticeMessage(String openid, String title, String content, String date) {
        Map<String, Object> data = new HashMap<>();
        
        // 标题
        Map<String, Object> first = new HashMap<>();
        first.put("value", title);
        first.put("color", "#173177");
        data.put("first", first);
        
        // 内容
        Map<String, Object> keyword1 = new HashMap<>();
        keyword1.put("value", content);
        data.put("keyword1", keyword1);
        
        // 时间
        Map<String, Object> keyword2 = new HashMap<>();
        keyword2.put("value", date);
        data.put("keyword2", keyword2);
        
        // 备注
        Map<String, Object> remark = new HashMap<>();
        remark.put("value", "点击查看详情");
        data.put("remark", remark);
        
        return sendTemplateMessage(openid, noticeTemplateId, data);
    }

    /**
     * 发送成长记录模板消息
     */
    public boolean sendRecordMessage(String openid, String studentName, String date, String emotion, String note) {
        Map<String, Object> data = new HashMap<>();
        
        // 孩子姓名
        Map<String, Object> first = new HashMap<>();
        first.put("value", "今日成长记录已发布");
        data.put("first", first);
        
        // 姓名
        Map<String, Object> keyword1 = new HashMap<>();
        keyword1.put("value", studentName);
        data.put("keyword1", keyword1);
        
        // 日期
        Map<String, Object> keyword2 = new HashMap<>();
        keyword2.put("value", date);
        data.put("keyword2", keyword2);
        
        // 情绪
        Map<String, Object> keyword3 = new HashMap<>();
        keyword3.put("value", emotion);
        data.put("keyword3", keyword3);
        
        // 备注
        Map<String, Object> remark = new HashMap<>();
        remark.put("value", note != null ? note : "点击查看详情");
        data.put("remark", remark);
        
        return sendTemplateMessage(openid, recordTemplateId, data);
    }

    /**
     * 发送月度报告模板消息
     */
    public boolean sendReportMessage(String openid, String studentName, String month, String summary) {
        Map<String, Object> data = new HashMap<>();
        
        // 标题
        Map<String, Object> first = new HashMap<>();
        first.put("value", month + "成长报告已生成");
        data.put("first", first);
        
        // 孩子姓名
        Map<String, Object> keyword1 = new HashMap<>();
        keyword1.put("value", studentName);
        data.put("keyword1", keyword1);
        
        // 月份
        Map<String, Object> keyword2 = new HashMap<>();
        keyword2.put("value", month);
        data.put("keyword2", keyword2);
        
        // 摘要
        Map<String, Object> keyword3 = new HashMap<>();
        keyword3.put("value", summary != null ? summary.substring(0, Math.min(summary.length(), 20)) : "点击查看完整报告");
        data.put("keyword3", keyword3);
        
        // 备注
        Map<String, Object> remark = new HashMap<>();
        remark.put("value", "点击查看详细成长报告");
        data.put("remark", remark);
        
        return sendTemplateMessage(openid, reportTemplateId, data);
    }

    /**
     * 发送成长视频模板消息
     */
    public boolean sendVideoMessage(String openid, String studentName, String month, String duration) {
        Map<String, Object> data = new HashMap<>();
        
        // 标题
        Map<String, Object> first = new HashMap<>();
        first.put("value", "成长视频已生成");
        data.put("first", first);
        
        // 孩子姓名
        Map<String, Object> keyword1 = new HashMap<>();
        keyword1.put("value", studentName);
        data.put("keyword1", keyword1);
        
        // 月份
        Map<String, Object> keyword2 = new HashMap<>();
        keyword2.put("value", month);
        data.put("keyword2", keyword2);
        
        // 时长
        Map<String, Object> keyword3 = new HashMap<>();
        keyword3.put("value", duration);
        data.put("keyword3", keyword3);
        
        // 备注
        Map<String, Object> remark = new HashMap<>();
        remark.put("value", "点击观看孩子的精彩成长瞬间");
        data.put("remark", remark);
        
        return sendTemplateMessage(openid, videoTemplateId, data);
    }

    /**
     * 发送模板消息
     */
    private boolean sendTemplateMessage(String openid, String templateId, Map<String, Object> data) {
        String accessToken = getAccessToken();
        if (accessToken == null) {
            log.error("获取AccessToken失败");
            return false;
        }

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        Map<String, Object> request = new HashMap<>();
        request.put("touser", openid);
        request.put("template_id", templateId);
        request.put("data", data);
        
        // 点击跳转小程序页面
        Map<String, String> miniprogram = new HashMap<>();
        miniprogram.put("appid", appid);
        miniprogram.put("pagepath", "pages/home/home");
        request.put("miniprogram", miniprogram);

        try {
            String response = restTemplate.postForObject(url, request, String.class);
            JSONObject json = JSON.parseObject(response);
            
            if (json.containsKey("errcode") && json.getInteger("errcode") == 0) {
                log.info("模板消息发送成功: openid={}", openid);
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
}
