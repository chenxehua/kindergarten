package com.kgms.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgms.common.util.IdGenerator;
import com.kgms.user.dto.PermissionDTO;
import com.kgms.user.entity.Permission;
import com.kgms.user.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 获取用户权限列表
     */
    public List<Permission> getPermissionsByUserId(String userId) {
        return baseMapper.selectPermissionsByUserId(userId);
    }

    /**
     * 获取角色权限列表
     */
    public List<Permission> getPermissionsByRoleId(String roleId) {
        return baseMapper.selectPermissionsByRoleId(roleId);
    }

    /**
     * 获取所有权限树
     */
    public List<PermissionDTO> getPermissionTree() {
        List<Permission> allPermissions = baseMapper.selectList(
                new LambdaQueryWrapper<Permission>()
                        .eq(Permission::getStatus, "ENABLED")
                        .orderByAsc(Permission::getSort)
        );
        return buildTree(allPermissions);
    }

    /**
     * 构建权限树
     */
    private List<PermissionDTO> buildTree(List<Permission> permissions) {
        List<PermissionDTO> roots = new ArrayList<>();
        for (Permission p : permissions) {
            PermissionDTO dto = convertToDTO(p);
            if (p.getPermissionCode() == null || !p.getPermissionCode().contains(".")) {
                // 根节点
                List<PermissionDTO> children = permissions.stream()
                        .filter(child -> child.getPermissionCode() != null &&
                                child.getPermissionCode().startsWith(p.getPermissionCode() + "."))
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
                dto.setChildren(children);
                roots.add(dto);
            }
        }
        return roots;
    }

    private PermissionDTO convertToDTO(Permission p) {
        PermissionDTO dto = new PermissionDTO();
        dto.setPermissionId(p.getPermissionId());
        dto.setPermissionName(p.getPermissionName());
        dto.setPermissionCode(p.getPermissionCode());
        dto.setPermissionType(p.getPermissionType());
        dto.setPath(p.getPath());
        dto.setComponent(p.getComponent());
        dto.setIcon(p.getIcon());
        dto.setSort(p.getSort());
        dto.setStatus(p.getStatus());
        return dto;
    }

    /**
     * 创建权限
     */
    public Permission createPermission(PermissionDTO dto) {
        Permission permission = new Permission();
        permission.setPermissionId(IdGenerator.generateStrId());
        permission.setPermissionName(dto.getPermissionName());
        permission.setPermissionCode(dto.getPermissionCode());
        permission.setPermissionType(dto.getPermissionType());
        permission.setPath(dto.getPath());
        permission.setComponent(dto.getComponent());
        permission.setIcon(dto.getIcon());
        permission.setSort(dto.getSort());
        permission.setStatus("ENABLED");
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(permission);
        log.info("Created permission: {}", permission.getPermissionId());
        return permission;
    }

    /**
     * 更新权限
     */
    public Permission updatePermission(PermissionDTO dto) {
        Permission permission = baseMapper.selectById(dto.getPermissionId());
        if (permission == null) {
            throw new RuntimeException("Permission not found");
        }

        permission.setPermissionName(dto.getPermissionName());
        permission.setPermissionCode(dto.getPermissionCode());
        permission.setPermissionType(dto.getPermissionType());
        permission.setPath(dto.getPath());
        permission.setComponent(dto.getComponent());
        permission.setIcon(dto.getIcon());
        permission.setSort(dto.getSort());
        permission.setStatus(dto.getStatus());
        permission.setUpdateTime(LocalDateTime.now());

        baseMapper.updateById(permission);
        log.info("Updated permission: {}", permission.getPermissionId());
        return permission;
    }

    /**
     * 删除权限
     */
    public void deletePermission(String permissionId) {
        // 检查是否有子权限
        long childCount = count(new LambdaQueryWrapper<Permission>()
                .likeRight(Permission::getPermissionCode,
                        baseMapper.selectById(permissionId).getPermissionCode() + "."));
        if (childCount > 0) {
            throw new RuntimeException("Cannot delete permission with children");
        }

        // 删除角色权限关联
        String sql = "DELETE FROM t_role_permission WHERE permission_id = ?";
        jdbcTemplate.update(sql, permissionId);

        baseMapper.deleteById(permissionId);
        log.info("Deleted permission: {}", permissionId);
    }

    /**
     * 为角色添加权限
     */
    @Transactional
    public void addRolePermissions(String roleId, List<String> permissionIds) {
        String sql = "INSERT INTO t_role_permission (role_id, permission_id) VALUES (?, ?)";
        for (String permissionId : permissionIds) {
            jdbcTemplate.update(sql, roleId, permissionId);
        }
        log.info("Added {} permissions to role {}", permissionIds.size(), roleId);
    }

    /**
     * 更新角色权限
     */
    @Transactional
    public void updateRolePermissions(String roleId, List<String> permissionIds) {
        // 先删除原有权限
        String deleteSql = "DELETE FROM t_role_permission WHERE role_id = ?";
        jdbcTemplate.update(deleteSql, roleId);

        // 添加新权限
        if (permissionIds != null && !permissionIds.isEmpty()) {
            addRolePermissions(roleId, permissionIds);
        }
    }

    /**
     * 删除角色所有权限
     */
    public void removeRolePermissions(String roleId) {
        String sql = "DELETE FROM t_role_permission WHERE role_id = ?";
        jdbcTemplate.update(sql, roleId);
    }
}