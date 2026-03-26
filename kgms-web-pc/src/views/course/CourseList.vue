<template>
  <div class="page-container">
    <div class="page-header">
      <h2>课程管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新增课程
      </el-button>
    </div>

    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="courseId" label="课程ID" width="120" />
        <el-table-column prop="courseName" label="课程名称" width="150" />
        <el-table-column prop="courseType" label="课程类型" width="100">
          <template #default="{ row }">
            {{ getTypeText(row.courseType) }}
          </template>
        </el-table-column>
        <el-table-column prop="ageGroup" label="适龄年龄段" width="120" />
        <el-table-column prop="description" label="课程描述" min-width="200" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '启用' : '停用' }}
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-select v-model="form.courseType" placeholder="请选择类型">
            <el-option label="艺术" value="art" />
            <el-option label="科学" value="science" />
            <el-option label="语言" value="language" />
            <el-option label="运动" value="sport" />
            <el-option label="社会" value="social" />
            <el-option label="健康" value="health" />
          </el-select>
        </el-form-item>
        <el-form-item label="适龄年龄段">
          <el-input v-model="form.ageGroup" placeholder="如：3-4岁" />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请输入课程描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确定</el-button>
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
const dialogTitle = ref('新增课程')
const formRef = ref()
const tableData = ref([])

const pagination = reactive({ page: 1, pageSize: 10, total: 0 })

const form = reactive({
  courseId: '',
  courseName: '',
  courseType: '',
  ageGroup: '',
  description: ''
})

const rules = {
  courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  courseType: [{ required: true, message: '请选择课程类型', trigger: 'change' }]
}

const getTypeText = (type) => {
  const map = { art: '艺术', science: '科学', language: '语言', sport: '运动', social: '社会', health: '健康' }
  return map[type] || type
}

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
  dialogTitle.value = '新增课程'
  Object.keys(form).forEach(key => form[key] = '')
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑课程'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleView = (row) => handleEdit(row)

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    submitLoading.value = true
    ElMessage.success(form.courseId ? '更新成功' : '新增成功')
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
</style>
