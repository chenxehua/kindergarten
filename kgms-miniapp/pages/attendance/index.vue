<template>
  <view class="page">
    <view class="header">
      <text class="title">考勤打卡</text>
    </view>

    <!-- 今日状态 -->
    <view class="today-status">
      <view class="status-card">
        <text class="status-label">今日状态</text>
        <text class="status-value" :class="'status-' + todayStatus">{{ getStatusText() }}</text>
      </view>
    </view>

    <!-- 打卡按钮 -->
    <view class="action-area" v-if="todayStatus === 'none'">
      <button class="check-in-btn" @click="handleCheckIn">签到</button>
      <button class="check-out-btn" @click="handleCheckOut">签退</button>
    </view>

    <!-- 考勤记录 -->
    <view class="records-section">
      <view class="section-title">考勤记录</view>
      <view class="records-list">
        <view v-for="record in records" :key="record.id" class="record-item">
          <view class="record-date">{{ record.date }}</view>
          <view class="record-info">
            <text class="time">{{ record.checkInTime }}</text>
            <text class="status" :class="'status-' + record.status">{{ record.statusText }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'

const todayStatus = ref('none') // none, checkedIn, checkedOut
const records = ref([
  { id: 1, date: '2026-03-25', checkInTime: '08:30', status: 'normal', statusText: '正常' },
  { id: 2, date: '2026-03-24', checkInTime: '08:25', status: 'late', statusText: '迟到' }
])

const getStatusText = () => {
  const texts = {
    none: '未打卡',
    checkedIn: '已签到',
    checkedOut: '已完成'
  }
  return texts[todayStatus.value]
}

const handleCheckIn = () => {
  uni.showLoading({ title: '签到中...' })
  setTimeout(() => {
    uni.hideLoading()
    uni.showToast({ title: '签到成功', icon: 'success' })
    todayStatus.value = 'checkedIn'
  }, 1000)
}

const handleCheckOut = () => {
  uni.showLoading({ title: '签退中...' })
  setTimeout(() => {
    uni.hideLoading()
    uni.showToast({ title: '签退成功', icon: 'success' })
    todayStatus.value = 'checkedOut'
  }, 1000)
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f5f5;
}

.header {
  padding: 30rpx;
  background: #fff;
  text-align: center;
}

.title {
  font-size: 34rpx;
  font-weight: bold;
  color: #333;
}

.today-status {
  margin: 30rpx;
}

.status-card {
  background: #fff;
  border-radius: 20rpx;
  padding: 40rpx;
  text-align: center;
}

.status-label {
  font-size: 28rpx;
  color: #999;
  display: block;
  margin-bottom: 20rpx;
}

.status-value {
  font-size: 48rpx;
  font-weight: bold;
}

.status-none { color: #999; }
.status-normal, .status-checkedIn { color: #67C23A; }
.status-late { color: #fa8c16; }
.status-checkedOut { color: #1890ff; }

.action-area {
  display: flex;
  justify-content: center;
  gap: 40rpx;
  padding: 40rpx;
}

.check-in-btn, .check-out-btn {
  width: 250rpx;
  height: 100rpx;
  line-height: 100rpx;
  border-radius: 50rpx;
  font-size: 32rpx;
  border: none;
}

.check-in-btn {
  background: linear-gradient(135deg, #67C23A 0%, #5daf34 100%);
  color: #fff;
}

.check-out-btn {
  background: linear-gradient(135deg, #409eff 0%, #337ecc 100%);
  color: #fff;
}

.records-section {
  margin: 30rpx;
  background: #fff;
  border-radius: 20rpx;
  padding: 30rpx;
}

.section-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
}

.record-item {
  display: flex;
  justify-content: space-between;
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
}

.record-item:last-child {
  border-bottom: none;
}

.record-date {
  font-size: 28rpx;
  color: #333;
}

.record-info {
  display: flex;
  align-items: center;
  gap: 20rpx;
}

.time {
  font-size: 28rpx;
  color: #666;
}

.status {
  font-size: 24rpx;
  padding: 4rpx 12rpx;
  border-radius: 20rpx;
}

.status-normal { background: #e8faf0; color: #67C23A; }
.status-late { background: #fef6e8; color: #fa8c16; }
</style>