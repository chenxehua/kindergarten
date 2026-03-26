<template>
  <div class="page-container">
    <div class="page-header">
      <h2>成长记录</h2>
      <el-select v-model="selectedClassId" placeholder="选择班级" @change="loadData" style="width: 200px; margin-right: 10px;">
        <el-option v-for="item in classList" :key="item.classId" :label="item.className" :value="item.classId" />
      </el-select>
      <el-date-picker v-model="recordDate" type="date" placeholder="选择日期" @change="loadData" value-format="YYYY-MM-DD" />
    </div>

    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="studentName" label="学生姓名" width="100" />
        <el-table-column prop="recordDate" label="记录日期" width="120" />
        <el-table-column prop="emotionType" label="情绪状态" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.emotionType || '未记录' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="breakfast" label="早餐" width="100" />
        <el-table-column prop="lunch" label="午餐" width="100" />
        <el-table-column prop="dinner" label="晚餐" width="100" />
        <el-table-column prop="activityDetail" label="活动内容" min-width="150" />
        <el-table-column prop="publishStatus" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.publishStatus === 1 ? 'success' : 'warning'">
              {{ row.publishStatus === 1 ? '已发布' : '草稿' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="120">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">查看</el-button>
            <el-button v-if="row.publishStatus !== 1" size="small" type="primary" @click="handlePublish(row)">发布</el-button>
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

    <el-dialog v-model="viewVisible" title="成长记录详情" width="700px">
      <div v-if="currentRecord" class="record-detail">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="学生姓名">{{ currentRecord.studentName }}</el-descriptions-item>
          <el-descriptions-item label="记录日期">{{ currentRecord.recordDate }}</el-descriptions-item>
          <el-descriptions-item label="情绪状态">{{ currentRecord.emotionType || '未记录' }}</el-descriptions-item>
          <el-descriptions-item label="体温">{{ currentRecord.temperature || '正常' }}</el-descriptions-item>
          <el-descriptions-item label="早餐">{{ currentRecord.breakfast || '未记录' }}</el-descriptions-item>
          <el-descriptions-item label="午餐">{{ currentRecord.lunch || '未记录' }}</el-descriptions-item>
          <el-descriptions-item label="晚餐">{{ currentRecord.dinner || '未记录' }}</el-descriptions-item>
          <el-descriptions-item label="点心">{{ currentRecord.snack || '未记录' }}</el-descriptions-item>
          <el-descriptions-item label="睡眠">{{ currentRecord.sleepTime || '未记录' }}</el-descriptions-item>
          <el-descriptions-item label="活动">{{ currentRecord.activityDetail || '未记录' }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const viewVisible = ref(false)
const currentRecord = ref(null)
const tableData = ref([])
const classList = ref([])
const selectedClassId = ref('')
const recordDate = ref('')

const pagination = reactive({ page: 1, pageSize: 10, total: 0 })

const loadData = async () => {
  loading.value = true
  try {
    // TODO: 调用API获取成长记录列表
    tableData.value = []
    pagination.total = 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const loadClassList = async () => {
  try {
    // TODO: 调用API获取班级列表
    classList.value = []
  } catch (e) {
    console.error(e)
  }
}

const handleView = (row) => {
  currentRecord.value = row
  viewVisible.value = true
}

const handlePublish = async (row) => {
  try {
    // TODO: 调用发布API
    ElMessage.success('发布成功')
    loadData()
  } catch (e) {
    ElMessage.error('发布失败')
  }
}

onMounted(() => {
  loadData()
  loadClassList()
})
</script>

<style scoped>
.page-container { padding: 20px; }
.page-header { display: flex; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; margin-right: 20px; }
.pagination { display: flex; justify-content: flex-end; margin-top: 20px; }
</style>
