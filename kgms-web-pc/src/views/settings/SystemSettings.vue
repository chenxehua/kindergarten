<template>
  <div class="settings-page">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="园所设置" name="school">
        <el-form label-width="120px">
          <el-form-item label="园所名称">
            <el-input v-model="settings.schoolName" />
          </el-form-item>
          <el-form-item label="园所Logo">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :show-file-list="false"
            >
              <img v-if="settings.logo" :src="settings.logo" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="settings.phone" />
          </el-form-item>
          <el-form-item label="园所地址">
            <el-input v-model="settings.address" />
          </el-form-item>
          <el-form-item label="营业时间">
            <el-time-picker v-model="settings.openTime" placeholder="开始时间" />
            <el-time-picker v-model="settings.closeTime" placeholder="结束时间" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveSettings">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="功能开关" name="features">
        <div class="feature-list">
          <div class="feature-item" v-for="f in features" :key="f.key">
            <div class="feature-info">
              <div class="feature-name">{{ f.name }}</div>
              <div class="feature-desc">{{ f.description }}</div>
            </div>
            <el-switch v-model="f.enabled" />
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="推送设置" name="push">
        <el-form label-width="120px">
          <el-form-item label="推送时间">
            <el-time-picker v-model="settings.pushTime" placeholder="选择时间" />
          </el-form-item>
          <el-form-item label="免打扰时段">
            <el-time-picker v-model="settings.quietStart" placeholder="开始" />
            <span>-</span>
            <el-time-picker v-model="settings.quietEnd" placeholder="结束" />
          </el-form-item>
          <el-form-item label="推送类型">
            <el-checkbox-group v-model="settings.pushTypes">
              <el-checkbox label="record">成长记录</el-checkbox>
              <el-checkbox label="notice">通知公告</el-checkbox>
              <el-checkbox label="activity">活动报名</el-checkbox>
              <el-checkbox label="report">月度报告</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveSettings">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="角色权限" name="roles">
        <div class="role-list">
          <div class="role-item" v-for="role in roles" :key="role.roleId">
            <div class="role-info">
              <div class="role-name">{{ role.roleName }}</div>
              <div class="role-desc">{{ role.description }}</div>
            </div>
            <el-button type="primary" link @click="editPermissions(role)">配置权限</el-button>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const activeTab = ref('school')

const settings = ref({
  schoolName: '智慧幼儿园',
  logo: '',
  phone: '010-12345678',
  address: '北京市朝阳区',
  openTime: new Date(2024, 0, 1, 8, 0),
  closeTime: new Date(2024, 0, 1, 17, 0),
  pushTime: new Date(2024, 0, 1, 16, 0),
  quietStart: new Date(2024, 0, 1, 22, 0),
  quietEnd: new Date(2024, 0, 1, 7, 0),
  pushTypes: ['record', 'notice']
})

const features = ref([
  { key: 'aiProfile', name: 'AI成长画像', description: '自动分析学生成长数据，生成成长画像', enabled: true },
  { key: 'aiVideo', name: 'AI成长视频', description: '自动生成月度成长视频', enabled: true },
  { key: 'attendance', name: '考勤打卡', description: '学生入园离园签到', enabled: true },
  { key: 'message', name: '家校沟通', description: '家长与老师即时通讯', enabled: true }
])

const roles = ref([
  { roleId: '1', roleName: '园长', description: '最高管理员权限' },
  { roleId: '2', roleName: '老师', description: '班级管理和记录权限' },
  { roleId: '3', roleName: '家长', description: '查看和互动权限' }
])

const saveSettings = () => {
  ElMessage.success('保存成功')
}

const editPermissions = (role) => {
  ElMessage.info('配置权限: ' + role.roleName)
}
</script>

<style scoped>
.feature-list, .role-list {
  padding: 20px 0;
}

.feature-item, .role-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.feature-name, .role-name {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.feature-desc, .role-desc {
  font-size: 14px;
  color: #999;
  margin-top: 8px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
}

.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>