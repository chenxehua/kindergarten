<template>
  <view class="record-list-container">
    <!-- 筛选栏 -->
    <view class="filter-bar">
      <picker mode="date" :value="selectedDate" @change="onDateChange">
        <view class="date-picker">
          <text>{{ selectedDate || '选择日期' }}</text>
          <text class="arrow">▼</text>
        </view>
      </picker>
      <view class="child-picker" @click="showChildPicker = true">
        <text>{{ selectedChildName || '选择孩子' }}</text>
        <text class="arrow">▼</text>
      </view>
    </view>
    
    <!-- 记录列表 -->
    <scroll-view scroll-y class="record-list" @scrolltolower="loadMore">
      <view class="record-item" v-for="record in records" :key="record.recordId" @click="goToDetail(record.recordId)">
        <view class="record-header">
          <text class="record-date">{{ record.recordDate }}</text>
          <text class="record-status" :class="record.publishStatus === 1 ? 'published' : 'draft'">
            {{ record.publishStatus === 1 ? '已发布' : '草稿' }}
          </text>
        </view>
        
        <view class="record-content">
          <view class="record-tag" v-if="record.emotionType">
            <text class="tag-icon">😊</text>
            <text>{{ record.emotionType }}</text>
          </view>
          <view class="record-tag" v-if="record.breakfast || record.lunch">
            <text class="tag-icon">🍚</text>
            <text>{{ record.breakfast || record.lunch }}</text>
          </view>
          <view class="record-tag" v-if="record.activityType">
            <text class="tag-icon">🏃</text>
            <text>{{ record.activityType }}</text>
          </view>
        </view>
        
        <view class="record-note" v-if="record.overallNote">
          {{ record.overallNote }}
        </view>
        
        <view class="record-footer">
          <text class="teacher">{{ record.teacherName || '老师' }}</text>
          <text class="time">{{ record.publishTime }}</text>
        </view>
      </view>
      
      <view class="empty" v-if="records.length === 0">
        <text>暂无记录</text>
      </view>
    </scroll-view>
    
    <!-- 孩子选择器 -->
    <uni-popup ref="childPicker" type="bottom">
      <view class="picker-content">
        <view class="picker-header">
          <text>选择孩子</text>
          <text @click="showChildPicker = false">确定</text>
        </view>
        <view class="picker-list">
          <view 
            class="picker-item" 
            :class="{ active: selectedChildId === child.studentId }"
            v-for="child in children" 
            :key="child.studentId"
            @click="selectChild(child)"
          >
            {{ child.studentName }}
          </view>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script setup>
import { ref } from 'vue';
import { recordApi, studentApi } from '../../api/index';

const records = ref([]);
const children = ref([]);
const selectedChildId = ref('');
const selectedChildName = ref('');
const selectedDate = ref('');
const showChildPicker = ref(false);
const page = ref(1);
const hasMore = ref(true);

const loadChildren = async () => {
  try {
    const res = await studentApi.list({ page: 1, pageSize: 10 });
    children.value = res.records || [];
    if (children.value.length > 0) {
      selectedChildId.value = children.value[0].studentId;
      selectedChildName.value = children.value[0].studentName;
      loadRecords();
    }
  } catch (e) {
    console.log(e);
  }
};

const loadRecords = async () => {
  if (!selectedChildId.value) return;
  
  try {
    const res = await recordApi.list({
      studentId: selectedChildId.value,
      startDate: selectedDate.value,
      endDate: selectedDate.value,
      page: page.value,
      pageSize: 10
    });
    
    if (page.value === 1) {
      records.value = res.records || [];
    } else {
      records.value = [...records.value, ...(res.records || [])];
    }
    hasMore.value = res.records && res.records.length === 10;
  } catch (e) {
    console.log(e);
  }
};

const loadMore = () => {
  if (hasMore.value) {
    page.value++;
    loadRecords();
  }
};

const selectChild = (child) => {
  selectedChildId.value = child.studentId;
  selectedChildName.value = child.studentName;
  showChildPicker.value = false;
  page.value = 1;
  loadRecords();
};

const onDateChange = (e) => {
  selectedDate.value = e.detail.value;
  page.value = 1;
  loadRecords();
};

const goToDetail = (recordId) => {
  uni.navigateTo({ url: `/pages/record/detail?recordId=${recordId}` });
};

loadChildren();
</script>

<style lang="scss" scoped>
.record-list-container {
  min-height: 100vh;
  background: #f5f5f5;
}

.filter-bar {
  display: flex;
  padding: 20rpx 30rpx;
  background: #fff;
  
  .date-picker, .child-picker {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 70rpx;
    background: #f5f5f5;
    border-radius: 10rpx;
    margin: 0 10rpx;
    
    .arrow {
      margin-left: 10rpx;
      font-size: 20rpx;
      color: #999;
    }
  }
}

.record-list {
  height: calc(100vh - 130rpx);
  padding: 20rpx;
  
  .record-item {
    background: #fff;
    border-radius: 16rpx;
    padding: 30rpx;
    margin-bottom: 20rpx;
    
    .record-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 20rpx;
      
      .record-date {
        font-size: 28rpx;
        font-weight: bold;
        color: #333;
      }
      
      .record-status {
        font-size: 24rpx;
        padding: 4rpx 16rpx;
        border-radius: 20rpx;
        
        &.published {
          background: #e8faf0;
          color: #07c160;
        }
        
        &.draft {
          background: #fef6e8;
          color: #fa8c16;
        }
      }
    }
    
    .record-content {
      display: flex;
      flex-wrap: wrap;
      gap: 20rpx;
      margin-bottom: 20rpx;
      
      .record-tag {
        display: flex;
        align-items: center;
        background: #f5f5f5;
        padding: 10rpx 20rpx;
        border-radius: 30rpx;
        
        .tag-icon {
          margin-right: 8rpx;
        }
        
        text {
          font-size: 24rpx;
          color: #666;
        }
      }
    }
    
    .record-note {
      font-size: 26rpx;
      color: #333;
      line-height: 1.6;
      margin-bottom: 20rpx;
    }
    
    .record-footer {
      display: flex;
      justify-content: space-between;
      font-size: 24rpx;
      color: #999;
    }
  }
  
  .empty {
    text-align: center;
    padding: 100rpx;
    color: #999;
  }
}

.picker-content {
  background: #fff;
  border-radius: 30rpx 30rpx 0 0;
  padding: 30rpx;
  
  .picker-header {
    display: flex;
    justify-content: space-between;
    padding-bottom: 20rpx;
    border-bottom: 1rpx solid #f0f0f0;
    margin-bottom: 20rpx;
  }
  
  .picker-item {
    padding: 30rpx;
    text-align: center;
    border-radius: 10rpx;
    
    &.active {
      background: #f5f5f5;
      color: #667eea;
    }
  }
}
</style>
