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
    teacher_id      VARCHAR(32) NOT NULL COMMENT '老师ID',
    user_id         VARCHAR(32) NOT NULL COMMENT '关联用户ID',
    class_id        VARCHAR(32) COMMENT '所属班级ID',
    teacher_no      VARCHAR(20) NOT NULL COMMENT '工号',
    teacher_name    VARCHAR(50) COMMENT '老师姓名',
    phone           VARCHAR(20) COMMENT '手机号',
    position        VARCHAR(50) COMMENT '职位: 主班/副班/保育员/保健医',
    department      VARCHAR(50) COMMENT '所属部门',
    hire_date       DATE COMMENT '入职日期',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_teacher_id (teacher_id),
    UNIQUE KEY uk_user_id (user_id),
    INDEX idx_teacher_no (teacher_no),
    INDEX idx_class_id (class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师信息表';

-- 家长信息扩展表
CREATE TABLE IF NOT EXISTS t_parent_info (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id       VARCHAR(32) NOT NULL COMMENT '家长ID',
    user_id         VARCHAR(32) NOT NULL COMMENT '关联用户ID',
    student_id      VARCHAR(32) NOT NULL COMMENT '关联学生ID',
    parent_name     VARCHAR(50) COMMENT '家长姓名',
    phone           VARCHAR(20) COMMENT '手机号',
    relation_type   VARCHAR(20) NOT NULL COMMENT '关系: 爸爸/妈妈/爷爷/奶奶/其他',
    is_emergency    TINYINT DEFAULT 0 COMMENT '是否紧急联系人: 0-否 1-是',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_parent_id (parent_id),
    INDEX idx_student_id (student_id),
    INDEX idx_user_id (user_id)
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
INSERT INTO t_teacher_info (teacher_id, user_id, class_id, teacher_no, teacher_name, phone, position, department) VALUES
('teacher_001', 'user_teacher_001', 'class_001', 'T001', '张老师', '13800000001', '主班', '教学部'),
('teacher_002', 'user_teacher_002', 'class_002', 'T002', '李老师', '13800000002', '副班', '教学部');

-- 插入家长信息
INSERT INTO t_parent_info (parent_id, user_id, student_id, parent_name, phone, relation_type, is_emergency) VALUES
('parent_001', 'user_parent_001', 'stu_001', '张爸爸', '13900000001', '爸爸', 1),
('parent_002', 'user_parent_002', 'stu_002', '李妈妈', '13900000002', '妈妈', 1);

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

-- =============================================
-- 考勤管理模块
-- =============================================

-- 考勤记录表
CREATE TABLE IF NOT EXISTS t_attendance (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    attendance_id       VARCHAR(32) NOT NULL UNIQUE COMMENT '考勤ID',
    student_id          VARCHAR(32) NOT NULL COMMENT '学生ID',
    class_id            VARCHAR(32) NOT NULL COMMENT '班级ID',
    kg_id               VARCHAR(32) NOT NULL COMMENT '园区ID',
    attendance_date     DATE NOT NULL COMMENT '考勤日期',

    -- 签到签退
    check_in_time       TIME COMMENT '签到时间',
    check_in_photo      VARCHAR(500) COMMENT '签到照片',
    check_out_time      TIME COMMENT '签退时间',
    check_out_photo     VARCHAR(500) COMMENT '签退照片',

    -- 考勤状态
    status              VARCHAR(20) NOT NULL DEFAULT 'NORMAL' COMMENT '状态: NORMAL-正常 LATE-迟到 ABSENT-缺勤 LEAVE-请假',
    leave_type          VARCHAR(20) COMMENT '请假类型: SICK-病假 PERSONAL-事假',
    leave_reason        TEXT COMMENT '请假原因',

    -- 接送信息
    pickup_person       VARCHAR(50) COMMENT '接园人',
    pickup_relation     VARCHAR(20) COMMENT '与学生关系',

    -- 审批信息
    apply_by            VARCHAR(32) COMMENT '申请人',
    approve_by          VARCHAR(32) COMMENT '审批人',
    approve_time        DATETIME COMMENT '审批时间',
    approve_status      VARCHAR(20) DEFAULT 'APPROVED' COMMENT '审批状态: PENDING-待审批 APPROVED-已批准 REJECTED-已拒绝',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_date (student_id, attendance_date),
    INDEX idx_class_date (class_id, attendance_date),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤记录表';

-- 插入示例考勤记录
INSERT INTO t_attendance (attendance_id, student_id, class_id, kg_id, attendance_date, check_in_time, check_out_time, status) VALUES
('att_001', 'stu_001', 'class_001', 'kg_001', '2026-03-26', '08:00:00', '16:30:00', 'NORMAL'),
('att_002', 'stu_002', 'class_001', 'kg_001', '2026-03-26', '08:05:00', '16:30:00', 'NORMAL'),
('att_003', 'stu_003', 'class_002', 'kg_001', '2026-03-26', '08:00:00', '16:30:00', 'NORMAL'),
('att_004', 'stu_004', 'class_002', 'kg_001', '2026-03-26', NULL, NULL, 'LEAVE');

-- 老师考勤表
CREATE TABLE IF NOT EXISTS t_teacher_attendance (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '考勤记录ID',
    teacher_id          VARCHAR(32) NOT NULL COMMENT '老师ID',
    attendance_date     DATE NOT NULL COMMENT '考勤日期',

    -- 签到签退
    check_in_time       TIME COMMENT '签到时间',
    check_in_location   VARCHAR(100) COMMENT '签到位置',
    check_out_time      TIME COMMENT '签退时间',
    check_out_location  VARCHAR(100) COMMENT '签退位置',

    -- 考勤状态
    status              VARCHAR(20) NOT NULL DEFAULT 'NORMAL' COMMENT '状态: NORMAL-正常 LATE-迟到 ABSENT-缺勤 LEAVE-请假',
    leave_type          VARCHAR(20) COMMENT '请假类型',
    leave_reason        TEXT COMMENT '请假原因',
    leave_proof         VARCHAR(500) COMMENT '请假证明材料',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_teacher_date (teacher_id, attendance_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师考勤表';

-- =============================================
-- 数据看板模块
-- =============================================

-- 看板数据快照表（每日定时生成）
CREATE TABLE IF NOT EXISTS t_dashboard_snapshot (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    snapshot_id         VARCHAR(32) NOT NULL UNIQUE COMMENT '快照ID',
    kg_id               VARCHAR(32) NOT NULL COMMENT '园区ID',
    snapshot_date       DATE NOT NULL COMMENT '快照日期',
    snapshot_type       VARCHAR(20) NOT NULL COMMENT '快照类型: DAILY-每日 WEEKLY-每周 MONTHLY-每月',

    -- 学生统计数据
    total_students      INT DEFAULT 0 COMMENT '在园学生总数',
    new_students        INT DEFAULT 0 COMMENT '本月新增学生',
    left_students       INT DEFAULT 0 COMMENT '本月离园学生',

    -- 考勤统计数据
    attendance_rate     DECIMAL(5,2) DEFAULT 0 COMMENT '出勤率',
    absent_count        INT DEFAULT 0 COMMENT '缺勤人数',
    leave_count         INT DEFAULT 0 COMMENT '请假人数',
    late_count          INT DEFAULT 0 COMMENT '迟到人数',

    -- 成长记录统计数据
    total_records       INT DEFAULT 0 COMMENT '成长记录总数',
    published_records   INT DEFAULT 0 COMMENT '已发布记录数',
    total_photos         INT DEFAULT 0 COMMENT '照片上传数',

    -- 家长活跃度
    active_parents      INT DEFAULT 0 COMMENT '活跃家长数',
    message_count       INT DEFAULT 0 COMMENT '消息数',

    -- 课程统计
    total_courses       INT DEFAULT 0 COMMENT '课程总数',
    completed_courses   INT DEFAULT 0 COMMENT '已完成课程数',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_kg_date (kg_id, snapshot_date, snapshot_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='看板数据快照表';

-- 插入示例看板快照
INSERT INTO t_dashboard_snapshot (snapshot_id, kg_id, snapshot_date, snapshot_type, total_students, attendance_rate, total_records, published_records, total_photos, active_parents, message_count, total_courses) VALUES
('snap_001', 'kg_001', '2026-03-26', 'DAILY', 50, 96.00, 25, 25, 50, 20, 30, 10);

-- =============================================
-- 家校沟通模块
-- =============================================

-- 私信消息表
CREATE TABLE IF NOT EXISTS t_message (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    message_id          VARCHAR(32) NOT NULL UNIQUE COMMENT '消息ID',
    conversation_id     VARCHAR(32) NOT NULL COMMENT '会话ID',
    sender_id           VARCHAR(32) NOT NULL COMMENT '发送者ID',
    sender_type         VARCHAR(20) NOT NULL COMMENT '发送者类型: TEACHER/PARENT',
    receiver_id         VARCHAR(32) NOT NULL COMMENT '接收者ID',
    receiver_type       VARCHAR(20) NOT NULL COMMENT '接收者类型: TEACHER/PARENT',

    -- 消息内容
    message_type       VARCHAR(20) NOT NULL DEFAULT 'TEXT' COMMENT '消息类型: TEXT-文字 IMAGE-图片 VOICE-语音 LOCATION-位置',
    content            TEXT COMMENT '消息内容',
    media_url           VARCHAR(500) COMMENT '媒体文件URL',
    media_duration      INT COMMENT '语音时长(秒)',

    -- 消息状态
    is_read             TINYINT DEFAULT 0 COMMENT '是否已读',
    read_time           DATETIME COMMENT '阅读时间',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_conversation (conversation_id, create_time),
    INDEX idx_receiver (receiver_id, is_read)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信消息表';

-- 会话表
CREATE TABLE IF NOT EXISTS t_conversation (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    conversation_id    VARCHAR(32) NOT NULL UNIQUE COMMENT '会话ID',
    conversation_type  VARCHAR(20) NOT NULL COMMENT '会话类型: PRIVATE-私信 GROUP-群组',
    name                VARCHAR(100) COMMENT '群名称',
    avatar              VARCHAR(500) COMMENT '群头像',

    -- 成员信息
    member_ids          JSON COMMENT '成员ID列表',
    member_count        INT DEFAULT 0 COMMENT '成员数量',

    -- 群信息
    owner_id            VARCHAR(32) COMMENT '群主ID',
    notice              TEXT COMMENT '群公告',

    -- 状态
    last_message        TEXT COMMENT '最后一条消息',
    last_message_time   DATETIME COMMENT '最后消息时间',
    is_muted            TINYINT DEFAULT 0 COMMENT '是否禁言',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表';

-- =============================================
-- 活动管理模块
-- =============================================

-- 活动表
CREATE TABLE IF NOT EXISTS t_activity (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id         VARCHAR(32) NOT NULL UNIQUE COMMENT '活动ID',
    kg_id               VARCHAR(32) NOT NULL COMMENT '园区ID',
    activity_name       VARCHAR(100) NOT NULL COMMENT '活动名称',
    activity_type       VARCHAR(20) NOT NULL COMMENT '活动类型: THEME-主题 FESTIVAL-节庆 SPORTS-体育 OUTDOOR-户外 PARENT-亲子 TEACHING-教学',

    -- 活动信息
    activity_time       DATETIME NOT NULL COMMENT '活动时间',
    end_time            DATETIME COMMENT '活动结束时间',
    location            VARCHAR(200) COMMENT '活动地点',
    description         TEXT COMMENT '活动简介',
    process             TEXT COMMENT '活动流程',

    -- 参与对象
    target_type         VARCHAR(20) NOT NULL COMMENT '目标类型: ALL-全园 CLASS-班级 STUDENT-指定学生',
    target_ids          JSON COMMENT '目标ID列表',
    max_participants    INT COMMENT '最大参与人数',

    -- 费用
    fee                 DECIMAL(10,2) DEFAULT 0 COMMENT '活动费用',
    fee_description     VARCHAR(500) COMMENT '费用说明',

    -- 报名信息
    require_signup      TINYINT DEFAULT 0 COMMENT '是否需要报名',
    signup_deadline     DATETIME COMMENT '报名截止时间',

    -- 状态
    status              VARCHAR(20) DEFAULT 'DRAFT' COMMENT '状态: DRAFT-草稿 PUBLISHED-已发布 CANCELLED-已取消 COMPLETED-已完成',
    publish_time        DATETIME COMMENT '发布时间',

    -- 负责人
    principal_id        VARCHAR(32) COMMENT '负责人ID',
    create_by           VARCHAR(32) COMMENT '创建人ID',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_id (kg_id),
    INDEX idx_activity_time (activity_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- 插入示例活动
INSERT INTO t_activity (activity_id, kg_id, activity_name, activity_type, activity_time, end_time, location, description, target_type, max_participants, require_signup, status, principal_id, create_by) VALUES
('activity_001', 'kg_001', '春季亲子运动会', 'PARENT', '2026-04-15 09:00:00', '2026-04-15 12:00:00', '幼儿园操场', '邀请家长参与孩子户外运动活动', 'ALL', 100, 1, 'PUBLISHED', 'teacher_001', 'user_principal_001'),
('activity_002', 'kg_001', '清明节主题活动', 'FESTIVAL', '2026-04-04 10:00:00', '2026-04-04 11:30:00', '多功能教室', '了解清明节习俗', 'ALL', 50, 0, 'PUBLISHED', 'teacher_001', 'user_principal_001'),
('activity_003', 'kg_001', '科学探索课', 'TEACHING', '2026-04-10 14:00:00', '2026-04-10 15:00:00', '科学实验室', '探索科学奥秘', 'CLASS', 30, 0, 'PUBLISHED', 'teacher_002', 'user_teacher_001');

-- 活动报名表
CREATE TABLE IF NOT EXISTS t_activity_signup (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    signup_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '报名ID',
    activity_id         VARCHAR(32) NOT NULL COMMENT '活动ID',
    student_id          VARCHAR(32) NOT NULL COMMENT '学生ID',
    parent_id           VARCHAR(32) NOT NULL COMMENT '家长ID',

    -- 报名信息
    status              VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING-待审核 APPROVED-已通过 REJECTED-已拒绝 CANCELLED-已取消',
    remark              VARCHAR(500) COMMENT '备注',

    -- 审批信息
    approve_by          VARCHAR(32) COMMENT '审批人',
    approve_time        DATETIME COMMENT '审批时间',
    approve_remark      VARCHAR(500) COMMENT '审批备注',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_activity_student (activity_id, student_id),
    INDEX idx_activity (activity_id),
    INDEX idx_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

-- 插入示例活动报名
INSERT INTO t_activity_signup (signup_id, activity_id, student_id, parent_id, status) VALUES
('signup_001', 'activity_001', 'stu_001', 'parent_001', 'APPROVED'),
('signup_002', 'activity_001', 'stu_002', 'parent_002', 'PENDING');

-- 活动记录表
CREATE TABLE IF NOT EXISTS t_activity_record (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '记录ID',
    activity_id         VARCHAR(32) NOT NULL COMMENT '活动ID',

    -- 记录内容
    summary             TEXT COMMENT '活动总结',
    photos              JSON COMMENT '活动照片',
    videos              JSON COMMENT '活动视频',

    -- 参与学生
    participant_ids     JSON COMMENT '参与学生ID列表',
    absent_ids          JSON COMMENT '缺席学生ID列表',

    -- 评价
    parent_feedback     JSON COMMENT '家长反馈',

    create_by           VARCHAR(32) COMMENT '记录人',
    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动记录表';

-- =============================================
-- 系统设置模块
-- =============================================

-- 系统配置表
CREATE TABLE IF NOT EXISTS t_system_config (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '配置ID',
    config_key          VARCHAR(50) NOT NULL UNIQUE COMMENT '配置键',
    config_value        TEXT COMMENT '配置值',
    config_type         VARCHAR(20) DEFAULT 'STRING' COMMENT '类型: STRING/NUMBER/BOOLEAN/JSON',
    description         VARCHAR(200) COMMENT '描述',

    -- 功能开关
    is_enabled          TINYINT DEFAULT 1 COMMENT '是否启用',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 插入默认系统配置
INSERT INTO t_system_config (config_id, config_key, config_value, config_type, description, is_enabled) VALUES
('config_ai_profile', 'feature.ai.profile.enabled', 'true', 'BOOLEAN', 'AI成长画像功能开关', 1),
('config_ai_video', 'feature.ai.video.enabled', 'true', 'BOOLEAN', 'AI成长视频功能开关', 1),
('config_attendance', 'feature.attendance.enabled', 'true', 'BOOLEAN', '考勤打卡功能开关', 1),
('config_message', 'feature.message.enabled', 'true', 'BOOLEAN', '家校沟通功能开关', 1),
('config_push_time', 'push.daily.time', '16:00', 'STRING', '每日推送时间', 1),
('config_video_max_size', 'video.max.size', '500', 'NUMBER', '视频最大MB', 1);

-- =============================================
-- 课程调课模块
-- =============================================

-- 调课记录表
CREATE TABLE IF NOT EXISTS t_schedule_adjust (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    adjust_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '调课ID',
    schedule_id         VARCHAR(32) NOT NULL COMMENT '原课表ID',
    class_id            VARCHAR(32) NOT NULL COMMENT '班级ID',

    -- 调课信息
    adjust_type         VARCHAR(20) NOT NULL COMMENT '调整类型: SWAP-换课 SUBSTITUTE-代课 MOVE-调时段',
    original_date       DATE NOT NULL COMMENT '原上课日期',
    original_time       VARCHAR(20) COMMENT '原上课时间',
    adjusted_date       DATE NOT NULL COMMENT '调整后日期',
    adjusted_time       VARCHAR(20) COMMENT '调整后时间',

    -- 代课信息
    original_teacher_id VARCHAR(32) COMMENT '原授课老师ID',
    substitute_teacher_id VARCHAR(32) COMMENT '代课老师ID',
    substitute_reason   VARCHAR(500) COMMENT '代课原因',

    -- 课程信息
    course_id           VARCHAR(32) NOT NULL COMMENT '课程ID',

    -- 状态
    status              VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING-待审批 APPROVED-已批准 REJECTED-已拒绝 CANCELLED-已取消',
    apply_by            VARCHAR(32) COMMENT '申请人',
    approve_by          VARCHAR(32) COMMENT '审批人',
    approve_time        DATETIME COMMENT '审批时间',
    approve_remark      VARCHAR(500) COMMENT '审批备注',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_class_date (class_id, original_date),
    INDEX idx_schedule (schedule_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调课记录表';

-- 老师课表安排表（个人）
CREATE TABLE IF NOT EXISTS t_teacher_schedule (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '安排ID',
    teacher_id          VARCHAR(32) NOT NULL COMMENT '老师ID',
    schedule_id         VARCHAR(32) COMMENT '关联课表ID',
    class_id            VARCHAR(32) NOT NULL COMMENT '班级ID',
    course_id           VARCHAR(32) NOT NULL COMMENT '课程ID',

    -- 时间信息
    schedule_date       DATE NOT NULL COMMENT '上课日期',
    week_day            TINYINT NOT NULL COMMENT '星期(1-7)',
    start_time          TIME NOT NULL COMMENT '开始时间',
    end_time            TIME NOT NULL COMMENT '结束时间',

    -- 状态
    status              VARCHAR(20) DEFAULT 'SCHEDULED' COMMENT '状态: SCHEDULED-已安排 SUBSTITUTED-已代课 CANCELLED-已取消',
    remark              VARCHAR(500) COMMENT '备注',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_teacher_date (teacher_id, schedule_date),
    INDEX idx_class_date (class_id, schedule_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师课表安排表';

-- =============================================
-- 食谱营养分析模块
-- =============================================

-- 营养成分标准表
CREATE TABLE IF NOT EXISTS t_nutrition_standard (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    standard_id         VARCHAR(32) NOT NULL UNIQUE COMMENT '标准ID',
    age_group           VARCHAR(20) NOT NULL COMMENT '年龄段: 3-4岁/4-5岁/5-6岁',
    nutrient_name       VARCHAR(50) NOT NULL COMMENT '营养素名称',
    daily_amount        DECIMAL(10,2) NOT NULL COMMENT '每日推荐量',
    unit                VARCHAR(20) NOT NULL COMMENT '单位',
    description         VARCHAR(200) COMMENT '说明',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_age_nutrient (age_group, nutrient_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营养成分标准表';

-- 食材营养表
CREATE TABLE IF NOT EXISTS t_food_nutrition (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    food_id             VARCHAR(32) NOT NULL UNIQUE COMMENT '食材ID',
    food_name           VARCHAR(100) NOT NULL COMMENT '食材名称',
    category            VARCHAR(50) COMMENT '分类: 主食/蔬菜/水果/肉蛋/奶制品/豆制品',

    -- 每100g营养成分
    calories            DECIMAL(10,2) COMMENT '热量(kcal)',
    protein             DECIMAL(10,2) COMMENT '蛋白质(g)',
    fat                 DECIMAL(10,2) COMMENT '脂肪(g)',
    carbohydrates       DECIMAL(10,2) COMMENT '碳水化合物(g)',
    vitamin_a           DECIMAL(10,2) COMMENT '维生素A(μg)',
    vitamin_c           DECIMAL(10,2) COMMENT '维生素C(mg)',
    calcium             DECIMAL(10,2) COMMENT '钙(mg)',
    iron                DECIMAL(10,2) COMMENT '铁(mg)',
    zinc                DECIMAL(10,2) COMMENT '锌(mg)',
    fiber               DECIMAL(10,2) COMMENT '膳食纤维(g)',

    -- 过敏原
    allergens           VARCHAR(200) COMMENT '过敏原',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食材营养表';

-- 插入营养标准数据
INSERT INTO t_nutrition_standard (standard_id, age_group, nutrient_name, daily_amount, unit, description) VALUES
('ns_001', '3-4岁', '热量', 1200, 'kcal', '每日推荐摄入量'),
('ns_002', '3-4岁', '蛋白质', 45, 'g', '每日推荐摄入量'),
('ns_003', '3-4岁', '钙', 600, 'mg', '每日推荐摄入量'),
('ns_004', '3-4岁', '铁', 10, 'mg', '每日推荐摄入量'),
('ns_005', '3-4岁', '锌', 5, 'mg', '每日推荐摄入量'),
('ns_006', '4-5岁', '热量', 1300, 'kcal', '每日推荐摄入量'),
('ns_007', '4-5岁', '蛋白质', 50, 'g', '每日推荐摄入量'),
('ns_008', '4-5岁', '钙', 650, 'mg', '每日推荐摄入量'),
('ns_009', '5-6岁', '热量', 1400, 'kcal', '每日推荐摄入量'),
('ns_010', '5-6岁', '蛋白质', 55, 'g', '每日推荐摄入量');

-- 插入常见食材营养数据
INSERT INTO t_food_nutrition (food_id, food_name, category, calories, protein, fat, carbohydrates, calcium, iron, allergens) VALUES
('food_001', '米饭', '主食', 116, 2.6, 0.3, 25.9, 7, 0.3, NULL),
('food_002', '面条', '主食', 109, 2.7, 0.2, 24.7, 8, 0.4, '小麦'),
('food_003', '鸡胸肉', '肉蛋', 165, 31, 3.6, 0, 15, 0.4, NULL),
('food_004', '鸡蛋', '肉蛋', 143, 12.6, 9.5, 0.8, 44, 1.2, '鸡蛋'),
('food_005', '牛奶', '奶制品', 61, 3.2, 3.3, 4.5, 113, 0.1, '牛奶'),
('food_006', '胡萝卜', '蔬菜', 32, 0.7, 0.2, 7.6, 32, 0.3, NULL),
('food_007', '西红柿', '蔬菜', 18, 0.9, 0.2, 3.9, 10, 0.4, NULL),
('food_008', '苹果', '水果', 52, 0.3, 0.2, 13.8, 6, 0.1, NULL),
('food_009', '香蕉', '水果', 89, 1.1, 0.3, 22.8, 7, 0.3, NULL);

-- =============================================
-- 视频模板模块
-- =============================================

-- 视频模板表
CREATE TABLE IF NOT EXISTS t_video_template (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_id         VARCHAR(32) NOT NULL UNIQUE COMMENT '模板ID',
    template_name       VARCHAR(100) NOT NULL COMMENT '模板名称',
    template_type       VARCHAR(20) NOT NULL COMMENT '模板类型: CARTOON-卡通 WARM-温馨 ACTIVE-活泼 SIMPLE-简约',

    -- 模板信息
    preview_url         VARCHAR(500) COMMENT '预览图URL',
    template_url        VARCHAR(500) NOT NULL COMMENT '模板文件URL',
    duration            INT COMMENT '视频时长(秒)',
    default_music       VARCHAR(500) COMMENT '默认背景音乐',

    -- 样式配置
    style_config        JSON COMMENT '样式配置JSON',
    text_style          JSON COMMENT '文字样式配置',
    transition_effect   VARCHAR(50) COMMENT '转场效果',

    -- 状态
    is_default          TINYINT DEFAULT 0 COMMENT '是否默认模板',
    status              TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-启用',
    create_by           VARCHAR(32) COMMENT '创建人',
    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_template_type (template_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频模板表';

-- 视频素材表
CREATE TABLE IF NOT EXISTS t_video_material (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '素材ID',
    material_type       VARCHAR(20) NOT NULL COMMENT '素材类型: MUSIC-音乐 EFFECT-特效 FONT-字体',

    -- 素材信息
    material_name      VARCHAR(100) NOT NULL COMMENT '素材名称',
    material_url       VARCHAR(500) NOT NULL COMMENT '素材URL',
    duration            INT COMMENT '时长(秒)',
    size                INT COMMENT '文件大小(KB)',

    -- 状态
    status              TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-启用',
    use_count           INT DEFAULT 0 COMMENT '使用次数',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频素材表';

-- 视频生成任务表（增强版）
CREATE TABLE IF NOT EXISTS t_video_task (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id             VARCHAR(32) NOT NULL UNIQUE COMMENT '任务ID',
    video_id            VARCHAR(32) COMMENT '关联视频ID',
    student_id          VARCHAR(32) NOT NULL COMMENT '学生ID',

    -- 生成参数
    video_type          VARCHAR(20) NOT NULL COMMENT '视频类型: MONTHLY/REVIEW/HIGHLIGHT',
    start_date          DATE COMMENT '开始日期',
    end_date            DATE COMMENT '结束日期',
    template_id         VARCHAR(32) COMMENT '使用模板ID',
    music_id            VARCHAR(32) COMMENT '背景音乐ID',
    custom_text         VARCHAR(500) COMMENT '自定义文字',

    -- 生成状态
    status              VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING-排队中 PROCESSING-处理中 COMPLETED-已完成 FAILED-失败',
    progress            INT DEFAULT 0 COMMENT '进度(0-100)',
    error_msg           TEXT COMMENT '失败原因',

    -- 智能选片
    selected_photos    JSON COMMENT '选中的照片(JSON数组)',
    photo_count         INT DEFAULT 0 COMMENT '照片数量',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_student_id (student_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频生成任务表';

-- 插入默认视频模板
INSERT INTO t_video_template (template_id, template_name, template_type, template_url, preview_url, default_music, is_default, status) VALUES
('tpl_001', '卡通风格', 'CARTOON', '/templates/cartoon.mp4', '/templates/cartoon.jpg', 'bgm_001.mp3', 1, 1),
('tpl_002', '温馨风格', 'WARM', '/templates/warm.mp4', '/templates/warm.jpg', 'bgm_002.mp3', 0, 1),
('tpl_003', '活泼风格', 'ACTIVE', '/templates/active.mp4', '/templates/active.jpg', 'bgm_003.mp3', 0, 1);

-- 插入默认素材
INSERT INTO t_video_material (material_id, material_type, material_name, material_url, duration, status) VALUES
('music_001', 'MUSIC', '儿童轻音乐', '/music/light.mp3', 180, 1),
('music_002', 'MUSIC', '温馨背景乐', '/music/warm.mp3', 180, 1),
('music_003', 'MUSIC', '欢快儿歌', '/music/happy.mp3', 120, 1);

-- =============================================
-- 记录模板模块
-- =============================================

-- 成长记录模板表
CREATE TABLE IF NOT EXISTS t_record_template (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_id        VARCHAR(32) NOT NULL UNIQUE COMMENT '模板ID',
    template_name      VARCHAR(100) NOT NULL COMMENT '模板名称',
    content            TEXT NOT NULL COMMENT '模板内容',
    scenario           VARCHAR(50) COMMENT '适用场景: course/emotion/diet/activity/sleep',
    is_default         TINYINT DEFAULT 0 COMMENT '是否默认模板',
    use_count          INT DEFAULT 0 COMMENT '使用次数',
    create_by           VARCHAR(32) COMMENT '创建人',
    create_time        DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_scenario (scenario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长记录模板表';

-- 草稿记录表
CREATE TABLE IF NOT EXISTS t_record_draft (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    draft_id            VARCHAR(32) NOT NULL UNIQUE COMMENT '草稿ID',
    student_id          VARCHAR(32) NOT NULL COMMENT '学生ID',
    teacher_id          VARCHAR(32) NOT NULL COMMENT '老师ID',
    record_date         DATE NOT NULL COMMENT '记录日期',

    -- 草稿内容(JSON)
    content             TEXT COMMENT '草稿内容',
    version             INT DEFAULT 1 COMMENT '版本号',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_date (student_id, record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='草稿记录表';

-- 插入默认模板
INSERT INTO t_record_template (template_id, template_name, content, scenario, is_default) VALUES
('tpl_001', '今日户外活动丰富', '今天进行了丰富的户外活动，孩子参与积极，表现活跃', 'activity', 1),
('tpl_002', '今日午餐正常', '午餐食欲良好，不挑食，全部吃完', 'diet', 1),
('tpl_003', '情绪稳定', '今天情绪稳定，与同伴相处融洽', 'emotion', 1),
('tpl_004', '午休良好', '午休入睡快，睡眠质量好', 'sleep', 1);

-- =============================================
-- 消息推送模块
-- =============================================

-- 推送配置表
CREATE TABLE IF NOT EXISTS t_push_config (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_id           VARCHAR(32) NOT NULL UNIQUE COMMENT '配置ID',
    kg_id               VARCHAR(32) COMMENT '园区ID',
    push_type           VARCHAR(20) NOT NULL COMMENT '推送类型: DAILY-每日 RECORD-成长记录 NOTICE-通知',

    -- 推送设置
    enabled             TINYINT DEFAULT 1 COMMENT '是否启用',
    push_time           TIME COMMENT '推送时间',
    template_id         VARCHAR(32) COMMENT '推送模板ID',

    -- 免打扰设置
    quiet_hours_start   TIME COMMENT '免打扰开始时间',
    quiet_hours_end     TIME COMMENT '免打扰结束时间',
    quiet_days          VARCHAR(50) COMMENT '免扰日期(周末等)',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_kg_type (kg_id, push_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推送配置表';

-- 推送记录表
CREATE TABLE IF NOT EXISTS t_push_record (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    push_id             VARCHAR(32) NOT NULL UNIQUE COMMENT '推送ID',
    user_id             VARCHAR(32) NOT NULL COMMENT '接收用户ID',
    push_type           VARCHAR(20) NOT NULL COMMENT '推送类型',
    title               VARCHAR(200) COMMENT '标题',
    content             TEXT COMMENT '内容',
    related_id          VARCHAR(32) COMMENT '关联业务ID',

    -- 推送状态
    channel             VARCHAR(20) COMMENT '推送渠道: WECHAT-微信 SMS-短信 APP-APP',
    status              VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING-待发送 SENT-已发送 FAILED-失败',
    send_time           DATETIME COMMENT '发送时间',
    read_time           DATETIME COMMENT '阅读时间',

    create_time         DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (user_id, status),
    INDEX idx_related (related_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推送记录表';

-- 插入默认推送配置
INSERT INTO t_push_config (config_id, push_type, enabled, push_time, quiet_hours_start, quiet_hours_end) VALUES
('push_001', 'DAILY', 1, '16:00', '22:00', '07:00'),
('push_002', 'RECORD', 1, NULL, '22:00', '07:00'),
('push_003', 'NOTICE', 1, NULL, '22:00', '07:00');

-- =============================================
-- 权限管理模块
-- =============================================

-- 角色表
CREATE TABLE IF NOT EXISTS t_role (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id            VARCHAR(32) NOT NULL UNIQUE COMMENT '角色ID',
    role_name          VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code          VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    description        VARCHAR(200) COMMENT '角色描述',
    is_system          TINYINT DEFAULT 0 COMMENT '是否系统角色',
    status             TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    create_time        DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE IF NOT EXISTS t_permission (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    permission_id      VARCHAR(32) NOT NULL UNIQUE COMMENT '权限ID',
    permission_name     VARCHAR(100) NOT NULL COMMENT '权限名称',
    permission_code     VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
    permission_type    VARCHAR(20) NOT NULL COMMENT '权限类型: MENU-菜单 BUTTON-按钮 API-接口',
    parent_id          VARCHAR(32) COMMENT '父权限ID',
    path               VARCHAR(200) COMMENT '路由路径',
    component          VARCHAR(200) COMMENT '组件路径',
    icon               VARCHAR(50) COMMENT '图标',
    sort               INT DEFAULT 0 COMMENT '排序',
    status             TINYINT DEFAULT 1 COMMENT '状态',
    create_time        DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS t_role_permission (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id            VARCHAR(32) NOT NULL COMMENT '角色ID',
    permission_id      VARCHAR(32) NOT NULL COMMENT '权限ID',
    create_time        DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS t_user_role (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id            VARCHAR(32) NOT NULL COMMENT '用户ID',
    role_id            VARCHAR(32) NOT NULL COMMENT '角色ID',
    create_time        DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS t_operation_log (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    log_id             VARCHAR(32) NOT NULL UNIQUE COMMENT '日志ID',
    user_id            VARCHAR(32) COMMENT '操作用户ID',
    username           VARCHAR(50) COMMENT '操作用户名',
    operation          VARCHAR(100) COMMENT '操作描述',
    method             VARCHAR(200) COMMENT '请求方法',
    params             TEXT COMMENT '请求参数',
    result             TEXT COMMENT '返回结果',
    ip                 VARCHAR(50) COMMENT 'IP地址',
    duration           INT COMMENT '耗时(毫秒)',
    status             TINYINT COMMENT '状态: 0-失败 1-成功',
    create_time        DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 插入默认角色
INSERT INTO t_role (role_id, role_name, role_code, description, is_system) VALUES
('role_001', '系统管理员', 'ADMIN', '系统管理员，拥有所有权限', 1),
('role_002', '园长', 'PRINCIPAL', '幼儿园园长，管理全园', 1),
('role_003', '老师', 'TEACHER', '幼儿园老师', 1),
('role_004', '家长', 'PARENT', '学生家长', 1);

-- 插入默认权限
INSERT INTO t_permission (permission_id, permission_name, permission_code, permission_type, path, sort) VALUES
('perm_001', '用户管理', 'user:manage', 'MENU', '/user', 1),
('perm_002', '用户查询', 'user:list', 'BUTTON', NULL, 0),
('perm_003', '用户新增', 'user:add', 'BUTTON', NULL, 0),
('perm_004', '用户编辑', 'user:edit', 'BUTTON', NULL, 0),
('perm_005', '用户删除', 'user:delete', 'BUTTON', NULL, 0),
('perm_006', '学生管理', 'student:manage', 'MENU', '/student', 2),
('perm_007', '学生查询', 'student:list', 'BUTTON', NULL, 0),
('perm_008', '学生新增', 'student:add', 'BUTTON', NULL, 0),
('perm_009', '学生编辑', 'student:edit', 'BUTTON', NULL, 0),
('perm_010', '班级管理', 'class:manage', 'MENU', '/class', 3),
('perm_011', '食谱管理', 'menu:manage', 'MENU', '/menu', 4),
('perm_012', '课程管理', 'course:manage', 'MENU', '/course', 5),
('perm_013', '成长记录', 'record:manage', 'MENU', '/record', 6),
('perm_014', '考勤管理', 'attendance:manage', 'MENU', '/attendance', 7),
('perm_015', '数据看板', 'dashboard:view', 'MENU', '/dashboard', 8),
('perm_016', '通知管理', 'notice:manage', 'MENU', '/notice', 9),
('perm_017', '活动管理', 'activity:manage', 'MENU', '/activity', 10),
('perm_018', '系统设置', 'system:manage', 'MENU', '/system', 11);


