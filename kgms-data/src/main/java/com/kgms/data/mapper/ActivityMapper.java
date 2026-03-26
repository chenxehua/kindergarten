package com.kgms.data.mapper;

import com.kgms.data.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ActivityMapper {

    Activity selectById(@Param("id") Long id);

    Activity selectByActivityId(@Param("activityId") String activityId);

    List<Activity> selectByKgId(@Param("kgId") String kgId);

    List<Activity> selectByClassId(@Param("classId") String classId);

    List<Map<String, Object>> selectActivityList(
            @Param("kgId") String kgId,
            @Param("activityType") String activityType,
            @Param("status") String status,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    int insert(Activity activity);

    int updateByActivityId(Activity activity);

    int deleteByActivityId(@Param("activityId") String activityId);
}