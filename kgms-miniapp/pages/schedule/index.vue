<template>
  <view class="page">
    <view class="header">
      <text class="title">课程表</text>
    </view>

    <!-- 周选择 -->
    <view class="week-selector">
      <view class="week-days">
        <view
          v-for="(day, index) in weekDays"
          :key="index"
          class="day-item"
          :class="{ active: currentDay === index }"
          @click="currentDay = index"
        >
          <text class="day-name">{{ day.name }}</text>
          <text class="day-date">{{ day.date }}</text>
        </view>
      </view>
    </view>

    <!-- 课程列表 -->
    <scroll-view scroll-y class="course-list">
      <view v-if="courses.length === 0" class="empty">
        <text>今日无课程安排</text>
      </view>

      <view v-for="course in courses" :key="course.scheduleId" class="course-item">
        <view class="course-time">
          <text class="time">{{ course.startTime }} - {{ course.endTime }}</text>
        </view>
        <view class="course-info">
          <text class="course-name">{{ course.courseName }}</text>
          <text class="course-teacher">{{ course.teacherName }}</text>
          <text class="course-location">{{ course.location }}</text>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { courseApi } from '@/api'

const currentDay = ref(new Date().getDay())
const courses = ref([])

const weekDays = ref([])
const initWeekDays = () => {
  const now = new Date()
  const week = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
  const days = []
  for (let i = 0; i < 7; i++) {
    const date = new Date(now)
    date.setDate(now.getDate() - currentDay.value + i)
    days.push({
      name: week[i],
      date: `${date.getMonth() + 1}-${date.getDate()}`
    })
  }
  weekDays.value = days
}

const loadCourses = async () => {
  // TODO: 实际调用API
  courses.value = [
    { scheduleId: '1', startTime: '08:30', endTime: '09:00', courseName: '早读', teacherName: '张老师', location: '教室1' },
    { scheduleId: '2', startTime: '09:00', endTime: '09:30', courseName: '创意美术', teacherName: '李老师', location: '美术室' },
    { scheduleId: '3', startTime: '10:00', endTime: '10:30', courseName: '户外活动', teacherName: '张老师', location: '操场' }
  ]
}

initWeekDays()
loadCourses()
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

.week-selector {
  background: #fff;
  padding: 20rpx 0;
  margin-bottom: 20rpx;
}

.week-days {
  display: flex;
  justify-content: space-around;
}

.day-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10rpx 20rpx;
  border-radius: 16rpx;
}

.day-item.active {
  background: #67C23A;
}

.day-name {
  font-size: 26rpx;
  color: #333;
  font-weight: bold;
}

.day-item.active .day-name {
  color: #fff;
}

.day-date {
  font-size: 22rpx;
  color: #999;
  margin-top: 4rpx;
}

.day-item.active .day-date {
  color: rgba(255, 255, 255, 0.8);
}

.course-list {
  height: calc(100vh - 250rpx);
  padding: 20rpx;
}

.course-item {
  display: flex;
  background: #fff;
  border-radius: 16rpx;
  margin-bottom: 20rpx;
  overflow: hidden;
}

.course-time {
  width: 180rpx;
  background: #67C23A;
  padding: 30rpx 20rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.time {
  font-size: 24rpx;
  color: #fff;
  text-align: center;
}

.course-info {
  flex: 1;
  padding: 30rpx;
}

.course-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 10rpx;
}

.course-teacher, .course-location {
  font-size: 24rpx;
  color: #999;
  margin-right: 20rpx;
}

.empty {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>