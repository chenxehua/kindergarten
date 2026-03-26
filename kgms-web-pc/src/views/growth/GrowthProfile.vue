<template>
  <div class="page-container">
    <div class="page-header">
      <h2>成长画像</h2>
      <div class="header-actions">
        <el-select v-model="selectedStudentId" placeholder="选择学生" @change="loadData" style="width: 200px; margin-right: 10px;">
          <el-option v-for="item in studentList" :key="item.studentId" :label="item.studentName" :value="item.studentId" />
        </el-select>
        <el-date-picker v-model="month" type="month" placeholder="选择月份" @change="loadData" value-format="YYYY-MM" />
      </div>
    </div>

    <div v-if="profileData" class="profile-content">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-card class="score-card">
            <template #header>
              <div class="card-header">
                <span>综合评分</span>
              </div>
            </template>
            <div class="score-circle">
              <el-progress type="circle" :percentage="profileData.overallScore || 0" :color="getScoreColor(profileData.overallScore)" />
            </div>
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-card class="dimension-card">
            <template #header>
              <div class="card-header">
                <span>各维度评分</span>
              </div>
            </template>
            <div class="dimension-list">
              <div class="dimension-item">
                <span class="label">情绪评分</span>
                <el-progress :percentage="profileData.emotionScore || 0" :stroke-width="12" />
              </div>
              <div class="dimension-item">
                <span class="label">活动评分</span>
                <el-progress :percentage="profileData.activityScore || 0" :stroke-width="12" />
              </div>
              <div class="dimension-item">
                <span class="label">饮食评分</span>
                <el-progress :percentage="profileData.dietScore || 0" :stroke-width="12" />
              </div>
              <div class="dimension-item">
                <span class="label">学习评分</span>
                <el-progress :percentage="profileData.learningScore || 0" :stroke-width="12" />
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="20" style="margin-top: 20px;">
        <el-col :span="12">
          <el-card>
            <template #header>
              <div class="card-header">
                <span>情绪分析</span>
              </div>
            </template>
            <div class="analysis-content">
              {{ profileData.emotionAnalysis || '暂无数据' }}
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <template #header>
              <div class="card-header">
                <span>活动分析</span>
              </div>
            </template>
            <div class="analysis-content">
              {{ profileData.activityAnalysis || '暂无数据' }}
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-card style="margin-top: 20px;">
        <template #header>
          <div class="card-header">
            <span>建议</span>
          </div>
        </template>
        <div class="suggestions">
          <el-tag v-for="(item, index) in profileData.suggestions" :key="index" type="primary" style="margin-right: 10px; margin-bottom: 10px;">
            {{ item }}
          </el-tag>
          <span v-if="!profileData.suggestions || profileData.suggestions.length === 0">暂无建议</span>
        </div>
      </el-card>

      <el-card style="margin-top: 20px;">
        <template #header>
          <div class="card-header">
            <span>生成成长报告</span>
          </div>
        </template>
        <el-button type="primary" @click="handleGenerateReport">生成月度成长报告</el-button>
      </el-card>
    </div>

    <el-empty v-else description="请选择学生和月份查看成长画像" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const studentList = ref([])
const selectedStudentId = ref('')
const month = ref('')
const profileData = ref(null)

const getScoreColor = (score) => {
  if (score >= 90) return '#67c23a'
  if (score >= 70) return '#409eff'
  if (score >= 60) return '#e6a23c'
  return '#f56c6c'
}

const loadData = async () => {
  if (!selectedStudentId.value || !month.value) {
    profileData.value = null
    return
  }
  loading.value = true
  try {
    // TODO: 调用API获取成长画像
    profileData.value = null
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const loadStudentList = async () => {
  try {
    // TODO: 调用API获取学生列表
    studentList.value = []
  } catch (e) {
    console.error(e)
  }
}

const handleGenerateReport = async () => {
  if (!selectedStudentId.value || !month.value) {
    ElMessage.warning('请选择学生和月份')
    return
  }
  try {
    // TODO: 调用生成报告API
    ElMessage.success('报告生成中，请稍后查看')
  } catch (e) {
    ElMessage.error('生成失败')
  }
}

onMounted(() => {
  loadStudentList()
  // 默认选择当前月份
  const now = new Date()
  month.value = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`
})
</script>

<style scoped>
.page-container { padding: 20px; }
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; }
.header-actions { display: flex; }
.score-card { text-align: center; }
.score-circle { display: flex; justify-content: center; padding: 20px; }
.dimension-list { padding: 10px; }
.dimension-item { margin-bottom: 20px; }
.dimension-item .label { display: block; margin-bottom: 8px; font-weight: 500; }
.analysis-content { line-height: 2; color: #666; }
.suggestions { line-height: 2; }
.card-header { font-weight: bold; }
</style>
