package com.kgms.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgms.user.entity.OperationLog;
import com.kgms.user.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogService extends ServiceImpl<OperationLogMapper, OperationLog> {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OperationLogService.class);

    /**
     * 记录操作日志
     */
    public void saveLog(OperationLog log) {
        baseMapper.insert(log);
        logger.debug("Saved operation log: " + log.getOperation());
    }

    /**
     * 获取用户操作日志
     */
    public List<OperationLog> getUserLogs(String userId) {
        return baseMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<OperationLog>()
                        .eq(OperationLog::getUserId, userId)
                        .orderByDesc(OperationLog::getOperateTime)
        );
    }
}