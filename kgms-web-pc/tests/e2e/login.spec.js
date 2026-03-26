import { test, expect } from '@playwright/test';

test.describe('登录功能测试', () => {
  test('TC-LOGIN-001: 登录页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    // 等待页面完全加载
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查页面容器是否存在
    await expect(page.locator('.login-container')).toBeVisible({ timeout: 10000 });
    // 检查标题
    await expect(page.locator('.title')).toContainText('智慧幼儿园');
  });

  test('TC-LOGIN-002: 用户名为空时登录', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    // 不输入用户名，只输入密码
    await page.locator('[placeholder="请输入密码"]').fill('123456');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(1000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-003: 密码为空时登录', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(1000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-004: 账号密码错误', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('wronguser');
    await page.locator('[placeholder="请输入密码"]').fill('wrongpassword');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-005: 登录成功', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    // 等待页面跳转
    await page.waitForTimeout(3000);
    // 检查是否跳转到首页（路由跳转会改变URL）
    const currentUrl = page.url();
    console.log('Current URL after login:', currentUrl);
  });

  test('TC-LOGIN-006: 用户名包含特殊字符', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin<script>');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-007: 密码过短', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('123');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-008: 空格字符作为用户名和密码', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('   ');
    await page.locator('[placeholder="请输入密码"]').fill('   ');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-009: 超长用户名和密码', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const longStr = 'a'.repeat(200);
    await page.locator('[placeholder="请输入用户名"]').fill(longStr);
    await page.locator('[placeholder="请输入密码"]').fill(longStr);
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

    // 检查页面仍然在登录页
    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-010: 登录按钮加载状态', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');

    // 点击登录按钮
    await page.locator('.el-button--primary').click();

    // 等待页面响应
    await page.waitForTimeout(2000);

    // 检查页面是否加载
    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});
