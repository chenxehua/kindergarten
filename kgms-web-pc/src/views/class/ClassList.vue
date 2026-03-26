<template>
  <div class="page-container">
    <h1>班级管理</h1>
    <el-card>
      <div class="toolbar">
        <el-button type="primary" @click="handleAdd">新增班级</el-button>
        <el-input v-model="searchKey" placeholder="搜索班级名称" style="width: 200px; margin-left: 10px;"></el-input>
      </div>
      <el-table :data="tableData" border style="margin-top: 20px;">
        <el-table-column prop="classId" label="班级ID"></el-table-column>
        <el-table-column prop="className" label="班级名称"></el-table-column>
        <el-table-column prop="grade" label="年级"></el-table-column>
        <el-table-column prop="capacity" label="容量"></el-table-column>
        <el-table-column prop="studentCount" label="学生数"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
const searchKey = ref('')
const tableData = ref([])
const handleAdd = () => {}
const handleEdit = (row) => {}
const handleDelete = (row) => {}
</script>

<style scoped>
.page-container { padding: 20px; }
.toolbar { display: flex; align-items: center; }
</style>
