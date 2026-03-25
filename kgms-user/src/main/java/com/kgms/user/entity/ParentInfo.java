package com.kgms.user.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
@TableName("t_parent_info")
public class ParentInfo extends BaseEntity {
    private String userId;
    private String studentId;
    private String relationType;
    private Integer isEmergency;
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }
    public Integer getIsEmergency() { return isEmergency; }
    public void setIsEmergency(Integer isEmergency) { this.isEmergency = isEmergency; }
}
