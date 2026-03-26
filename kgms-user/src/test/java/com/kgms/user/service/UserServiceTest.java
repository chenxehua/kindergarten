package com.kgms.user.service;

import com.kgms.user.dto.LoginResponse;
import com.kgms.user.dto.UserInfoVO;
import com.kgms.user.entity.SysUser;
import com.kgms.user.mapper.SysUserMapper;
import com.kgms.common.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private SysUserMapper sysUserMapper;

    @InjectMocks
    private UserService userService;

    private SysUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new SysUser();
        testUser.setUserId("user_001");
        testUser.setUserType(1);
        testUser.setUsername("13800138000");
        testUser.setNickname("测试用户");
        testUser.setPassword("cdfca8752affb64f09a35c739e2479a8"); // 123456
        testUser.setPhone("13800138000");
        testUser.setAvatar("https://example.com/avatar.jpg");
        testUser.setStatus(1);
        testUser.setLastLoginTime(LocalDateTime.now());
    }

    /**
     * TC-USER-001: 账号密码登录 - 成功
     */
    @Test
    void testLoginByPassword_Success() {
        // Given
        String username = "13800138000";
        String password = "123456";
        
        when(sysUserMapper.selectOne(any())).thenReturn(testUser);

        // When
        LoginResponse response = userService.loginByPassword(username, password);

        // Then
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("user_001", response.getUserId());
        assertEquals(1, response.getUserType());
        assertEquals("测试用户", response.getNickname());
    }

    /**
     * TC-USER-001: 账号密码登录 - 用户不存在
     */
    @Test
    void testLoginByPassword_UserNotFound() {
        // Given
        when(sysUserMapper.selectOne(any())).thenReturn(null);

        // When & Then
        try {
            userService.loginByPassword("13800138000", "123456");
            fail("应该抛出异常");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("用户不存在"));
        }
    }

    /**
     * TC-USER-001: 账号密码登录 - 密码错误
     */
    @Test
    void testLoginByPassword_WrongPassword() {
        // Given
        when(sysUserMapper.selectOne(any())).thenReturn(testUser);

        // When & Then
        try {
            userService.loginByPassword("13800138000", "wrongpassword");
            fail("应该抛出异常");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("密码错误"));
        }
    }

    /**
     * TC-USER-001: 账号密码登录 - 用户被禁用
     */
    @Test
    void testLoginByPassword_UserDisabled() {
        // Given
        testUser.setStatus(0); // 禁用
        when(sysUserMapper.selectOne(any())).thenReturn(testUser);

        // When & Then
        try {
            userService.loginByPassword("13800138000", "123456");
            fail("应该抛出异常");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("已被禁用"));
        }
    }

    /**
     * TC-USER-003: 获取用户信息 - 成功
     */
    @Test
    void testGetUserInfo_Success() {
        // Given
        when(sysUserMapper.selectOne(any())).thenReturn(testUser);

        // When
        UserInfoVO userInfo = userService.getUserInfo("user_001");

        // Then
        assertNotNull(userInfo);
        assertEquals("user_001", userInfo.getUserId());
        assertEquals("测试用户", userInfo.getNickname());
        assertEquals("13800138000", userInfo.getPhone());
        assertEquals(UserType.PARENT.getDesc(), userInfo.getUserTypeDesc());
    }

    /**
     * TC-USER-003: 获取用户信息 - 用户不存在
     */
    @Test
    void testGetUserInfo_NotFound() {
        // Given
        when(sysUserMapper.selectOne(any())).thenReturn(null);

        // When & Then
        try {
            userService.getUserInfo("not_exist");
            fail("应该抛出异常");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("用户不存在"));
        }
    }
}