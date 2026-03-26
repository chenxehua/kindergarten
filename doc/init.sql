-- =====================================================
-- 智慧幼儿园成长管理系统 - 数据库初始化脚本
-- 版本: 1.0
-- 日期: 2026-03-26
-- =====================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS kindergarten DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kindergarten;

-- =====================================================
-- 用户模块
-- =====================================================
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '手机号',
    wx_openid VARCHAR(100) COMMENT '微信OpenID',
    role VARCHAR(20) NOT NULL DEFAULT 'PARENT' COMMENT '角色: PARENT/TEACHER/DIRECTOR',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS parent_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    parent_name VARCHAR(50) COMMENT '家长姓名',
    relation VARCHAR(20) COMMENT '与孩子关系',
    phone VARCHAR(20) COMMENT '联系电话',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家长信息表';

CREATE TABLE IF NOT EXISTS teacher_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    teacher_name VARCHAR(50) COMMENT '老师姓名',
    employee_no VARCHAR(20) COMMENT '工号',
    phone VARCHAR(20) COMMENT '联系电话',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师信息表';

-- =====================================================
-- 学生模块
-- =====================================================
CREATE TABLE IF NOT EXISTS student_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_no VARCHAR(30) NOT NULL UNIQUE COMMENT '学号',
    student_name VARCHAR(50) NOT NULL COMMENT '学生姓名',
    gender TINYINT COMMENT '性别: 0男 1女',
    birth_date DATE COMMENT '出生日期',
    class_id BIGINT COMMENT '班级ID',
    parent_id BIGINT COMMENT '家长ID',
    id_card VARCHAR(30) COMMENT '身份证号',
    allergy_ids VARCHAR(255) COMMENT '过敏原IDs',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0离园 1在园',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生信息表';

-- =====================================================
-- 班级模块
-- =====================================================
CREATE TABLE IF NOT EXISTS class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    class_name VARCHAR(50) NOT NULL COMMENT '班级名称',
    class_no VARCHAR(20) COMMENT '班级编号',
    grade VARCHAR(20) COMMENT '年级',
    capacity INT DEFAULT 30 COMMENT '容量',
    teacher_ids VARCHAR(255) COMMENT '老师IDs',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级信息表';

-- =====================================================
-- 成长记录模块
-- =====================================================
CREATE TABLE IF NOT EXISTS growth_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    record_id VARCHAR(50) NOT NULL UNIQUE COMMENT '记录ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    record_type VARCHAR(20) COMMENT '记录类型',
    title VARCHAR(100) COMMENT '标题',
    content TEXT COMMENT '内容',
    tags VARCHAR(255) COMMENT '标签',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0草稿 1已发布',
    create_by BIGINT COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长记录表';

CREATE TABLE IF NOT EXISTS growth_photo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    record_id BIGINT NOT NULL COMMENT '记录ID',
    photo_url VARCHAR(500) NOT NULL COMMENT '照片URL',
    photo_type VARCHAR(20) COMMENT '照片类型',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长照片表';

-- =====================================================
-- 通知模块
-- =====================================================
CREATE TABLE IF NOT EXISTS notice_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    notice_id VARCHAR(50) NOT NULL UNIQUE COMMENT '通知ID',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    notice_type VARCHAR(20) COMMENT '通知类型',
    target_type VARCHAR(20) COMMENT '发送范围: ALL/CLASS/PARENT',
    target_ids VARCHAR(500) COMMENT '目标IDs',
    publish_time DATETIME COMMENT '发布时间',
    publisher_id BIGINT COMMENT '发布人ID',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0草稿 1已发布 2已撤回',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知信息表';

-- =====================================================
-- 食谱模块
-- =====================================================
CREATE TABLE IF NOT EXISTS food_recipe (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    recipe_id VARCHAR(50) NOT NULL UNIQUE COMMENT '食谱ID',
    recipe_name VARCHAR(100) NOT NULL COMMENT '食谱名称',
    meal_type VARCHAR(20) COMMENT '餐型: BREAKFAST/LUNCH/DINNER/SNACK',
    recipe_date DATE COMMENT '日期',
    dishes JSON COMMENT '菜品JSON',
    calorie INT COMMENT '热量',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0草稿 1已发布',
    create_by BIGINT COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食谱信息表';

-- =====================================================
-- 课程模块
-- =====================================================
CREATE TABLE IF NOT EXISTS course_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_id VARCHAR(50) NOT NULL UNIQUE COMMENT '课程ID',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    course_type VARCHAR(20) COMMENT '课程类型',
    description TEXT COMMENT '课程描述',
    duration INT COMMENT '时长(分钟)',
    age_range VARCHAR(20) COMMENT '适龄范围',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程信息表';

CREATE TABLE IF NOT EXISTS class_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    schedule_id VARCHAR(50) NOT NULL UNIQUE COMMENT '课程安排ID',
    class_id BIGINT NOT NULL COMMENT '班级ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    weekday TINYINT COMMENT '星期: 1-7',
    start_time VARCHAR(10) COMMENT '开始时间',
    end_time VARCHAR(10) COMMENT '结束时间',
    teacher_id BIGINT COMMENT '老师ID',
    room VARCHAR(50) COMMENT '教室',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程安排表';

-- =====================================================
-- 视频模块
-- =====================================================
CREATE TABLE IF NOT EXISTS growth_video (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    video_id VARCHAR(50) NOT NULL UNIQUE COMMENT '视频ID',
    kg_id VARCHAR(50) COMMENT '幼儿园ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    video_type VARCHAR(20) COMMENT '视频类型',
    video_month VARCHAR(10) COMMENT '视频月份',
    video_url VARCHAR(500) COMMENT '视频URL',
    cover_url VARCHAR(500) COMMENT '封面URL',
    duration INT COMMENT '时长(秒)',
    template_id VARCHAR(50) COMMENT '模板ID',
    gen_status TINYINT DEFAULT 0 COMMENT '生成状态: 0待生成 1生成中 2已完成 3失败',
    gen_progress TINYINT DEFAULT 0 COMMENT '生成进度',
    error_msg TEXT COMMENT '错误信息',
    audit_status TINYINT DEFAULT 0 COMMENT '审核状态: 0待审核 1通过 2拒绝',
    audit_time DATETIME COMMENT '审核时间',
    view_count INT DEFAULT 0 COMMENT '播放次数',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长视频表';

-- =====================================================
-- 成长模块
-- =====================================================
CREATE TABLE IF NOT EXISTS growth_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    profile_id VARCHAR(50) NOT NULL UNIQUE COMMENT '画像ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    profile_month VARCHAR(10) NOT NULL COMMENT '月份',
    emotion_score INT COMMENT '情感得分',
    emotion_detail TEXT COMMENT '情感详情',
    social_score INT COMMENT '社交得分',
    social_detail TEXT COMMENT '社交详情',
    learning_score INT COMMENT '学习得分',
    learning_detail TEXT COMMENT '学习详情',
    sport_score INT COMMENT '运动得分',
    sport_detail TEXT COMMENT '运动详情',
    diet_score INT COMMENT '饮食得分',
    diet_detail TEXT COMMENT '饮食详情',
    overall_score INT COMMENT '综合得分',
    ai_analysis TEXT COMMENT 'AI分析',
    suggestions TEXT COMMENT '建议',
    warnings TEXT COMMENT '预警',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长画像表';

CREATE TABLE IF NOT EXISTS monthly_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    report_id VARCHAR(50) NOT NULL UNIQUE COMMENT '报告ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    report_month VARCHAR(10) NOT NULL COMMENT '月份',
    summary TEXT COMMENT '月度总结',
    highlights TEXT COMMENT '精彩瞬间',
    concerns TEXT COMMENT '关注点',
    status TINYINT DEFAULT 0 COMMENT '状态: 0生成中 1已完成',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='月度报告表';

-- =====================================================
-- 初始化测试数据
-- =====================================================
INSERT INTO sys_user (username, password, phone, role, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138000', 'DIRECTOR', 1),
('teacher1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138001', 'TEACHER', 1),
('parent1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138002', 'PARENT', 1);

INSERT INTO class_info (class_name, class_no, grade, capacity, status) VALUES 
('小一班', 'C2026001', '小班', 25, 1),
('中一班', 'C2025001', '中班', 30, 1),
('大一班', 'C2024001', '大班', 35, 1);

INSERT INTO student_info (student_no, student_name, gender, birth_date, class_id, status) VALUES 
('S202600001', '张三', 0, '2020-09-01', 1, 1),
('S202600002', '李四', 1, '2020-08-15', 1, 1),
('S202500001', '王五', 0, '2019-09-01', 2, 1);
