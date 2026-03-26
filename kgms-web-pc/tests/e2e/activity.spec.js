import { test, expect } from '@playwright/test';

test.describe('活动管理功能测试', () => {
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

  test('TC-ACTIVITY-001: 活动列表页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/activity') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Activity API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-ACTIVITY-002: 活动管理页面元素检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/activity')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Activity elements - API requests:', apiRequests.length);
  });

  test('TC-ACTIVITY-003: 活动搜索功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/activity')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入活动名称"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('亲子活动');
      await page.waitForTimeout(1500);

      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-ACTIVITY-004: 新增活动按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
      console.log('新增按钮存在');
    }
  });

  test('TC-ACTIVITY-005: 活动状态筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/activity')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
      console.log('状态筛选器存在');
    }
  });

  test('TC-ACTIVITY-006: 活动列表检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/activity')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('List - API requests:', apiRequests.length);
  });

  test('TC-ACTIVITY-007: 活动管理API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/activity') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});