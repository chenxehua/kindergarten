<template>
  <div class="page-container">
    <div class="page-header">
      <h2>通知公告</h2>
      <el-button type="primary" @click="handlePublish">
        <el-icon><Plus /></el-icon>发布通知
      </el-button>
    </div>

    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="noticeId" label="通知ID" width="120" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="noticeType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getTypeTag(row.noticeType)">
              {{ getTypeText(row.noticeType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishBy" label="发布人" width="100" />
        <el-table-column prop="publishTime" label="发布时间" width="180" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '已发布' : '已撤回' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">查看</el-button>
            <el-button v-if="row.status === 1" size="small" type="warning" @click="handleWithdraw(row)">撤回</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="通知标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="通知类型" prop="noticeType">
          <el-select v-model="form.noticeType" placeholder="请选择类型">
            <el-option label="系统通知" value="1" />
            <el-option label="活动通知" value="2" />
            <el-option label="安全通知" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="通知内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="6" placeholder="请输入通知内容" />
        </el-form-item>
        <el-form-item label="发布范围">
          <el-radio-group v-model="form.targetType">
            <el-radio value="all">全园</el-radio>
            <el-radio value="class">指定班级</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">发布</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="viewVisible" title="通知详情" width="600px">
      <div v-if="currentNotice" class="notice-detail">
        <h3>{{ currentNotice.title }}</h3>
        <div class="notice-meta">
          <span>类型：{{ getTypeText(currentNotice.noticeType) }}</span>
          <span>发布人：{{ currentNotice.publishBy }}</span>
          <span>时间：{{ currentNotice.publishTime }}</span>
        </div>
        <div class="notice-content">{{ currentNotice.content }}</div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const loading = ref(false)
const dialogVisible = ref(false)
const viewVisible = ref(false)
const submitLoading = ref(false)
const currentNotice = ref(null)
const formRef = ref()
const tableData = ref([])

const pagination = reactive({ page: 1, pageSize: 10, total: 0 })

const form = reactive({
  title: '',
  noticeType: '1',
  content: '',
  targetType: 'all',
  targetIds: ''
})

const rules = {
  title: [{ required: true, message: '请输入通知标题', trigger: 'blur' }],
  noticeType: [{ required: true, message: '请选择通知类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入通知内容', trigger: 'blur' }]
}

const getTypeTag = (type) => {
  const map = { '1': '', '2': 'success', '3': 'warning' }
  return map[type] || ''
}

const getTypeText = (type) => {
  const map = { '1': '系统通知', '2': '活动通知', '3': '安全通知' }
  return map[type] || type
}

const loadData = async () => {
  loading.value = true
  try {
    // TODO: 调用API获取通知列表
    tableData.value = []
    pagination.total = 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handlePublish = () => {
  form.title = ''
  form.noticeType = '1'
  form.content = ''
  form.targetType = 'all'
  dialogVisible.value = true
}

const handleView = (row) => {
  currentNotice.value = row
  viewVisible.value = true
}

const handleWithdraw = async (row) => {
  try {
    await ElMessageBox.confirm('确定要撤回该通知吗？', '提示', { type: 'warning' })
    // TODO: 调用撤回API
    ElMessage.success('撤回成功')
    loadData()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('撤回失败')
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    submitLoading.value = true
    // TODO: 调用发布API
    ElMessage.success('发布成功')
    dialogVisible.value = false
    loadData()
  } catch (e) {
    console.error(e)
  } finally {
    submitLoading.value = false
  }
}

onMounted(() => loadData())
</script>

<style scoped>
.page-container { padding: 20px; }
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; }
.pagination { display: flex; justify-content: flex-end; margin-top: 20px; }
.notice-detail h3 { margin-top: 0; }
.notice-meta { color: #999; font-size: 14px; margin-bottom: 20px; }
.notice-meta span { margin-right: 20px; }
.notice-content { line-height: 2; }
</style>
