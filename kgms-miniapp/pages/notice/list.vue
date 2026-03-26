<template>
  <view class="page">
    <!-- 顶部导航 -->
    <view class="header">
      <text class="title">通知消息</text>
    </view>

    <!-- 通知列表 -->
    <scroll-view scroll-y class="notice-list">
      <view v-if="notices.length === 0" class="empty">
        <text>暂无通知</text>
      </view>

      <view
        v-for="item in notices"
        :key="item.noticeId"
        class="notice-item"
        :class="{ unread: item.isRead === 0 }"
        @click="goToDetail(item.noticeId)"
      >
        <view class="notice-header">
          <view class="notice-type" :class="'type-' + item.noticeType">
            {{ getTypeName(item.noticeType) }}
          </view>
          <text class="notice-time">{{ item.publishTime }}</text>
        </view>

        <view class="notice-title">{{ item.title }}</view>
        <view class="notice-content">{{ item.content }}</view>

        <view class="notice-footer">
          <view class="footer-item" v-if="item.isSignup">
            <text>报名</text>
          </view>
          <view class="footer-item" v-if="item.needReply">
            <text>需回复</text>
          </view>
          <view class="read-status" v-if="item.isRead === 0">
            <text>未读</text>
          </view>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { noticeApi } from '@/api'

const notices = ref([])

const getTypeName = (type) => {
  const types = {
    SCHOOL: '园所',
    CLASS: '班级',
    ACTIVITY: '活动',
    PAYMENT: '缴费',
    TIP: '温馨提示',
    URGENT: '紧急'
  }
  return types[type] || '通知'
}

const loadNotices = async () => {
  try {
    const res = await noticeApi.list()
    notices.value = res || []
  } catch (e) {
    console.error('加载通知失败', e)
  }
}

const goToDetail = (noticeId) => {
  uni.navigateTo({ url: `/pages/notice/detail?noticeId=${noticeId}` })
}

onMounted(() => {
  loadNotices()
})
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

.notice-list {
  height: calc(100vh - 100rpx);
  padding: 20rpx;
}

.notice-item {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.notice-item.unread {
  border-left: 8rpx solid #67C23A;
}

.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.notice-type {
  font-size: 22rpx;
  padding: 6rpx 16rpx;
  border-radius: 20rpx;
}

.type-SCHOOL { background: #e6f7ff; color: #1890ff; }
.type-CLASS { background: #f6ffed; color: #52c41a; }
.type-ACTIVITY { background: #fff7e6; color: #fa8c16; }
.type-PAYMENT { background: #fff0f6; color: #eb2f96; }
.type-TIP { background: #f0f5ff; color: #2f54eb; }
.type-URGENT { background: #fff1f0; color: #ff4d4f; }

.notice-time {
  font-size: 24rpx;
  color: #999;
}

.notice-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 12rpx;
}

.notice-content {
  font-size: 26rpx;
  color: #666;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.notice-footer {
  display: flex;
  gap: 20rpx;
  margin-top: 20rpx;
  padding-top: 20rpx;
  border-top: 1rpx solid #f5f5f5;
}

.footer-item text {
  font-size: 24rpx;
  color: #67C23A;
  padding: 4rpx 12rpx;
  border: 1rpx solid #67C23A;
  border-radius: 20rpx;
}

.read-status text {
  font-size: 24rpx;
  color: #999;
}

.empty {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>