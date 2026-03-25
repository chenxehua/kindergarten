package com.kgms.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kgms.user.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
