<template>
  <view class="page">
    <view class="header">
      <text class="title">学生管理</text>
    </view>
    <view class="content">
      <view class="list">
        <view class="item" v-for="item in studentList" :key="item.id">
          <text class="name">{{ item.studentName }}</text>
          <text class="info">{{ item.className }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { studentApi } from '@/api'

const studentList = ref([])

const loadData = async () => {
  try {
    const res = await studentApi.list({ page: 1, pageSize: 20 })
    studentList.value = res.records || []
  } catch (e) {
    console.error(e)
  }
}

loadData()
</script>

<style scoped>
.page { padding: 20rpx; }
.header { padding: 20rpx; background: #409EFF; border-radius: 10rpx; margin-bottom: 20rpx; }
.title { color: #fff; font-size: 32rpx; font-weight: bold; }
.item { padding: 20rpx; border-bottom: 1rpx solid #eee; }
.name { font-size: 28rpx; color: #333; }
.info { font-size: 24rpx; color: #999; margin-left: 20rpx; }
</style>
