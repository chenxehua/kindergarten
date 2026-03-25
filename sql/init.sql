-- =============================================
-- 智慧幼儿园成长管理系统 - 数据库初始化脚本
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS kgms DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kgms;

-- =============================================
-- 用户与权限模块
-- =============================================

-- 用户表
CREATE TABLE IF NOT EXISTS t_sys_user (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id         VARCHAR(32) NOT NULL UNIQUE COMMENT '用户ID(UUID)',
    user_type       TINYINT NOT NULL DEFAULT 1 COMMENT '用户类型: 1-家长 2-老师 3-园长 4-管理员',
    username        VARCHAR(50) COMMENT '用户名(手机号)',
    password        VARCHAR(128) COMMENT '密码(MD5加密)',
    nickname        VARCHAR(50) COMMENT '昵称',
    avatar          VARCHAR(255) COMMENT '头像URL',
    phone           VARCHAR(20) COMMENT '手机号',
    wechat_openid   VARCHAR(64) COMMENT '微信OpenID',
    wechat_unionid  VARCHAR(64) COMMENT '微信UnionID',
    status          TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-正常',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT DEFAULT 0 COMMENT '删除标记: 0-未删除 1-已删除',
    INDEX idx_phone (phone),
    INDEX idx_wechat_openid (wechat_openid),
    INDEX idx_user_type (user_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 老师信息扩展表
CREATE TABLE IF NOT EXISTS t_teacher_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL COMMENT '关联用户ID',
    teacher_no      VARCHAR(20) NOT NULL COMMENT '工号',
    position        VARCHAR(50) COMMENT '职位: 主班/副班/保育员/保健医',
    department      VARCHAR(50) COMMENT '所属部门',
    hire_date       DATE COMMENT '入职日期',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_id (user_id),
    INDEX idx_teacher_no (teacher_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师信息表';

-- 家长信息扩展表
CREATE TABLE IF NOT EXISTS t_parent_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(32) NOT NULL COMMENT '关联用户ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '关联学生ID',
    relation_type   VARCHAR(20) NOT NULL COMMENT '关系: 爸爸/妈妈/爷爷/奶奶/其他',
    is_emergency    TINYINT DEFAULT 0 COMMENT '是否紧急联系人: 0-否 1-是',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家长信息表';

-- =============================================
-- 组织架构模块
-- =============================================

-- 园区/幼儿园表
CREATE TABLE IF NOT EXISTS t_org_kindergarten (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    kg_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '园区ID',
    kg_name         VARCHAR(100) NOT NULL COMMENT '园区名称',
    kg_address      VARCHAR(255) COMMENT '园区地址',
    kg_phone        VARCHAR(20) COMMENT '联系电话',
    logo_url        VARCHAR(255) COMMENT 'Logo',
    principal_id    VARCHAR(32) COMMENT '园长用户ID',
    status          TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-正常',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='幼儿园/园区表';

-- 班级表
CREATE TABLE IF NOT EXISTS t_class_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '班级ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '所属园区ID',
    class_name      VARCHAR(50) NOT NULL COMMENT '班级名称: 星星班/阳光班',
    grade           VARCHAR(20) NOT NULL COMMENT '年级: 小班/中班/大班',
    head_teacher_id VARCHAR(32) COMMENT '班主任用户ID',
    capacity        INT DEFAULT 30 COMMENT '容量',
    student_count   INT DEFAULT 0 COMMENT '当前人数',
    status          TINYINT DEFAULT 1 COMMENT '状态: 0-结班 1-在读',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_grade (grade)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 学生信息表
CREATE TABLE IF NOT EXISTS t_student_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id      VARCHAR(32) NOT NULL UNIQUE COMMENT '学生ID',
    student_name    VARCHAR(50) NOT NULL COMMENT '学生姓名',
    gender          TINYINT NOT NULL COMMENT '性别: 1-男 2-女',
    birthday        DATE COMMENT '出生日期',
    id_card         VARCHAR(20) COMMENT '身份证号',
    avatar          VARCHAR(255) COMMENT '头像URL',
    class_id        VARCHAR(32) COMMENT '当前班级ID',
    enroll_date     DATE COMMENT '入园日期',
    status          TINYINT DEFAULT 1 COMMENT '状态: 0-离园 1-在园 2-休学',
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

-- =============================================
-- 成长记录模块
-- =============================================

-- 每日成长记录表
CREATE TABLE IF NOT EXISTS t_growth_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '记录ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    record_date     DATE NOT NULL COMMENT '记录日期',
    teacher_id      VARCHAR(32) NOT NULL COMMENT '记录老师ID',
    class_id        VARCHAR(32) NOT NULL COMMENT '班级ID',
    
    -- 课程记录
    course_record   TEXT COMMENT '课程学习记录(JSON)',
    course_photos   JSON COMMENT '课程相关照片(JSON数组)',
    
    -- 情绪记录
    emotion_type    VARCHAR(20) COMMENT '情绪类型: 开心/平静/低落/哭闹',
    emotion_detail  TEXT COMMENT '情绪详情',
    
    -- 饮食记录
    breakfast       VARCHAR(200) COMMENT '早餐情况',
    lunch           VARCHAR(200) COMMENT '午餐情况',
    dinner          VARCHAR(200) COMMENT '晚餐情况',
    snack           VARCHAR(200) COMMENT '点心情况',
    food_photos     JSON COMMENT '饮食照片',
    allergy_flag    TINYINT DEFAULT 0 COMMENT '是否有过敏反应: 0-否 1-是',
    allergy_detail  TEXT COMMENT '过敏详情',
    
    -- 午休记录
    sleep_time      VARCHAR(50) COMMENT '入睡时间',
    sleep_quality   VARCHAR(20) COMMENT '睡眠质量: 好/一般/差',
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
    publish_status  TINYINT DEFAULT 0 COMMENT '发布状态: 0-草稿 1-已发布',
    publish_time    DATETIME COMMENT '发布时间',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_date (student_id, record_date),
    INDEX idx_class_date (class_id, record_date),
    INDEX idx_publish_status (publish_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日成长记录表';

-- 成长照片库
CREATE TABLE IF NOT EXISTS t_growth_photo (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    photo_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '照片ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    record_id       VARCHAR(32) COMMENT '关联记录ID',
    photo_type      VARCHAR(20) NOT NULL COMMENT '照片类型: 课程/活动/饮食/合影/其他',
    photo_url       VARCHAR(500) NOT NULL COMMENT '照片OSS路径',
    thumbnail_url   VARCHAR(500) COMMENT '缩略图路径',
    photo_time      DATETIME COMMENT '照片拍摄时间',
    take_by         VARCHAR(32) COMMENT '拍摄人ID',
    watermark_url   VARCHAR(500) COMMENT '带水印版本',
    tags            JSON COMMENT '标签: ["开心","户外"]',
    ai_tags         JSON COMMENT 'AI识别标签',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_record_id (record_id),
    INDEX idx_photo_type (photo_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长照片库';

-- =============================================
-- 食谱与课程模块
-- =============================================

-- 食谱表
CREATE TABLE IF NOT EXISTS t_food_recipe (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    recipe_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '食谱ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '园区ID',
    week_start      DATE NOT NULL COMMENT '食谱开始日期(周一)',
    monday          TEXT COMMENT '周一食谱(JSON)',
    tuesday         TEXT COMMENT '周二食谱(JSON)',
    wednesday       TEXT COMMENT '周三食谱(JSON)',
    thursday        TEXT COMMENT '周四食谱(JSON)',
    friday          TEXT COMMENT '周五食谱(JSON)',
    saturday        TEXT COMMENT '周六食谱(JSON)',
    sunday          TEXT COMMENT '周日食谱(JSON)',
    nutritionist    VARCHAR(50) COMMENT '营养师',
    publish_status  TINYINT DEFAULT 0 COMMENT '发布状态: 0-草稿 1-已发布',
    publish_time    DATETIME COMMENT '发布时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_week (kg_id, week_start)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食谱表';

-- 课程表
CREATE TABLE IF NOT EXISTS t_course (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '课程ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '园区ID',
    course_name     VARCHAR(100) NOT NULL COMMENT '课程名称',
    course_type     VARCHAR(50) NOT NULL COMMENT '课程类别: 艺术/科学/语言/运动/社会/健康',
    age_group       VARCHAR(20) COMMENT '适用年龄: 小班/中班/大班',
    teacher_id      VARCHAR(32) COMMENT '授课老师',
    course_desc     TEXT COMMENT '课程简介',
    course_goal     TEXT COMMENT '课程目标',
    duration        INT COMMENT '课时长(分钟)',
    status          TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-启用',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_course_type (course_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 班级课表
CREATE TABLE IF NOT EXISTS t_class_schedule (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id     VARCHAR(32) NOT NULL UNIQUE COMMENT '课表ID',
    class_id        VARCHAR(32) NOT NULL COMMENT '班级ID',
    week_day        TINYINT NOT NULL COMMENT '星期: 1-7',
    time_slot       VARCHAR(20) NOT NULL COMMENT '时间段: 上午/中午/下午',
    course_id       VARCHAR(32) NOT NULL COMMENT '课程ID',
    teacher_id      VARCHAR(32) COMMENT '授课老师',
    classroom       VARCHAR(50) COMMENT '教室',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_class_week (class_id, week_day, time_slot)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级课表';

-- =============================================
-- AI成长与报告模块
-- =============================================

-- AI成长画像表
CREATE TABLE IF NOT EXISTS t_growth_profile (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id      VARCHAR(32) NOT NULL UNIQUE COMMENT '画像ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    profile_month   VARCHAR(7) NOT NULL COMMENT '所属月份: 2026-03',
    
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
CREATE TABLE IF NOT EXISTS t_monthly_report (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '报告ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    report_month    VARCHAR(7) NOT NULL COMMENT '报告月份: 2026-03',
    
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
    status          TINYINT DEFAULT 0 COMMENT '状态: 0-生成中 1-待审核 2-已发布',
    publish_time    DATETIME COMMENT '发布时间',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_month (student_id, report_month),
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='月度成长报告表';

-- 成长视频表
CREATE TABLE IF NOT EXISTS t_growth_video (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '视频ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '学生ID',
    video_type      VARCHAR(20) NOT NULL COMMENT '视频类型: monthly/学期/学年/活动',
    video_month     VARCHAR(7) COMMENT '视频所属月份',
    
    video_url       VARCHAR(500) NOT NULL COMMENT '视频OSS路径',
    cover_url       VARCHAR(500) COMMENT '封面图',
    duration        INT COMMENT '视频时长(秒)',
    template_id     VARCHAR(32) COMMENT '使用模板ID',
    
    -- 生成状态
    gen_status      TINYINT DEFAULT 0 COMMENT '生成状态: 0-排队中 1-生成中 2-已完成 3-失败',
    gen_progress    INT DEFAULT 0 COMMENT '生成进度(0-100)',
    error_msg       TEXT COMMENT '失败原因',
    
    -- 审核状态
    audit_status    TINYINT DEFAULT 0 COMMENT '审核状态: 0-待审核 1-已通过 2-已拒绝',
    audit_time      DATETIME COMMENT '审核时间',
    
    view_count      INT DEFAULT 0 COMMENT '查看次数',
    download_count  INT DEFAULT 0 COMMENT '下载次数',
    
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_gen_status (gen_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长视频表';

-- =============================================
-- 通知与消息模块
-- =============================================

-- 通知公告表
CREATE TABLE IF NOT EXISTS t_notice (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    notice_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '通知ID',
    kg_id           VARCHAR(32) NOT NULL COMMENT '园区ID',
    title           VARCHAR(200) NOT NULL COMMENT '通知标题',
    content         TEXT NOT NULL COMMENT '通知内容',
    notice_type     VARCHAR(20) NOT NULL COMMENT '通知类型: 系统公告/活动通知/安全提醒',
    target_type     VARCHAR(20) NOT NULL COMMENT '发送范围: 全体/班级/指定家长',
    target_ids      JSON COMMENT '指定目标IDs',
    attach_urls     JSON COMMENT '附件URLs',
    
    publish_by      VARCHAR(32) COMMENT '发布人',
    publish_time    DATETIME COMMENT '发布时间',
    expire_time     DATETIME COMMENT '过期时间',
    
    status          TINYINT DEFAULT 1 COMMENT '状态: 0-已撤回 1-已发布',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_publish_time (publish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- 消息通知记录表
CREATE TABLE IF NOT EXISTS t_message_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    msg_id          VARCHAR(32) NOT NULL UNIQUE COMMENT '消息ID',
    msg_type        VARCHAR(20) NOT NULL COMMENT '消息类型: 通知/成长记录/报告/视频',
    receiver_id     VARCHAR(32) NOT NULL COMMENT '接收者ID',
    title           VARCHAR(200) COMMENT '消息标题',
    content         TEXT COMMENT '消息内容',
    related_id      VARCHAR(32) COMMENT '关联业务ID',
    
    -- 推送状态
    push_status     TINYINT DEFAULT 0 COMMENT '推送状态: 0-未推送 1-已推送 2-推送失败',
    push_time       DATETIME COMMENT '推送时间',
    read_status     TINYINT DEFAULT 0 COMMENT '阅读状态: 0-未读 1-已读',
    read_time       DATETIME COMMENT '阅读时间',
    
    channel         VARCHAR(20) COMMENT '推送渠道: 小程序/短信/微信',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_receiver (receiver_id, read_status),
    INDEX idx_push_status (push_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知记录表';

-- =============================================
-- 初始化测试数据
-- =============================================

-- 插入测试园区
INSERT INTO t_org_kindergarten (kg_id, kg_name, kg_address, kg_phone, status) 
VALUES ('kg_001', '阳光幼儿园', '北京市朝阳区阳光路88号', '010-12345678', 1);

-- 插入测试班级
INSERT INTO t_class_info (class_id, kg_id, class_name, grade, capacity, status) VALUES
('class_001', 'kg_001', '星星班', '小班', 25, 1),
('class_002', 'kg_001', '月亮班', '中班', 30, 1),
('class_003', 'kg_001', '太阳班', '大班', 35, 1);

-- 插入测试学生
INSERT INTO t_student_info (student_id, student_name, gender, birthday, class_id, enroll_date, status) VALUES
('stu_001', '张小明', 1, '2020-03-15', 'class_001', '2023-09-01', 1),
('stu_002', '李小红', 2, '2020-05-20', 'class_001', '2023-09-01', 1),
('stu_003', '王小刚', 1, '2019-08-10', 'class_002', '2022-09-01', 1),
('stu_004', '赵小芳', 2, '2019-11-25', 'class_003', '2021-09-01', 1);

-- 插入测试用户 (密码: 123456)
INSERT INTO t_sys_user (user_id, user_type, username, password, nickname, phone, status) VALUES
('user_teacher_001', 2, 'teacher001', 'e10adc3949ba59abbe56e057f20f883e', '张老师', '13800000001', 1),
('user_teacher_002', 2, 'teacher002', 'e10adc3949ba59abbe56e057f20f883e', '李老师', '13800000002', 1),
('user_parent_001', 1, 'parent001', 'e10adc3949ba59abbe56e057f20f883e', '张爸爸', '13900000001', 1),
('user_parent_002', 1, 'parent002', 'e10adc3949ba59abbe56e057f20f883e', '李妈妈', '13900000002', 1),
('user_principal_001', 3, 'principal001', 'e10adc3949ba59abbe56e057f20f883e', '园长', '13700000001', 1);

-- 插入老师信息
INSERT INTO t_teacher_info (user_id, teacher_no, position, department) VALUES
('user_teacher_001', 'T001', '主班', '教学部'),
('user_teacher_002', 'T002', '副班', '教学部');

-- 插入家长信息
INSERT INTO t_parent_info (user_id, student_id, relation_type, is_emergency) VALUES
('user_parent_001', 'stu_001', '爸爸', 1),
('user_parent_002', 'stu_002', '妈妈', 1);

-- 插入测试课程
INSERT INTO t_course (course_id, kg_id, course_name, course_type, age_group, duration, status) VALUES
('course_001', 'kg_001', '创意美术', '艺术', '小班', 30, 1),
('course_002', 'kg_001', '科学探索', '科学', '中班', 35, 1),
('course_003', 'kg_001', '趣味数学', '语言', '大班', 40, 1);

-- 插入示例食谱
INSERT INTO t_food_recipe (recipe_id, kg_id, week_start, monday, tuesday, nutritionist, publish_status) VALUES
('recipe_001', 'kg_001', '2026-03-30', 
'{"breakfast":"牛奶+面包","lunch":"红烧肉+米饭+蔬菜汤","dinner":"粥+小菜"}',
'{"breakfast":"豆浆+油条","lunch":"鸡腿+面条+水果","dinner":"饺子"}',
'营养师张老师', 1);

-- 插入示例通知
INSERT INTO t_notice (notice_id, kg_id, title, content, notice_type, target_type, publish_by, publish_time, status) VALUES
('notice_001', 'kg_001', '清明节放假通知', '清明节放假3天，请家长做好安排。', '系统公告', '全体', 'user_principal_001', NOW(), 1);

-- 插入示例成长记录
INSERT INTO t_growth_record (record_id, student_id, record_date, teacher_id, class_id, emotion_type, breakfast, lunch, overall_note, publish_status, publish_time) VALUES
('record_001', 'stu_001', '2026-03-25', 'user_teacher_001', 'class_001', '开心', '食欲良好', '食欲良好', '今天表现很好，积极参与活动。', 1, NOW());

SELECT '数据库初始化完成！' AS result;
