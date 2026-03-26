<template>
  <div class="page-container">
    <div class="page-header">
      <h2>班级管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新增班级
      </el-button>
    </div>

    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="classId" label="班级ID" width="120" />
        <el-table-column prop="className" label="班级名称" width="120" />
        <el-table-column prop="grade" label="年级" width="100" />
        <el-table-column prop="capacity" label="容量" width="80" />
        <el-table-column prop="studentCount" label="当前人数" width="80" />
        <el-table-column prop="headTeacherId" label="班主任" width="100" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '在用' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">查看</el-button>
            <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="班级名称" prop="className">
          <el-input v-model="form.className" placeholder="如：星星班" />
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-select v-model="form.grade" placeholder="请选择年级">
            <el-option label="托班" value="托班" />
            <el-option label="小班" value="小班" />
            <el-option label="中班" value="中班" />
            <el-option label="大班" value="大班" />
          </el-select>
        </el-form-item>
        <el-form-item label="容量" prop="capacity">
          <el-input-number v-model="form.capacity" :min="1" :max="50" />
        </el-form-item>
        <el-form-item label="班主任">
          <el-input v-model="form.headTeacherId" placeholder="请输入班主任ID" />
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const loading = ref(false)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const dialogTitle = ref('新增班级')
const formRef = ref()
const tableData = ref([])

const pagination = reactive({ page: 1, pageSize: 10, total: 0 })

const form = reactive({
  classId: '',
  className: '',
  grade: '',
  capacity: 30,
  headTeacherId: ''
})

const rules = {
  className: [{ required: true, message: '请输入班级名称', trigger: 'blur' }],
  grade: [{ required: true, message: '请选择年级', trigger: 'change' }],
  capacity: [{ required: true, message: '请输入容量', trigger: 'blur' }]
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
  dialogTitle.value = '新增班级'
  form.classId = ''
  form.className = ''
  form.grade = ''
  form.capacity = 30
  form.headTeacherId = ''
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑班级'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleView = (row) => {
  handleEdit(row)
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该班级吗？', '提示', { type: 'warning' })
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('删除失败')
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    submitLoading.value = true
    ElMessage.success(form.classId ? '更新成功' : '新增成功')
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
