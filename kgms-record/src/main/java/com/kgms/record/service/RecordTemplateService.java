package com.kgms.record.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.record.dto.RecordTemplateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordTemplateService {

    // 内存存储模板（实际应存数据库）
    private static final Map<String, RecordTemplateDTO> TEMPLATES = new HashMap<>();

    static {
        // 初始化默认模板
        RecordTemplateDTO tpl1 = new RecordTemplateDTO();
        tpl1.setTemplateId("tpl_001");
        tpl1.setTemplateName("今日户外活动丰富");
        tpl1.setContent("今天进行了丰富的户外活动，孩子参与积极");
        tpl1.setScenario("activity");
        tpl1.setIsDefault(true);
        TEMPLATES.put(tpl1.getTemplateId(), tpl1);

        RecordTemplateDTO tpl2 = new RecordTemplateDTO();
        tpl2.setTemplateId("tpl_002");
        tpl2.setTemplateName("今日午餐正常");
        tpl2.setContent("午餐食欲良好，全部吃完");
        tpl2.setScenario("diet");
        tpl2.setIsDefault(true);
        TEMPLATES.put(tpl2.getTemplateId(), tpl2);

        RecordTemplateDTO tpl3 = new RecordTemplateDTO();
        tpl3.setTemplateId("tpl_003");
        tpl3.setTemplateName("情绪稳定");
        tpl3.setContent("今天情绪稳定，与同伴相处融洽");
        tpl3.setScenario("emotion");
        tpl3.setIsDefault(true);
        TEMPLATES.put(tpl3.getTemplateId(), tpl3);
    }

    /**
     * 获取所有模板
     */
    public List<RecordTemplateDTO> getAllTemplates() {
        return new ArrayList<>(TEMPLATES.values());
    }

    /**
     * 获取默认模板
     */
    public List<RecordTemplateDTO> getDefaultTemplates() {
        List<RecordTemplateDTO> defaults = new ArrayList<>();
        for (RecordTemplateDTO tpl : TEMPLATES.values()) {
            if (Boolean.TRUE.equals(tpl.getIsDefault())) {
                defaults.add(tpl);
            }
        }
        return defaults;
    }

    /**
     * 根据场景获取模板
     */
    public List<RecordTemplateDTO> getTemplatesByScenario(String scenario) {
        List<RecordTemplateDTO> result = new ArrayList<>();
        for (RecordTemplateDTO tpl : TEMPLATES.values()) {
            if (scenario.equals(tpl.getScenario())) {
                result.add(tpl);
            }
        }
        return result;
    }

    /**
     * 获取模板详情
     */
    public RecordTemplateDTO getTemplateDetail(String templateId) {
        return TEMPLATES.get(templateId);
    }

    /**
     * 创建模板
     */
    public String createTemplate(RecordTemplateDTO dto) {
        dto.setTemplateId(IdGenerator.generateIdWithPrefix("TPL"));
        TEMPLATES.put(dto.getTemplateId(), dto);
        log.info("Template created: {}", dto.getTemplateId());
        return dto.getTemplateId();
    }

    /**
     * 更新模板
     */
    public void updateTemplate(RecordTemplateDTO dto) {
        if (TEMPLATES.containsKey(dto.getTemplateId())) {
            TEMPLATES.put(dto.getTemplateId(), dto);
            log.info("Template updated: {}", dto.getTemplateId());
        }
    }

    /**
     * 删除模板
     */
    public void deleteTemplate(String templateId) {
        TEMPLATES.remove(templateId);
        log.info("Template deleted: {}", templateId);
    }

    /**
     * 获取热门模板
     */
    public List<RecordTemplateDTO> getPopularTemplates(int limit) {
        // 实际应按使用次数排序
        return getDefaultTemplates().subList(0, Math.min(limit, getDefaultTemplates().size()));
    }
}