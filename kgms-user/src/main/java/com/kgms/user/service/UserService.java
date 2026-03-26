package com.kgms.user.service;

import com.kgms.common.exception.BusinessException;
import com.kgms.user.dto.*;
import com.kgms.user.entity.ParentInfo;
import com.kgms.user.entity.SysUser;
import com.kgms.user.entity.TeacherInfo;
import com.kgms.user.mapper.ParentInfoMapper;
import com.kgms.user.mapper.SysUserMapper;
import com.kgms.user.mapper.TeacherInfoMapper;
import com.kgms.common.enums.UserType;
import com.kgms.common.util.IdGenerator;
import com.kgms.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SysUserMapper sysUserMapper;
    private final TeacherInfoMapper teacherInfoMapper;
    private final ParentInfoMapper parentInfoMapper;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final com.kgms.user.mapper.PermissionMapper permissionMapper;

    /**
     * 账号密码登录
     */
    public LoginResponse loginByPassword(String username, String password) {
        SysUser user = sysUserMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<SysUser>()
                        .eq("username", username)
                        .or()
                        .eq("phone", username)
        );

        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }

        // 验证密码
        String encryptPassword = encryptPassword(password);
        if (!encryptPassword.equals(user.getPassword())) {
            throw new BusinessException(401, "密码错误");
        }

        // 检查状态
        if (user.getStatus() == 0) {
            throw new BusinessException(401, "账号已被禁用");
        }

        // 生成Token
        return generateLoginResponse(user);
    }

    /**
     * 微信授权登录
     */
    public LoginResponse loginByWechat(String code, String nickname, String avatar, Integer userType) {
        // TODO: 实际应该调用微信API获取openid
        // 这里简化处理，假设code就是openid
        String openid = code;

        SysUser user = sysUserMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<SysUser>()
                        .eq("wechat_openid", openid)
        );

        if (user == null) {
            // 新用户，创建账号
            user = new SysUser();
            user.setUserId(IdGenerator.generateStrId());
            user.setWechatOpenid(openid);
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setUserType(userType);
            user.setStatus(1);
            user.setLastLoginTime(LocalDateTime.now());
            sysUserMapper.insert(user);
        } else {
            // 更新用户信息
            if (nickname != null) {
                user.setNickname(nickname);
            }
            if (avatar != null) {
                user.setAvatar(avatar);
            }
            user.setLastLoginTime(LocalDateTime.now());
            sysUserMapper.updateById(user);
        }

        return generateLoginResponse(user);
    }

    /**
     * 手机验证码登录
     */
    public LoginResponse loginBySms(String phone, String code) {
        // TODO: 实际应该验证验证码
        // 这里简化处理

        SysUser user = sysUserMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<SysUser>()
                        .eq("phone", phone)
        );

        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }

        return generateLoginResponse(user);
    }

    /**
     * 获取用户信息
     */
    public UserInfoVO getUserInfo(String userId) {
        SysUser user = sysUserMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<SysUser>()
                        .eq("user_id", userId)
        );

        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        return buildUserInfoVO(user);
    }

    /**
     * 获取家长关联的孩子列表
     */
    public List<ChildVO> getChildList(String userId) {
        List<ParentInfo> parentInfos = parentInfoMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<ParentInfo>()
                        .eq("user_id", userId)
        );
        
        // 转换为ChildVO
        // TODO: 实际需要调用student服务获取学生信息
        return null;
    }

    /**
     * 获取孩子详情
     */
    public ChildDetailVO getChildDetail(String studentId, String userId) {
        // 验证权限：检查是否是该孩子的家长
        long count = parentInfoMapper.selectCount(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<ParentInfo>()
                        .eq("user_id", userId)
                        .eq("student_id", studentId)
        );
        
        if (count == 0) {
            throw new BusinessException(403, "无权限查看该孩子信息");
        }
        
        // TODO: 调用student服务获取详情
        return null;
    }

    /**
     * 生成登录响应
     */
    private LoginResponse generateLoginResponse(SysUser user) {
        String token = JwtUtil.generateToken(user.getUserId(), user.getUserType());
        String refreshToken = JwtUtil.generateRefreshToken(user.getUserId());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setUserId(user.getUserId());
        response.setUserType(user.getUserType());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());

        return response;
    }

    /**
     * 构建用户信息VO
     */
    private UserInfoVO buildUserInfoVO(SysUser user) {
        UserInfoVO vo = new UserInfoVO();
        vo.setUserId(user.getUserId());
        vo.setUserType(user.getUserType());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setPhone(user.getPhone());

        UserType userType = UserType.getByCode(user.getUserType());
        if (userType != null) {
            vo.setUserTypeDesc(userType.getDesc());
        }

        return vo;
    }

    /**
     * 密码加密
     */
    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(("kgms_" + password).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 为用户分配角色
     */
    public void assignRoles(String userId, List<String> roleIds) {
        userRoleService.assignRolesToUser(userId, roleIds);
        log.info("Assigned roles to user: {}", userId);
    }

    /**
     * 获取用户角色列表
     */
    public List<com.kgms.user.entity.Role> getUserRoles(String userId) {
        return roleService.getRolesByUserId(userId);
    }

    /**
     * 获取用户权限列表
     */
    public List<com.kgms.user.entity.Permission> getUserPermissions(String userId) {
        return permissionMapper.selectPermissionsByUserId(userId);
    }
}
