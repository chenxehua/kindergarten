package com.kgms.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色DTO
 */
@Data
public class RoleDTO {

    private String roleId;

    private String roleName;

    private String roleCode;

    private String description;

    private Boolean isSystem;

    private Integer sort;

    private String status;

    private List<String> permissionIds;
}