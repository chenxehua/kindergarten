<template>
  <div class="page-container">
    <div class="page-header">
      <h2>视频管理</h2>
    </div>

    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="videoId" label="视频ID" width="120" />
        <el-table-column prop="studentName" label="学生姓名" width="100" />
        <el-table-column prop="title" label="视频标题" min-width="200" />
        <el-table-column prop="month" label="所属月份" width="100" />
        <el-table-column prop="coverUrl" label="封面" width="100">
          <template #default="{ row }">
            <el-image v-if="row.coverUrl" :src="row.coverUrl" style="width: 60px; height: 40px;" fit="cover" />
            <span v-else class="empty">无</span>
          </template>
        </el-table-column>
        <el-table-column prop="duration" label="时长" width="80">
          <template #default="{ row }">
            {{ formatDuration(row.duration) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTag(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handlePlay(row)">播放</el-button>
            <el-button size="small" @click="handleDownload(row)">下载</el-button>
            <el-button v-if="row.status === 2" size="small" type="success" @click="handleAudit(row, 1)">通过</el-button>
            <el-button v-if="row.status === 2" size="small" type="danger" @click="handleAudit(row, 2)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :total="pagination.total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next"
          @size-change="loadData"
          @current-change="loadData"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])

const pagination = reactive({ page: 1, pageSize: 10, total: 0 })

const getStatusTag = (status) => {
  const map = { 0: 'warning', 1: 'success', 2: 'primary', 3: 'success' }
  return map[status] || ''
}

const getStatusText = (status) => {
  const map = { 0: '生成中', 1: '已完成', 2: '待审核', 3: '已发布' }
  return map[status] || status
}

const formatDuration = (seconds) => {
  if (!seconds) return '0:00'
  const m = Math.floor(seconds / 60)
  const s = seconds % 60
  return `${m}:${s.toString().padStart(2, '0')}`
}

const loadData = async () => {
  loading.value = true
  try {
    // TODO: 调用API获取视频列表
    tableData.value = []
    pagination.total = 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handlePlay = (row) => {
  // TODO: 调用播放API
  ElMessage.info('播放功能开发中')
}

const handleDownload = (row) => {
  // TODO: 调用下载API
  ElMessage.info('下载功能开发中')
}

const handleAudit = async (row, status) => {
  try {
    if (status === 2) {
      await ElMessageBox.prompt('请输入拒绝原因', '审核拒绝', { type: 'warning' })
    }
    // TODO: 调用审核API
    ElMessage.success(status === 1 ? '审核通过' : '已拒绝')
    loadData()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('操作失败')
  }
}

onMounted(() => loadData())
</script>

<style scoped>
.page-container { padding: 20px; }
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; }
.pagination { display: flex; justify-content: flex-end; margin-top: 20px; }
.empty { color: #999; }
</style>
