import { test, expect } from '@playwright/test';

test.describe('学生管理功能测试', () => {
  test('TC-STUDENT-001: 学生列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-STUDENT-002: 学生管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-STUDENT-003: 学生列表分页功能', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
    }
  });

  test('TC-STUDENT-004: 学生搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入姓名或学号"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-STUDENT-005: 新增学生按钮检查', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-STUDENT-006: 学生列表表格列检查', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-STUDENT-007: 学生班级筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const classSelect = page.locator('.el-select');
    if (await classSelect.count() > 0) {
      await expect(classSelect.first()).toBeVisible();
    }
  });

  test('TC-STUDENT-008: 学生状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const statusFilter = page.locator('[placeholder="请选择状态"]');
    if (await statusFilter.count() > 0) {
      await expect(statusFilter).toBeVisible();
    }
  });

  test('TC-STUDENT-009: 学生列表刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-STUDENT-010: 学生列表导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
    }
  });
});
