<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #409eff">
            <el-icon><User /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.studentCount }}</div>
            <div class="stat-label">学生总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #67c23a">
            <el-icon><School /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.classCount }}</div>
            <div class="stat-label">班级数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #e6a23c">
            <el-icon><Document /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.todayRecordCount }}</div>
            <div class="stat-label">今日记录</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: #f56c6c">
            <el-icon><Bell /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.noticeCount }}</div>
            <div class="stat-label">未读通知</div>
          </div>
        </div>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>最近动态</span>
          </template>
          <div class="activity-list">
            <div class="activity-item" v-for="item in activities" :key="item.id">
              <div class="activity-icon" :style="{ background: item.color }">
                <el-icon><component :is="item.icon" /></el-icon>
              </div>
              <div class="activity-content">
                <div class="activity-title">{{ item.title }}</div>
                <div class="activity-time">{{ item.time }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>快捷操作</span>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="$router.push('/student')">学生管理</el-button>
            <el-button type="success" @click="$router.push('/record')">成长记录</el-button>
            <el-button type="warning" @click="$router.push('/notice')">发布通知</el-button>
            <el-button type="info" @click="$router.push('/food')">食谱管理</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const stats = ref({
  studentCount: 128,
  classCount: 8,
  todayRecordCount: 45,
  noticeCount: 3
})

const activities = ref([
  { id: 1, title: '张老师发布了今日成长记录', time: '10分钟前', icon: 'Document', color: '#409eff' },
  { id: 2, title: '新增学生: 王小明的家长', time: '30分钟前', icon: 'User', color: '#67c23a' },
  { id: 3, title: '李妈妈查看了成长报告', time: '1小时前', icon: 'View', color: '#e6a23c' },
  { id: 4, title: '发布清明节放假通知', time: '2小时前', icon: 'Bell', color: '#f56c6c' }
])

onMounted(() => {
  // 加载统计数据
})
</script>

<style lang="scss" scoped>
.dashboard {
  .stat-card {
    background: #fff;
    border-radius: 8px;
    padding: 20px;
    display: flex;
    align-items: center;
    box-shadow: 0 2px 12px rgba(0,0,0,0.1);
    
    .stat-icon {
      width: 60px;
      height: 60px;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 28px;
      color: #fff;
      margin-right: 20px;
    }
    
    .stat-content {
      .stat-value {
        font-size: 28px;
        font-weight: bold;
        color: #333;
      }
      
      .stat-label {
        font-size: 14px;
        color: #999;
        margin-top: 5px;
      }
    }
  }
  
  .activity-list {
    .activity-item {
      display: flex;
      align-items: center;
      padding: 15px 0;
      border-bottom: 1px solid #f0f0f0;
      
      &:last-child {
        border-bottom: none;
      }
      
      .activity-icon {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #fff;
        margin-right: 15px;
      }
      
      .activity-content {
        .activity-title {
          font-size: 14px;
          color: #333;
        }
        
        .activity-time {
          font-size: 12px;
          color: #999;
          margin-top: 5px;
        }
      }
    }
  }
  
  .quick-actions {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    
    .el-button {
      width: 120px;
    }
  }
}
</style>
