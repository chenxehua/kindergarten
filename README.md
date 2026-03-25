# 智慧幼儿园成长管理系统

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Cloud-2023.0-brightgreen" alt="Spring Cloud">
  <img src="https://img.shields.io/badge/Vue-3-brightgreen" alt="Vue 3">
  <img src="https://img.shields.io/badge/UniApp-2-brightgreen" alt="UniApp">
  <img src="https://img.shields.io/badge/License-MIT-blue" alt="License">
</p>

## 📚 项目介绍

智慧幼儿园成长管理系统（KGMS）是一套面向幼儿园的数字化管理平台，旨在实现幼儿园日常管理的智能化，帮助老师高效记录学生在校情况，同时为家长提供孩子成长的实时了解渠道。

### 核心功能

- 👨‍👩‍👧‍👦 **用户管理** - 家长、老师、园长多角色登录
- 📝 **成长记录** - 每日课程、情绪、饮食、活动记录
- 📸 **照片管理** - 成长照片上传、智能分类
- 🤖 **AI成长画像** - 多维度智能分析
- 📊 **月度报告** - 自动生成成长报告
- 🎬 **AI视频** - 自动生成成长视频
- 🍚 **食谱管理** - 每周营养食谱
- 📚 **课程管理** - 课程表编排
- 📢 **通知公告** - 家园沟通

## 🏗️ 技术架构

### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| Spring Boot | 3.2.0 | 基础框架 |
| Spring Cloud | 2023.0 | 微服务架构 |
| Nacos | 2.x | 注册中心/配置中心 |
| MyBatis Plus | 3.5.5 | ORM框架 |
| MySQL | 8.0 | 关系型数据库 |
| Redis | 7.x | 缓存/会话 |
| JWT | 0.12.3 | 认证授权 |

### 前端技术栈

| 技术 | 说明 |
|------|------|
| UniApp | 小程序跨平台开发 |
| Vue 3 | PC端开发框架 |
| Element Plus | PC端UI组件库 |
| Vite | 构建工具 |

### 系统架构图

```
┌─────────────────────────────────────────────────────────────────┐
│                      微信小程序 / PC浏览器                        │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Spring Cloud Gateway (8080)               │
└─────────────────────────────────────────────────────────────────┘
                                │
        ┌───────────┬───────────┬───────────┬───────────┐
        ▼           ▼           ▼           ▼           ▼
   ┌────────┐  ┌────────┐  ┌────────┐  ┌────────┐  ┌────────┐
   │ 用户服务 │  │学生服务 │  │班级服务 │  │记录服务 │  │通知服务 │
   │ 8081   │  │ 8082   │  │ 8083   │  │ 8084   │  │ 8089   │
   └────────┘  └────────┘  └────────┘  └────────┘  └────────┘
        │           │           │           │           │
        └───────────┴───────────┴───────────┴───────────┘
                                │
        ┌───────────────────────┴───────────────────────┐
        ▼                                               ▼
   ┌─────────┐                                    ┌─────────┐
   │  MySQL  │                                    │  Redis  │
   └─────────┘                                    └─────────┘
```

## 📁 项目结构

```
kindergarten/
├── kgms-common/              # 公共模块
├── kgms-user/                # 用户服务 (8081)
├── kgms-student/             # 学生服务 (8082)
├── kgms-class/               # 班级服务 (8083)
├── kgms-record/              # 成长记录服务 (8084)
├── kgms-food/                # 食谱服务 (8085)
├── kgms-course/              # 课程服务 (8086)
├── kgms-growth/              # AI成长服务 (8087)
├── kgms-video/               # 视频服务 (8088)
├── kgms-notice/             # 通知服务 (8089)
├── kgms-gateway/            # 网关服务 (8080)
├── kgms-miniapp/            # 小程序前端 (UniApp)
├── kgms-web-pc/             # PC管理后台 (Vue3)
├── sql/                     # 数据库脚本
└── doc/                     # 项目文档
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.x
- Node.js 18+ (前端)

### 后端启动

1. **创建数据库**

```bash
mysql -u root -p < sql/init.sql
```

2. **配置Nacos**

启动Nacos服务（默认8848端口）

3. **编译项目**

```bash
mvn clean package -DskipTests
```

4. **启动服务**

```bash
# 启动网关
java -jar kgms-gateway/target/kgms-gateway.jar

# 启动各个微服务
java -jar kgms-user/target/kgms-user.jar
java -jar kgms-student/target/kgms-student.jar
# ... 其他服务
```

### 前端启动

**小程序**

```bash
cd kgms-miniapp
# 使用HBuilderX打开运行
```

**PC管理后台**

```bash
cd kgms-web-pc
npm install
npm run dev
```

## 📖 接口文档

### 用户模块

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/user/login` | POST | 账号密码登录 |
| `/api/user/login/wechat` | POST | 微信授权登录 |
| `/api/user/info` | GET | 获取用户信息 |

### 学生模块

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/student/list` | GET | 学生列表 |
| `/api/student/add` | POST | 新增学生 |
| `/api/student/update` | PUT | 更新学生 |
| `/api/student/delete` | DELETE | 删除学生 |

### 成长记录

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/record/save` | POST | 保存记录 |
| `/api/record/publish` | POST | 发布记录 |
| `/api/record/list` | GET | 记录列表 |

### 完整接口列表见 [接口设计文档](doc/kindergarten-dev-design.md)

## 📊 数据库表

| 表名 | 说明 |
|------|------|
| t_sys_user | 用户表 |
| t_student_info | 学生信息表 |
| t_class_info | 班级表 |
| t_growth_record | 成长记录表 |
| t_growth_photo | 成长照片表 |
| t_food_recipe | 食谱表 |
| t_course | 课程表 |
| t_growth_profile | AI成长画像表 |
| t_monthly_report | 月度报告表 |
| t_growth_video | 成长视频表 |
| t_notice | 通知公告表 |

## 🔧 配置说明

### 微服务配置

各微服务的配置文件位于 `src/main/resources/application.yml`

主要配置项：
- `spring.cloud.nacos.discovery.server-addr` Nacos地址
- `spring.datasource.url` 数据库连接
- `spring.data.redis.host` Redis地址

### JWT配置

- 默认Token有效期：7天
- 刷新Token有效期：30天
- 密钥：kgms@2024#kindergarten#growth#management#system

## 📝 开发指南

### 代码规范

- 遵循阿里巴巴Java开发规范
- 使用Lombok简化代码
- 所有API返回统一Result格式

### 分支管理

- `main` - 主分支
- `feature/*` - 功能分支
- `bugfix/*` - 修复分支

## 🤝 贡献指南

欢迎提交Issue和Pull Request！

## 📄 许可证

MIT License

---

<p align="center">Made with ❤️ by 智慧幼儿园团队</p>
