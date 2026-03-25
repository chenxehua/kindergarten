// API基础配置
const BASE_URL = 'http://localhost:8080/api';

const request = (options) => {
  return new Promise((resolve, reject) => {
    const token = uni.getStorageSync('token');
    
    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '',
        ...options.header
      },
      success: (res) => {
        if (res.data.code === 200) {
          resolve(res.data.data);
        } else if (res.data.code === 401) {
          uni.removeStorageSync('token');
          uni.removeStorageSync('userInfo');
          uni.reLaunch({ url: '/pages/login/login' });
          reject(res.data);
        } else {
          uni.showToast({
            title: res.data.message || '请求失败',
            icon: 'none'
          });
          reject(res.data);
        }
      },
      fail: (err) => {
        uni.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
        reject(err);
      }
    });
  });
};

// 用户相关API
export const userApi = {
  login: (data) => request({ url: '/user/login', method: 'POST', data }),
  loginByWechat: (data) => request({ url: '/user/login/wechat', method: 'POST', data }),
  getUserInfo: () => request({ url: '/user/info', method: 'GET' }),
  logout: () => request({ url: '/user/logout', method: 'POST' })
};

// 学生相关API
export const studentApi = {
  list: (params) => request({ url: '/student/list', method: 'GET', data: params }),
  detail: (studentId) => request({ url: '/student/detail', method: 'GET', data: { studentId } }),
  add: (data) => request({ url: '/student/add', method: 'POST', data }),
  update: (studentId, data) => request({ url: '/student/update', method: 'PUT', data: { studentId, ...data } }),
  delete: (studentId) => request({ url: '/student/delete', method: 'DELETE', data: { studentId } }),
  getByClass: (classId) => request({ url: '/student/class/students', method: 'GET', data: { classId } })
};

// 班级相关API
export const classApi = {
  list: (params) => request({ url: '/class/list', method: 'GET', data: params }),
  detail: (classId) => request({ url: '/class/detail', method: 'GET', data: { classId } }),
  getByKg: (kgId) => request({ url: '/class/kg/classes', method: 'GET', data: { kgId } })
};

// 成长记录API
export const recordApi = {
  list: (params) => request({ url: '/record/list', method: 'GET', data: params }),
  detail: (recordId) => request({ url: '/record/detail', method: 'GET', data: { recordId } }),
  save: (data) => request({ url: '/record/save', method: 'POST', data }),
  publish: (data) => request({ url: '/record/publish', method: 'POST', data }),
  getByDate: (studentId, date) => request({ url: '/record/by-date', method: 'GET', data: { studentId, date } })
};

// 食谱API
export const foodApi = {
  thisWeek: (kgId) => request({ url: '/food/recipe/thisWeek', method: 'GET', data: { kgId } }),
  list: (params) => request({ url: '/food/recipe/list', method: 'GET', data: params })
};

// 课程API
export const courseApi = {
  list: (params) => request({ url: '/course/list', method: 'GET', data: params }),
  detail: (courseId) => request({ url: '/course/detail', method: 'GET', data: { courseId } })
};

// 成长画像API
export const growthApi = {
  profile: (studentId, month) => request({ url: '/growth/profile', method: 'GET', data: { studentId, month } }),
  generateProfile: (studentId, month) => request({ url: '/growth/profile/generate', method: 'POST', data: { studentId, month } }),
  report: (studentId, month) => request({ url: '/growth/report/monthly', method: 'GET', data: { studentId, month } }),
  generateReport: (studentId, month) => request({ url: '/growth/report/generate', method: 'POST', data: { studentId, month } })
};

// 视频API
export const videoApi = {
  list: (studentId) => request({ url: '/video/list', method: 'GET', data: { studentId } }),
  detail: (videoId) => request({ url: '/video/detail', method: 'GET', data: { videoId } }),
  generate: (studentId, month, templateId) => request({ url: '/video/generate', method: 'POST', data: { studentId, month, templateId } })
};

// 通知API
export const noticeApi = {
  list: (kgId, noticeType) => request({ url: '/notice/list', method: 'GET', data: { kgId, noticeType } }),
  detail: (noticeId) => request({ url: '/notice/detail', method: 'GET', data: { noticeId } }),
  publish: (data) => request({ url: '/notice/publish', method: 'POST', data })
};
