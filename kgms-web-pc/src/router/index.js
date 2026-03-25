import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue')
      },
      {
        path: 'student',
        name: 'Student',
        component: () => import('@/views/student/StudentList.vue')
      },
      {
        path: 'class',
        name: 'Class',
        component: () => import('@/views/class/ClassList.vue')
      },
      {
        path: 'record',
        name: 'Record',
        component: () => import('@/views/record/RecordList.vue')
      },
      {
        path: 'food',
        name: 'Food',
        component: () => import('@/views/food/RecipeList.vue')
      },
      {
        path: 'course',
        name: 'Course',
        component: () => import('@/views/course/CourseList.vue')
      },
      {
        path: 'growth',
        name: 'Growth',
        component: () => import('@/views/growth/GrowthProfile.vue')
      },
      {
        path: 'video',
        name: 'Video',
        component: () => import('@/views/video/VideoList.vue')
      },
      {
        path: 'notice',
        name: 'Notice',
        component: () => import('@/views/notice/NoticeList.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
