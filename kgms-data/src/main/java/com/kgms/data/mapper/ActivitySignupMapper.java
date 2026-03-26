package com.kgms.data.mapper;

import com.kgms.data.entity.ActivitySignup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ActivitySignupMapper {

    ActivitySignup selectById(@Param("id") Long id);

    ActivitySignup selectBySignupId(@Param("signupId") String signupId);

    ActivitySignup selectByActivityAndStudent(@Param("activityId") String activityId, @Param("studentId") String studentId);

    List<ActivitySignup> selectByActivityId(@Param("activityId") String activityId);

    List<Map<String, Object>> selectSignupListWithStudent(@Param("activityId") String activityId);

    int insert(ActivitySignup signup);

    int updateBySignupId(ActivitySignup signup);

    int deleteBySignupId(@Param("signupId") String signupId);
}
