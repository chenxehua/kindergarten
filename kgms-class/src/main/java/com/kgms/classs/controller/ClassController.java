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

@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping("/add")
    public Result<String> addClass(@RequestBody @Valid ClassDTO dto) {
        return Result.success(classService.addClass(dto));
    }

    @PutMapping("/update")
    public Result<Void> updateClass(@RequestParam String classId, @RequestBody @Valid ClassDTO dto) {
        classService.updateClass(classId, dto);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<ClassVO> getClassDetail(@RequestParam String classId) {
        return Result.success(classService.getClassDetail(classId));
    }

    @GetMapping("/list")
    public Result<PageResult<ClassVO>> getClassList(
            @RequestParam(required = false) String kgId,
            @RequestParam(required = false) String grade,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(classService.getClassList(kgId, grade, page, pageSize));
    }

    @GetMapping("/kg/classes")
    public Result<List<ClassVO>> getClassesByKgId(@RequestParam String kgId) {
        return Result.success(classService.getClassesByKgId(kgId));
    }
}
