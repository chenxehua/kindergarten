# 智慧幼儿园成长管理系统 - 功能规格说明书

**文档版本**: V2.0  
**编制日期**: 2026-03-26  
**项目名称**: 智慧幼儿园成长管理系统  
**项目代号**: KGMS

---

## 一、用户模块 (User)

### 1.1 账号密码登录

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/user/login |
| 功能说明 | 用户使用账号密码登录系统 |
| 权限角色 | 家长、老师、园长、管理员 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名或手机号 |
| password | String | 是 | 密码(明文传输，后端MD5加密存储) |
| userType | Integer | 否 | 用户类型: 1-家长 2-老师 3-园长 |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| token | String | JWT访问令牌 |
| userId | String | 用户ID |
| nickname | String | 昵称 |
| avatar | String | 头像URL |
| userType | Integer | 用户类型 |

---

### 1.2 微信授权登录

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/user/login/wechat |
| 功能说明 | 用户通过微信授权登录 |
| 权限角色 | 家长、老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| code | String | 是 | 微信授权码 |
| nickname | String | 否 | 微信昵称 |
| avatar | String | 否 | 微信头像URL |
| userType | Integer | 是 | 用户类型: 1-家长 2-老师 |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| token | String | JWT访问令牌 |
| userId | String | 用户ID |
| isNewUser | Boolean | 是否新用户 |

---

### 1.3 短信验证码登录

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/user/login/sms |
| 功能说明 | 用户通过短信验证码登录 |
| 权限角色 | 家长、老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| phone | String | 是 | 手机号 |
| code | String | 是 | 短信验证码 |
| userType | Integer | 是 | 用户类型 |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| token | String | JWT访问令牌 |
| userId | String | 用户ID |

---

### 1.4 获取当前用户信息

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/user/info |
| 功能说明 | 获取当前登录用户的基本信息 |
| 权限角色 | 已登录用户 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| userId | String | 用户ID |
| nickname | String | 昵称 |
| avatar | String | 头像URL |
| phone | String | 手机号 |
| userType | Integer | 用户类型 |

---

### 1.5 家长获取孩子列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/user/child/list |
| 功能说明 | 家长获取自己关联的孩子列表 |
| 权限角色 | 家长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| (Header) X-User-Id | String | 是 | 当前家长ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| studentId | String | 学生ID |
| studentName | String | 学生姓名 |
| avatar | String | 头像URL |
| className | String | 班级名称 |
| classId | String | 班级ID |
| status | Integer | 在园状态: 1-在园 2-离园 |

---

### 1.6 家长查看孩子详细信息

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/user/child/detail |
| 功能说明 | 家长查看孩子的详细信息 |
| 权限角色 | 家长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| (Header) X-User-Id | String | 是 | 当前家长ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| 基本信息 |
| studentId | String | 学生ID |
| studentName | String | 学生姓名 |
| gender | Integer | 性别: 1-男 2-女 |
| birthday | Date | 出生日期 |
| avatar | String | 头像URL |
| className | String | 班级名称 |
| enrollDate | Date | 入园日期 |
| 健康信息 |
| allergyInfo | String | 过敏信息 |
| medicalHistory | String | 既往病史 |
| heightWeight | String | 身高体重 |
| 家庭信息 |
| homeAddress | String | 家庭住址 |
| emergencyContact | String | 紧急联系人 |
| emergencyPhone | String | 紧急联系电话 |

---

## 二、学生模块 (Student)

### 2.1 新增学生

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/student/add |
| 功能说明 | 老师新增学生信息 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentName | String | 是 | 学生姓名 |
| gender | Integer | 是 | 性别: 1-男 2-女 |
| birthday | Date | 是 | 出生日期 |
| classId | String | 是 | 班级ID |
| enrollDate | Date | 是 | 入园日期 |
| idCard | String | 否 | 身份证号 |
| avatar | String | 否 | 头像URL |
| allergyInfo | String | 否 | 过敏信息 |
| medicalHistory | String | 否 | 既往病史 |
| homeAddress | String | 否 | 家庭住址 |
| emergencyContact | String | 是 | 紧急联系人 |
| emergencyPhone | String | 是 | 紧急联系电话 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| studentId | String | 学生ID |

---

### 2.2 更新学生信息

| 项目 | 说明 |
|------|------|
| 接口地址 | PUT /api/student/update |
| 功能说明 | 老师编辑学生信息 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| studentName | String | 否 | 学生姓名 |
| gender | Integer | 否 | 性别 |
| birthday | Date | 否 | 出生日期 |
| classId | String | 否 | 班级ID |
| avatar | String | 否 | 头像URL |
| allergyInfo | String | 否 | 过敏信息 |
| medicalHistory | String | 否 | 既往病史 |
| homeAddress | String | 否 | 家庭住址 |
| emergencyContact | String | 否 | 紧急联系人 |
| emergencyPhone | String | 否 | 紧急联系电话 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

### 2.3 删除学生

| 项目 | 说明 |
|------|------|
| 接口地址 | DELETE /api/student/delete |
| 功能说明 | 删除(逻辑删除)学生信息 |
| 权限角色 | 园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

### 2.4 获取学生详情

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/student/detail |
| 功能说明 | 获取学生详细信息 |
| 权限角色 | 老师、园长、家长(自己孩子) |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| studentId | String | 学生ID |
| studentName | String | 学生姓名 |
| gender | Integer | 性别 |
| birthday | Date | 出生日期 |
| idCard | String | 身份证号 |
| avatar | String | 头像URL |
| classId | String | 班级ID |
| className | String | 班级名称 |
| enrollDate | Date | 入园日期 |
| status | Integer | 状态: 1-在园 2-离园 |
| allergyInfo | String | 过敏信息 |
| medicalHistory | String | 既往病史 |
| heightWeight | String | 身高体重 |
| homeAddress | String | 家庭住址 |
| emergencyContact | String | 紧急联系人 |
| emergencyPhone | String | 紧急联系电话 |
| createTime | DateTime | 创建时间 |

---

### 2.5 获取学生列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/student/list |
| 功能说明 | 分页获取学生列表 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| classId | String | 否 | 班级ID |
| keyword | String | 否 | 搜索关键词(姓名) |
| status | Integer | 否 | 状态: 1-在园 2-离园 |
| page | Integer | 否 | 页码, 默认1 |
| pageSize | Integer | 否 | 每页数量, 默认10 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 学生列表 |
| total | Long | 总数 |
| page | Integer | 当前页码 |
| pageSize | Integer | 每页数量 |

---

### 2.6 学生转班

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/student/transfer |
| 功能说明 | 学生转到另一个班级 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| targetClassId | String | 是 | 目标班级ID |
| transferDate | Date | 是 | 转班日期 |
| reason | String | 否 | 转班原因 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

## 三、班级模块 (Class)

### 3.1 新增班级

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/class/add |
| 功能说明 | 园长新增班级 |
| 权限角色 | 园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |
| className | String | 是 | 班级名称 |
| grade | String | 是 | 年级 |
| headTeacherId | String | 否 | 班主任ID |
| capacity | Integer | 是 | 容量 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| classId | String | 班级ID |

---

### 3.2 更新班级

| 项目 | 说明 |
|------|------|
| 接口地址 | PUT /api/class/update |
| 功能说明 | 园长编辑班级信息 |
| 权限角色 | 园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| classId | String | 是 | 班级ID |
| className | String | 否 | 班级名称 |
| grade | String | 否 | 年级 |
| headTeacherId | String | 否 | 班主任ID |
| capacity | Integer | 否 | 容量 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

### 3.3 获取班级详情

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/class/detail |
| 功能说明 | 获取班级详细信息 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| classId | String | 是 | 班级ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| classId | String | 班级ID |
| className | String | 班级名称 |
| grade | String | 年级 |
| headTeacherId | String | 班主任ID |
| capacity | Integer | 容量 |
| studentCount | Integer | 学生数量 |
| status | Integer | 状态 |

---

### 3.4 获取班级列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/class/list |
| 功能说明 | 分页获取班级列表 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 否 | 幼儿园ID |
| grade | String | 否 | 年级 |
| page | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页数量 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 班级列表 |
| total | Long | 总数 |

---

### 3.5 获取班级学生列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/class/students |
| 功能说明 | 获取班级的学生列表 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| classId | String | 是 | 班级ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| studentId | String | 学生ID |
| studentName | String | 学生姓名 |
| avatar | String | 头像URL |
| gender | Integer | 性别 |
| status | Integer | 状态 |

---

### 3.6 获取班级老师列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/class/teachers |
| 功能说明 | 获取班级的老师列表 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| classId | String | 是 | 班级ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| userId | String | 老师ID |
| nickname | String | 姓名 |
| avatar | String | 头像URL |
| role | String | 角色: 班主任/副班/保育员 |

---

## 四、成长记录模块 (Record)

### 4.1 保存成长记录

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/record/save |
| 功能说明 | 老师保存学生成长记录(草稿或发布) |
| 权限角色 | 老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| recordDate | Date | 是 | 记录日期 |
| courseRecord | String | 否 | 课程记录 |
| coursePhotos | String | 否 | 课程照片(逗号分隔) |
| emotionType | String | 否 | 情绪类型 |
| emotionDetail | String | 否 | 情绪详情 |
| breakfast | String | 否 | 早餐情况 |
| lunch | String | 否 | 午餐情况 |
| dinner | String | 否 | 晚餐情况 |
| snack | String | 否 | 点心情况 |
| foodPhotos | String | 否 | 饮食照片 |
| sleepTime | String | 否 | 睡眠时间 |
| sleepQuality | String | 否 | 睡眠质量 |
| activityType | String | 否 | 活动类型 |
| activityDetail | String | 否 | 活动详情 |
| activityPhotos | String | 否 | 活动照片 |
| temperature | String | 否 | 体温 |
| publish | Boolean | 否 | 是否立即发布, 默认false |
| (Header) X-User-Id | String | 是 | 老师ID |
| (Header) X-Class-Id | String | 是 | 班级ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| recordId | String | 记录ID |

---

### 4.2 发布成长记录

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/record/publish |
| 功能说明 | 发布已保存的成长记录给家长 |
| 权限角色 | 老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| recordId | String | 是 | 记录ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

### 4.3 获取记录详情

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/record/detail |
| 功能说明 | 获取成长记录详情 |
| 权限角色 | 老师、家长(自己孩子) |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| recordId | String | 是 | 记录ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| recordId | String | 记录ID |
| studentId | String | 学生ID |
| studentName | String | 学生姓名 |
| recordDate | Date | 记录日期 |
| teacherId | String | 老师ID |
| teacherName | String | 老师姓名 |
| publishStatus | Integer | 发布状态: 0-草稿 1-已发布 |
| publishTime | DateTime | 发布时间 |
| courseRecord | String | 课程记录 |
| coursePhotos | List | 课程照片列表 |
| emotionType | String | 情绪类型 |
| breakfast | String | 早餐情况 |
| lunch | String | 午餐情况 |
| dinner | String | 晚餐情况 |
| activityDetail | String | 活动详情 |
| activityPhotos | List | 活动照片列表 |
| temperature | String | 体温 |

---

### 4.4 获取学生记录列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/record/student/list |
| 功能说明 | 获取学生的成长记录列表 |
| 权限角色 | 家长(自己孩子)、老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| startDate | Date | 否 | 开始日期 |
| endDate | Date | 否 | 结束日期 |
| page | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页数量 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 记录列表 |
| total | Long | 总数 |

---

### 4.5 上传成长照片

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/record/photo/upload |
| 功能说明 | 上传记录相关照片 |
| 权限角色 | 老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | MultipartFile | 是 | 图片文件 |
| studentId | String | 否 | 学生ID |
| recordId | String | 否 | 记录ID |
| photoType | String | 否 | 照片类型: course/activity/food |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| photoId | String | 照片ID |
| photoUrl | String | 照片URL |
| thumbUrl | String | 缩略图URL |

---

## 五、通知模块 (Notice)

### 5.1 发布通知

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/notice/publish |
| 功能说明 | 发布通知公告 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |
| title | String | 是 | 通知标题 |
| content | String | 是 | 通知内容 |
| noticeType | String | 是 | 通知类型: 1-系统通知 2-活动通知 3-安全通知 |
| targetType | String | 否 | 目标类型: all-全部 class-班级 |
| targetIds | String | 否 | 目标ID列表(逗号分隔) |
| attachUrls | String | 否 | 附件URL列表 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| noticeId | String | 通知ID |

---

### 5.2 获取通知列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/notice/list |
| 功能说明 | 获取通知列表 |
| 权限角色 | 家长、老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 否 | 幼儿园ID |
| noticeType | String | 否 | 通知类型 |
| page | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页数量 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 通知列表 |
| total | Long | 总数 |

---

### 5.3 获取通知详情

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/notice/detail |
| 功能说明 | 获取通知详情 |
| 权限角色 | 家长、老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| noticeId | String | 是 | 通知ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| noticeId | String | 通知ID |
| title | String | 标题 |
| content | String | 内容 |
| noticeType | String | 通知类型 |
| publishBy | String | 发布人 |
| publishTime | DateTime | 发布时间 |
| attachUrls | List | 附件列表 |

---

### 5.4 撤回通知

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/notice/withdraw |
| 功能说明 | 撤回已发布的通知 |
| 权限角色 | 园长、发布者 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| noticeId | String | 是 | 通知ID |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

## 六、食谱模块 (Food)

### 6.1 新增食谱

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/recipe/add |
| 功能说明 | 营养师新增每周食谱 |
| 权限角色 | 园长、营养师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |
| weekStart | Date | 是 | 周开始日期 |
| monday | String | 否 | 周一食谱 |
| tuesday | String | 否 | 周二食谱 |
| wednesday | String | 否 | 周三食谱 |
| thursday | String | 否 | 周四食谱 |
| friday | String | 否 | 周五食谱 |
| saturday | String | 否 | 周六食谱 |
| sunday | String | 否 | 周日食谱 |
| nutritionist | String | 否 | 营养师 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| recipeId | String | 食谱ID |

---

### 6.2 获取食谱列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/recipe/list |
| 功能说明 | 获取食谱列表 |
| 权限角色 | 老师、园长、家长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |
| year | Integer | 否 | 年份 |
| month | Integer | 否 | 月份 |
| page | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页数量 |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 食谱列表 |
| total | Long | 总数 |

---

### 6.3 获取本周食谱

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/recipe/current |
| 功能说明 | 获取本周食谱详情 |
| 权限角色 | 老师、家长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| recipeId | String | 食谱ID |
| weekStart | Date | 周开始日期 |
| monday | String | 周一食谱 |
| tuesday | String | 周二食谱 |
| wednesday | String | 周三食谱 |
| thursday | String | 周四食谱 |
| friday | String | 周五食谱 |
| saturday | String | 周六食谱 |
| sunday | String | 周日食谱 |
| nutritionist | String | 营养师 |

---

### 6.4 检查过敏

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/recipe/check/allergy |
| 功能说明 | 检查食谱是否包含过敏原 |
| 权限角色 | 老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |
| studentId | String | 是 | 学生ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| hasAllergy | Boolean | 是否有过敏风险 |
| allergyInfo | String | 过敏信息 |
| alertMeals | List | 需要注意的餐点 |

---

## 七、课程模块 (Course)

### 7.1 新增课程

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/course/add |
| 功能说明 | 新增课程 |
| 权限角色 | 园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 是 | 幼儿园ID |
| courseName | String | 是 | 课程名称 |
| courseType | String | 是 | 课程类型 |
| ageGroup | String | 否 | 适龄年龄段 |
| description | String | 否 | 课程描述 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| courseId | String | 课程ID |

---

### 7.2 获取课程列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/course/list |
| 功能说明 | 获取课程列表 |
| 权限角色 | 老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 否 | 幼儿园ID |
| courseType | String | 否 | 课程类型 |
| ageGroup | String | 否 | 适龄年龄段 |
| page | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页数量 |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 课程列表 |
| total | Long | 总数 |

---

### 7.3 添加课程安排

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/course/schedule/add |
| 功能说明 | 编排班级课程表 |
| 权限角色 | 园长、老师 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| courseId | String | 是 | 课程ID |
| classId | String | 是 | 班级ID |
| dayOfWeek | Integer | 是 | 星期: 1-7 |
| startTime | String | 是 | 开始时间 HH:mm |
| endTime | String | 是 | 结束时间 HH:mm |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| scheduleId | String | 安排ID |

---

### 7.4 获取班级课表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/course/schedule/class |
| 功能说明 | 获取班级课程表 |
| 权限角色 | 老师、家长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| classId | String | 是 | 班级ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| scheduleId | String | 安排ID |
| courseId | String | 课程ID |
| courseName | String | 课程名称 |
| dayOfWeek | Integer | 星期 |
| startTime | String | 开始时间 |
| endTime | String | 结束时间 |

---

## 八、视频模块 (Video)

### 8.1 获取视频列表

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/video/list |
| 功能说明 | 获取成长视频列表 |
| 权限角色 | 家长、老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| kgId | String | 否 | 幼儿园ID |
| studentId | String | 否 | 学生ID |
| status | Integer | 否 | 状态: 0-生成中 1-已完成 2-审核中 3-已发布 |
| page | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页数量 |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| records | List | 视频列表 |
| total | Long | 总数 |

---

### 8.2 生成成长视频

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/video/generate |
| 功能说明 | AI生成成长视频 |
| 权限角色 | 系统(自动) |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| month | String | 是 | 月份: YYYY-MM |
| templateId | String | 否 | 视频模板ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| videoId | String | 视频ID |

---

### 8.3 视频审核

| 项目 | 说明 |
|------|------|
| 接口地址 | POST /api/video/audit |
| 功能说明 | 园长审核视频 |
| 权限角色 | 园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| videoId | String | 是 | 视频ID |
| status | Integer | 是 | 审核状态: 1-通过 2-拒绝 |
| reason | String | 否 | 拒绝原因 |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| success | Boolean | 是否成功 |

---

## 九、成长模块 (Growth)

### 9.1 获取成长画像

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/growth/profile |
| 功能说明 | 获取学生AI成长画像 |
| 权限角色 | 家长(自己孩子)、老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| month | String | 是 | 月份: YYYY-MM |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| studentId | String | 学生ID |
| profileMonth | String | 月份 |
| overallScore | Integer | 综合评分 |
| emotionScore | Integer | 情绪评分 |
| activityScore | Integer | 活动评分 |
| dietScore | Integer | 饮食评分 |
| emotionAnalysis | String | 情绪分析 |
| activityAnalysis | String | 活动分析 |
| suggestions | List | 建议列表 |

---

### 9.2 获取月度成长报告

| 项目 | 说明 |
|------|------|
| 接口地址 | GET /api/growth/report |
| 功能说明 | 获取月度成长报告 |
| 权限角色 | 家长(自己孩子)、老师、园长 |

**输入参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| studentId | String | 是 | 学生ID |
| month | String | 是 | 月份: YYYY-MM |
| (Header) X-User-Id | String | 是 | 当前用户ID |

**输出参数**
| 参数名 | 类型 | 说明 |
|--------|------|------|
| reportId | String | 报告ID |
| studentId | String | 学生ID |
| reportMonth | String | 报告月份 |
| overview | String | 月度概述 |
| highlights | List | 成长亮点 |
| emotionAnalysis | Object | 情绪分析 |
| activityAnalysis | Object | 活动分析 |
| dietAnalysis | Object | 饮食分析 |
| aiAnalysis | String | AI分析解读 |
| teacherComment | String | 教师寄语 |
| photos | List | 精选照片 |

---

*文档生成时间: 2026-03-26*
