<template>
  <div class="parent-list">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">新增家长</el-button>
      <el-input v-model="searchKeyword" placeholder="搜索家长姓名/手机号" style="width: 300px" @change="loadData">
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="userId" label="ID" width="80" />
      <el-table-column prop="parentName" label="姓名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="relation" label="与孩子关系" />
      <el-table-column prop="studentName" label="关联孩子" />
      <el-table-column prop="wechatBind" label="微信绑定">
        <template #default="{ row }">
          <el-tag :type="row.wechatBind ? 'success' : 'info'">
            {{ row.wechatBind ? '已绑定' : '未绑定' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '正常' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="primary" link @click="handleLinkStudent(row)">关联孩子</el-button>
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
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')

const loadData = async () => {
  // 模拟数据
  tableData.value = [
    { userId: '1', parentName: '王爸爸', phone: '13800138000', relation: '爸爸', studentName: '王小明', wechatBind: true, status: 1 },
    { userId: '2', parentName: '王妈妈', phone: '13800138001', relation: '妈妈', studentName: '王小明', wechatBind: true, status: 1 }
  ]
  total.value = 2
}

const handleAdd = () => {
  ElMessage.info('新增家长')
}

const handleEdit = (row) => {
  ElMessage.info('编辑家长: ' + row.parentName)
}

const handleLinkStudent = (row) => {
  ElMessage.info('关联孩子: ' + row.parentName)
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