package com.kgms.food.service;

import com.kgms.food.dto.RecipeDTO;
import com.kgms.food.dto.RecipeVO;
import com.kgms.food.entity.Recipe;
import com.kgms.food.mapper.RecipeMapper;
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
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setId(1L);
        testRecipe.setRecipeId("recipe_001");
        testRecipe.setRecipeDate(LocalDate.now());
        testRecipe.setMealType(1);
        testRecipe.setFoodName("牛奶面包");
        testRecipe.setCalorie(300);
        testRecipe.setPublishStatus(1);
    }

    /**
     * TC-FOOD-001: 发布食谱
     */
    @Test
    void testPublishRecipe_Success() {
        // Given
        RecipeDTO dto = new RecipeDTO();
        dto.setRecipeDate(LocalDate.now());
        dto.setMealType(1);
        dto.setFoodName("豆浆油条");
        
        when(recipeMapper.insert(any(Recipe.class))).thenReturn(1);

        // When
        String recipeId = recipeService.publishRecipe(dto);

        // Then
        assertNotNull(recipeId);
        verify(recipeMapper, times(1)).insert(any(Recipe.class));
    }

    /**
     * TC-FOOD-002: 获取食谱列表
     */
    @Test
    void testGetRecipeList() {
        // Given
        List<Recipe> recipes = Arrays.asList(testRecipe);
        when(recipeMapper.selectList(any())).thenReturn(recipes);

        // When
        List<RecipeVO> result = recipeService.getRecipeList(LocalDate.now(), LocalDate.now().plusDays(7));

        // Then
        assertNotNull(result);
        assertTrue(result.size() >= 1);
    }

    /**
     * TC-FOOD-003: 获取今日食谱
     */
    @Test
    void testGetTodayRecipes() {
        // Given
        List<Recipe> recipes = Arrays.asList(testRecipe);
        when(recipeMapper.selectList(any())).thenReturn(recipes);

        // When
        List<RecipeVO> result = recipeService.getTodayRecipes();

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.get(0).getRecipeDate());
    }
}
