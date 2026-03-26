package com.kgms.user.controller;

import com.kgms.common.result.Result;
import com.kgms.user.dto.PermissionDTO;
import com.kgms.user.entity.Permission;
import com.kgms.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理控制器
 */
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 获取权限树
     */
    @GetMapping("/tree")
    public Result<List<PermissionDTO>> getPermissionTree() {
        return Result.success(permissionService.getPermissionTree());
    }

    /**
     * 获取用户权限
     */
    @GetMapping("/user/{userId}")
    public Result<List<Permission>> getUserPermissions(@PathVariable String userId) {
        return Result.success(permissionService.getPermissionsByUserId(userId));
    }

    /**
     * 获取角色权限
     */
    @GetMapping("/role/{roleId}")
    public Result<List<Permission>> getRolePermissions(@PathVariable String roleId) {
        return Result.success(permissionService.getPermissionsByRoleId(roleId));
    }

    /**
     * 创建权限
     */
    @PostMapping
    public Result<Permission> createPermission(@RequestBody PermissionDTO dto) {
        return Result.success(permissionService.createPermission(dto));
    }

    /**
     * 更新权限
     */
    @PutMapping("/{permissionId}")
    public Result<Permission> updatePermission(@PathVariable String permissionId, @RequestBody PermissionDTO dto) {
        dto.setPermissionId(permissionId);
        return Result.success(permissionService.updatePermission(dto));
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{permissionId}")
    public Result<Void> deletePermission(@PathVariable String permissionId) {
        permissionService.deletePermission(permissionId);
        return Result.success(null);
    }
}