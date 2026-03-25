package com.kgms.classs.controller;

import com.kgms.classs.dto.ClassDTO;
import com.kgms.classs.dto.ClassVO;
import com.kgms.classs.service.ClassService;
import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理Controller
 * 权限: 园长可管理所有班级, 老师可查看
 */
@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    /**
     * 新增班级 - TC-CLASS-001
     * 权限: 园长
     */
    @PostMapping("/add")
    public Result<String> addClass(@RequestBody @Valid ClassDTO dto) {
        // TODO: 权限检查 - 只有园长能新增班级
        return Result.success(classService.addClass(dto));
    }

    /**
     * 更新班级信息 - TC-CLASS-001
     * 权限: 园长
     */
    @PutMapping("/update")
    public Result<Void> updateClass(@RequestParam String classId, @RequestBody @Valid ClassDTO dto) {
        // TODO: 权限检查
        classService.updateClass(classId, dto);
        return Result.success();
    }

    /**
     * 获取班级详情 - TC-CLASS-001
     */
    @GetMapping("/detail")
    public Result<ClassVO> getClassDetail(@RequestParam String classId) {
        return Result.success(classService.getClassDetail(classId));
    }

    /**
     * 获取班级列表（分页）
     */
    @GetMapping("/list")
    public Result<PageResult<ClassVO>> getClassList(
            @RequestParam(required = false) String kgId,
            @RequestParam(required = false) String grade,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(classService.getClassList(kgId, grade, page, pageSize));
    }

    /**
     * 根据园区获取班级列表 - TC-CLASS-002
     */
    @GetMapping("/kg/classes")
    public Result<List<ClassVO>> getClassesByKgId(@RequestParam String kgId) {
        return Result.success(classService.getClassesByKgId(kgId));
    }

    /**
     * 获取班级学生列表 - TC-CLASS-002
     */
    @GetMapping("/students")
    public Result<List<com.kgms.student.dto.StudentVO>> getClassStudents(@RequestParam String classId) {
        // TODO: 调用学生服务获取班级学生列表
        return Result.success(null);
    }

    /**
     * 获取班级老师列表 - TC-CLASS-003
     */
    @GetMapping("/teachers")
    public Result<List<com.kgms.user.dto.UserInfoVO>> getClassTeachers(@RequestParam String classId) {
        // TODO: 调用用户服务获取班级老师列表
        return Result.success(null);
    }
}