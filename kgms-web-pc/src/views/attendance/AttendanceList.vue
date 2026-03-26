<template>
  <div class="attendance-page">
    <div class="toolbar">
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        @change="loadData"
      />
      <el-select v-model="classId" placeholder="选择班级" @change="loadData" style="width: 200px">
        <el-option label="全部班级" value="" />
        <el-option v-for="c in classes" :key="c.classId" :label="c.className" :value="c.classId" />
      </el-select>
      <el-button type="primary" @click="exportData">导出</el-button>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-cards">
      <div class="stat-card">
        <div class="stat-value">{{ stats.total }}</div>
        <div class="stat-label">应到人数</div>
      </div>
      <div class="stat-card success">
        <div class="stat-value">{{ stats.normal }}</div>
        <div class="stat-label">正常出勤</div>
      </div>
      <div class="stat-card warning">
        <div class="stat-value">{{ stats.late }}</div>
        <div class="stat-label">迟到</div>
      </div>
      <div class="stat-card danger">
        <div class="stat-value">{{ stats.absent }}</div>
        <div class="stat-label">缺勤</div>
      </div>
      <div class="stat-card info">
        <div class="stat-value">{{ stats.leave }}</div>
        <div class="stat-label">请假</div>
      </div>
    </div>

    <!-- 考勤表格 -->
    <el-table :data="tableData" border stripe>
      <el-table-column prop="studentName" label="学生姓名" />
      <el-table-column prop="className" label="班级" />
      <el-table-column prop="checkInTime" label="签到时间" />
      <el-table-column prop="checkOutTime" label="签退时间" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">
            {{ getStatusText(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="leaveType" label="请假类型" />
      <el-table-column prop="pickupPerson" label="接送人" />
    </el-table>

    <el-pagination
      v-model:current-page="page"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next"
      @current-change="loadData"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const dateRange = ref([])
const classId = ref('')
const classes = ref([])
const tableData = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const stats = ref({
  total: 30,
  normal: 25,
  late: 2,
  absent: 1,
  leave: 2
})

const getStatusType = (status) => {
  const types = {
    NORMAL: 'success',
    LATE: 'warning',
    ABSENT: 'danger',
    LEAVE: 'info'
  }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = {
    NORMAL: '正常',
    LATE: '迟到',
    ABSENT: '缺勤',
    LEAVE: '请假'
  }
  return texts[status] || '未知'
}

const loadData = () => {
  // 模拟数据
  tableData.value = [
    { studentName: '王小明', className: '星星班', checkInTime: '08:30', checkOutTime: '16:30', status: 'NORMAL', pickupPerson: '王爸爸' },
    { studentName: '李小红', className: '星星班', checkInTime: '08:45', checkOutTime: '16:30', status: 'LATE', pickupPerson: '李妈妈' },
    { studentName: '张小华', className: '星星班', checkInTime: '-', checkOutTime: '-', status: 'LEAVE', leaveType: '病假' }
  ]
  total.value = 3
}

const exportData = () => {
  ElMessage.success('导出成功')
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stats-cards {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  flex: 1;
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.stat-card.success { border-left: 4px solid #67C23A; }
.stat-card.warning { border-left: 4px solid #E6A23C; }
.stat-card.danger { border-left: 4px solid #F56C6C; }
.stat-card.info { border-left: 4px solid #909399; }

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 8px;
}
</style>