<template>
  <div class="activity-page">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">创建活动</el-button>
      <el-select v-model="status" placeholder="活动状态" @change="loadData" style="width: 150px">
        <el-option label="全部" value="" />
        <el-option label="未开始" value="PENDING" />
        <el-option label="进行中" value="ONGOING" />
        <el-option label="已结束" value="FINISHED" />
      </el-select>
    </div>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="activityId" label="ID" width="80" />
      <el-table-column prop="activityName" label="活动名称" />
      <el-table-column prop="activityType" label="活动类型" />
      <el-table-column prop="startTime" label="开始时间" />
      <el-table-column prop="endTime" label="结束时间" />
      <el-table-column prop="targetClass" label="目标班级" />
      <el-table-column prop="signupCount" label="报名人数" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">
            {{ getStatusText(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleDetail(row)">详情</el-button>
          <el-button type="primary" link @click="handleSignup(row)">报名管理</el-button>
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
        </template>
      </el-table-column>
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

const tableData = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const status = ref('')

const getStatusType = (status) => {
  const types = { PENDING: 'info', ONGOING: 'success', FINISHED: 'danger' }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = { PENDING: '未开始', ONGOING: '进行中', FINISHED: '已结束' }
  return texts[status] || '未知'
}

const loadData = () => {
  tableData.value = [
    { activityId: '1', activityName: '春游活动', activityType: 'OUTDOOR', startTime: '2026-04-01', endTime: '2026-04-01', targetClass: '全园', signupCount: 45, status: 'PENDING' },
    { activityId: '2', activityName: '六一汇演', activityType: 'PERFORMANCE', startTime: '2026-06-01', endTime: '2026-06-01', targetClass: '全园', signupCount: 0, status: 'PENDING' }
  ]
  total.value = 2
}

const handleAdd = () => ElMessage.info('创建活动')
const handleDetail = (row) => ElMessage.info('查看详情: ' + row.activityName)
const handleSignup = (row) => ElMessage.info('报名管理: ' + row.activityName)
const handleEdit = (row) => ElMessage.info('编辑: ' + row.activityName)

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>