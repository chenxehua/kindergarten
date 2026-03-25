package com.kgms.student.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import com.kgms.common.util.IdGenerator;
import com.kgms.student.dto.StudentDTO;
import com.kgms.student.dto.StudentVO;
import com.kgms.student.entity.StudentInfo;
import com.kgms.student.mapper.StudentInfoMapper;
import com.kgms.common.enums.Gender;
import com.kgms.common.enums.StudentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentInfoMapper studentInfoMapper;

    /**
     * 新增学生
     */
    @Transactional
    public String addStudent(StudentDTO dto) {
        StudentInfo student = new StudentInfo();
        student.setStudentId(IdGenerator.generateStrId());
        student.setStudentName(dto.getStudentName());
        student.setGender(dto.getGender());
        student.setBirthday(dto.getBirthday());
        student.setIdCard(dto.getIdCard());
        student.setAvatar(dto.getAvatar());
        student.setClassId(dto.getClassId());
        student.setEnrollDate(dto.getEnrollDate());
        student.setStatus(1); // 在园
        student.setAllergyInfo(dto.getAllergyInfo());
        student.setMedicalHistory(dto.getMedicalHistory());
        student.setHomeAddress(dto.getHomeAddress());
        student.setEmergencyContact(dto.getEmergencyContact());
        student.setEmergencyPhone(dto.getEmergencyPhone());

        studentInfoMapper.insert(student);
        return student.getStudentId();
    }

    /**
     * 更新学生信息
     */
    @Transactional
    public void updateStudent(String studentId, StudentDTO dto) {
        StudentInfo student = getStudentById(studentId);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }

        if (StringUtils.hasText(dto.getStudentName())) {
            student.setStudentName(dto.getStudentName());
        }
        if (dto.getGender() != null) {
            student.setGender(dto.getGender());
        }
        if (dto.getBirthday() != null) {
            student.setBirthday(dto.getBirthday());
        }
        if (dto.getClassId() != null) {
            student.setClassId(dto.getClassId());
        }
        if (StringUtils.hasText(dto.getAllergyInfo())) {
            student.setAllergyInfo(dto.getAllergyInfo());
        }
        if (StringUtils.hasText(dto.getEmergencyContact())) {
            student.setEmergencyContact(dto.getEmergencyContact());
        }
        if (StringUtils.hasText(dto.getEmergencyPhone())) {
            student.setEmergencyPhone(dto.getEmergencyPhone());
        }

        studentInfoMapper.updateById(student);
    }

    /**
     * 删除学生
     */
    @Transactional
    public void deleteStudent(String studentId) {
        StudentInfo student = getStudentById(studentId);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }
        // 逻辑删除
        student.setStatus(0); // 离园
        studentInfoMapper.updateById(student);
    }

    /**
     * 获取学生详情
     */
    public StudentVO getStudentDetail(String studentId) {
        StudentInfo student = getStudentById(studentId);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }
        return convertToVO(student);
    }

    /**
     * 分页查询学生列表
     */
    public PageResult<StudentVO> getStudentList(String classId, String keyword, Integer status, Integer page, Integer pageSize) {
        LambdaQueryWrapper<StudentInfo> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(classId)) {
            wrapper.eq(StudentInfo::getClassId, classId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.like(StudentInfo::getStudentName, keyword);
        }
        if (status != null) {
            wrapper.eq(StudentInfo::getStatus, status);
        } else {
            wrapper.eq(StudentInfo::getStatus, 1); // 默认查询在园学生
        }

        wrapper.orderByDesc(StudentInfo::getCreateTime);

        IPage<StudentInfo> pageResult = studentInfoMapper.selectPage(new Page<>(page, pageSize), wrapper);

        List<StudentVO> voList = pageResult.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(voList, pageResult.getTotal(), page, pageSize);
    }

    /**
     * 根据班级获取学生列表
     */
    public List<StudentVO> getStudentsByClassId(String classId) {
        LambdaQueryWrapper<StudentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentInfo::getClassId, classId);
        wrapper.eq(StudentInfo::getStatus, 1);
        wrapper.orderByAsc(StudentInfo::getStudentName);

        return studentInfoMapper.selectList(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 转班操作
     */
    @Transactional
    public void transferClass(String studentId, String targetClassId) {
        StudentInfo student = getStudentById(studentId);
        if (student == null) {
            throw new BusinessException(404, "学生不存在");
        }
        student.setClassId(targetClassId);
        studentInfoMapper.updateById(student);
    }

    /**
     * 根据ID获取学生
     */
    private StudentInfo getStudentById(String studentId) {
        return studentInfoMapper.selectOne(
                new LambdaQueryWrapper<StudentInfo>()
                        .eq(StudentInfo::getStudentId, studentId)
        );
    }

    /**
     * 转换为VO
     */
    private StudentVO convertToVO(StudentInfo student) {
        StudentVO vo = new StudentVO();
        vo.setStudentId(student.getStudentId());
        vo.setStudentName(student.getStudentName());
        vo.setGender(student.getGender());
        vo.setBirthday(student.getBirthday());
        vo.setIdCard(student.getIdCard());
        vo.setAvatar(student.getAvatar());
        vo.setClassId(student.getClassId());
        vo.setEnrollDate(student.getEnrollDate());
        vo.setStatus(student.getStatus());
        vo.setAllergyInfo(student.getAllergyInfo());
        vo.setMedicalHistory(student.getMedicalHistory());
        vo.setHomeAddress(student.getHomeAddress());
        vo.setEmergencyContact(student.getEmergencyContact());
        vo.setEmergencyPhone(student.getEmergencyPhone());
        vo.setCreateTime(student.getCreateTime());

        // 性别描述
        Gender gender = Gender.getByCode(student.getGender());
        if (gender != null) {
            vo.setGenderDesc(gender.getDesc());
        }

        // 状态描述
        StudentStatus status = StudentStatus.getByCode(student.getStatus());
        if (status != null) {
            vo.setStatusDesc(status.getDesc());
        }

        return vo;
    }
}
