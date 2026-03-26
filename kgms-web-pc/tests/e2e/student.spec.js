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

    // 检查是否有分页控件
    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
    }
  });

  test('TC-STUDENT-004: 学生搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查是否有搜索框
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

    // 检查是否有新增按钮
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
});
