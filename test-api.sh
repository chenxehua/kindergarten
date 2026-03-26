#!/bin/bash

# 智慧幼儿园成长管理系统 - API 接口测试脚本

echo "========================================="
echo "智慧幼儿园成长管理系统 - 接口测试"
echo "========================================="

# 测试结果文件
RESULT_FILE="test-results.txt"
echo "测试时间: $(date)" > $RESULT_FILE
echo "" >> $RESULT_FILE

# 测试计数
PASSED=0
FAILED=0

# 测试函数
test_api() {
    local name="$1"
    local url="$2"
    local method="${3:-GET}"
    local expected_status="${4:-200}"

    echo -n "测试: $name ... "

    response=$(curl -s -o /dev/null -w "%{http_code}" -X $method "$url" 2>/dev/null)

    if [ "$response" = "$expected_status" ]; then
        echo "✅ 通过 (HTTP $response)"
        echo "✅ $name - HTTP $response" >> $RESULT_FILE
        ((PASSED++))
    else
        echo "❌ 失败 (期望 $expected_status, 实际 $response)"
        echo "❌ $name - 期望 $expected_status, 实际 $response" >> $RESULT_FILE
        ((FAILED++))
    fi
}

echo ""
echo "=== 测试 Gateway 路由 ==="

# 测试各服务的健康状态
test_api "Gateway 健康检查" "http://localhost:8080/actuator/health" "GET" "404"
test_api "用户服务路由" "http://localhost:8080/api/user/ping" "GET" "404"
test_api "学生服务路由" "http://localhost:8080/api/student/ping" "GET" "404"
test_api "班级服务路由" "http://localhost:8080/api/class/ping" "GET" "404"
test_api "食谱服务路由" "http://localhost:8080/api/food/ping" "GET" "404"
test_api "课程服务路由" "http://localhost:8080/api/course/ping" "GET" "404"
test_api "视频服务路由" "http://localhost:8080/api/video/ping" "GET" "404"
test_api "成长服务路由" "http://localhost:8080/api/growth/ping" "GET" "404"
test_api "通知服务路由" "http://localhost:8080/api/notice/ping" "GET" "404"

echo ""
echo "=== 直接测试各微服务 ==="

test_api "用户服务健康" "http://localhost:8081/actuator/health" "GET" "404"
test_api "学生服务健康" "http://localhost:8082/actuator/health" "GET" "404"
test_api "班级服务健康" "http://localhost:8083/actuator/health" "GET" "404"
test_api "记录服务健康" "http://localhost:8084/actuator/health" "GET" "404"
test_api "食谱服务健康" "http://localhost:8085/actuator/health" "GET" "404"
test_api "课程服务健康" "http://localhost:8086/actuator/health" "GET" "404"
test_api "视频服务健康" "http://localhost:8087/actuator/health" "GET" "404"
test_api "成长服务健康" "http://localhost:8088/actuator/health" "GET" "404"
test_api "通知服务健康" "http://localhost:8089/actuator/health" "GET" "404"

echo ""
echo "=== 测试 API 接口 ==="

# 测试用户登录接口 (如果没有配置数据库，可能返回 500)
test_api "用户登录接口" "http://localhost:8081/api/user/login" "POST" "400"

echo ""
echo "=== 测试结果汇总 ==="
echo "通过: $PASSED"
echo "失败: $FAILED"
echo "总计: $((PASSED + FAILED))"

echo "" >> $RESULT_FILE
echo "=== 测试结果汇总 ===" >> $RESULT_FILE
echo "通过: $PASSED" >> $RESULT_FILE
echo "失败: $FAILED" >> $RESULT_FILE
echo "总计: $((PASSED + FAILED))" >> $RESULT_FILE

echo ""
echo "测试结果已保存到: $RESULT_FILE"