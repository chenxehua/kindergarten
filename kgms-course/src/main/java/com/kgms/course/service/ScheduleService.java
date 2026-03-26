package com.kgms.course.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.course.dto.ScheduleAdjustDTO;
import com.kgms.course.entity.ScheduleAdjust;
import com.kgms.course.mapper.ScheduleAdjustMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleAdjustMapper scheduleAdjustMapper;

    /**
     * 申请调课
     */
    public String applyAdjust(ScheduleAdjustDTO dto) {
        ScheduleAdjust adjust = new ScheduleAdjust();
        adjust.setAdjustId(IdGenerator.generateIdWithPrefix("ADJ"));
        adjust.setScheduleId(dto.getScheduleId());
        adjust.setClassId(dto.getClassId());
        adjust.setAdjustType(dto.getAdjustType());
        adjust.setOriginalDate(dto.getOriginalDate());
        adjust.setOriginalTime(dto.getOriginalTime());
        adjust.setAdjustedDate(dto.getAdjustedDate());
        adjust.setAdjustedTime(dto.getAdjustedTime());
        adjust.setOriginalTeacherId(dto.getOriginalTeacherId());
        adjust.setSubstituteTeacherId(dto.getSubstituteTeacherId());
        adjust.setSubstituteReason(dto.getSubstituteReason());
        adjust.setCourseId(dto.getCourseId());
        adjust.setStatus("PENDING");
        adjust.setApplyBy(dto.getApplyBy());

        scheduleAdjustMapper.insert(adjust);
        log.info("Schedule adjust applied: {}", adjust.getAdjustId());
        return adjust.getAdjustId();
    }

    /**
     * 审批调课
     */
    public void approveAdjust(String adjustId, String approveBy, boolean approved, String remark) {
        ScheduleAdjust adjust = scheduleAdjustMapper.selectByAdjustId(adjustId);
        if (adjust == null) {
            throw new RuntimeException("调课记录不存在");
        }

        adjust.setApproveBy(approveBy);
        adjust.setApproveTime(new Date());
        adjust.setApproveRemark(remark);

        // 如果批准，更新状态
        if (approved) {
            adjust.setStatus("APPROVED");
        } else {
            adjust.setStatus("REJECTED");
        }

        scheduleAdjustMapper.updateByAdjustId(adjust);
        log.info("Schedule adjust {} {}", adjustId, approved ? "approved" : "rejected");
    }

    /**
     * 取消调课
     */
    public void cancelAdjust(String adjustId) {
        ScheduleAdjust adjust = scheduleAdjustMapper.selectByAdjustId(adjustId);
        if (adjust == null) {
            throw new RuntimeException("调课记录不存在");
        }

        if (!"PENDING".equals(adjust.getStatus())) {
            throw new RuntimeException("只能取消待审批的调课申请");
        }

        adjust.setStatus("CANCELLED");
        scheduleAdjustMapper.updateByAdjustId(adjust);
        log.info("Schedule adjust cancelled: {}", adjustId);
    }

    /**
     * 获取班级调课记录
     */
    public List<ScheduleAdjust> getClassAdjusts(String classId) {
        return scheduleAdjustMapper.selectByClassId(classId);
    }

    /**
     * 获取待审批的调课申请
     */
    public List<ScheduleAdjust> getPendingAdjusts(String classId) {
        return scheduleAdjustMapper.selectPendingByClassId(classId);
    }

    /**
     * 同意代课
     */
    public void agreeSubstitute(String adjustId, String teacherId) {
        ScheduleAdjust adjust = scheduleAdjustMapper.selectByAdjustId(adjustId);
        if (adjust == null) {
            throw new RuntimeException("调课记录不存在");
        }

        adjust.setSubstituteTeacherId(teacherId);
        adjust.setStatus("APPROVED");
        scheduleAdjustMapper.updateByAdjustId(adjust);
        log.info("Teacher {} agreed to substitute for adjust {}", teacherId, adjustId);
    }

    /**
     * 检查课程冲突
     */
    public boolean checkConflict(String classId, String date, String timeSlot) {
        // TODO: 查询同一时段是否有其他课程
        // 返回true表示有冲突
        return false;
    }
}
