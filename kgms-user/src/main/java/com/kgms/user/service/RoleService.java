package com.kgms.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgms.common.util.IdGenerator;
import com.kgms.user.dto.RoleDTO;
import com.kgms.user.entity.Role;
import com.kgms.user.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    private final PermissionService permissionService;

    /**
     * 获取用户角色列表
     */
    public List<Role> getRolesByUserId(String userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    /**
     * 创建角色
     */
    @Transactional
    public Role createRole(RoleDTO dto) {
        Role role = new Role();
        role.setRoleId(IdGenerator.generateStrId());
        role.setRoleName(dto.getRoleName());
        role.setRoleCode(dto.getRoleCode());
        role.setDescription(dto.getDescription());
        role.setIsSystem(dto.getIsSystem());
        role.setSort(dto.getSort());
        role.setStatus("ENABLED");
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(role);

        // 关联权限
        if (dto.getPermissionIds() != null && !dto.getPermissionIds().isEmpty()) {
            permissionService.addRolePermissions(role.getRoleId(), dto.getPermissionIds());
        }

        log.info("Created role: {}", role.getRoleId());
        return role;
    }

    /**
     * 更新角色
     */
    @Transactional
    public Role updateRole(RoleDTO dto) {
        Role role = baseMapper.selectById(dto.getRoleId());
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());
        role.setSort(dto.getSort());
        role.setStatus(dto.getStatus());
        role.setUpdateTime(LocalDateTime.now());

        baseMapper.updateById(role);

        // 更新权限关联
        if (dto.getPermissionIds() != null) {
            permissionService.updateRolePermissions(role.getRoleId(), dto.getPermissionIds());
        }

        log.info("Updated role: {}", role.getRoleId());
        return role;
    }

    /**
     * 删除角色
     */
    @Transactional
    public void deleteRole(String roleId) {
        Role role = baseMapper.selectById(roleId);
        if (role != null && Boolean.TRUE.equals(role.getIsSystem())) {
            throw new RuntimeException("Cannot delete system role");
        }

        // 删除角色权限关联
        permissionService.removeRolePermissions(roleId);

        baseMapper.deleteById(roleId);
        log.info("Deleted role: {}", roleId);
    }

    /**
     * 获取所有角色列表
     */
    public List<Role> listAllRoles() {
        return baseMapper.selectList(new LambdaQueryWrapper<Role>()
                .orderByAsc(Role::getSort));
    }
}