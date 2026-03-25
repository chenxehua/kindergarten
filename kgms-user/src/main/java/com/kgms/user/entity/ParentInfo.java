package com.kgms.user.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_parent_info")
public class ParentInfo extends BaseEntity {
    private String userId;
    private String studentId;
    private String relationType;
    private Integer isEmergency;
}
