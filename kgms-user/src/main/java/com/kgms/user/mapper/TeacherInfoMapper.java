package com.kgms.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kgms.user.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 老师信息Mapper
 */
@Mapper
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {

    TeacherInfo selectByTeacherId(@Param("teacherId") String teacherId);

    List<TeacherInfo> selectTeacherList(
            @Param("kgId") String kgId,
            @Param("classId") String classId,
            @Param("keyword") String keyword);

    List<TeacherInfo> selectByClassId(@Param("classId") String classId);

    String insertTeacher(TeacherInfo teacher);

    int updateTeacher(TeacherInfo teacher);

    int deleteByTeacherId(@Param("teacherId") String teacherId);
}
