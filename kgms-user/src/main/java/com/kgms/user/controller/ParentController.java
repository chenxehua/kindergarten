package com.kgms.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kgms.common.result.Result;
import com.kgms.user.entity.ParentInfo;
import com.kgms.user.mapper.ParentInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 家长管理Controller
 */
@RestController
@RequestMapping("/api/parent")
@Slf4j
public class ParentController {

    @Autowired
    private ParentInfoMapper parentInfoMapper;

    /**
     * 获取家长列表
     */
    @GetMapping
    public Result<List<ParentInfo>> getParentList(
            @RequestParam(required = false) String kgId,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String keyword) {
        QueryWrapper<ParentInfo> wrapper = new QueryWrapper<>();
        if (studentId != null) wrapper.eq("student_id", studentId);
        if (keyword != null) {
            wrapper.and(w -> w.like("parent_name", keyword)
                    .or().like("phone", keyword));
        }
        List<ParentInfo> parents = parentInfoMapper.selectList(wrapper);
        return Result.success(parents);
    }

    /**
     * 获取家长详情
     */
    @GetMapping("/{parentId}")
    public Result<ParentInfo> getParentDetail(@PathVariable String parentId) {
        ParentInfo parent = parentInfoMapper.selectOne(
            new QueryWrapper<ParentInfo>().eq("parent_id", parentId));
        if (parent == null) {
            return Result.error(404, "家长不存在");
        }
        return Result.success(parent);
    }

    /**
     * 新增家长
     */
    @PostMapping
    public Result<String> addParent(@RequestBody ParentInfo parent) {
        parentInfoMapper.insert(parent);
        return Result.success(parent.getParentId());
    }

    /**
     * 更新家长信息
     */
    @PutMapping("/{parentId}")
    public Result<Void> updateParent(@PathVariable String parentId, @RequestBody ParentInfo parent) {
        parent.setParentId(parentId);
        parentInfoMapper.updateById(parent);
        return Result.success();
    }

    /**
     * 删除家长
     */
    @DeleteMapping("/{parentId}")
    public Result<Void> deleteParent(@PathVariable String parentId) {
        parentInfoMapper.delete(new QueryWrapper<ParentInfo>().eq("parent_id", parentId));
        return Result.success();
    }

    /**
     * 获取学生家长列表
     */
    @GetMapping("/student/{studentId}")
    public Result<List<ParentInfo>> getStudentParents(@PathVariable String studentId) {
        List<ParentInfo> parents = parentInfoMapper.selectList(
            new QueryWrapper<ParentInfo>().eq("student_id", studentId));
        return Result.success(parents);
    }
}