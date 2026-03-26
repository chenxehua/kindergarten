package com.kgms.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kgms.common.result.Result;
import com.kgms.user.entity.TeacherInfo;
import com.kgms.user.mapper.TeacherInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 老师管理Controller
 */
@RestController
@RequestMapping("/api/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    /**
     * 获取老师列表
     */
    @GetMapping
    public Result<List<TeacherInfo>> getTeacherList(
            @RequestParam(required = false) String kgId,
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String keyword) {
        QueryWrapper<TeacherInfo> wrapper = new QueryWrapper<>();
        if (kgId != null) wrapper.eq("kg_id", kgId);
        if (classId != null) wrapper.eq("class_id", classId);
        if (keyword != null) {
            wrapper.and(w -> w.like("teacher_name", keyword)
                    .or().like("phone", keyword));
        }
        List<TeacherInfo> teachers = teacherInfoMapper.selectList(wrapper);
        return Result.success(teachers);
    }

    /**
     * 获取老师详情
     */
    @GetMapping("/{teacherId}")
    public Result<TeacherInfo> getTeacherDetail(@PathVariable String teacherId) {
        TeacherInfo teacher = teacherInfoMapper.selectOne(
            new QueryWrapper<TeacherInfo>().eq("teacher_id", teacherId));
        if (teacher == null) {
            return Result.error(404, "老师不存在");
        }
        return Result.success(teacher);
    }

    /**
     * 新增老师
     */
    @PostMapping
    public Result<String> addTeacher(@RequestBody TeacherInfo teacher) {
        teacherInfoMapper.insert(teacher);
        return Result.success(teacher.getTeacherId());
    }

    /**
     * 更新老师信息
     */
    @PutMapping("/{teacherId}")
    public Result<Void> updateTeacher(@PathVariable String teacherId, @RequestBody TeacherInfo teacher) {
        teacher.setTeacherId(teacherId);
        teacherInfoMapper.updateById(teacher);
        return Result.success();
    }

    /**
     * 删除老师
     */
    @DeleteMapping("/{teacherId}")
    public Result<Void> deleteTeacher(@PathVariable String teacherId) {
        teacherInfoMapper.delete(new QueryWrapper<TeacherInfo>().eq("teacher_id", teacherId));
        return Result.success();
    }

    /**
     * 获取班级老师列表
     */
    @GetMapping("/class/{classId}")
    public Result<List<TeacherInfo>> getClassTeachers(@PathVariable String classId) {
        List<TeacherInfo> teachers = teacherInfoMapper.selectList(
            new QueryWrapper<TeacherInfo>().eq("class_id", classId));
        return Result.success(teachers);
    }
}
