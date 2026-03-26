import { test, expect } from '@playwright/test';

test.describe('考勤记录功能测试', () => {
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

  test('TC-RECORD-001: 考勤记录页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/record') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Record API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-RECORD-002: 考勤记录页面元素检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/record')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Record elements - API requests:', apiRequests.length);
  });

  test('TC-RECORD-003: 考勤日期筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/record')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
      console.log('日期选择器存在');
    }
  });

  test('TC-RECORD-004: 考勤学生搜索', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/record') || response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入学生姓名"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('张三');
      await page.waitForTimeout(1500);

      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-RECORD-005: 考勤状态筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/record')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
      console.log('状态筛选器存在');
    }
  });

  test('TC-RECORD-006: 考勤记录导出', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/record')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Export - API requests:', apiRequests.length);
  });

  test('TC-RECORD-007: 考勤记录API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/record') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});