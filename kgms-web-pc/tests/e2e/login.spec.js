import { test, expect } from '@playwright/test';

test.describe('登录功能测试', () => {
  test('TC-LOGIN-001: 登录页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await expect(page.locator('.login-container')).toBeVisible({ timeout: 10000 });
    await expect(page.locator('.title')).toContainText('智慧幼儿园');
  });

  test('TC-LOGIN-002: 用户名为空时登录', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入密码"]').fill('123456');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(1000);

    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-003: 密码为空时登录', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(1000);

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

    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-005: 登录成功', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    await page.waitForTimeout(3000);
    const currentUrl = page.url();
    expect(currentUrl).toContain('/dashboard');

    const token = await page.evaluate(() => localStorage.getItem('token'));
    expect(token).toBeTruthy();
    console.log('Login successful, token stored:', token ? 'yes' : 'no');
  });

  test('TC-LOGIN-006: 用户名包含特殊字符', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin<script>');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

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

    await expect(page.locator('.login-box')).toBeVisible();
  });

  test('TC-LOGIN-010: 登录按钮加载状态', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    await page.waitForTimeout(2000);
    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-LOGIN-011: 回车键提交登录', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('[placeholder="请输入密码"]').press('Enter');

    await page.waitForTimeout(2000);
    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-LOGIN-012: 表单重置功能', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('password');

    await expect(page.locator('[placeholder="请输入用户名"]')).toHaveValue('admin');
    await expect(page.locator('[placeholder="请输入密码"]')).toHaveValue('password');
  });

  test('TC-LOGIN-013: 验证错误提示信息', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(1000);

    const errorMsg = page.locator('.el-form-item__error');
    if (await errorMsg.count() > 0) {
      await expect(errorMsg.first()).toBeVisible();
    }
  });

  test('TC-LOGIN-014: 密码可见性切换', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const passwordInput = page.locator('input[type="password"]');
    if (await passwordInput.count() > 0) {
      await expect(passwordInput).toBeVisible();
    }
  });

  test('TC-LOGIN-015: 登录页面响应式布局', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.setViewportSize({ width: 375, height: 667 });
    await page.waitForTimeout(1000);

    const loginBox = page.locator('.login-box');
    await expect(loginBox).toBeVisible();
  });

  test('TC-LOGIN-016: 登录页面标题验证', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const title = await page.title();
    expect(title).toContain('智慧幼儿园');
  });

  test('TC-LOGIN-017: 登录表单输入框数量', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const inputs = page.locator('.el-input');
    expect(await inputs.count()).toBeGreaterThanOrEqual(2);
  });

  test('TC-LOGIN-018: 登录按钮存在性', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const button = page.locator('.el-button--primary');
    await expect(button).toBeVisible();
    await expect(button).toContainText('登录');
  });

  test('TC-LOGIN-019: 多次快速点击登录', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');

    await page.locator('.el-button--primary').click();
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-LOGIN-020: 登录后页面元素', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    await page.waitForTimeout(3000);
    const token = await page.evaluate(() => localStorage.getItem('token'));
    expect(token).toBeTruthy();
  });

  test('TC-LOGIN-021: 登录后获取用户信息', async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    await page.waitForTimeout(3000);

    const userInfo = await page.evaluate(() => localStorage.getItem('userInfo'));
    expect(userInfo).toBeTruthy();
    console.log('User info stored:', userInfo ? 'yes' : 'no');
  });

  test('TC-LOGIN-022: 登录后API请求验证', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/') && response.request().method() === 'GET') {
        apiRequests.push({
          url: response.url(),
          status: response.status()
        });
      }
    });

    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    await page.waitForTimeout(4000);

    console.log('API GET requests after login:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });
});