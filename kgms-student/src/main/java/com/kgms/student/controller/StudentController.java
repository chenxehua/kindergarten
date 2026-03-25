package com.kgms.student.controller;

import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import com.kgms.student.dto.StudentDTO;
import com.kgms.student.dto.StudentVO;
import com.kgms.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生管理Controller
 */
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * 新增学生
     */
    @PostMapping("/add")
    public Result<String> addStudent(@RequestBody @Valid StudentDTO dto) {
        String studentId = studentService.addStudent(dto);
        return Result.success(studentId);
    }

    /**
     * 更新学生信息
     */
    @PutMapping("/update")
    public Result<Void> updateStudent(@RequestParam String studentId, @RequestBody @Valid StudentDTO dto) {
        studentService.updateStudent(studentId, dto);
        return Result.success();
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/delete")
    public Result<Void> deleteStudent(@RequestParam String studentId) {
        studentService.deleteStudent(studentId);
        return Result.success();
    }

    /**
     * 获取学生详情
     */
    @GetMapping("/detail")
    public Result<StudentVO> getStudentDetail(@RequestParam String studentId) {
        StudentVO vo = studentService.getStudentDetail(studentId);
        return Result.success(vo);
    }

    /**
     * 获取学生列表（分页）
     */
    @GetMapping("/list")
    public Result<PageResult<StudentVO>> getStudentList(
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        PageResult<StudentVO> result = studentService.getStudentList(classId, keyword, status, page, pageSize);
        return Result.success(result);
    }

    /**
     * 根据班级获取学生列表
     */
    @GetMapping("/class/students")
    public Result<List<StudentVO>> getStudentsByClassId(@RequestParam String classId) {
        List<StudentVO> list = studentService.getStudentsByClassId(classId);
        return Result.success(list);
    }

    /**
     * 转班操作
     */
    @PostMapping("/transfer")
    public Result<Void> transferClass(@RequestParam String studentId, @RequestParam String targetClassId) {
        studentService.transferClass(studentId, targetClassId);
        return Result.success();
    }
}
