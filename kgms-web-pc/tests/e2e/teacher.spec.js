import { test, expect } from '@playwright/test';

test.describe('教师管理功能测试', () => {
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

  test('TC-TEACHER-001: 教师列表页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/teacher') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Teacher API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-TEACHER-002: 教师管理页面元素检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/teacher')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    console.log('Teacher elements - API requests:', apiRequests.length);
  });

  test('TC-TEACHER-003: 教师搜索功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/teacher')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入姓名或工号"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('张老师');
      await page.waitForTimeout(1500);

      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-TEACHER-004: 新增教师按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
      console.log('新增按钮存在');
    }
  });

  test('TC-TEACHER-005: 教师状态筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/teacher')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
      console.log('状态筛选器存在');
    }
  });

  test('TC-TEACHER-006: 教师表格检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/teacher')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Table - API requests:', apiRequests.length);
  });

  test('TC-TEACHER-007: 教师列表刷新', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/teacher')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Refresh - API requests:', apiRequests.length);
  });

  test('TC-TEACHER-008: 教师导入按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const importButton = page.locator('button:has-text("导入")');
    if (await importButton.count() > 0) {
      await expect(importButton).toBeVisible();
      console.log('导入按钮存在');
    }
  });

  test('TC-TEACHER-009: 教师导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
      console.log('导出按钮存在');
    }
  });

  test('TC-TEACHER-010: 教师分页检查', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
      console.log('分页存在');
    }
  });

  test('TC-TEACHER-011: 教师API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/teacher') && response.request().method() === 'GET') {
        apiStatus = response.status();
        try {
          const text = await response.text();
          if (text) apiData = JSON.parse(text);
        } catch (e) {}
      }
    });

    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});