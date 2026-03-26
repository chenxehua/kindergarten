package com.kgms.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 权限DTO
 */
@Data
public class PermissionDTO {

    private String permissionId;

    private String permissionName;

    private String permissionCode;

    private String permissionType;

    private String path;

    private String component;

    private String icon;

    private Integer sort;

    private String status;

    private String parentId;

    private List<PermissionDTO> children;
}