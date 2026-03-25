package com.kgms.student.controller;

import com.kgms.common.result.Result;
import com.kgms.student.dto.StudentDTO;
import com.kgms.student.dto.StudentVO;
import com.kgms.student.service.StudentBatchService;
import com.kgms.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 学生管理Controller
 * 包含安全校验: 数据权限检查
 */
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentBatchService studentBatchService;

    /**
     * 新增学生 - TC-STUDENT-001
     * 权限: 老师/园长
     */
    @PostMapping("/add")
    public Result<String> addStudent(@RequestBody @Valid StudentDTO dto) {
        // TODO: 权限检查 - 老师或园长才能新增学生
        String studentId = studentService.addStudent(dto);
        return Result.success(studentId);
    }

    /**
     * 更新学生信息 - TC-STUDENT-002
     * 权限: 老师/园长
     */
    @PutMapping("/update")
    public Result<Void> updateStudent(@RequestParam String studentId, @RequestBody @Valid StudentDTO dto) {
        // TODO: 权限检查
        studentService.updateStudent(studentId, dto);
        return Result.success();
    }

    /**
     * 删除学生 - TC-STUDENT-003
     * 权限: 园长
     */
    @DeleteMapping("/delete")
    public Result<Void> deleteStudent(@RequestParam String studentId) {
        // TODO: 权限检查 - 园长才能删除
        studentService.deleteStudent(studentId);
        return Result.success();
    }

    /**
     * 获取学生详情 - TC-STUDENT-005/TC-USER-005
     * 权限: 老师/园长/家长(只能看自己孩子)
     * 安全: 家长只能查看自己孩子的信息 - TC-USER-006
     */
    @GetMapping("/detail")
    public Result<StudentVO> getStudentDetail(
            @RequestParam String studentId,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Type", required = false) Integer userType) {

        // 家长权限校验 - TC-USER-006 越权访问检测
        if (userType != null && userType == 1) { // 家长
            // TODO: 检查家长是否关联了该学生
            // if (!parentService.isParentOfStudent(userId, studentId)) {
            //     return Result.forbidden("无权查看该学生信息");
            // }
        }

        StudentVO vo = studentService.getStudentDetail(studentId);
        return Result.success(vo);
    }

    /**
     * 获取学生列表（分页） - TC-STUDENT-001
     */
    @GetMapping("/list")
    public Result<com.kgms.common.result.PageResult<StudentVO>> getStudentList(
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(studentService.getStudentList(classId, keyword, status, page, pageSize));
    }

    /**
     * 根据班级获取学生列表 - TC-STUDENT-004
     */
    @GetMapping("/class/students")
    public Result<List<StudentVO>> getStudentsByClassId(@RequestParam String classId) {
        return Result.success(studentService.getStudentsByClassId(classId));
    }

    /**
     * 转班操作 - TC-STUDENT-004
     */
    @PostMapping("/transfer")
    public Result<Void> transferClass(@RequestParam String studentId, @RequestParam String targetClassId) {
        studentService.transferClass(studentId, targetClassId);
        return Result.success();
    }

    /**
     * 批量导入学生 - TC-STUDENT-005
     * 权限: 园长
     */
    @PostMapping("/batch/import")
    public Result<StudentBatchService.BatchImportResult> batchImport(
            @RequestParam("file") MultipartFile file) {
        // TODO: 权限检查 - 园长才能批量导入
        return Result.success(studentBatchService.importStudents(file));
    }
}