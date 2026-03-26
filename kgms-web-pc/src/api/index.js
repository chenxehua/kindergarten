import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res.data
    } else if (res.code === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      window.location.href = '/login'
      return Promise.reject(new Error(res.message))
    } else {
      return Promise.reject(new Error(res.message))
    }
  },
  error => {
    return Promise.reject(error)
  }
)

export default request

// ==================== 用户API ====================
export const userApi = {
  // 登录
  login: (data) => request.post('/user/login', data),
  loginByWechat: (data) => request.post('/user/login/wechat', data),
  loginBySms: (data) => request.post('/user/login/sms', data),
  
  // 用户信息
  getUserInfo: () => request.get('/user/info'),
  logout: () => request.post('/user/logout'),
  
  // 孩子管理
  getChildList: () => request.get('/user/child/list'),
  getChildDetail: (studentId) => request.get('/user/child/detail', { params: { studentId } })
}

// ==================== 学生API ====================
export const studentApi = {
  // 学生列表
  list: (params) => request.get('/student/list', { params }),
  
  // 学生详情
  detail: (studentId) => request.get('/student/detail', { params: { studentId } }),
  
  // 新增学生
  add: (data) => request.post('/student/add', data),
  
  // 更新学生
  update: (studentId, data) => request.put('/student/update', { studentId, ...data }),
  
  // 删除学生
  delete: (studentId) => request.delete('/student/delete', { params: { studentId } }),
  
  // 转班
  transfer: (data) => request.post('/student/transfer', data),
  
  // 批量导入
  batchImport: (data) => request.post('/student/batch/import', data, {
    headers: { 'Content-Type': 'multipart/form-data' }
  }),
  
  // 按班级获取学生
  getByClassId: (classId) => request.get('/student/list', { params: { classId } })
}

// ==================== 班级API ====================
export const classApi = {
  // 班级列表
  list: (params) => request.get('/class/list', { params }),
  
  // 班级详情
  detail: (classId) => request.get('/class/detail', { params: { classId } }),
  
  // 新增班级
  add: (data) => request.post('/class/add', data),
  
  // 更新班级
  update: (classId, data) => request.put('/class/update', { classId, ...data }),
  
  // 获取班级学生
  getStudents: (classId) => request.get('/class/students', { params: { classId } }),
  
  // 获取班级老师
  getTeachers: (classId) => request.get('/class/teachers', { params: { classId } })
}

// ==================== 成长记录API ====================
export const recordApi = {
  // 记录列表
  list: (params) => request.get('/record/student/list', { params }),
  
  // 记录详情
  detail: (recordId) => request.get('/record/detail', { params: { recordId } }),
  
  // 保存记录
  save: (data) => request.post('/record/save', data),
  
  // 发布记录
  publish: (recordId) => request.post('/record/publish', { params: { recordId } }),
  
  // 按日期获取记录
  getByDate: (params) => request.get('/record/date', { params }),
  
  // 上传照片
  uploadPhoto: (data) => request.post('/record/photo/upload', data, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// ==================== 食谱API ====================
export const foodApi = {
  // 本周食谱
  thisWeek: (kgId) => request.get('/recipe/current', { params: { kgId } }),
  
  // 食谱列表
  list: (params) => request.get('/recipe/list', { params }),
  
  // 食谱详情
  detail: (recipeId) => request.get('/recipe/detail', { params: { recipeId } }),
  
  // 新增食谱
  add: (data) => request.post('/recipe/add', data),
  
  // 更新食谱
  update: (recipeId, data) => request.put('/recipe/update', { recipeId, ...data }),
  
  // 发布食谱
  publish: (recipeId) => request.post('/recipe/publish', { params: { recipeId } }),
  
  // 检查过敏
  checkAllergy: (params) => request.get('/recipe/check/allergy', { params })
}

// ==================== 课程API ====================
export const courseApi = {
  // 课程列表
  list: (params) => request.get('/course/list', { params }),
  
  // 课程详情
  detail: (courseId) => request.get('/course/detail', { params: { courseId } }),
  
  // 新增课程
  add: (data) => request.post('/course/add', data),
  
  // 更新课程
  update: (courseId, data) => request.put('/course/update', { courseId, ...data }),
  
  // 课表列表
  scheduleList: (params) => request.get('/course/schedule/list', { params }),
  
  // 添加课表
  addSchedule: (data) => request.post('/course/schedule/add', data),
  
  // 更新课表
  updateSchedule: (scheduleId, data) => request.put('/course/schedule/update', { scheduleId, ...data }),
  
  // 删除课表
  deleteSchedule: (scheduleId) => request.delete('/course/schedule', { params: { scheduleId } }),
  
  // 班级课表
  classSchedule: (classId) => request.get('/course/schedule/class', { params: { classId } }),
  
  // 本周课表
  thisWeekSchedule: (classId) => request.get('/course/schedule/week', { params: { classId } })
}

// ==================== 视频API ====================
export const videoApi = {
  // 视频列表
  list: (params) => request.get('/video/list', { params }),
  
  // 视频详情
  detail: (videoId) => request.get('/video/detail', { params: { videoId } }),
  
  // 生成视频
  generate: (data) => request.post('/video/generate', data),
  
  // 重新生成
  regenerate: (videoId) => request.post('/video/regenerate', { params: { videoId } }),
  
  // 播放视频
  play: (videoId) => request.get('/video/play', { params: { videoId } }),
  
  // 下载视频
  download: (videoId) => request.get('/video/download', { params: { videoId } }),
  
  // 分享视频
  share: (videoId) => request.get('/video/share', { params: { videoId } }),
  
  // 审核视频
  audit: (data) => request.post('/video/audit', data)
}

// ==================== 成长API ====================
export const growthApi = {
  // 成长画像
  profile: (params) => request.get('/growth/profile', { params }),
  
  // 生成画像
  generateProfile: (data) => request.post('/growth/profile/generate', data),
  
  // 月度报告
  monthlyReport: (params) => request.get('/growth/report', { params }),
  
  // 生成报告
  generateReport: (data) => request.post('/growth/report/generate', data),
  
  // 审核报告
  auditReport: (data) => request.post('/growth/audit', data),
  
  // 导出报告
  exportReport: (params) => request.get('/growth/export', { params }),
  
  // 历史报告
  reportHistory: (params) => request.get('/growth/report/history', { params })
}

// ==================== 通知API ====================
export const noticeApi = {
  // 通知列表
  list: (params) => request.get('/notice/list', { params }),
  
  // 通知详情
  detail: (noticeId) => request.get('/notice/detail', { params: { noticeId } }),
  
  // 发布通知
  publish: (data) => request.post('/notice/publish', data),
  
  // 撤回通知
  withdraw: (noticeId) => request.post('/notice/withdraw', { params: { noticeId } })
}
