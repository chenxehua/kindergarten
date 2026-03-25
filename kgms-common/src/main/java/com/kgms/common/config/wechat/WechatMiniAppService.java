package com.kgms.common.config.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WechatMiniAppService {

    private static final Logger log = LoggerFactory.getLogger(WechatMiniAppService.class);

    private final WechatMiniAppConfig config;

    public WechatMiniAppService(WechatMiniAppConfig config) {
        this.config = config;
    }

    public void sendRecordNotification(String openId, String studentName, String date, String emotion) {
        log.info("发送成长记录通知: openId={}, studentName={}", openId, studentName);
    }

    public void sendReportNotification(String openId, String studentName, String month) {
        log.info("发送月度报告通知: openId={}, studentName={}", openId, studentName);
    }

    public void sendVideoNotification(String openId, String studentName, String month, String duration) {
        log.info("发送视频通知: openId={}, studentName={}", openId, studentName);
    }
}
