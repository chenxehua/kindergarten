package com.kgms.data.mapper;

import com.kgms.data.entity.TeacherAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherAttendanceMapper {

    TeacherAttendance selectById(@Param("id") Long id);

    TeacherAttendance selectByRecordId(@Param("recordId") String recordId);

    TeacherAttendance selectByTeacherAndDate(@Param("teacherId") String teacherId, @Param("attendanceDate") String attendanceDate);

    List<TeacherAttendance> selectByTeacherAndDateRange(
            @Param("teacherId") String teacherId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    int insert(TeacherAttendance attendance);

    int updateByRecordId(TeacherAttendance attendance);

    int deleteByRecordId(@Param("recordId") String recordId);
}