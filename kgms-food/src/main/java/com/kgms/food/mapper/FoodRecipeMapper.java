package com.kgms.food.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kgms.food.entity.FoodRecipe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodRecipeMapper extends BaseMapper<FoodRecipe> {
}
