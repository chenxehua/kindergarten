<template>
  <view class="home-container">
    <!-- 头部欢迎 -->
    <view class="header">
      <view class="user-info">
        <image :src="userInfo.avatar || '/static/avatar.png'" class="avatar"></image>
        <view class="info">
          <text class="nickname">{{ userInfo.nickname || '家长' }}</text>
          <text class="welcome">欢迎来到智慧幼儿园</text>
        </view>
      </view>
    </view>
    
    <!-- 孩子信息卡片 -->
    <view class="child-card" v-if="children.length > 0" @click="goToChildDetail">
      <view class="child-header">
        <text class="label">我的孩子</text>
        <text class="count">{{ children.length }}人</text>
      </view>
      <scroll-view scroll-x class="child-list">
        <view class="child-item" v-for="child in children" :key="child.studentId">
          <image :src="child.avatar || '/static/default-avatar.png'" class="child-avatar"></image>
          <text class="child-name">{{ child.studentName }}</text>
          <text class="child-class">{{ child.className }}</text>
        </view>
      </scroll-view>
    </view>
    
    <!-- 功能菜单 -->
    <view class="menu-grid">
      <view class="menu-item" @click="goToRecord">
        <view class="menu-icon record-icon">📝</view>
        <text class="menu-title">成长记录</text>
      </view>
      <view class="menu-item" @click="goToProfile">
        <view class="menu-icon profile-icon">📊</view>
        <text class="menu-title">成长画像</text>
      </view>
      <view class="menu-item" @click="goToFood">
        <view class="menu-icon food-icon">🍚</view>
        <text class="menu-title">今日食谱</text>
      </view>
      <view class="menu-item" @click="goToNotice">
        <view class="menu-icon notice-icon">📢</view>
        <text class="menu-title">通知公告</text>
      </view>
      <view class="menu-item" @click="goToVideo">
        <view class="menu-icon video-icon">🎬</view>
        <text class="menu-title">成长视频</text>
      </view>
      <view class="menu-item" @click="goToReport">
        <view class="menu-icon report-icon">📋</view>
        <text class="menu-title">月度报告</text>
      </view>
    </view>
    
    <!-- 今日动态 -->
    <view class="today-dynamic" v-if="todayRecords.length > 0">
      <view class="section-title">今日动态</view>
      <view class="dynamic-list">
        <view class="dynamic-item" v-for="record in todayRecords" :key="record.recordId">
          <view class="dynamic-content">
            <text class="dynamic-text">{{ record.overallNote || '今日表现良好' }}</text>
            <text class="dynamic-time">{{ record.recordDate }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, onShow } from 'vue';
import { userApi, studentApi, recordApi } from '../../api/index';

const userInfo = ref({});
const children = ref([]);
const todayRecords = ref([]);

onShow(() => {
  loadUserInfo();
  loadChildren();
  loadTodayRecords();
});

const loadUserInfo = () => {
  const info = uni.getStorageSync('userInfo');
  if (info) {
    userInfo.value = info;
  }
};

const loadChildren = async () => {
  try {
    // 获取家长关联的孩子列表
    // 这里简化处理，实际应该调用专门接口
    const list = await studentApi.list({ page: 1, pageSize: 10 });
    children.value = list.records || [];
  } catch (e) {
    console.log(e);
  }
};

const loadTodayRecords = async () => {
  if (children.value.length === 0) return;
  
  try {
    const childId = children.value[0].studentId;
    const record = await recordApi.getByDate(childId, new Date().toISOString().split('T')[0]);
    if (record) {
      todayRecords.value = [record];
    }
  } catch (e) {
    console.log(e);
  }
};

const goToChildDetail = () => {
  uni.navigateTo({ url: '/pages/child/detail' });
};

const goToRecord = () => {
  uni.navigateTo({ url: '/pages/record/list' });
};

const goToProfile = () => {
  uni.navigateTo({ url: '/pages/profile/growth' });
};

const goToFood = () => {
  uni.navigateTo({ url: '/pages/food/recipe' });
};

const goToNotice = () => {
  uni.navigateTo({ url: '/pages/notice/list' });
};

const goToVideo = () => {
  uni.navigateTo({ url: '/pages/video/list' });
};

const goToReport = () => {
  uni.navigateTo({ url: '/pages/report/monthly' });
};
</script>

<style lang="scss" scoped>
.home-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 20rpx;
}

.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40rpx 30rpx 80rpx;
  
  .user-info {
    display: flex;
    align-items: center;
    
    .avatar {
      width: 100rpx;
      height: 100rpx;
      border-radius: 50%;
      border: 4rpx solid #fff;
    }
    
    .info {
      margin-left: 20rpx;
      
      .nickname {
        display: block;
        font-size: 36rpx;
        color: #fff;
        font-weight: bold;
      }
      
      .welcome {
        font-size: 24rpx;
        color: rgba(255,255,255,0.8);
      }
    }
  }
}

.child-card {
  background: #fff;
  margin: -60rpx 30rpx 30rpx;
  border-radius: 20rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0,0,0,0.08);
  
  .child-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20rpx;
    
    .label {
      font-size: 32rpx;
      font-weight: bold;
      color: #333;
    }
    
    .count {
      font-size: 24rpx;
      color: #999;
    }
  }
  
  .child-list {
    white-space: nowrap;
    
    .child-item {
      display: inline-block;
      text-align: center;
      margin-right: 40rpx;
      
      .child-avatar {
        width: 100rpx;
        height: 100rpx;
        border-radius: 50%;
        background: #eee;
      }
      
      .child-name {
        display: block;
        font-size: 26rpx;
        color: #333;
        margin-top: 10rpx;
      }
      
      .child-class {
        font-size: 22rpx;
        color: #999;
      }
    }
  }
}

.menu-grid {
  display: flex;
  flex-wrap: wrap;
  padding: 0 20rpx;
  
  .menu-item {
    width: 25%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 30rpx;
    
    .menu-icon {
      width: 100rpx;
      height: 100rpx;
      border-radius: 20rpx;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 50rpx;
      margin-bottom: 10rpx;
    }
    
    .record-icon { background: #e8f4ff; }
    .profile-icon { background: #fef6e8; }
    .food-icon { background: #e8faf0; }
    .notice-icon { background: #ffe8e8; }
    .video-icon { background: #f0e8ff; }
    .report-icon { background: #e8f0ff; }
    
    .menu-title {
      font-size: 24rpx;
      color: #666;
    }
  }
}

.today-dynamic {
  margin: 0 30rpx;
  background: #fff;
  border-radius: 20rpx;
  padding: 30rpx;
  
  .section-title {
    font-size: 32rpx;
    font-weight: bold;
    color: #333;
    margin-bottom: 20rpx;
  }
  
  .dynamic-item {
    padding: 20rpx 0;
    border-bottom: 1rpx solid #f0f0f0;
    
    &:last-child {
      border-bottom: none;
    }
    
    .dynamic-content {
      .dynamic-text {
        display: block;
        font-size: 28rpx;
        color: #333;
        line-height: 1.5;
      }
      
      .dynamic-time {
        font-size: 24rpx;
        color: #999;
        margin-top: 10rpx;
      }
    }
  }
}
</style>
