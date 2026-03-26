package com.kgms.course.mapper;

import com.kgms.course.entity.ScheduleAdjust;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleAdjustMapper {

    ScheduleAdjust selectById(@Param("id") Long id);

    ScheduleAdjust selectByAdjustId(@Param("adjustId") String adjustId);

    List<ScheduleAdjust> selectByClassId(@Param("classId") String classId);

    List<ScheduleAdjust> selectByDateRange(
            @Param("classId") String classId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    List<ScheduleAdjust> selectPendingByClassId(@Param("classId") String classId);

    int insert(ScheduleAdjust adjust);

    int updateByAdjustId(ScheduleAdjust adjust);

    int deleteByAdjustId(@Param("adjustId") String adjustId);
}
