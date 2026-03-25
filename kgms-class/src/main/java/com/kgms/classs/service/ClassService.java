package com.kgms.classs.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgms.classs.dto.ClassDTO;
import com.kgms.classs.dto.ClassVO;
import com.kgms.classs.entity.ClassInfo;
import com.kgms.classs.mapper.ClassInfoMapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.result.PageResult;
import com.kgms.common.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassInfoMapper classInfoMapper;

    public String addClass(ClassDTO dto) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setClassId(IdGenerator.generateStrId());
        classInfo.setKgId(dto.getKgId());
        classInfo.setClassName(dto.getClassName());
        classInfo.setGrade(dto.getGrade());
        classInfo.setHeadTeacherId(dto.getHeadTeacherId());
        classInfo.setCapacity(dto.getCapacity());
        classInfo.setStudentCount(0);
        classInfo.setStatus(1);
        classInfoMapper.insert(classInfo);
        return classInfo.getClassId();
    }

    public void updateClass(String classId, ClassDTO dto) {
        ClassInfo classInfo = getClassById(classId);
        if (classInfo == null) {
            throw new BusinessException(404, "班级不存在");
        }
        if (StringUtils.hasText(dto.getClassName())) {
            classInfo.setClassName(dto.getClassName());
        }
        if (StringUtils.hasText(dto.getGrade())) {
            classInfo.setGrade(dto.getGrade());
        }
        if (dto.getHeadTeacherId() != null) {
            classInfo.setHeadTeacherId(dto.getHeadTeacherId());
        }
        if (dto.getCapacity() != null) {
            classInfo.setCapacity(dto.getCapacity());
        }
        classInfoMapper.updateById(classInfo);
    }

    public ClassVO getClassDetail(String classId) {
        ClassInfo classInfo = getClassById(classId);
        if (classInfo == null) {
            throw new BusinessException(404, "班级不存在");
        }
        return convertToVO(classInfo);
    }

    public PageResult<ClassVO> getClassList(String kgId, String grade, Integer page, Integer pageSize) {
        LambdaQueryWrapper<ClassInfo> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(kgId)) {
            wrapper.eq(ClassInfo::getKgId, kgId);
        }
        if (StringUtils.hasText(grade)) {
            wrapper.eq(ClassInfo::getGrade, grade);
        }
        wrapper.eq(ClassInfo::getStatus, 1);
        wrapper.orderByAsc(ClassInfo::getClassName);

        IPage<ClassInfo> pageResult = classInfoMapper.selectPage(new Page<>(page, pageSize), wrapper);
        List<ClassVO> voList = pageResult.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.of(voList, pageResult.getTotal(), page, pageSize);
    }

    public List<ClassVO> getClassesByKgId(String kgId) {
        LambdaQueryWrapper<ClassInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClassInfo::getKgId, kgId);
        wrapper.eq(ClassInfo::getStatus, 1);
        wrapper.orderByAsc(ClassInfo::getGrade, ClassInfo::getClassName);
        return classInfoMapper.selectList(wrapper).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private ClassInfo getClassById(String classId) {
        return classInfoMapper.selectOne(
                new LambdaQueryWrapper<ClassInfo>().eq(ClassInfo::getClassId, classId)
        );
    }

    private ClassVO convertToVO(ClassInfo classInfo) {
        ClassVO vo = new ClassVO();
        vo.setClassId(classInfo.getClassId());
        vo.setKgId(classInfo.getKgId());
        vo.setClassName(classInfo.getClassName());
        vo.setGrade(classInfo.getGrade());
        vo.setHeadTeacherId(classInfo.getHeadTeacherId());
        vo.setCapacity(classInfo.getCapacity());
        vo.setStudentCount(classInfo.getStudentCount());
        vo.setStatus(classInfo.getStatus());
        return vo;
    }
}
