package com.kgms.notice.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.notice.dto.PushConfigDTO;
import com.kgms.notice.dto.PushRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PushService {

    private final WechatPushService wechatPushService;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // 内存存储推送配置
    private static final Map<String, PushConfigDTO> PUSH_CONFIGS = new HashMap<>();

    static {
        // 默认配置
        PushConfigDTO daily = new PushConfigDTO();
        daily.setConfigId("push_001");
        daily.setPushType("DAILY");
        daily.setEnabled(true);
        daily.setPushTime("16:00");
        daily.setQuietHoursStart("22:00");
        daily.setQuietHoursEnd("07:00");
        PUSH_CONFIGS.put(daily.getConfigId(), daily);
    }

    /**
     * 推送成长记录给家长
     */
    public void pushRecordToParent(String parentId, String studentId, String recordId, String content) {
        // 检查是否在免打扰时间
        if (isQuietTime("RECORD")) {
            log.info("In quiet hours, skip push");
            return;
        }

        // 发送微信服务通知
        wechatPushService.sendTemplateMessage(parentId, "成长记录推送", content, recordId);

        log.info("Pushed record {} to parent {}", recordId, parentId);
    }

    /**
     * 推送通知给家长
     */
    public void pushNoticeToParents(List<String> parentIds, String noticeId, String title, String content) {
        for (String parentId : parentIds) {
            if (isQuietTime("NOTICE")) {
                continue;
            }
            wechatPushService.sendTemplateMessage(parentId, title, content, noticeId);
        }
        log.info("Pushed notice {} to {} parents", noticeId, parentIds.size());
    }

    /**
     * 推送每日汇总
     */
    public void pushDailySummary(String parentId, String studentId) {
        PushConfigDTO config = PUSH_CONFIGS.get("push_001");
        if (config == null || !Boolean.TRUE.equals(config.getEnabled())) {
            return;
        }

        if (isQuietTime("DAILY")) {
            return;
        }

        // 模拟汇总内容
        String content = "今日成长记录已发布，点击查看";
        wechatPushService.sendTemplateMessage(parentId, "每日汇总", content, studentId);

        log.info("Pushed daily summary to parent {}", parentId);
    }

    /**
     * 检查是否在免打扰时间
     */
    private boolean isQuietTime(String pushType) {
        String configKey = "push_" + pushType;
        PushConfigDTO config = PUSH_CONFIGS.get(configKey);

        if (config == null || !Boolean.TRUE.equals(config.getEnabled())) {
            return false;
        }

        LocalTime now = LocalTime.now();
        LocalTime quietStart = LocalTime.parse(config.getQuietHoursStart(), timeFormatter);
        LocalTime quietEnd = LocalTime.parse(config.getQuietHoursEnd(), timeFormatter);

        // 处理跨天情况
        if (quietStart.isAfter(quietEnd)) {
            // 22:00 - 07:00
            return now.isAfter(quietStart) || now.isBefore(quietEnd);
        } else {
            return now.isAfter(quietStart) && now.isBefore(quietEnd);
        }
    }

    /**
     * 获取推送配置
     */
    public PushConfigDTO getPushConfig(String pushType) {
        return PUSH_CONFIGS.get("push_" + pushType);
    }

    /**
     * 更新推送配置
     */
    public void updatePushConfig(PushConfigDTO config) {
        config.setConfigId("push_" + config.getPushType());
        PUSH_CONFIGS.put(config.getConfigId(), config);
        log.info("Updated push config: {}", config.getConfigId());
    }

    /**
     * 批量推送
     */
    public void batchPush(String pushType, List<String> userIds, String title, String content, String relatedId) {
        int successCount = 0;
        for (String userId : userIds) {
            try {
                if (isQuietTime(pushType)) {
                    continue;
                }
                wechatPushService.sendTemplateMessage(userId, title, content, relatedId);
                successCount++;
            } catch (Exception e) {
                log.error("Push failed to user: {}", userId, e);
            }
        }
        log.info("Batch push completed: {} / {}", successCount, userIds.size());
    }
}
