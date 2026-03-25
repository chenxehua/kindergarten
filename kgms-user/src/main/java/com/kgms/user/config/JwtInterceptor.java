package com.kgms.user.config;

import com.kgms.common.exception.BusinessException;
import com.kgms.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取Token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            token = request.getHeader("X-Token");
        }

        if (!StringUtils.hasText(token)) {
            throw new BusinessException(401, "请先登录");
        }

        // 去掉Bearer前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 验证Token
        if (!JwtUtil.validateToken(token)) {
            throw new BusinessException(401, "登录已过期，请重新登录");
        }

        // 设置用户信息到请求属性
        String userId = JwtUtil.getUserId(token);
        Integer userType = JwtUtil.getUserType(token);

        request.setAttribute("X-User-Id", userId);
        request.setAttribute("X-User-Type", userType);

        return true;
    }
}
