<template>
  <view class="page">
    <view class="header">
      <text class="title">成长画像</text>
    </view>

    <!-- 画像内容 -->
    <scroll-view scroll-y class="profile-content">
      <!-- 选择孩子 -->
      <view class="child-selector" v-if="children.length > 1" @click="showChildPicker = true">
        <text>{{ currentChild.studentName }}</text>
        <text class="arrow">▼</text>
      </view>

      <!-- 月份选择 -->
      <view class="month-selector">
        <picker mode="month" :value="currentMonth" @change="onMonthChange">
          <view class="picker">
            <text>{{ currentMonth }}月</text>
            <text class="arrow">▼</text>
          </view>
        </picker>
      </view>

      <!-- 雷达图 -->
      <view class="radar-section">
        <view class="section-title">发展概况</view>
        <view class="radar-chart">
          <image v-if="profile.radarChart" :src="profile.radarChart" class="radar-img" />
          <view v-else class="radar-placeholder">
            <text>暂无画像数据</text>
          </view>
        </view>
      </view>

      <!-- 维度详情 -->
      <view class="dimensions-section">
        <view class="section-title">各维度发展</view>
        <view class="dimension-item" v-for="dim in dimensions" :key="dim.name">
          <view class="dimension-header">
            <text class="dimension-name">{{ dim.name }}</text>
            <view class="dimension-score">
              <text class="score" :class="'level-' + dim.level">{{ dim.score }}分</text>
              <text class="trend" :class="'trend-' + dim.trend">
                {{ dim.trend === 'up' ? '↑' : dim.trend === 'down' ? '↓' : '→' }}
              </text>
            </view>
          </view>
          <view class="dimension-bar">
            <view class="bar-fill" :style="{ width: dim.score + '%' }" :class="'fill-' + dim.level"></view>
          </view>
          <text class="dimension-desc">{{ dim.description }}</text>
        </view>
      </view>

      <!-- 预警信息 -->
      <view class="warnings-section" v-if="profile.warnings && profile.warnings.length > 0">
        <view class="section-title">发展预警</view>
        <view class="warning-item" v-for="warning in profile.warnings" :key="warning.type">
          <text class="warning-icon">⚠️</text>
          <text class="warning-text">{{ warning.message }}</text>
        </view>
      </view>

      <!-- 建议 -->
      <view class="suggestions-section" v-if="profile.suggestions && profile.suggestions.length > 0">
        <view class="section-title">成长建议</view>
        <view class="suggestion-item" v-for="(suggestion, index) in profile.suggestions" :key="index">
          <text class="suggestion-text">{{ suggestion }}</text>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { growthApi } from '@/api'

const children = ref([])
const currentChild = ref({})
const currentMonth = ref(new Date().getMonth() + 1 + '')
const showChildPicker = ref(false)
const profile = ref({})

const dimensions = ref([
  { name: '情绪', score: 85, level: 'good', trend: 'up', description: '本月情绪稳定，阳光开朗，与同伴相处融洽' },
  { name: '社交', score: 80, level: 'good', trend: 'flat', description: '主动友好，喜欢分享，合作意识强' },
  { name: '学习', score: 88, level: 'excellent', trend: 'up', description: '学习兴趣浓厚，专注力好，参与积极' },
  { name: '运动', score: 82, level: 'good', trend: 'flat', description: '运动能力发展良好，体能表现不错' },
  { name: '饮食', score: 90, level: 'excellent', trend: 'up', description: '食欲良好，营养均衡，不挑食' }
])

const loadChildren = () => {
  // 模拟数据
  children.value = [{ studentId: '1', studentName: '王小明' }]
  currentChild.value = children.value[0]
}

const loadProfile = async () => {
  try {
    const res = await growthApi.profile(currentChild.value.studentId, currentMonth.value)
    profile.value = res || {}
  } catch (e) {
    console.error('加载画像失败', e)
  }
}

const onMonthChange = (e) => {
  currentMonth.value = e.detail.value
  loadProfile()
}

onMounted(() => {
  loadChildren()
  loadProfile()
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

.profile-content {
  height: calc(100vh - 100rpx);
  padding: 20rpx;
}

.child-selector, .month-selector {
  background: #fff;
  padding: 20rpx 30rpx;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.arrow {
  color: #999;
  font-size: 24rpx;
}

.radar-section, .dimensions-section, .warnings-section, .suggestions-section {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.section-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
}

.radar-chart {
  height: 400rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.radar-placeholder {
  color: #999;
  font-size: 28rpx;
}

.radar-img {
  width: 100%;
  height: 100%;
}

.dimension-item {
  margin-bottom: 30rpx;
}

.dimension-item:last-child {
  margin-bottom: 0;
}

.dimension-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10rpx;
}

.dimension-name {
  font-size: 28rpx;
  color: #333;
}

.dimension-score {
  display: flex;
  align-items: center;
  gap: 10rpx;
}

.score {
  font-size: 28rpx;
  font-weight: bold;
}

.level-excellent { color: #67C23A; }
.level-good { color: #1890ff; }
.level-normal { color: #fa8c16; }
.level-warning { color: #ff4d4f; }

.trend {
  font-size: 24rpx;
}

.trend-up { color: #67C23A; }
.trend-down { color: #ff4d4f; }
.trend-flat { color: #999; }

.dimension-bar {
  height: 16rpx;
  background: #f0f0f0;
  border-radius: 8rpx;
  margin-bottom: 10rpx;
  overflow: hidden;
}

.bar-fill {
  height: 100%;
  border-radius: 8rpx;
  transition: width 0.3s;
}

.fill-excellent { background: #67C23A; }
.fill-good { background: #1890ff; }
.fill-normal { background: #fa8c16; }
.fill-warning { background: #ff4d4f; }

.dimension-desc {
  font-size: 24rpx;
  color: #666;
}

.warning-item {
  display: flex;
  align-items: center;
  padding: 20rpx;
  background: #fff1f0;
  border-radius: 12rpx;
  margin-bottom: 16rpx;
}

.warning-icon {
  margin-right: 16rpx;
}

.warning-text {
  font-size: 26rpx;
  color: #ff4d4f;
}

.suggestion-item {
  padding: 20rpx;
  background: #f6ffed;
  border-radius: 12rpx;
  margin-bottom: 16rpx;
}

.suggestion-text {
  font-size: 26rpx;
  color: #52c41a;
}
</style>