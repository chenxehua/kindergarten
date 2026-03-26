<template>
  <view class="page">
    <!-- 顶部导航 -->
    <view class="header">
      <text class="title">家校沟通</text>
    </view>

    <!-- 联系人列表 -->
    <scroll-view scroll-y class="contact-list">
      <view class="search-bar">
        <input class="search-input" placeholder="搜索老师或家长" v-model="keyword" />
      </view>

      <view class="section-title">班级老师</view>
      <view class="contact-item" v-for="teacher in teachers" :key="teacher.userId" @click="goToChat(teacher)">
        <image class="avatar" :src="teacher.avatar || '/static/default-avatar.png'" />
        <view class="info">
          <text class="name">{{ teacher.name }}</text>
          <text class="role">{{ teacher.position || '老师' }}</text>
        </view>
        <text class="unread" v-if="teacher.unreadCount > 0">{{ teacher.unreadCount }}</text>
      </view>

      <view class="section-title" v-if="classParents.length > 0">班级家长</view>
      <view class="contact-item" v-for="parent in classParents" :key="parent.userId" @click="goToChat(parent)">
        <image class="avatar" :src="parent.avatar || '/static/default-avatar.png'" />
        <view class="info">
          <text class="name">{{ parent.name }}</text>
          <text class="role">{{ parent.relation }}</text>
        </view>
        <text class="unread" v-if="parent.unreadCount > 0">{{ parent.unreadCount }}</text>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref } from 'vue'

const keyword = ref('')
const teachers = ref([
  { userId: '1', name: '张老师', position: '班主任', avatar: '', unreadCount: 2 },
  { userId: '2', name: '李老师', position: '副班老师', avatar: '', unreadCount: 0 }
])
const classParents = ref([])

const goToChat = (contact) => {
  uni.navigateTo({
    url: `/pages/chat/chat?userId=${contact.userId}&name=${contact.name}&role=${contact.role || '老师'}`
  })
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

.contact-list {
  height: calc(100vh - 100rpx);
  padding: 20rpx;
}

.search-bar {
  margin-bottom: 20rpx;
}

.search-input {
  background: #fff;
  padding: 20rpx 30rpx;
  border-radius: 30rpx;
  font-size: 28rpx;
}

.section-title {
  font-size: 26rpx;
  color: #999;
  padding: 20rpx 10rpx;
}

.contact-item {
  display: flex;
  align-items: center;
  background: #fff;
  padding: 24rpx;
  border-radius: 16rpx;
  margin-bottom: 16rpx;
}

.avatar {
  width: 90rpx;
  height: 90rpx;
  border-radius: 50%;
  margin-right: 20rpx;
}

.info {
  flex: 1;
}

.name {
  font-size: 30rpx;
  color: #333;
  font-weight: bold;
  display: block;
}

.role {
  font-size: 24rpx;
  color: #999;
}

.unread {
  background: #ff4d4f;
  color: #fff;
  font-size: 20rpx;
  padding: 4rpx 12rpx;
  border-radius: 20rpx;
}
</style>