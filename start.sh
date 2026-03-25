#!/bin/bash

# ============================================
# 智慧幼儿园成长管理系统 - 一键启动脚本
# ============================================

echo "=========================================="
echo "智慧幼儿园成长管理系统 - 启动脚本"
echo "=========================================="

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "❌ 未检测到Java，请先安装JDK 17+"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1)
echo "✅ Java环境: $JAVA_VERSION"

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ 未检测到Maven，请先安装Maven 3.8+"
    exit 1
fi

echo "✅ Maven环境: $(mvn -version | head -1)"

# 检查MySQL
if ! command -v mysql &> /dev/null; then
    echo "⚠️ 未检测到MySQL，将跳过数据库初始化"
    SKIP_DB=true
else
    echo "✅ MySQL环境: $(mysql --version)"
    SKIP_DB=false
fi

# 检查Redis
if ! command -v redis-server &> /dev/null; then
    echo "⚠️ 未检测到Redis，将跳过Redis启动"
    SKIP_REDIS=true
else
    echo "✅ Redis环境: 已安装"
    SKIP_REDIS=false
fi

# 启动Redis
if [ "$SKIP_REDIS" = "false" ]; then
    echo ""
    echo "启动Redis..."
    redis-server --daemonize yes --port 6379
    sleep 2
    echo "✅ Redis已启动 (端口6379)"
fi

# 初始化数据库
if [ "$SKIP_DB" = "false" ]; then
    echo ""
    echo "初始化数据库..."
    mysql -u root -p < sql/init.sql 2>/dev/null || mysql -u root < sql/init.sql
    echo "✅ 数据库初始化完成"
fi

# 编译项目
echo ""
echo "编译项目..."
cd /Users/czh/git/kindergarten
mvn clean package -DskipTests -q

if [ $? -ne 0 ]; then
    echo "❌ 项目编译失败"
    exit 1
fi

echo "✅ 项目编译完成"

# 定义服务数组
SERVICES=(
    "kgms-gateway:8080"
    "kgms-user:8081"
    "kgms-student:8082"
    "kgms-class:8083"
    "kgms-record:8084"
    "kgms-food:8085"
    "kgms-course:8086"
    "kgms-growth:8087"
    "kgms-video:8088"
    "kgms-notice:8089"
)

# 启动服务
echo ""
echo "启动微服务..."

for service_info in "${SERVICES[@]}"; do
    IFS=':' read -r service port <<< "$service_info"
    echo "启动 $service (端口 $port)..."
    
    nohup java -jar "$service/target/$service.jar" > "$service.log" 2>&1 &
    echo "✅ $service 已启动 (PID: $!)"
    sleep 3
done

echo ""
echo "=========================================="
echo "所有服务启动完成！"
echo "=========================================="
echo ""
echo "服务访问地址:"
echo "  - 网关:     http://localhost:8080"
echo "  - 用户服务:   http://localhost:8081"
echo "  - 学生服务:   http://localhost:8082"
echo "  - 班级服务:   http://localhost:8083"
echo "  - 记录服务:   http://localhost:8084"
echo "  - 食谱服务:   http://localhost:8085"
echo "  - 课程服务:   http://localhost:8086"
echo "  - 成长服务:   http://localhost:8087"
echo "  - 视频服务:   http://localhost:8088"
echo "  - 通知服务:   http://localhost:8089"
echo ""
echo "API测试示例:"
echo "  curl http://localhost:8080/api/user/login"
echo ""
echo "日志查看:"
echo "  tail -f kgms-gateway/logs/gateway.log"
echo ""
echo "停止所有服务:"
echo "  pkill -f 'kgms-.*jar'"
echo "=========================================="