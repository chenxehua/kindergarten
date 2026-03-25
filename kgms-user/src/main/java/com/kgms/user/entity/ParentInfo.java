package com.kgms.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 家长信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_parent_info")
public class ParentInfo extends BaseEntity {

    /** 关联用户ID */
    private String userId;

    /** 关联学生ID */
    private String studentId;

    /** 关系: 爸爸/妈妈/爷爷/奶奶/其他 */
    private String relationType;

    /** 是否紧急联系人: 0-否 1-是 */
    private Integer isEmergency;
}
