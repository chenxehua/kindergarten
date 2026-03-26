<template>
  <view class="page">
    <view class="header">
      <text class="title">班级列表</text>
    </view>
    <view class="content">
      <view class="list">
        <view class="item" v-for="item in classList" :key="item.id">
          <text class="name">{{ item.className }}</text>
          <text class="info">{{ item.studentCount }}人</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { classApi } from '@/api'

const classList = ref([])

const loadData = async () => {
  try {
    const res = await classApi.list({ page: 1, pageSize: 20 })
    classList.value = res.records || []
  } catch (e) {
    console.error(e)
  }
}

loadData()
</script>

<style scoped>
.page { padding: 20rpx; }
.header { padding: 20rpx; background: #67C23A; border-radius: 10rpx; margin-bottom: 20rpx; }
.title { color: #fff; font-size: 32rpx; font-weight: bold; }
.item { padding: 20rpx; border-bottom: 1rpx solid #eee; }
.name { font-size: 28rpx; color: #333; }
.info { font-size: 24rpx; color: #999; margin-left: 20rpx; }
</style>
