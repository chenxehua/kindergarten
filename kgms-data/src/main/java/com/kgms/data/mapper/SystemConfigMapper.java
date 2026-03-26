package com.kgms.data.mapper;

import com.kgms.data.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemConfigMapper {

    SystemConfig selectById(@Param("id") Long id);

    SystemConfig selectByConfigId(@Param("configId") String configId);

    SystemConfig selectByConfigKey(@Param("configKey") String configKey);

    List<SystemConfig> selectAll();

    int insert(SystemConfig config);

    int updateByConfigId(SystemConfig config);

    int deleteByConfigId(@Param("configId") String configId);
}