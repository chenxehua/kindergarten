<template>
  <view class="page">
    <view class="header">
      <text class="title">食谱预览</text>
    </view>
    <view class="content">
      <view class="week-tabs">
        <view class="tab" :class="{active: currentDay === index}" v-for="(day, index) in weekDays" :key="index" @click="switchDay(index)">
          <text>{{ day }}</text>
        </view>
      </view>
      <view class="recipe-list">
        <view class="item" v-for="item in recipeList" :key="item.id">
          <text class="type">{{ item.mealType }}</text>
          <text class="name">{{ item.recipeName }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { foodApi } from '@/api'

const currentDay = ref(0)
const weekDays = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
const recipeList = ref([])

const loadData = async () => {
  try {
    const res = await foodApi.thisWeek()
    recipeList.value = res || []
  } catch (e) {
    console.error(e)
  }
}

const switchDay = (index) => {
  currentDay.value = index
  loadData()
}

loadData()
</script>

<style scoped>
.page { padding: 20rpx; }
.header { padding: 20rpx; background: #E6A23C; border-radius: 10rpx; margin-bottom: 20rpx; }
.title { color: #fff; font-size: 32rpx; font-weight: bold; }
.week-tabs { display: flex; justify-content: space-around; margin-bottom: 20rpx; }
.tab { padding: 15rpx 20rpx; border-radius: 10rpx; background: #f5f5f5; }
.tab.active { background: #E6A23C; color: #fff; }
.item { padding: 20rpx; border-bottom: 1rpx solid #eee; display: flex; justify-content: space-between; }
.type { font-size: 24rpx; color: #E6A23C; }
.name { font-size: 28rpx; color: #333; }
</style>
