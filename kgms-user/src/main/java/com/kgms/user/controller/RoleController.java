package com.kgms.user.controller;

import com.kgms.common.result.Result;
import com.kgms.user.dto.RoleDTO;
import com.kgms.user.entity.Role;
import com.kgms.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 获取所有角色列表
     */
    @GetMapping
    public Result<List<Role>> listAllRoles() {
        return Result.success(roleService.listAllRoles());
    }

    /**
     * 获取用户角色
     */
    @GetMapping("/user/{userId}")
    public Result<List<Role>> getUserRoles(@PathVariable String userId) {
        return Result.success(roleService.getRolesByUserId(userId));
    }

    /**
     * 创建角色
     */
    @PostMapping
    public Result<Role> createRole(@RequestBody RoleDTO dto) {
        return Result.success(roleService.createRole(dto));
    }

    /**
     * 更新角色
     */
    @PutMapping("/{roleId}")
    public Result<Role> updateRole(@PathVariable String roleId, @RequestBody RoleDTO dto) {
        dto.setRoleId(roleId);
        return Result.success(roleService.updateRole(dto));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleId}")
    public Result<Void> deleteRole(@PathVariable String roleId) {
        roleService.deleteRole(roleId);
        return Result.success(null);
    }
}