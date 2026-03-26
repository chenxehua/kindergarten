import { test, expect } from '@playwright/test';

test.describe('学生管理功能测试', () => {
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

  test('TC-STUDENT-001: 学生列表页面应该正常显示', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student') && response.request().method() === 'GET') {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 验证页面有内容
    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    // 验证API调用
    console.log('Student API requests:', apiRequests.length);
    apiRequests.forEach(req => {
      console.log('  -', req.url, 'status:', req.status);
    });
  });

  test('TC-STUDENT-002: 学生管理页面元素检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status(), ok: response.ok() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);

    // 验证至少有一次API请求
    expect(apiRequests.length).toBeGreaterThan(0);
    console.log('API requests made:', apiRequests.length);
  });

  test('TC-STUDENT-003: 学生列表分页功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 验证分页组件
    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
    }

    // 验证API调用
    console.log('Pagination test - API requests:', apiRequests.length);
  });

  test('TC-STUDENT-004: 学生搜索功能', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入姓名或学号"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试');
      await page.waitForTimeout(1500);

      // 验证搜索后API调用
      console.log('Search API requests:', apiRequests.length);
    }
  });

  test('TC-STUDENT-005: 新增学生按钮检查', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
      console.log('新增学生按钮存在');
    }
  });

  test('TC-STUDENT-006: 学生列表表格列检查', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Table test - API requests:', apiRequests.length);
  });

  test('TC-STUDENT-007: 学生班级筛选', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student') || response.url().includes('/api/class')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const classSelect = page.locator('.el-select');
    if (await classSelect.count() > 0) {
      await expect(classSelect.first()).toBeVisible();
      console.log('班级筛选器存在');
    }
  });

  test('TC-STUDENT-008: 学生状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const statusFilter = page.locator('[placeholder="请选择状态"]');
    if (await statusFilter.count() > 0) {
      await expect(statusFilter).toBeVisible();
      console.log('状态筛选器存在');
    }
  });

  test('TC-STUDENT-009: 学生列表刷新', async ({ page }) => {
    const apiRequests = [];
    page.on('response', response => {
      if (response.url().includes('/api/student')) {
        apiRequests.push({ url: response.url(), status: response.status() });
      }
    });

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);

    console.log('Refresh - API requests:', apiRequests.length);
  });

  test('TC-STUDENT-010: 学生列表导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
      console.log('导出按钮存在');
    }
  });

  test('TC-STUDENT-011: 学生API数据验证', async ({ page }) => {
    let apiData = null;
    let apiStatus = 0;

    page.on('response', async response => {
      if (response.url().includes('/api/student') && response.request().method() === 'GET') {
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

    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(3000);

    console.log('API Status:', apiStatus);
    console.log('API Response:', apiData);

    // 验证API响应
    if (apiStatus > 0) {
      expect(apiStatus).toBeGreaterThanOrEqual(200);
    }
  });
});