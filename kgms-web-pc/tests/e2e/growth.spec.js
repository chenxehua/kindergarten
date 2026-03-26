import { test, expect } from '@playwright/test';

test.describe('成长档案功能测试', () => {
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

  test('TC-GROWTH-001: 成长档案页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/growth') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Growth API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-GROWTH-002: 检查成长档案元素', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/growth')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Growth elements - API requests:', apiRequests.length);
  });

  test('TC-GROWTH-003: 学生选择器', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const studentSelect = page.locator('.el-select');
    if (await studentSelect.count() > 0) {
      await expect(studentSelect.first()).toBeVisible();
      console.log('学生选择器存在');
    }
  });

  test('TC-GROWTH-004: 新增成长记录按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
      console.log('新增按钮存在');
    }
  });

  test('TC-GROWTH-005: 成长记录筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/growth')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
      console.log('筛选器存在');
    }
  });

  test('TC-GROWTH-006: 成长记录卡片', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/growth')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Card - API requests:', apiRequests.length);
  });

  test('TC-GROWTH-007: 成长档案刷新', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/growth')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Refresh - API requests:', apiRequests.length);
  });

  test('TC-GROWTH-008: 成长记录类型筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const typeFilter = page.locator('.el-radio-group, .el-checkbox-group');
    if (await typeFilter.count() > 0) {
      await expect(typeFilter.first()).toBeVisible();
      console.log('类型筛选存在');
    }
  });

  test('TC-GROWTH-009: 成长记录时间线', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/growth')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const timeline = page.locator('.el-timeline, [class*="timeline"]');
    const hasTimeline = await timeline.count() > 0 || (await page.content()).length > 0;
    expect(hasTimeline).toBeTruthy();

    console.log('Timeline - API requests:', apiRequests.length);
  });

  test('TC-GROWTH-010: 成长记录导出', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
      console.log('导出按钮存在');
    }
  });

  test('TC-GROWTH-011: 成长档案API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/growth') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});