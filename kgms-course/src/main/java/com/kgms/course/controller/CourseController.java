package com.kgms.course.controller;

import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import com.kgms.course.dto.CourseDTO;
import com.kgms.course.dto.CourseVO;
import com.kgms.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/add")
    public Result<String> addCourse(@RequestBody @Valid CourseDTO dto) {
        return Result.success(courseService.addCourse(dto));
    }

    @PutMapping("/update")
    public Result<Void> updateCourse(@RequestParam String courseId, @RequestBody @Valid CourseDTO dto) {
        courseService.updateCourse(courseId, dto);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<CourseVO> getCourseDetail(@RequestParam String courseId) {
        return Result.success(courseService.getCourseDetail(courseId));
    }

    @GetMapping("/list")
    public Result<PageResult<CourseVO>> getCourseList(
            @RequestParam(required = false) String kgId,
            @RequestParam(required = false) String courseType,
            @RequestParam(required = false) String ageGroup,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(courseService.getCourseList(kgId, courseType, ageGroup, page, pageSize));
    }
}
