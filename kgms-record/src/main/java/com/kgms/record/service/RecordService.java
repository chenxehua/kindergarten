package com.kgms.record.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import com.kgms.common.util.IdGenerator;
import com.kgms.record.dto.RecordDTO;
import com.kgms.record.dto.RecordVO;
import com.kgms.record.entity.GrowthRecord;
import com.kgms.record.mapper.GrowthRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final GrowthRecordMapper recordMapper;

    /**
     * 保存成长记录（草稿或发布）
     */
    @Transactional
    public String saveRecord(RecordDTO dto, String teacherId, String classId, boolean publish) {
        // 检查是否已存在当天记录
        GrowthRecord existing = getRecordByStudentAndDate(dto.getStudentId(), dto.getRecordDate());

        GrowthRecord record;
        if (existing != null) {
            record = existing;
        } else {
            record = new GrowthRecord();
            record.setRecordId(IdGenerator.generateStrId());
            record.setStudentId(dto.getStudentId());
            record.setRecordDate(dto.getRecordDate());
            record.setTeacherId(teacherId);
            record.setClassId(classId);
        }

        // 更新记录内容
        updateRecordFromDTO(record, dto);

        if (publish) {
            record.setPublishStatus(1);
            record.setPublishTime(LocalDateTime.now());
        } else {
            record.setPublishStatus(0);
        }

        if (existing != null) {
            recordMapper.updateById(record);
        } else {
            recordMapper.insert(record);
        }

        // TODO: 如果发布成功，推送消息给家长

        return record.getRecordId();
    }

    /**
     * 发布成长记录
     */
    @Transactional
    public void publishRecord(String recordId) {
        GrowthRecord record = getRecordById(recordId);
        if (record == null) {
            throw new BusinessException(404, "记录不存在");
        }

        record.setPublishStatus(1);
        record.setPublishTime(LocalDateTime.now());
        recordMapper.updateById(record);

        // TODO: 推送消息给家长
    }

    /**
     * 获取记录详情
     */
    public RecordVO getRecordDetail(String recordId) {
        GrowthRecord record = getRecordById(recordId);
        if (record == null) {
            throw new BusinessException(404, "记录不存在");
        }
        return convertToVO(record);
    }

    /**
     * 获取学生的记录列表
     */
    public PageResult<RecordVO> getStudentRecords(String studentId, LocalDate startDate, LocalDate endDate,
                                                   Integer page, Integer pageSize) {
        LambdaQueryWrapper<GrowthRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GrowthRecord::getStudentId, studentId);
        if (startDate != null) {
            wrapper.ge(GrowthRecord::getRecordDate, startDate);
        }
        if (endDate != null) {
            wrapper.le(GrowthRecord::getRecordDate, endDate);
        }
        wrapper.orderByDesc(GrowthRecord::getRecordDate);

        IPage<GrowthRecord> pageResult = recordMapper.selectPage(new Page<>(page, pageSize), wrapper);
        List<RecordVO> voList = pageResult.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(voList, pageResult.getTotal(), page, pageSize);
    }

    /**
     * 获取某天的记录
     */
    public RecordVO getRecordByDate(String studentId, LocalDate date) {
        GrowthRecord record = getRecordByStudentAndDate(studentId, date);
        if (record == null) {
            return null;
        }
        return convertToVO(record);
    }

    private GrowthRecord getRecordById(String recordId) {
        return recordMapper.selectOne(
                new LambdaQueryWrapper<GrowthRecord>().eq(GrowthRecord::getRecordId, recordId)
        );
    }

    private GrowthRecord getRecordByStudentAndDate(String studentId, LocalDate date) {
        return recordMapper.selectOne(
                new LambdaQueryWrapper<GrowthRecord>()
                        .eq(GrowthRecord::getStudentId, studentId)
                        .eq(GrowthRecord::getRecordDate, date)
        );
    }

    private void updateRecordFromDTO(GrowthRecord record, RecordDTO dto) {
        if (StringUtils.hasText(dto.getCourseRecord())) {
            record.setCourseRecord(dto.getCourseRecord());
        }
        if (dto.getCoursePhotos() != null) {
            record.setCoursePhotos(dto.getCoursePhotos());
        }
        if (StringUtils.hasText(dto.getEmotionType())) {
            record.setEmotionType(dto.getEmotionType());
        }
        if (StringUtils.hasText(dto.getEmotionDetail())) {
            record.setEmotionDetail(dto.getEmotionDetail());
        }
        if (StringUtils.hasText(dto.getBreakfast())) {
            record.setBreakfast(dto.getBreakfast());
        }
        if (StringUtils.hasText(dto.getLunch())) {
            record.setLunch(dto.getLunch());
        }
        if (StringUtils.hasText(dto.getDinner())) {
            record.setDinner(dto.getDinner());
        }
        if (StringUtils.hasText(dto.getSnack())) {
            record.setSnack(dto.getSnack());
        }
        if (dto.getFoodPhotos() != null) {
            record.setFoodPhotos(dto.getFoodPhotos());
        }
        if (dto.getAllergyFlag() != null) {
            record.setAllergyFlag(dto.getAllergyFlag());
        }
        if (StringUtils.hasText(dto.getSleepTime())) {
            record.setSleepTime(dto.getSleepTime());
        }
        if (StringUtils.hasText(dto.getSleepQuality())) {
            record.setSleepQuality(dto.getSleepQuality());
        }
        if (StringUtils.hasText(dto.getActivityType())) {
            record.setActivityType(dto.getActivityType());
        }
        if (StringUtils.hasText(dto.getActivityDetail())) {
            record.setActivityDetail(dto.getActivityDetail());
        }
        if (dto.getActivityPhotos() != null) {
            record.setActivityPhotos(dto.getActivityPhotos());
        }
        if (StringUtils.hasText(dto.getTemperature())) {
            record.setTemperature(new java.math.BigDecimal(dto.getTemperature()));
        }
        if (StringUtils.hasText(dto.getHealthStatus())) {
            record.setHealthStatus(dto.getHealthStatus());
        }
        if (StringUtils.hasText(dto.getOverallNote())) {
            record.setOverallNote(dto.getOverallNote());
        }
    }

    private RecordVO convertToVO(GrowthRecord record) {
        RecordVO vo = new RecordVO();
        vo.setRecordId(record.getRecordId());
        vo.setStudentId(record.getStudentId());
        vo.setRecordDate(record.getRecordDate());
        vo.setTeacherId(record.getTeacherId());
        vo.setClassId(record.getClassId());
        vo.setCourseRecord(record.getCourseRecord());
        vo.setCoursePhotos(record.getCoursePhotos());
        vo.setEmotionType(record.getEmotionType());
        vo.setEmotionDetail(record.getEmotionDetail());
        vo.setBreakfast(record.getBreakfast());
        vo.setLunch(record.getLunch());
        vo.setDinner(record.getDinner());
        vo.setSnack(record.getSnack());
        vo.setFoodPhotos(record.getFoodPhotos());
        vo.setAllergyFlag(record.getAllergyFlag());
        vo.setAllergyDetail(record.getAllergyDetail());
        vo.setSleepTime(record.getSleepTime());
        vo.setSleepQuality(record.getSleepQuality());
        vo.setSleepNote(record.getSleepNote());
        vo.setActivityType(record.getActivityType());
        vo.setActivityDetail(record.getActivityDetail());
        vo.setActivityPhotos(record.getActivityPhotos());
        vo.setTemperature(record.getTemperature() != null ? record.getTemperature().toString() : null);
        vo.setHealthStatus(record.getHealthStatus());
        vo.setHealthNote(record.getHealthNote());
        vo.setOverallNote(record.getOverallNote());
        vo.setPublishStatus(record.getPublishStatus());
        vo.setPublishTime(record.getPublishTime());
        return vo;
    }
}
