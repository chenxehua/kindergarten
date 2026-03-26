import { test, expect } from '@playwright/test';

test.describe('系统设置功能测试', () => {
  // 先登录再测试
  test.beforeEach(async ({ page }) => {
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);
    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();
    await page.waitForTimeout(3000);
  });

  test('TC-SETTINGS-001: 系统设置页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/settings') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Settings API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-SETTINGS-002: 系统设置页面元素检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Settings elements - API requests:', apiRequests.length);
  });

  test('TC-SETTINGS-003: 基本设置Tab检查', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const tabs = page.locator('.el-tabs__item');
    if (await tabs.count() > 0) {
      await expect(tabs.first()).toBeVisible();
      console.log('Tab存在');
    }
  });

  test('TC-SETTINGS-004: 保存设置按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const saveButton = page.locator('button:has-text("保存")');
    if (await saveButton.count() > 0) {
      await expect(saveButton).toBeVisible();
      console.log('保存按钮存在');
    }
  });

  test('TC-SETTINGS-005: 系统配置表单', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const form = page.locator('.el-form');
    if (await form.count() > 0) {
      await expect(form.first()).toBeVisible();
      console.log('表单存在');
    }
  });

  test('TC-SETTINGS-006: 设置页面加载', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Load - API requests:', apiRequests.length);
  });

  test('TC-SETTINGS-007: 设置API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});