# 智慧幼儿园成长管理系统
## 开发设计文档 (DDD)

---

**文档版本**: V1.0  
**编制日期**: 2026-03-25  
**项目名称**: 智慧幼儿园成长管理系统  
**项目代号**: KGMS (Kindergarten Growth Management System)

---

## 1. 系统架构设计

### 1.1 整体架构

系统采用微服务架构设计，分为接入层、业务层、数据层、基础设施层四大部分。

```
┌─────────────────────────────────────────────────────────────────┐
│                         接入层                                   │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐ │
│  │ 微信小程序   │  │  PC管理后台  │  │    APP（H5响应式）       │ │
│  └─────────────┘  └─────────────┘  └─────────────────────────┘ │
├─────────────────────────────────────────────────────────────────┤
│                         网关层                                   │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │                    Spring Cloud Gateway                     ││
│  │         (路由转发 / 限流 / 鉴权 / 统一响应)                   ││
│  └─────────────────────────────────────────────────────────────┘│
├─────────────────────────────────────────────────────────────────┤
│                         业务微服务层                             │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌───────┐│
│  │ 用户服务  │ │ 班级服务  │ │ 记录服务  │ │ 成长服务  │ │ 视频  ││
│  │ (user)   │ │ (class)  │ │ (record) │ │ (growth) │ │(video)││
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘ └───────┘│
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌─────────────────────┐│
│  │ 食谱服务  │ │ 课程服务  │ │ 通知服务  │ │   AI分析服务       ││
│  │ (food)   │ │ (course) │ │ (notice) │ │   (ai-service)     ││
│  └──────────┘ └──────────┘ └──────────┘ └─────────────────────┘│
├─────────────────────────────────────────────────────────────────┤
│                         数据持久层                               │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌─────────────────────┐│
│  │  MySQL   │ │ MongoDB  │ │  Redis   │ │  对象存储(OSS)      ││
│  │ (业务数据) │ │(非结构化) │ │ (缓存)   │ │  (照片/视频)       ││
│  └──────────┘ └──────────┘ └──────────┘ └─────────────────────┘│
├─────────────────────────────────────────────────────────────────┤
│                         基础设施层                               │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌─────────────────────┐│
│  │K8s/Docker│ │  CDN    │ │  日志    │ │   监控(Prometheus)  ││
│  └──────────┘ └──────────┘ └──────────┘ └─────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
```

### 1.2 技术栈选型

| 层级 | 技术选型 | 说明 |
|------|---------|------|
| 接入层(小程序) | UniApp/Vue3 | 跨平台小程序框架 |
| 接入层(PC) | Vue3 + Element Plus | PC管理后台 |
| 网关 | Spring Cloud Gateway | 统一入口、限流、鉴权 |
| 微服务框架 | Spring Cloud Alibaba | Nacos注册/配置、Sentinel限流 |
| 业务语言 | Java 17 + Spring Boot 3.x |  |
| 数据库 | MySQL 8.0 | 主业务数据存储 |
| 文档数据库 | MongoDB | 日志、原始数据存储 |
| 缓存 | Redis Cluster | 会话、热点数据缓存 |
| 对象存储 | 阿里云OSS/MinIO | 照片、视频文件存储 |
| 消息队列 | RocketMQ | 异步任务、事件驱动 |
| AI能力 | 百度智能云/阿里云 | 视频生成、图像处理 |
| 日志 | ELK (Elasticsearch + Logstash + Kibana) |  |
| 监控 | Prometheus + Grafana |  |

---

## 2. 数据库设计

### 2.1 数据库命名规范

- 库名：`kgms_xxx`（xxx为业务模块缩写）
- 表名：`t_xxx_xxx`（全小写，下划线分隔）
- 字段名：`xxx_xxx`（全小写，下划线分隔）
- 索引名：`idx_xxx_field`

### 2.2 核心表结构

#### 2.2.1 用户与权限

```sql
-- 用户表
CREATE TABLE t_sys_user (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id         VARCHAR(32) NOT NULL UNIQUE COMMENT '用户ID(UUID)',
    user_type       TINYINT NOT NULL COMMENT '用户类型:1-家长 2-老师 3-园长 4-管理员',
    username        VARCHAR(50) COMMENT '用户名(手机号)',
    password        VARCHAR(128) COMMENT '密码(加密存储)',
    nickname        VARCHAR(50) COMMENT '昵称',
    avatar          VARCHAR(255) COMMENT '头像URL',
    phone           VARCHAR(20) COMMENT '手机号',
    wechat_openid   VARCHAR(64) COMMENT '微信OpenID',
    wechat_unionid  VARCHAR(64) COMMENT '微信UnionID',
    status          TINYINT DEFAULT 1 COMMENT '状态:0-禁用 1-正常',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT DEFAULT 0 COMMENT '删除标记:0-未删除 1-已删除',
    INDEX idx_phone (phone),
    INDEX idx_wechat_openid (wechat_openid),
    INDEX idx_user_type (user_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 老师信息扩展表
CREATE TABLE t_teacher_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL COMMENT '关联用户ID',
    teacher_no      VARCHAR(20) NOT NULL COMMENT '工号',
    position        VARCHAR(50) COMMENT '职位:主班/副班/保育员/保健医',
    department      VARCHAR(50) COMMENT '所属部门',
    hire_date       DATE COMMENT '入职日期',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_id (user_id),
    INDEX idx_teacher_no (teacher_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师信息表';

-- 家长信息扩展表
CREATE TABLE t_parent_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL COMMENT '关联用户ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '关联学生ID',
    relation_type   VARCHAR(20) NOT NULL COMMENT '关系:爸爸/妈妈/爷爷/奶奶/其他',
    is_emergency    TINYINT DEFAULT 0 COMMENT '是否紧急联系人:0-否 1-是',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家长信息表';
```

#### 2.2.2 组织架构

```sql
-- 园区/幼儿园表
CREATE TABLE t_org_kindergarten (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    kg_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '园区ID',
    kg_name         VARCHAR(100) NOT NULL COMMENT '园区名称',
    kg_address      VARCHAR(255) COMMENT '园区地址',
    kg_phone        VARCHAR(20) COMMENT '联系电话',
    logo_url        VARCHAR(255) COMMENT 'Logo',
    principal_id    VARCHAR(32) COMMENT '园长用户ID',
    status          TINYINT DEFAULT 1 COMMENT '状态:0-停用 1-正常',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='幼儿园/园区表';

-- 班级表
CREATE TABLE t_class_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '班级ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '所属园区ID',
    class_name      VARCHAR(50) NOT NULL COMMENT '班级名称:星星班/阳光班',
    grade           VARCHAR(20) NOT NULL COMMENT '年级:小班/中班/大班',
    head_teacher_id VARCHAR(32) COMMENT '班主任用户ID',
    capacity        INT DEFAULT 30 COMMENT '容量',
    student_count   INT DEFAULT 0 COMMENT '当前人数',
    status          TINYINT DEFAULT 1 COMMENT '状态:0-结班 1-在读',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_grade (grade)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 学生表
CREATE TABLE t_student_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id      VARCHAR(32) NOT NULL UNIQUE COMMENT '学生ID',
    student_name    VARCHAR(50) NOT NULL COMMENT '学生姓名',
    gender          TINYINT NOT NULL COMMENT '性别:1-男 2-女',
    birthday        DATE COMMENT '出生日期',
    id_card         VARCHAR(20) COMMENT '身份证号',
    avatar          VARCHAR(255) COMMENT '头像URL',
    class_id        VARCHAR(32) COMMENT '当前班级ID',
    enroll_date     DATE COMMENT '入园日期',
    status          TINYINT DEFAULT 1 COMMENT '状态:0-离园 1-在园 2-休学',
    allergy_info    TEXT COMMENT '过敏信息(JSON)',
    medical_history TEXT COMMENT '既往病史(JSON)',
    height_weight   JSON COMMENT '身高体重记录',
    home_address    VARCHAR(255) COMMENT '家庭住址',
    emergency_contact VARCHAR(50) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_class_id (class_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生信息表';
```

#### 2.2.3 成长记录

```sql
-- 每日成长记录表
CREATE TABLE t_growth_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '记录ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    record_date     DATE NOT NULL COMMENT '记录日期',
    teacher_id      VARCHAR(32) NOT NULL COMMENT '记录老师ID',
    class_id        VARCHAR(32) NOT NULL COMMENT '班级ID',
    
    -- 课程记录
    course_record   JSON COMMENT '课程学习记录(JSON数组)',
    course_photos   JSON COMMENT '课程相关照片(JSON数组)',
    
    -- 情绪记录
    emotion_type    VARCHAR(20) COMMENT '情绪类型:开心/平静/低落/哭闹',
    emotion_detail  TEXT COMMENT '情绪详情',
    
    -- 饮食记录
    breakfast       JSON COMMENT '早餐情况',
    lunch           JSON COMMENT '午餐情况',
    dinner          JSON COMMENT '晚餐情况',
    snack           JSON COMMENT '点心情况',
    food_photos     JSON COMMENT '饮食照片',
    allergy_flag    TINYINT DEFAULT 0 COMMENT '是否有过敏反应:0-否 1-是',
    allergy_detail  TEXT COMMENT '过敏详情',
    
    -- 午休记录
    sleep_time      VARCHAR(50) COMMENT '入睡时间',
    sleep_quality   VARCHAR(20) COMMENT '睡眠质量:好/一般/差',
    sleep_note      TEXT COMMENT '午休备注',
    
    -- 活动记录
    activity_type   VARCHAR(50) COMMENT '活动类型',
    activity_detail TEXT COMMENT '活动详情',
    activity_photos JSON COMMENT '活动照片',
    
    -- 健康记录
    temperature     DECIMAL(4,1) COMMENT '体温',
    health_status   VARCHAR(20) COMMENT '健康状况',
    health_note     TEXT COMMENT '健康备注',
    
    -- 总体评价
    overall_note    TEXT COMMENT '老师总评',
    publish_status  TINYINT DEFAULT 0 COMMENT '发布状态:0-草稿 1-已发布',
    publish_time    DATETIME COMMENT '发布时间',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_date (student_id, record_date),
    INDEX idx_class_date (class_id, record_date),
    INDEX idx_publish_status (publish_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日成长记录表';

-- 成长照片库
CREATE TABLE t_growth_photo (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    photo_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '照片ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    record_id       VARCHAR(32) COMMENT '关联记录ID',
    photo_type      VARCHAR(20) NOT NULL COMMENT '照片类型:课程/活动/饮食/合影/其他',
    photo_url       VARCHAR(500) NOT NULL COMMENT '照片OSS路径',
    thumbnail_url   VARCHAR(500) COMMENT '缩略图路径',
    photo_time      DATETIME COMMENT '照片拍摄时间',
    take_by         VARCHAR(32) COMMENT '拍摄人ID',
    watermark_url   VARCHAR(500) COMMENT '带水印版本',
    tags            JSON COMMENT '标签:["开心","户外"]',
    ai_tags         JSON COMMENT 'AI识别标签',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_record_id (record_id),
    INDEX idx_photo_type (photo_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长照片库';
```

#### 2.2.4 食谱与课程

```sql
-- 食谱表
CREATE TABLE t_food_recipe (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    recipe_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '食谱ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '园区ID',
    week_start      DATE NOT NULL COMMENT '食谱开始日期(周一)',
    monday          JSON COMMENT '周一食谱',
    tuesday         JSON COMMENT '周二食谱',
    wednesday       JSON COMMENT '周三食谱',
    thursday        JSON COMMENT '周四食谱',
    friday          JSON COMMENT '周五食谱',
    saturday        JSON COMMENT '周六食谱',
    sunday          JSON COMMENT '周日食谱',
    nutritionist    VARCHAR(50) COMMENT '营养师',
    publish_status  TINYINT DEFAULT 0 COMMENT '发布状态:0-草稿 1-已发布',
    publish_time    DATETIME COMMENT '发布时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_week (kg_id, week_start)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食谱表';

-- 课程表
CREATE TABLE t_course (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '课程ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '园区ID',
    course_name     VARCHAR(100) NOT NULL COMMENT '课程名称',
    course_type     VARCHAR(50) NOT NULL COMMENT '课程类别:艺术/科学/语言/运动/社会/健康',
    age_group       VARCHAR(20) COMMENT '适用年龄:小班/中班/大班',
    teacher_id      VARCHAR(32) COMMENT '授课老师',
    course_desc     TEXT COMMENT '课程简介',
    course_goal     TEXT COMMENT '课程目标',
    duration        INT COMMENT '课时长(分钟)',
    status          TINYINT DEFAULT 1 COMMENT '状态:0-停用 1-启用',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_course_type (course_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 班级课表
CREATE TABLE t_class_schedule (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id     VARCHAR(32) NOT NULL UNIQUE COMMENT '课表ID',
    class_id        VARCHAR(32) NOT NULL COMMENT '班级ID',
    week_day        TINYINT NOT NULL COMMENT '星期:1-7',
    time_slot       VARCHAR(20) NOT NULL COMMENT '时间段:上午/中午/下午',
    course_id       VARCHAR(32) NOT NULL COMMENT '课程ID',
    teacher_id      VARCHAR(32) COMMENT '授课老师',
    classroom       VARCHAR(50) COMMENT '教室',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_class_week (class_id, week_day, time_slot)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级课表';
```

#### 2.2.5 成长报告与AI

```sql
-- AI成长画像表
CREATE TABLE t_growth_profile (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id      VARCHAR(32) NOT NULL UNIQUE COMMENT '画像ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    profile_month   VARCHAR(7) NOT NULL COMMENT '所属月份:2026-03',
    
    -- 各维度评分(0-100)
    emotion_score   INT COMMENT '情绪稳定度评分',
    emotion_detail  JSON COMMENT '情绪分析详情',
    
    social_score    INT COMMENT '社交能力评分',
    social_detail   JSON COMMENT '社交分析详情',
    
    learning_score  INT COMMENT '学习能力评分',
    learning_detail JSON COMMENT '学习分析详情',
    
    sport_score     INT COMMENT '运动发展评分',
    sport_detail    JSON COMMENT '运动分析详情',
    
    diet_score      INT COMMENT '饮食健康评分',
    diet_detail     JSON COMMENT '饮食分析详情',
    
    -- 综合评价
    overall_score   INT COMMENT '综合评分',
    ai_analysis    TEXT COMMENT 'AI综合分析',
    suggestions    JSON COMMENT '针对性建议',
    warnings        JSON COMMENT '预警信息',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_month (student_id, profile_month),
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI成长画像表';

-- 月度成长报告
CREATE TABLE t_monthly_report (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '报告ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    report_month    VARCHAR(7) NOT NULL COMMENT '报告月份:2026-03',
    
    -- 基础统计
    attendance_days INT COMMENT '出勤天数',
    total_days      INT COMMENT '统计天数',
    
    -- 成长亮点
    highlights      JSON COMMENT '成长亮点(JSON数组)',
    
    -- 各维度发展
    dimension_data  JSON COMMENT '各维度发展数据',
    
    -- AI分析
    ai_summary      TEXT COMMENT 'AI分析总结',
    teacher_summary TEXT COMMENT '教师寄语',
    
    -- 照片精选
    featured_photos JSON COMMENT '精选照片(JSON数组)',
    
    -- 报告状态
    status          TINYINT DEFAULT 0 COMMENT '状态:0-生成中 1-待审核 2-已发布',
    publish_time    DATETIME COMMENT '发布时间',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_month (student_id, report_month),
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='月度成长报告表';

-- 成长视频表
CREATE TABLE t_growth_video (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '视频ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    video_type      VARCHAR(20) NOT NULL COMMENT '视频类型:月度/学期/学年/活动',
    video_month     VARCHAR(7) COMMENT '视频所属月份',
    
    video_url       VARCHAR(500) NOT NULL COMMENT '视频OSS路径',
    cover_url       VARCHAR(500) COMMENT '封面图',
    duration        INT COMMENT '视频时长(秒)',
    template_id     VARCHAR(32) COMMENT '使用模板ID',
    
    -- 生成状态
    gen_status      TINYINT DEFAULT 0 COMMENT '生成状态:0-排队中 1-生成中 2-已完成 3-失败',
    gen_progress    INT DEFAULT 0 COMMENT '生成进度(0-100)',
    error_msg       TEXT COMMENT '失败原因',
    
    -- 审核状态
    audit_status    TINYINT DEFAULT 0 COMMENT '审核状态:0-待审核 1-已通过 2-已拒绝',
    audit_time      DATETIME COMMENT '审核时间',
    
    view_count      INT DEFAULT 0 COMMENT '查看次数',
    download_count  INT DEFAULT 0 COMMENT '下载次数',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_gen_status (gen_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长视频表';
```

#### 2.2.6 通知与消息

```sql
-- 通知公告表
CREATE TABLE t_notice (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    notice_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '通知ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '园区ID',
    title           VARCHAR(200) NOT NULL COMMENT '通知标题',
    content         TEXT NOT NULL COMMENT '通知内容',
    notice_type     VARCHAR(20) NOT NULL COMMENT '通知类型:系统公告/活动通知/安全提醒',
    target_type     VARCHAR(20) NOT NULL COMMENT '发送范围:全体/班级/指定家长',
    target_ids      JSON COMMENT '指定目标IDs',
    attach_urls     JSON COMMENT '附件URLs',
    
    publish_by      VARCHAR(32) COMMENT '发布人',
    publish_time    DATETIME COMMENT '发布时间',
    expire_time     DATETIME COMMENT '过期时间',
    
    status          TINYINT DEFAULT 1 COMMENT '状态:0-已撤回 1-已发布',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_publish_time (publish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- 消息通知记录表
CREATE TABLE t_message_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    msg_id          VARCHAR(32) NOT NULL UNIQUE COMMENT '消息ID',
    msg_type        VARCHAR(20) NOT NULL COMMENT '消息类型:通知/成长记录/报告/视频',
    receiver_id     VARCHAR(32) NOT NULL COMMENT '接收者ID',
    title           VARCHAR(200) COMMENT '消息标题',
    content         TEXT COMMENT '消息内容',
    related_id      VARCHAR(32) COMMENT '关联业务ID',
    
    -- 推送状态
    push_status     TINYINT DEFAULT 0 COMMENT '推送状态:0-未推送 1-已推送 2-推送失败',
    push_time       DATETIME COMMENT '推送时间',
    read_status     TINYINT DEFAULT 0 COMMENT '阅读状态:0-未读 1-已读',
    read_time       DATETIME COMMENT '阅读时间',
    
    channel         VARCHAR(20) COMMENT '推送渠道:小程序/短信/微信',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_receiver (receiver_id, read_status),
    INDEX idx_push_status (push_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知记录表';
```

---

## 3. 接口设计

### 3.1 接口规范

**统一响应格式**:
```json
{
  "code": 200,
  "message": "success",
  "data": { },
  "timestamp": 1701234567890
}
```

**分页响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 100,
    "page": 1,
    "pageSize": 20
  }
}
```

**错误响应**:
```json
{
  "code": 400,
  "message": "参数错误",
  "data": null
}
```

### 3.2 核心接口列表

#### 3.2.1 用户模块

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/user/login | POST | 账号密码登录 |
| /api/user/login/wechat | POST | 微信授权登录 |
| /api/user/login/sms | POST | 手机号验证码登录 |
| /api/user/info | GET | 获取当前用户信息 |
| /api/user/logout | POST | 登出 |
| /api/user/child/list | GET | 家长获取孩子列表 |
| /api/user/child/detail | GET | 获取孩子详细信息 |

#### 3.2.2 学生管理

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/student/list | GET | 获取学生列表(分页) |
| /api/student/detail | GET | 获取学生详情 |
| /api/student/add | POST | 新增学生 |
| /api/student/update | PUT | 更新学生信息 |
| /api/student/delete | DELETE | 删除学生 |
| /api/student/transfer | POST | 转班操作 |
| /api/student/batch/import | POST | 批量导入学生 |

#### 3.2.3 班级管理

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/class/list | GET | 获取班级列表 |
| /api/class/detail | GET | 获取班级详情 |
| /api/class/students | GET | 获取班级学生列表 |
| /api/class/teachers | GET | 获取班级老师列表 |
| /api/class/add | POST | 新增班级 |
| /api/class/update | PUT | 更新班级信息 |

#### 3.2.4 成长记录

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/record/daily/list | GET | 获取每日记录列表 |
| /api/record/daily/detail | GET | 获取每日记录详情 |
| /api/record/daily/save | POST | 保存每日记录(草稿) |
| /api/record/daily/publish | POST | 发布每日记录 |
| /api/record/photo/upload | POST | 上传成长照片 |
| /api/record/photo/list | GET | 获取照片列表 |

#### 3.2.5 成长报告

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/growth/profile | GET | 获取AI成长画像 |
| /api/growth/report/monthly | GET | 获取月度成长报告 |
| /api/growth/report/generate | POST | 手动触发报告生成 |

#### 3.2.6 视频服务

| 接口 | 方法 |说明 |
|------|------|-----|
| /api/video/list | GET | 获取成长视频列表 |
| /api/video/detail | GET | 获取视频详情 |
| /api/video/generate | POST | 手动触发视频生成 |
| /api/video/download | GET | 获取下载链接 |
| /api/video/share | GET | 获取分享链接 |

---

## 4. 核心模块设计

### 4.1 用户模块

**模块职责**: 处理用户登录、认证、授权

**关键类设计**:

```
com.kgms.user.service.UserService
├── loginByPassword(username, password) - 账号密码登录
├── loginByWechat(code) - 微信授权登录
├── loginBySms(phone, code) - 手机验证码登录
├── getUserInfo(userId) - 获取用户信息
└── getChildren(parentId) - 获取家长关联的孩子

com.kgms.user.service.AuthService
├── generateToken(userId) - 生成JWT令牌
├── verifyToken(token) - 验证令牌
└── refreshToken(token) - 刷新令牌
```

### 4.2 成长记录模块

**模块职责**: 管理每日成长记录的创建、编辑、发布

**业务流程**:

```
老师发布记录流程:
1. 老师进入记录页面
2. 选择学生(可批量)
3. 选择记录维度(课程/情绪/饮食/活动等)
4. 填写记录内容(支持语音/AI辅助)
5. 上传相关照片
6. 保存草稿或直接发布
7. 系统推送通知给对应家长
```

**关键类设计**:

```
com.kgms.record.service.GrowthRecordService
├── saveRecord(recordDto) - 保存记录(草稿)
├── publishRecord(recordId) - 发布记录
├── getRecordList(studentId, date) - 获取记录列表
├── getRecordDetail(recordId) - 获取记录详情
└── batchSaveRecords(batchDto) - 批量保存

com.kgms.record.service.PhotoService
├── uploadPhoto(multipartFile) - 上传照片
├── addWatermark(photoId) - 添加水印
├── generateThumbnail(photoId) - 生成缩略图
└── recommendStudent(photoId) - AI推荐关联学生
```

### 4.3 AI分析模块

**模块职责**: AI成长画像分析、月度报告生成、视频生成

**业务流程**:

```
AI成长画像生成流程:
1. 定时任务(每日凌晨)扫描需要生成画像的学生
2. 拉取该学生本月的所有成长记录
3. 调用AI服务进行多维度分析
4. 生成各维度评分和详细分析
5. 保存画像数据
6. 推送通知给家长

月度报告生成流程:
1. 每月1号自动触发上月报告生成
2. 汇总学生出勤、成长记录、照片
3. 调用AI生成分析总结
4. 智能筛选本月精选照片
5. 生成PDF/H5报告
6. 标记待审核状态
7. 园长审核后发布给家长

AI视频生成流程:
1. 触发条件:定时任务/家长手动请求
2. 选取指定月份的照片素材
3. 调用视频AI服务生成视频
4. 添加模板特效和背景音乐
5. 生成视频封面
6. 保存并通知家长
```

**关键类设计**:

```
com.kgms.ai.service.GrowthProfileService
├── generateProfile(studentId, month) - 生成成长画像
├── analyzeEmotion(records) - 分析情绪
├── analyzeSocial(records) - 分析社交能力
├── analyzeLearning(records) - 分析学习能力
├── analyzeSport(records) - 分析运动发展
├── analyzeDiet(records) - 分析饮食习惯
└── generateSuggestions(profile) - 生成建议

com.kgms.ai.service.MonthlyReportService
├── generateReport(studentId, month) - 生成月度报告
├── extractHighlights(records) - 提取成长亮点
├── selectFeaturedPhotos(records, count) - 精选照片
└── generatePDF(reportId) - 生成PDF版本

com.kgms.ai.service.VideoService
├── generateVideo(studentId, month, templateId) - 生成视频
├── selectVideoPhotos(studentId, month, count) - 选取视频素材
├── callVideoAI(photos, template) - 调用视频AI
└── notify家长(videoId) - 通知家长
```

### 4.4 消息通知模块

**模块职责**: 统一消息推送、通知管理

**关键类设计**:

```
com.kgms.notice.service.NoticeService
├── publishNotice(noticeDto) - 发布通知
├── withdrawNotice(noticeId) - 撤回通知
├── getNoticeList(kgId, type) - 获取通知列表
└── getNoticeDetail(noticeId) - 获取通知详情

com.kgms.message.service.MessagePushService
├── pushToUser(userId, message) - 推送消息给用户
├── pushToClass(classId, message) - 推送消息给班级
├── pushToAll(kinderId, message) - 推送消息给全园
├── sendWechatTemplate(msg) - 发送微信模板消息
└── markAsRead(msgId) - 标记已读
```

---

## 5. 安全设计

### 5.1 认证授权

- 所有API接口需要通过JWT令牌认证
- 令牌有效期: 7天(可配置)
- 刷新令牌有效期: 30天
- 密码使用BCrypt加密存储
- 敏感操作(如删除)需要二次验证

### 5.2 数据安全

- 传输层全站HTTPS
- 身份证号等敏感信息脱敏存储
- 照片存储使用私有OSS桶
- 数据库定期备份
- 操作日志留存不少于180天

### 5.3 权限控制

- 基于RBAC的权限模型
- 数据权限: 家长只能看自己孩子，老师只能看班级学生，园长可看全园
- 功能权限: 按钮级控制
- 接口级鉴权: 每个接口验证权限

---

## 6. 部署架构

### 6.1 K8s部署

```yaml
# Deployment 示例
apiVersion: apps/v1
kind: Deployment
metadata:
  name: kgms-user-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: kgms-user-service
  template:
    spec:
      containers:
      - name: kgms-user-service
        image: kgms/user-service:latest
        ports:
        - containerPort: 8080
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: prod
        - name: NACOS_SERVER_ADDR
          valueFrom:
            configMapKeyRef:
              name: kgms-config
              key: nacos.addr
```

### 6.2 服务网格

- 使用Istio实现服务治理
- 限流: 每IP每分钟100次请求
- 熔断: 连续5次失败触发熔断
- 链路追踪: Jaeger

---

## 7. 附录

### 7.1 字典表

| 字典码 | 字典值 | 说明 |
|--------|--------|------|
| user_type | 1 | 家长 |
| user_type | 2 | 老师 |
| user_type | 3 | 园长 |
| user_type | 4 | 管理员 |
| gender | 1 | 男 |
| gender | 2 | 女 |
| record_status | 0 | 草稿 |
| record_status | 1 | 已发布 |
| video_type | monthly | 月度视频 |
| video_type | semester | 学期视频 |
| video_gen_status | 0 | 排队中 |
| video_gen_status | 1 | 生成中 |
| video_gen_status | 2 | 已完成 |
| video_gen_status | 3 | 失败 |

### 7.2 配置中心配置项

| 配置项 | 说明 | 示例值 |
|--------|------|--------|
| jwt.secret | JWT密钥 | kgms@2024#secret |
| jwt.expire | Token过期时间(秒) | 604800 |
| oss.endpoint | OSSEndpoint | oss-cn-shanghai.aliyuncs.com |
| oss.bucket | OSS桶名 | kgms-files |
| ai.video.template | 默认视频模板 | template_v1 |
| push.wechat.appid | 微信AppID | wx123456789 |

---

*本文档为开发设计初稿，具体实现细节待进一步确认*
