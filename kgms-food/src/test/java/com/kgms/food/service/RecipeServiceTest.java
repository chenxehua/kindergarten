package com.kgms.food.service;

import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.entity.FoodRecipe;
import com.kgms.food.mapper.FoodRecipeMapper;
import com.kgms.common.exception.BusinessException;
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
}
