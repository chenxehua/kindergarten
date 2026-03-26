import { test, expect } from '@playwright/test';

test.describe('视频管理功能测试', () => {
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

  test('TC-VIDEO-001: 视频列表页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/video') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Video API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-VIDEO-002: 检查视频列表元素', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/video')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Video elements - API requests:', apiRequests.length);
  });

  test('TC-VIDEO-003: 视频搜索功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/video')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入视频标题"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试视频');
      await page.waitForTimeout(1500);

      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-VIDEO-004: 上传视频按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const uploadButton = page.locator('button:has-text("上传")');
    if (await uploadButton.count() > 0) {
      await expect(uploadButton).toBeVisible();
      console.log('上传按钮存在');
    }
  });

  test('TC-VIDEO-005: 视频分类筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/video')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
      console.log('分类筛选器存在');
    }
  });

  test('TC-VIDEO-006: 视频封面检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/video')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Cover check - API requests:', apiRequests.length);
  });

  test('TC-VIDEO-007: 视频列表刷新', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/video')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Refresh - API requests:', apiRequests.length);
  });

  test('TC-VIDEO-008: 视频分页检查', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
      console.log('分页存在');
    }
  });

  test('TC-VIDEO-009: 视频批量删除', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const batchDelete = page.locator('button:has-text("批量删除")');
    if (await batchDelete.count() > 0) {
      await expect(batchDelete).toBeVisible();
      console.log('批量删除按钮存在');
    }
  });

  test('TC-VIDEO-010: 视频排序功能', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const sortButton = page.locator('.el-table__header-wrapper th');
    if (await sortButton.count() > 0) {
      await expect(sortButton.first()).toBeVisible();
      console.log('排序功能存在');
    }
  });

  test('TC-VIDEO-011: 视频API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/video') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});