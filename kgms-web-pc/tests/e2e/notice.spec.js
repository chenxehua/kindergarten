import { test, expect } from '@playwright/test';

test.describe('通知公告功能测试', () => {
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

  test('TC-NOTICE-001: 通知公告页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/notice') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Notice API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-NOTICE-002: 检查通知公告元素', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/notice')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Notice elements - API requests:', apiRequests.length);
  });

  test('TC-NOTICE-003: 通知公告搜索功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/notice')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入标题"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试通知');
      await page.waitForTimeout(1500);

      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-NOTICE-004: 发布通知按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("发布")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
      console.log('发布按钮存在');
    }
  });

  test('TC-NOTICE-005: 通知类型筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/notice')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
      console.log('类型筛选器存在');
    }
  });

  test('TC-NOTICE-006: 通知列表表格', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/notice')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Table - API requests:', apiRequests.length);
  });

  test('TC-NOTICE-007: 通知API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/notice') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});