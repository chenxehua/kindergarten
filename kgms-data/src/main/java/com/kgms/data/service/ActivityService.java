package com.kgms.data.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.data.dto.ActivityDTO;
import com.kgms.data.entity.Activity;
import com.kgms.data.entity.ActivitySignup;
import com.kgms.data.mapper.ActivityMapper;
import com.kgms.data.mapper.ActivitySignupMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivitySignupMapper activitySignupMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 创建活动
     */
    public Activity createActivity(ActivityDTO dto) {
        Activity activity = new Activity();
        activity.setActivityId(IdGenerator.generateIdWithPrefix("ACT"));
        activity.setKgId(dto.getKgId());
        activity.setActivityName(dto.getActivityName());
        activity.setActivityType(dto.getActivityType());
        activity.setActivityTime(dto.getActivityTime());
        activity.setEndTime(dto.getEndTime());
        activity.setLocation(dto.getLocation());
        activity.setDescription(dto.getDescription());
        activity.setProcess(dto.getProcess());
        activity.setTargetType(dto.getTargetType());
        activity.setTargetIds(convertListToJson(dto.getTargetIds()));
        activity.setMaxParticipants(dto.getMaxParticipants());
        activity.setFee(dto.getFee() != null ? BigDecimal.valueOf(dto.getFee()) : null);
        activity.setFeeDescription(dto.getFeeDescription());
        activity.setRequireSignup(dto.getRequireSignup());
        activity.setSignupDeadline(dto.getSignupDeadline());
        activity.setStatus("DRAFT");
        activity.setPrincipalId(dto.getPrincipalId());
        activity.setCreateBy(dto.getCreateBy());

        activityMapper.insert(activity);
        log.info("Activity created: {}", activity.getActivityId());
        return activity;
    }

    /**
     * 发布活动
     */
    public Activity publishActivity(String activityId) {
        Activity activity = activityMapper.selectByActivityId(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        activity.setStatus("PUBLISHED");
        activity.setPublishTime(new Date());
        activityMapper.updateByActivityId(activity);
        log.info("Activity published: {}", activityId);
        return activity;
    }

    /**
     * 取消活动
     */
    public Activity cancelActivity(String activityId) {
        Activity activity = activityMapper.selectByActivityId(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        activity.setStatus("CANCELLED");
        activityMapper.updateByActivityId(activity);
        log.info("Activity cancelled: {}", activityId);
        return activity;
    }

    /**
     * 获取活动详情
     */
    public ActivityDTO getActivityDetail(String activityId) {
        Activity activity = activityMapper.selectByActivityId(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        return convertToDTO(activity);
    }

    /**
     * 获取活动列表
     */
    public List<ActivityDTO> getActivityList(String kgId, String activityType, String status, String startDate, String endDate) {
        List<Map<String, Object>> maps = activityMapper.selectActivityList(kgId, activityType, status, startDate, endDate);
        List<ActivityDTO> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Activity activity = objectMapper.convertValue(map, Activity.class);
            result.add(convertToDTO(activity));
        }
        return result;
    }

    /**
     * 报名参加活动
     */
    public ActivitySignup signUp(String activityId, String studentId, String parentId, String remark) {
        // 检查是否已报名
        ActivitySignup existing = activitySignupMapper.selectByActivityAndStudent(activityId, studentId);
        if (existing != null) {
            throw new RuntimeException("您已报名此活动");
        }

        Activity activity = activityMapper.selectByActivityId(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        // 检查是否需要审核
        String status = "PENDING";
        if (activity.getRequireSignup() == 0) {
            status = "APPROVED";
        }

        ActivitySignup signup = new ActivitySignup();
        signup.setSignupId(IdGenerator.generateIdWithPrefix("SIGN"));
        signup.setActivityId(activityId);
        signup.setStudentId(studentId);
        signup.setParentId(parentId);
        signup.setRemark(remark);
        signup.setStatus(status);

        activitySignupMapper.insert(signup);
        log.info("Activity signup: activityId={}, studentId={}", activityId, studentId);
        return signup;
    }

    /**
     * 审批报名
     */
    public ActivitySignup approveSignup(String signupId, String approveBy, boolean approved, String remark) {
        ActivitySignup signup = activitySignupMapper.selectBySignupId(signupId);
        if (signup == null) {
            throw new RuntimeException("报名记录不存在");
        }

        signup.setApproveBy(approveBy);
        signup.setApproveTime(new Date());
        signup.setStatus(approved ? "APPROVED" : "REJECTED");
        signup.setApproveRemark(remark);

        activitySignupMapper.updateBySignupId(signup);
        log.info("Signup {} {}", signupId, approved ? "approved" : "rejected");
        return signup;
    }

    /**
     * 取消报名
     */
    public void cancelSignup(String signupId) {
        ActivitySignup signup = activitySignupMapper.selectBySignupId(signupId);
        if (signup == null) {
            throw new RuntimeException("报名记录不存在");
        }

        signup.setStatus("CANCELLED");
        activitySignupMapper.updateBySignupId(signup);
        log.info("Signup cancelled: {}", signupId);
    }

    private ActivityDTO convertToDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setActivityId(activity.getActivityId());
        dto.setKgId(activity.getKgId());
        dto.setActivityName(activity.getActivityName());
        dto.setActivityType(activity.getActivityType());
        dto.setActivityTime(activity.getActivityTime());
        dto.setEndTime(activity.getEndTime());
        dto.setLocation(activity.getLocation());
        dto.setDescription(activity.getDescription());
        dto.setProcess(activity.getProcess());
        dto.setTargetType(activity.getTargetType());
        dto.setMaxParticipants(activity.getMaxParticipants());
        dto.setFee(activity.getFee() != null ? activity.getFee().doubleValue() : null);
        dto.setFeeDescription(activity.getFeeDescription());
        dto.setRequireSignup(activity.getRequireSignup());
        dto.setSignupDeadline(activity.getSignupDeadline());
        dto.setStatus(activity.getStatus());
        dto.setPublishTime(activity.getPublishTime());
        dto.setPrincipalId(activity.getPrincipalId());
        dto.setCreateBy(activity.getCreateBy());

        // 转换targetIds
        if (activity.getTargetIds() != null) {
            try {
                dto.setTargetIds(Arrays.asList(objectMapper.readValue(activity.getTargetIds(), String[].class)));
            } catch (Exception e) {
                log.error("Error parsing targetIds", e);
            }
        }

        return dto;
    }

    private String convertListToJson(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            log.error("Error converting list to JSON", e);
            return null;
        }
    }
}
