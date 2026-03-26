package com.kgms.data.mapper;

import com.kgms.data.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface AttendanceMapper {

    Attendance selectById(@Param("id") Long id);

    Attendance selectByAttendanceId(@Param("attendanceId") String attendanceId);

    List<Attendance> selectByStudentIdAndDateRange(
            @Param("studentId") String studentId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    List<Attendance> selectByClassIdAndDate(
            @Param("classId") String classId,
            @Param("attendanceDate") String attendanceDate
    );

    List<Map<String, Object>> selectAttendanceStats(
            @Param("classId") String classId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    int insert(Attendance attendance);

    int updateByAttendanceId(Attendance attendance);

    int deleteByAttendanceId(@Param("attendanceId") String attendanceId);
}
