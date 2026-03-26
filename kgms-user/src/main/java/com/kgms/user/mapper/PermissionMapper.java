package com.kgms.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kgms.user.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限Mapper
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT p.* FROM t_permission p " +
            "INNER JOIN t_role_permission rp ON p.permission_id = rp.permission_id " +
            "INNER JOIN t_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} " +
            "AND p.status = 'ENABLED'")
    List<Permission> selectPermissionsByUserId(@Param("userId") String userId);

    @Select("SELECT p.* FROM t_permission p " +
            "INNER JOIN t_role_permission rp ON p.permission_id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    List<Permission> selectPermissionsByRoleId(@Param("roleId") String roleId);
}