package com.kgms.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 为用户分配角色
     */
    public void assignRolesToUser(String userId, List<String> roleIds) {
        // 先删除原有角色
        String deleteSql = "DELETE FROM t_user_role WHERE user_id = ?";
        jdbcTemplate.update(deleteSql, userId);

        // 添加新角色
        String insertSql = "INSERT INTO t_user_role (user_id, role_id) VALUES (?, ?)";
        for (String roleId : roleIds) {
            jdbcTemplate.update(insertSql, userId, roleId);
        }
        log.info("Assigned {} roles to user {}", roleIds.size(), userId);
    }

    /**
     * 为用户添加角色
     */
    public void addRoleToUser(String userId, String roleId) {
        String sql = "INSERT IGNORE INTO t_user_role (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
        log.info("Added role {} to user {}", roleId, userId);
    }

    /**
     * 移除用户角色
     */
    public void removeRoleFromUser(String userId, String roleId) {
        String sql = "DELETE FROM t_user_role WHERE user_id = ? AND role_id = ?";
        jdbcTemplate.update(sql, userId, roleId);
        log.info("Removed role {} from user {}", roleId, userId);
    }

    /**
     * 获取用户角色ID列表
     */
    public List<String> getUserRoleIds(String userId) {
        String sql = "SELECT role_id FROM t_user_role WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId);
    }
}