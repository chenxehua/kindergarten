package com.kgms.student.service;

import com.kgms.common.exception.BusinessException;
import com.kgms.student.dto.StudentDTO;
import com.kgms.student.entity.StudentInfo;
import com.kgms.student.mapper.StudentInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生批量导入服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentBatchService {

    private final StudentInfoMapper studentInfoMapper;

    /**
     * 批量导入学生
     * @param file Excel文件
     * @return 导入结果
     */
    @Transactional
    public BatchImportResult importStudents(MultipartFile file) {
        log.info("开始批量导入学生");

        BatchImportResult result = new BatchImportResult();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            int successCount = 0;
            int failCount = 0;
            List<String> errors = new ArrayList<>();

            // 从第2行开始读取（跳过表头）
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String studentName = getCellValue(row.getCell(0));
                    Integer gender = getGenderFromString(getCellValue(row.getCell(1)));
                    String birthdayStr = getCellValue(row.getCell(2));
                    LocalDate birthday = birthdayStr != null ? LocalDate.parse(birthdayStr) : null;
                    String className = getCellValue(row.getCell(3));
                    String enrollDateStr = getCellValue(row.getCell(4));
                    LocalDate enrollDate = enrollDateStr != null ? LocalDate.parse(enrollDateStr) : null;
                    String emergencyContact = getCellValue(row.getCell(5));
                    String emergencyPhone = getCellValue(row.getCell(6));

                    if (studentName == null || studentName.isEmpty()) {
                        throw new Exception("学生姓名不能为空");
                    }

                    // 创建学生
                    StudentInfo student = new StudentInfo();
                    student.setStudentId(com.kgms.common.util.IdGenerator.generateStrId());
                    student.setStudentName(studentName);
                    student.setGender(gender);
                    student.setBirthday(birthday);
                    student.setClassId(className); // TODO: 需要根据班级名称查询classId
                    student.setEnrollDate(enrollDate);
                    student.setEmergencyContact(emergencyContact);
                    student.setEmergencyPhone(emergencyPhone);
                    student.setStatus(1); // 在园

                    studentInfoMapper.insert(student);
                    successCount++;

                } catch (Exception e) {
                    failCount++;
                    errors.add(String.format("第%d行导入失败: %s", i + 1, e.getMessage()));
                    log.error("第{}行导入失败: {}", i + 1, e.getMessage());
                }
            }

            result.setTotal(sheet.getLastRowNum());
            result.setSuccessCount(successCount);
            result.setFailCount(failCount);
            result.setErrors(errors);

            log.info("批量导入完成: 成功{}条, 失败{}条", successCount, failCount);

        } catch (Exception e) {
            log.error("批量导入异常: {}", e.getMessage());
            throw new BusinessException(500, "批量导入失败: " + e.getMessage());
        }

        return result;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default -> null;
        };
    }

    private Integer getGenderFromString(String gender) {
        if (gender == null) return null;
        return gender.contains("男") ? 1 : 2;
    }

    @lombok.Data
    public static class BatchImportResult {
        private int total;
        private int successCount;
        private int failCount;
        private List<String> errors;
    }
}