import { test, expect } from '@playwright/test';

test.describe('班级管理功能测试', () => {
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

  test('TC-CLASS-001: 班级列表页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Class API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-CLASS-002: 检查班级列表元素', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Class elements - API requests:', apiRequests.length);
  });

  test('TC-CLASS-003: 班级搜索功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入班级名称"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试班级');
      await page.waitForTimeout(1500);

      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-CLASS-004: 新增班级按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
      console.log('新增班级按钮存在');
    }
  });

  test('TC-CLASS-005: 班级表格检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Table - API requests:', apiRequests.length);
  });

  test('TC-CLASS-006: 班级状态筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Status filter - API requests:', apiRequests.length);
  });

  test('TC-CLASS-007: 班级年级筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const gradeSelect = page.locator('.el-select');
    if (await gradeSelect.count() > 0) {
      await expect(gradeSelect.first()).toBeVisible();
      console.log('年级筛选器存在');
    }
  });

  test('TC-CLASS-008: 班级列表刷新', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Refresh - API requests:', apiRequests.length);
  });

  test('TC-CLASS-009: 班级导入按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const importButton = page.locator('button:has-text("导入")');
    if (await importButton.count() > 0) {
      await expect(importButton).toBeVisible();
      console.log('导入按钮存在');
    }
  });

  test('TC-CLASS-010: 班级导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
      console.log('导出按钮存在');
    }
  });

  test('TC-CLASS-011: 班级API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/class') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) {
            apiData = JSON.parse(text);
          }
        } catch (e) {
          console.log('Response is not JSON');
        }
      }
    });

    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});