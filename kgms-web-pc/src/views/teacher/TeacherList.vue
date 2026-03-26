<template>
  <div class="teacher-list">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">新增老师</el-button>
      <el-input v-model="searchKeyword" placeholder="搜索老师姓名/工号" style="width: 300px" @change="loadData">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="teacherId" label="ID" width="80" />
      <el-table-column prop="teacherName" label="姓名" />
      <el-table-column prop="employeeNo" label="工号" />
      <el-table-column prop="position" label="职位" />
      <el-table-column prop="phone" label="联系电话" />
      <el-table-column prop="className" label="负责班级" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '在职' : '离职' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="primary" link @click="handleDetail(row)">详情</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="page"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next"
      @current-change="loadData"
      @size-change="loadData"
    />

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑老师' : '新增老师'" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form.teacherName" />
        </el-form-item>
        <el-form-item label="工号">
          <el-input v-model="form.employeeNo" />
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="form.position">
            <el-option label="班主任" value="HEAD_TEACHER" />
            <el-option label="副班老师" value="TEACHER" />
            <el-option label="保育员" value="CAREGIVER" />
            <el-option label="保健医" value="DOCTOR" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="负责班级">
          <el-select v-model="form.classId">
            <el-option v-for="c in classes" :key="c.classId" :label="c.className" :value="c.classId" />
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期">
          <el-date-picker v-model="form.hireDate" type="date" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">在职</el-radio>
            <el-radio :label="0">离职</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  teacherId: '',
  teacherName: '',
  employeeNo: '',
  position: 'TEACHER',
  phone: '',
  classId: '',
  hireDate: '',
  status: 1
})
const classes = ref([])

const loadData = async () => {
  // 模拟数据
  tableData.value = [
    { teacherId: '1', teacherName: '张老师', employeeNo: 'T001', position: 'HEAD_TEACHER', phone: '13800138000', className: '星星班', status: 1 },
    { teacherId: '2', teacherName: '李老师', employeeNo: 'T002', position: 'TEACHER', phone: '13800138001', className: '阳光班', status: 1 }
  ]
  total.value = 2
}

const handleAdd = () => {
  isEdit.value = false
  form.value = {
    teacherId: '',
    teacherName: '',
    employeeNo: '',
    position: 'TEACHER',
    phone: '',
    classId: '',
    hireDate: '',
    status: 1
  }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const handleDetail = (row) => {
  ElMessage.info('查看详情: ' + row.teacherName)
}

const handleDelete = (row) => {
  ElMessage.warning('删除老师: ' + row.teacherName)
}

const handleSave = () => {
  ElMessage.success(isEdit.value ? '编辑成功' : '新增成功')
  dialogVisible.value = false
  loadData()
}

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