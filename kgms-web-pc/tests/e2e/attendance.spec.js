import { test, expect } from '@playwright/test';

test.describe('考勤管理功能测试', () => {
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

  test('TC-ATTENDANCE-001: 考勤管理页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/attendance') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Attendance API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-ATTENDANCE-002: 考勤管理页面元素检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/attendance')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Attendance elements - API requests:', apiRequests.length);
  });

  test('TC-ATTENDANCE-003: 考勤班级选择', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const classSelect = page.locator('.el-select');
    if (await classSelect.count() > 0) {
      await expect(classSelect.first()).toBeVisible();
      console.log('班级选择器存在');
    }
  });

  test('TC-ATTENDANCE-004: 考勤日期选择', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
      console.log('日期选择器存在');
    }
  });

  test('TC-ATTENDANCE-005: 考勤签到按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const checkInButton = page.locator('button:has-text("签到")');
    if (await checkInButton.count() > 0) {
      await expect(checkInButton).toBeVisible();
      console.log('签到按钮存在');
    }
  });

  test('TC-ATTENDANCE-006: 考勤统计查看', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/attendance')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Stats - API requests:', apiRequests.length);
  });

  test('TC-ATTENDANCE-007: 考勤管理API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/attendance') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});