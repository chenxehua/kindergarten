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

// 用户API
export const userApi = {
  login: (data) => request.post('/user/login', data),
  getUserInfo: () => request.get('/user/info')
}

// 学生API
export const studentApi = {
  list: (params) => request.get('/student/list', { params }),
  detail: (studentId) => request.get('/student/detail', { params: { studentId } }),
  add: (data) => request.post('/student/add', data),
  update: (studentId, data) => request.put('/student/update', { studentId, ...data }),
  delete: (studentId) => request.delete('/student/delete', { params: { studentId } })
}

// 班级API
export const classApi = {
  list: (params) => request.get('/class/list', { params }),
  detail: (classId) => request.get('/class/detail', { params: { classId } }),
  add: (data) => request.post('/class/add', data)
}

// 成长记录API
export const recordApi = {
  list: (params) => request.get('/record/list', { params }),
  detail: (recordId) => request.get('/record/detail', { params: { recordId } }),
  publish: (data) => request.post('/record/publish', data)
}

// 食谱API
export const foodApi = {
  thisWeek: (kgId) => request.get('/food/recipe/thisWeek', { params: { kgId } }),
  list: (params) => request.get('/food/recipe/list', { params })
}

// 课程API
export const courseApi = {
  list: (params) => request.get('/course/list', { params }),
  add: (data) => request.post('/course/add', data)
}

// 成长API
export const growthApi = {
  profile: (params) => request.get('/growth/profile', { params }),
  report: (params) => request.get('/growth/report/monthly', { params })
}

// 视频API
export const videoApi = {
  list: (params) => request.get('/video/list', { params }),
  generate: (params) => request.post('/video/generate', null, { params })
}

// 通知API
export const noticeApi = {
  list: (params) => request.get('/notice/list', { params }),
  publish: (data) => request.post('/notice/publish', data)
}
