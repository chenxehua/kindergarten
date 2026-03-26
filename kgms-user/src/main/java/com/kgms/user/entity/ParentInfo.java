package com.kgms.user.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 家长信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_parent_info")
public class ParentInfo extends BaseEntity {
    /** 家长ID */
    private String parentId;
    /** 用户ID */
    private String userId;
    /** 学生ID */
    private String studentId;
    /** 家长姓名 */
    private String parentName;
    /** 手机号 */
    private String phone;
    /** 关系类型 */
    private String relationType;
    /** 是否紧急联系人 */
    private Integer isEmergency;
}
