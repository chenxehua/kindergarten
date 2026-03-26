<template>
  <div class="page-container">
    <div class="page-header">
      <h2>学生管理</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>新增学生
        </el-button>
        <el-button @click="handleImport">
          <el-icon><Upload /></el-icon>批量导入
        </el-button>
      </div>
    </div>

    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="班级">
          <el-select v-model="searchForm.classId" placeholder="请选择班级" clearable>
            <el-option v-for="item in classList" :key="item.classId" :label="item.className" :value="item.classId" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="searchForm.keyword" placeholder="请输入学生姓名" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="在园" :value="1" />
            <el-option label="离园" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="studentId" label="学生ID" width="120" />
        <el-table-column prop="studentName" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="className" label="班级" width="120" />
        <el-table-column prop="enrollDate" label="入园日期" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '在园' : '离园' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="emergencyContact" label="紧急联系人" width="100" />
        <el-table-column prop="emergencyPhone" label="联系电话" width="120" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">查看</el-button>
            <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker v-model="form.birthday" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-select v-model="form.classId" placeholder="请选择班级">
            <el-option v-for="item in classList" :key="item.classId" :label="item.className" :value="item.classId" />
          </el-select>
        </el-form-item>
        <el-form-item label="入园日期" prop="enrollDate">
          <el-date-picker v-model="form.enrollDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="过敏信息">
          <el-input v-model="form.allergyInfo" type="textarea" placeholder="请输入过敏信息" />
        </el-form-item>
        <el-form-item label="既往病史">
          <el-input v-model="form.medicalHistory" type="textarea" placeholder="请输入既往病史" />
        </el-form-item>
        <el-form-item label="家庭住址">
          <el-input v-model="form.homeAddress" placeholder="请输入家庭住址" />
        </el-form-item>
        <el-form-item label="紧急联系人" prop="emergencyContact">
          <el-input v-model="form.emergencyContact" placeholder="请输入紧急联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="emergencyPhone">
          <el-input v-model="form.emergencyPhone" placeholder="请输入联系电话" />
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
import { Plus, Upload } from '@element-plus/icons-vue'
import { studentApi } from '@/api'

const loading = ref(false)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const dialogTitle = ref('新增学生')
const formRef = ref()
const tableData = ref([])
const classList = ref([])

const searchForm = reactive({
  classId: '',
  keyword: '',
  status: 1
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const form = reactive({
  studentId: '',
  studentName: '',
  gender: 1,
  birthday: '',
  classId: '',
  enrollDate: '',
  idCard: '',
  allergyInfo: '',
  medicalHistory: '',
  homeAddress: '',
  emergencyContact: '',
  emergencyPhone: ''
})

const rules = {
  studentName: [{ required: true, message: '请输入学生姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  birthday: [{ required: true, message: '请选择出生日期', trigger: 'change' }],
  classId: [{ required: true, message: '请选择班级', trigger: 'change' }],
  enrollDate: [{ required: true, message: '请选择入园日期', trigger: 'change' }],
  emergencyContact: [{ required: true, message: '请输入紧急联系人', trigger: 'blur' }],
  emergencyPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await studentApi.getList({
      ...searchForm,
      page: pagination.page,
      pageSize: pagination.pageSize
    })
    tableData.value = res.data.records || []
    pagination.total = res.data.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const loadClassList = async () => {
  try {
    const res = await classApi.getList()
    classList.value = res.data.records || []
  } catch (e) {
    console.error(e)
  }
}

const handleSearch = () => {
  pagination.page = 1
  loadData()
}

const handleReset = () => {
  searchForm.classId = ''
  searchForm.keyword = ''
  searchForm.status = 1
  handleSearch()
}

const handleAdd = () => {
  dialogTitle.value = '新增学生'
  Object.keys(form).forEach(key => {
    form[key] = key === 'gender' ? 1 : ''
  })
  form.studentId = ''
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑学生'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleView = (row) => {
  handleEdit(row)
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该学生吗？', '提示', { type: 'warning' })
    await studentApi.delete(row.studentId)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {
    if (e !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    submitLoading.value = true
    if (form.studentId) {
      await studentApi.update(form.studentId, form)
      ElMessage.success('更新成功')
    } else {
      await studentApi.add(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (e) {
    console.error(e)
  } finally {
    submitLoading.value = false
  }
}

const handleImport = () => {
  ElMessage.info('批量导入功能开发中')
}

const handleSizeChange = (size) => {
  pagination.pageSize = size
  loadData()
}

const handlePageChange = (page) => {
  pagination.page = page
  loadData()
}

onMounted(() => {
  loadData()
  loadClassList()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
}
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.page-header h2 {
  margin: 0;
}
.header-actions {
  display: flex;
  gap: 10px;
}
.search-card {
  margin-bottom: 20px;
}
.table-card {
  margin-bottom: 20px;
}
.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
