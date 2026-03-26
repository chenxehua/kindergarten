<template>
  <view class="page">
    <!-- 顶部信息 -->
    <view class="header">
      <view class="avatar-section">
        <image class="avatar" :src="userInfo.avatar || '/static/default-avatar.png'" @click="chooseAvatar" />
        <text class="nickname">{{ userInfo.nickname || '请登录' }}</text>
        <text class="role">{{ userInfo.userType === 'TEACHER' ? '老师' : '家长' }}</text>
      </view>
    </view>

    <!-- 功能列表 -->
    <view class="section">
      <view class="section-title">账号设置</view>
      <view class="menu-list">
        <view class="menu-item" @click="goToPage('/pages/profile/edit')">
          <text class="icon">👤</text>
          <text class="label">个人信息</text>
          <text class="arrow">></text>
        </view>
        <view class="menu-item" @click="goToPage('/pages/profile/children')">
          <text class="icon">👶</text>
          <text class="label">孩子信息</text>
          <text class="arrow">></text>
        </view>
        <view class="menu-item" @click="showPushSettings = true">
          <text class="icon">🔔</text>
          <text class="label">通知设置</text>
          <text class="arrow">></text>
        </view>
      </view>
    </view>

    <view class="section">
      <view class="section-title">通用设置</view>
      <view class="menu-list">
        <view class="menu-item">
          <text class="icon">🔒</text>
          <text class="label">修改密码</text>
          <text class="arrow">></text>
        </view>
        <view class="menu-item">
          <text class="icon">🌙</text>
          <text class="label">深色模式</text>
          <switch :checked="darkMode" @change="darkMode = !darkMode" />
        </view>
      </view>
    </view>

    <view class="section">
      <view class="section-title">其他</view>
      <view class="menu-list">
        <view class="menu-item">
          <text class="icon">ℹ️</text>
          <text class="label">关于我们</text>
          <text class="arrow">></text>
        </view>
        <view class="menu-item" @click="handleLogout">
          <text class="icon">🚪</text>
          <text class="label">退出登录</text>
          <text class="arrow">></text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, onShow } from 'vue'
import { userApi } from '@/api'

const userInfo = ref({})
const darkMode = ref(false)
const showPushSettings = ref(false)

const loadUserInfo = () => {
  const info = uni.getStorageSync('userInfo')
  if (info) {
    userInfo.value = info
  }
}

const chooseAvatar = () => {
  uni.chooseImage({
    count: 1,
    success: (res) => {
      // TODO: 上传头像
      console.log('选择头像', res)
    }
  })
}

const goToPage = (url) => {
  uni.navigateTo({ url })
}

const handleLogout = () => {
  uni.showModal({
    title: '提示',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        uni.clearStorageSync()
        uni.reLaunch({ url: '/pages/login/login' })
      }
    }
  })
}

onShow(() => {
  loadUserInfo()
})
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f5f5;
}

.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 60rpx 30rpx;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.avatar {
  width: 150rpx;
  height: 150rpx;
  border-radius: 50%;
  border: 6rpx solid rgba(255, 255, 255, 0.3);
  margin-bottom: 20rpx;
}

.nickname {
  font-size: 36rpx;
  font-weight: bold;
  color: #fff;
  margin-bottom: 10rpx;
}

.role {
  font-size: 24rpx;
  color: rgba(255, 255, 255, 0.8);
  background: rgba(255, 255, 255, 0.2);
  padding: 6rpx 20rpx;
  border-radius: 20rpx;
}

.section {
  margin: 20rpx;
  background: #fff;
  border-radius: 16rpx;
  overflow: hidden;
}

.section-title {
  padding: 24rpx 30rpx 16rpx;
  font-size: 26rpx;
  color: #999;
}

.menu-list {
  padding: 0 10rpx;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 28rpx 20rpx;
  border-bottom: 1rpx solid #f5f5f5;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-item .icon {
  font-size: 36rpx;
  margin-right: 20rpx;
}

.menu-item .label {
  flex: 1;
  font-size: 28rpx;
  color: #333;
}

.menu-item .arrow {
  color: #999;
  font-size: 24rpx;
}
</style>