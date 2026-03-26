package com.kgms.video.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.video.entity.VideoTemplate;
import com.kgms.video.mapper.VideoTemplateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoTemplateService {

    private final VideoTemplateMapper templateMapper;

    /**
     * 获取所有视频模板
     */
    public List<VideoTemplate> getAllTemplates() {
        return templateMapper.selectAll();
    }

    /**
     * 根据类型获取模板
     */
    public List<VideoTemplate> getTemplatesByType(String templateType) {
        return templateMapper.selectByType(templateType);
    }

    /**
     * 获取默认模板
     */
    public VideoTemplate getDefaultTemplate() {
        return templateMapper.selectDefault();
    }

    /**
     * 获取模板详情
     */
    public VideoTemplate getTemplateDetail(String templateId) {
        return templateMapper.selectByTemplateId(templateId);
    }

    /**
     * 创建模板
     */
    public String createTemplate(VideoTemplate template) {
        template.setTemplateId(IdGenerator.generateIdWithPrefix("TPL"));
        template.setStatus(1);
        templateMapper.insert(template);
        log.info("Video template created: {}", template.getTemplateId());
        return template.getTemplateId();
    }

    /**
     * 更新模板
     */
    public void updateTemplate(VideoTemplate template) {
        templateMapper.updateByTemplateId(template);
        log.info("Video template updated: {}", template.getTemplateId());
    }

    /**
     * 删除模板
     */
    public void deleteTemplate(String templateId) {
        templateMapper.deleteByTemplateId(templateId);
        log.info("Video template deleted: {}", templateId);
    }

    /**
     * 设置默认模板
     */
    public void setDefaultTemplate(String templateId) {
        // 先取消所有默认
        List<VideoTemplate> templates = templateMapper.selectAll();
        for (VideoTemplate t : templates) {
            if (t.getIsDefault() != null && t.getIsDefault() == 1) {
                t.setIsDefault(0);
                templateMapper.updateByTemplateId(t);
            }
        }

        // 设置新的默认
        VideoTemplate template = templateMapper.selectByTemplateId(templateId);
        if (template != null) {
            template.setIsDefault(1);
            templateMapper.updateByTemplateId(template);
            log.info("Default template set to: {}", templateId);
        }
    }

    /**
     * 智能选片 - 根据规则自动选择照片
     */
    public List<String> smartSelectPhotos(String studentId, String startDate, String endDate, int maxCount) {
        // TODO: 从照片库查询符合条件的照片
        // 规则：
        // 1. 优先选择质量好的照片（清晰度、构图）
        // 2. 确保覆盖整个月份（时间分布均匀）
        // 3. 确保每个场景都有（课程、活动、户外等）
        // 4. 确保每个小朋友都有出镜

        // 模拟返回
        log.info("Smart select photos for student: {}, date range: {} to {}, max: {}",
                studentId, startDate, endDate, maxCount);

        return List.of();
    }
}
