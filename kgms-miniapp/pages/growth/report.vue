<template>
  <view class="page">
    <view class="header">
      <text class="title">月度报告</text>
    </view>

    <!-- 报告列表 -->
    <scroll-view scroll-y class="report-list">
      <view v-if="reports.length === 0" class="empty">
        <text>暂无月度报告</text>
      </view>

      <view v-for="report in reports" :key="report.reportId" class="report-item" @click="viewReport(report)">
        <view class="report-month">{{ report.month }}</view>
        <view class="report-content">
          <view class="report-summary">
            <text class="summary-text">{{ report.summary || '本月成长报告已生成' }}</text>
          </view>
          <view class="report-stats">
            <view class="stat-item">
              <text class="stat-value">{{ report.daysInPark }}</text>
              <text class="stat-label">在园天数</text>
            </view>
            <view class="stat-item">
              <text class="stat-value">{{ report.attendanceRate }}%</text>
              <text class="stat-label">出勤率</text>
            </view>
            <view class="stat-item">
              <text class="stat-value">{{ report.recordCount }}</text>
              <text class="stat-label">记录数</text>
            </view>
          </view>
          <view class="report-status" :class="'status-' + report.status">
            {{ getStatusText(report.status) }}
          </view>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { growthApi } from '@/api'

const reports = ref([])

const getStatusText = (status) => {
  const texts = {
    DRAFT: '草稿',
    PENDING_REVIEW: '待审核',
    PUBLISHED: '已发布'
  }
  return texts[status] || '未知'
}

const loadReports = async () => {
  try {
    const res = await growthApi.report()
    reports.value = res || []
  } catch (e) {
    console.error('加载报告失败', e)
  }
}

const viewReport = (report) => {
  if (report.status !== 'PUBLISHED') {
    uni.showToast({ title: '报告审核中，暂不可查看', icon: 'none' })
    return
  }
  uni.navigateTo({
    url: `/pages/growth/report-detail?reportId=${report.reportId}`
  })
}

loadReports()
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

.report-list {
  height: calc(100vh - 100rpx);
  padding: 20rpx;
}

.report-item {
  background: #fff;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  overflow: hidden;
}

.report-month {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20rpx 30rpx;
  color: #fff;
  font-size: 32rpx;
  font-weight: bold;
}

.report-content {
  padding: 30rpx;
}

.report-summary {
  margin-bottom: 20rpx;
}

.summary-text {
  font-size: 28rpx;
  color: #333;
  line-height: 1.5;
}

.report-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20rpx;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  display: block;
}

.stat-label {
  font-size: 24rpx;
  color: #999;
}

.report-status {
  text-align: center;
  padding: 10rpx;
  font-size: 24rpx;
  border-radius: 20rpx;
}

.status-PUBLISHED { background: #e8faf0; color: #67C23A; }
.status-DRAFT { background: #fef6e8; color: #fa8c16; }
.status-PENDING_REVIEW { background: #e6f7ff; color: #1890ff; }

.empty {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>