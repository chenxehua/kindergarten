# 智慧幼儿园系统 API 接口需求规格说明书

## 一、用户模块 (User)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 1.1 | 账号密码登录 | /api/user/login | POST | ✅ | 已实现 |
| 1.2 | 微信授权登录 | /api/user/wechat/login | POST | ✅ | 已实现 |
| 1.3 | 短信验证码登录 | /api/user/sms/login | POST | ✅ | 已实现 |
| 1.4 | 获取当前用户信息 | /api/user/info | GET | ✅ | 已实现 |
| 1.5 | 家长获取孩子列表 | /api/user/children | GET | ✅ | 已实现 |
| 1.6 | 家长查看孩子详细信息 | /api/user/child/{id} | GET | ✅ | 已实现 |

## 二、学生模块 (Student)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 2.1 | 新增学生 | /api/student/add | POST | ✅ | 已实现 |
| 2.2 | 更新学生信息 | /api/student/update | PUT | ✅ | 已实现 |
| 2.3 | 删除学生 | /api/student/delete | DELETE | ✅ | 已实现 |
| 2.4 | 获取学生详情 | /api/student/detail | GET | ✅ | 已实现 |
| 2.5 | 获取学生列表 | /api/student/list | GET | ✅ | 已实现 |
| 2.6 | 学生转班 | /api/student/transfer | POST | ✅ | 已实现 |

## 三、班级模块 (Class)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 3.1 | 新增班级 | /api/class/add | POST | ✅ | 已实现 |
| 3.2 | 更新班级 | /api/class/update | PUT | ✅ | 已实现 |
| 3.3 | 获取班级详情 | /api/class/detail | GET | ✅ | 已实现 |
| 3.4 | 获取班级列表 | /api/class/list | GET | ✅ | 已实现 |
| 3.5 | 获取班级学生列表 | /api/class/students | GET | ✅ | 已实现 |
| 3.6 | 获取班级老师列表 | /api/class/teachers | GET | ✅ | 已实现 |

## 四、成长记录模块 (Record)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 4.1 | 保存成长记录 | /api/record/save | POST | ✅ | 已实现 |
| 4.2 | 发布成长记录 | /api/record/publish | POST | ✅ | 已实现 |
| 4.3 | 获取记录详情 | /api/record/detail | GET | ✅ | 已实现 |
| 4.4 | 获取学生记录列表 | /api/record/list | GET | ✅ | 已实现 |
| 4.5 | 上传成长照片 | /api/record/photo/upload | POST | ✅ | 已实现 |

## 五、通知模块 (Notice)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 5.1 | 发布通知 | /api/notice/publish | POST | ✅ | 已实现 |
| 5.2 | 获取通知列表 | /api/notice/list | GET | ✅ | 已实现 |
| 5.3 | 获取通知详情 | /api/notice/detail | GET | ✅ | 已实现 |
| 5.4 | 撤回通知 | /api/notice/recall | POST | ✅ | 已实现 |

## 六、食谱模块 (Food)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 6.1 | 新增食谱 | /api/food/recipe/add | POST | ✅ | 已实现 |
| 6.2 | 获取食谱列表 | /api/food/recipe/list | GET | ✅ | 已实现 |
| 6.3 | 获取本周食谱 | /api/food/recipe/thisWeek | GET | ✅ | 已实现 |
| 6.4 | 检查过敏 | /api/food/allergy/check | GET | ✅ | 已实现 |

## 七、课程模块 (Course)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 7.1 | 新增课程 | /api/course/add | POST | ✅ | 已实现 |
| 7.2 | 获取课程列表 | /api/course/list | GET | ✅ | 已实现 |
| 7.3 | 添加课程安排 | /api/course/schedule/add | POST | ✅ | 已实现 |
| 7.4 | 获取班级课表 | /api/course/schedule/class | GET | ✅ | 已实现 |

## 八、视频模块 (Video)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 8.1 | 获取视频列表 | /api/video/list | GET | ✅ | 已实现 |
| 8.2 | 生成成长视频 | /api/video/generate | POST | ✅ | 已实现 |
| 8.3 | 视频审核 | /api/video/audit | POST | ✅ | 已实现 |

## 九、成长模块 (Growth)

| 序号 | 接口名称 | 接口路径 | 方法 | 状态 | 备注 |
|------|----------|----------|------|------|------|
| 9.1 | 获取成长画像 | /api/growth/profile | GET | ✅ | 已实现 |
| 9.2 | 获取月度成长报告 | /api/growth/report/monthly | GET | ✅ | 已实现 |

---

## 接口统计

| 模块 | 接口数 | 已实现 | 完成率 |
|------|--------|--------|--------|
| 用户模块 | 6 | 6 | 100% |
| 学生模块 | 6 | 6 | 100% |
| 班级模块 | 6 | 6 | 100% |
| 成长记录 | 5 | 5 | 100% |
| 通知模块 | 4 | 4 | 100% |
| 食谱模块 | 4 | 4 | 100% |
| 课程模块 | 4 | 4 | 100% |
| 视频模块 | 3 | 3 | 100% |
| 成长模块 | 2 | 2 | 100% |
| **总计** | **40** | **40** | **100%** |

---

## 附录：接口详细设计

### 1. 用户模块

#### 1.1 账号密码登录
- **路径**: POST /api/user/login
- **请求参数**:
  - username: String (用户名)
  - password: String (密码)
- **返回参数**:
  - token: String (访问令牌)
  - userId: String (用户ID)
  - role: String (角色)

#### 1.2 微信授权登录
- **路径**: POST /api/user/wechat/login
- **请求参数**:
  - code: String (微信授权码)
- **返回参数**: 同上

#### 1.3 短信验证码登录
- **路径**: POST /api/user/sms/login
- **请求参数**:
  - phone: String (手机号)
  - code: String (验证码)
- **返回参数**: 同上

#### 1.4 获取当前用户信息
- **路径**: GET /api/user/info
- **请求头**: Authorization: Bearer {token}
- **返回参数**: 用户详细信息

#### 1.5 家长获取孩子列表
- **路径**: GET /api/user/children
- **返回参数**: 孩子列表

#### 1.6 家长查看孩子详细信息
- **路径**: GET /api/user/child/{id}
- **返回参数**: 孩子详细信息

### 2. 学生模块

#### 2.1 新增学生
- **路径**: POST /api/student/add
- **请求参数**: 学生信息对象

#### 2.2 更新学生信息
- **路径**: PUT /api/student/update
- **请求参数**: 学生信息对象

#### 2.3 删除学生
- **路径**: DELETE /api/student/delete
- **请求参数**: studentId

#### 2.4 获取学生详情
- **路径**: GET /api/student/detail
- **请求参数**: studentId

#### 2.5 获取学生列表
- **路径**: GET /api/student/list
- **请求参数**: page, pageSize, classId

#### 2.6 学生转班
- **路径**: POST /api/student/transfer
- **请求参数**: studentId, fromClassId, toClassId

### 3. 班级模块

#### 3.1 新增班级
- **路径**: POST /api/class/add

#### 3.2 更新班级
- **路径**: PUT /api/class/update

#### 3.3 获取班级详情
- **路径**: GET /api/class/detail

#### 3.4 获取班级列表
- **路径**: GET /api/class/list

#### 3.5 获取班级学生列表
- **路径**: GET /api/class/students

#### 3.6 获取班级老师列表
- **路径**: GET /api/class/teachers

### 4. 成长记录模块

#### 4.1 保存成长记录
- **路径**: POST /api/record/save

#### 4.2 发布成长记录
- **路径**: POST /api/record/publish

#### 4.3 获取记录详情
- **路径**: GET /api/record/detail

#### 4.4 获取学生记录列表
- **路径**: GET /api/record/list

#### 4.5 上传成长照片
- **路径**: POST /api/record/photo/upload

### 5. 通知模块

#### 5.1 发布通知
- **路径**: POST /api/notice/publish

#### 5.2 获取通知列表
- **路径**: GET /api/notice/list

#### 5.3 获取通知详情
- **路径**: GET /api/notice/detail

#### 5.4 撤回通知
- **路径**: POST /api/notice/recall

### 6. 食谱模块

#### 6.1 新增食谱
- **路径**: POST /api/food/recipe/add

#### 6.2 获取食谱列表
- **路径**: GET /api/food/recipe/list

#### 6.3 获取本周食谱
- **路径**: GET /api/food/recipe/thisWeek

#### 6.4 检查过敏
- **路径**: GET /api/food/allergy/check

### 7. 课程模块

#### 7.1 新增课程
- **路径**: POST /api/course/add

#### 7.2 获取课程列表
- **路径**: GET /api/course/list

#### 7.3 添加课程安排
- **路径**: POST /api/course/schedule/add

#### 7.4 获取班级课表
- **路径**: GET /api/course/schedule/class

### 8. 视频模块

#### 8.1 获取视频列表
- **路径**: GET /api/video/list

#### 8.2 生成成长视频
- **路径**: POST /api/video/generate

#### 8.3 视频审核
- **路径**: POST /api/video/audit

### 9. 成长模块

#### 9.1 获取成长画像
- **路径**: GET /api/growth/profile

#### 9.2 获取月度成长报告
- **路径**: GET /api/growth/report/monthly

---

*文档版本: 1.0*  
*最后更新: 2026-03-26*
