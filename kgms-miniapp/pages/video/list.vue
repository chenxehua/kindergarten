<template>
  <view class="page">
    <view class="header">
      <text class="title">成长视频</text>
    </view>

    <!-- 视频列表 -->
    <scroll-view scroll-y class="video-list">
      <view v-if="videos.length === 0" class="empty">
        <text>暂无成长视频</text>
      </view>

      <view v-for="video in videos" :key="video.videoId" class="video-item" @click="playVideo(video)">
        <view class="video-cover">
          <image class="cover-img" :src="video.coverUrl || '/static/default-video.png'" mode="aspectFill" />
          <view class="play-btn">▶</view>
          <view class="duration">{{ video.duration }}</view>
        </view>
        <view class="video-info">
          <text class="video-title">{{ video.title }}</text>
          <text class="video-date">{{ video.createTime }}</text>
        </view>
        <view class="video-status" :class="'status-' + video.status">
          {{ getStatusText(video.status) }}
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { videoApi } from '@/api'

const videos = ref([])

const getStatusText = (status) => {
  const texts = {
    GENERATING: '生成中',
    PENDING_REVIEW: '待审核',
    PUBLISHED: '已发布',
    REJECTED: '已驳回'
  }
  return texts[status] || '未知'
}

const loadVideos = async () => {
  try {
    const res = await videoApi.list()
    videos.value = res || []
  } catch (e) {
    console.error('加载视频失败', e)
  }
}

const playVideo = (video) => {
  if (video.status !== 'PUBLISHED') {
    uni.showToast({ title: '视频审核中，暂不可播放', icon: 'none' })
    return
  }
  uni.navigateTo({
    url: `/pages/video/play?videoId=${video.videoId}&url=${video.videoUrl}`
  })
}

loadVideos()
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

.video-list {
  height: calc(100vh - 100rpx);
  padding: 20rpx;
}

.video-item {
  background: #fff;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  overflow: hidden;
}

.video-cover {
  position: relative;
  width: 100%;
  height: 350rpx;
}

.cover-img {
  width: 100%;
  height: 100%;
}

.play-btn {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100rpx;
  height: 100rpx;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 40rpx;
}

.duration {
  position: absolute;
  bottom: 20rpx;
  right: 20rpx;
  background: rgba(0, 0, 0, 0.6);
  color: #fff;
  font-size: 22rpx;
  padding: 6rpx 12rpx;
  border-radius: 6rpx;
}

.video-info {
  padding: 20rpx;
}

.video-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 10rpx;
}

.video-date {
  font-size: 24rpx;
  color: #999;
}

.video-status {
  padding: 10rpx 20rpx;
  font-size: 22rpx;
}

.status-PUBLISHED { background: #e8faf0; color: #67C23A; }
.status-GENERATING { background: #e6f7ff; color: #1890ff; }
.status-PENDING_REVIEW { background: #fef6e8; color: #fa8c16; }
.status-REJECTED { background: #ffe8e8; color: #ff4d4f; }

.empty {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>