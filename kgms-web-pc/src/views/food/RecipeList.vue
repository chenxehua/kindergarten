<template>
  <div class="page-container">
    <div class="page-header">
      <h2>食谱管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新增食谱
      </el-button>
    </div>

    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="recipeId" label="食谱ID" width="120" />
        <el-table-column prop="weekStart" label="周开始日期" width="120" />
        <el-table-column label="周一" width="150">
          <template #default="{ row }">
            <span v-if="row.monday">{{ row.monday }}</span>
            <span v-else class="empty">未设置</span>
          </template>
        </el-table-column>
        <el-table-column label="周二" width="150">
          <template #default="{ row }">
            <span v-if="row.tuesday">{{ row.tuesday }}</span>
            <span v-else class="empty">未设置</span>
          </template>
        </el-table-column>
        <el-table-column label="周三" width="150">
          <template #default="{ row }">
            <span v-if="row.wednesday">{{ row.wednesday }}</span>
            <span v-else class="empty">未设置</span>
          </template>
        </el-table-column>
        <el-table-column prop="nutritionist" label="营养师" width="100" />
        <el-table-column prop="publishStatus" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.publishStatus === 1 ? 'success' : 'info'">
              {{ row.publishStatus === 1 ? '已发布' : '草稿' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">查看</el-button>
            <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="周开始日期">
          <el-date-picker v-model="form.weekStart" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="周一">
          <el-input v-model="form.monday" type="textarea" :rows="2" placeholder="早餐|午餐|晚餐" />
        </el-form-item>
        <el-form-item label="周二">
          <el-input v-model="form.tuesday" type="textarea" :rows="2" placeholder="早餐|午餐|晚餐" />
        </el-form-item>
        <el-form-item label="周三">
          <el-input v-model="form.wednesday" type="textarea" :rows="2" placeholder="早餐|午餐|晚餐" />
        </el-form-item>
        <el-form-item label="周四">
          <el-input v-model="form.thursday" type="textarea" :rows="2" placeholder="早餐|午餐|晚餐" />
        </el-form-item>
        <el-form-item label="周五">
          <el-input v-model="form.friday" type="textarea" :rows="2" placeholder="早餐|午餐|晚餐" />
        </el-form-item>
        <el-form-item label="营养师">
          <el-input v-model="form.nutritionist" placeholder="请输入营养师姓名" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button @click="handleSave">保存草稿</el-button>
        <el-button type="primary" @click="handlePublish" :loading="submitLoading">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const loading = ref(false)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const dialogTitle = ref('新增食谱')
const formRef = ref()
const tableData = ref([])

const pagination = reactive({ page: 1, pageSize: 10, total: 0 })

const form = reactive({
  recipeId: '',
  weekStart: '',
  monday: '',
  tuesday: '',
  wednesday: '',
  thursday: '',
  friday: '',
  saturday: '',
  sunday: '',
  nutritionist: ''
})

const loadData = async () => {
  loading.value = true
  try {
    // TODO: 调用API
    tableData.value = []
    pagination.total = 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增食谱'
  Object.keys(form).forEach(key => form[key] = '')
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑食谱'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleView = (row) => {
  handleEdit(row)
}

const handleSave = async () => {
  submitLoading.value = true
  try {
    // TODO: 调用保存API
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadData()
  } catch (e) {
    ElMessage.error('保存失败')
  } finally {
    submitLoading.value = false
  }
}

const handlePublish = async () => {
  submitLoading.value = true
  try {
    // TODO: 调用发布API
    ElMessage.success('发布成功')
    dialogVisible.value = false
    loadData()
  } catch (e) {
    ElMessage.error('发布失败')
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
.empty { color: #999; font-style: italic; }
</style>
