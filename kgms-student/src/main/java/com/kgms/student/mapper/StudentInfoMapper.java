package com.kgms.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kgms.student.entity.StudentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
}
