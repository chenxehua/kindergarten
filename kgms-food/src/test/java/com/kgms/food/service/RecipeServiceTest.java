package com.kgms.food.service;

import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.entity.FoodRecipe;
import com.kgms.food.mapper.FoodRecipeMapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.student.entity.StudentInfo;
import com.kgms.student.mapper.StudentInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private FoodRecipeMapper recipeMapper;

    @Mock
    private StudentInfoMapper studentInfoMapper;

    @InjectMocks
    private RecipeService recipeService;

    private FoodRecipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new FoodRecipe();
        testRecipe.setId(1L);
        testRecipe.setRecipeId("recipe_001");
        testRecipe.setKgId("kg_001");
        testRecipe.setWeekStart(LocalDate.of(2026, 3, 23));
        testRecipe.setPublishStatus(1);
    }

    /**
     * TC-FOOD-001: 新增食谱 - 成功
     */
    @Test
    void testAddRecipe_Success() {
        // Given
        RecipeDTO dto = new RecipeDTO();
        dto.setKgId("kg_001");
        dto.setWeekStart(LocalDate.of(2026, 3, 23));
        
        when(recipeMapper.selectOne(any())).thenReturn(null);
        when(recipeMapper.insert(any(FoodRecipe.class))).thenReturn(1);

        // When
        String recipeId = recipeService.addRecipe(dto);

        // Then
        assertNotNull(recipeId);
        verify(recipeMapper, times(1)).insert(any(FoodRecipe.class));
    }

    /**
     * TC-FOOD-002: 新增食谱 - 本周已存在
     */
    @Test
    void testAddRecipe_AlreadyExists() {
        // Given
        RecipeDTO dto = new RecipeDTO();
        dto.setKgId("kg_001");
        dto.setWeekStart(LocalDate.of(2026, 3, 23));
        
        when(recipeMapper.selectOne(any())).thenReturn(testRecipe);

        // When & Then
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> recipeService.addRecipe(dto)
        );
        assertEquals(400, exception.getCode());
    }

    /**
     * TC-FOOD-003: 获取食谱详情
     */
    @Test
    void testGetRecipeDetail() {
        // Given
        when(recipeMapper.selectOne(any())).thenReturn(testRecipe);

        // When
        RecipeVO vo = recipeService.getRecipeDetail("recipe_001");

        // Then
        assertNotNull(vo);
        assertEquals("recipe_001", vo.getRecipeId());
    }

    /**
     * TC-FOOD-004: 获取食谱列表
     */
    @Test
    void testGetRecipeList() {
        // Given
        when(recipeMapper.selectList(any())).thenReturn(Arrays.asList(testRecipe));

        // When
        List<RecipeVO> list = recipeService.getRecipeList("kg_001", 2026, 3);

        // Then
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    /**
     * TC-FOOD-005: 过敏食物检查 - 无过敏风险
     */
    @Test
    void testCheckAllergy_NoAllergy() {
        // Given
        StudentInfo student = new StudentInfo();
        student.setStudentId("stu_001");
        student.setAllergyInfo("鸡蛋,牛奶"); // 学生有过敏信息

        testRecipe.setMonday("米饭,青菜,猪肉");
        testRecipe.setTuesday("面条,黄瓜,鸡肉");
        testRecipe.setWednesday("粥,土豆,牛肉");
        testRecipe.setThursday("米饭,番茄,鱼肉");
        testRecipe.setFriday("馒头,白菜,猪肉");
        testRecipe.setSaturday("米饭,萝卜,鸡肉");
        testRecipe.setSunday("面条,青椒,牛肉");

        when(studentInfoMapper.selectOne(any())).thenReturn(student);
        when(recipeMapper.selectOne(any())).thenReturn(testRecipe);

        // When
        boolean hasAllergy = recipeService.checkAllergy("stu_001", "recipe_001");

        // Then
        assertFalse(hasAllergy);
    }

    /**
     * TC-FOOD-006: 过敏食物检查 - 有过敏风险
     */
    @Test
    void testCheckAllergy_HasAllergy() {
        // Given
        StudentInfo student = new StudentInfo();
        student.setStudentId("stu_001");
        student.setAllergyInfo("鸡蛋,牛奶"); // 学生对鸡蛋过敏

        testRecipe.setMonday("鸡蛋羹,牛奶,面包"); // 包含过敏食物

        when(studentInfoMapper.selectOne(any())).thenReturn(student);
        when(recipeMapper.selectOne(any())).thenReturn(testRecipe);

        // When
        boolean hasAllergy = recipeService.checkAllergy("stu_001", "recipe_001");

        // Then
        assertTrue(hasAllergy);
    }

    /**
     * TC-FOOD-007: 过敏食物检查 - 学生无过敏信息
     */
    @Test
    void testCheckAllergy_NoAllergyInfo() {
        // Given
        StudentInfo student = new StudentInfo();
        student.setStudentId("stu_001");
        student.setAllergyInfo(null); // 无过敏信息

        when(studentInfoMapper.selectOne(any())).thenReturn(student);
        when(recipeMapper.selectOne(any())).thenReturn(testRecipe);

        // When
        boolean hasAllergy = recipeService.checkAllergy("stu_001", "recipe_001");

        // Then
        assertFalse(hasAllergy);
    }
}
