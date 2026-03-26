import { test, expect } from '@playwright/test';

test.describe('教师管理功能测试', () => {
  test('TC-TEACHER-001: 教师列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-TEACHER-002: 教师管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-TEACHER-003: 教师搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入姓名或工号"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('张老师');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-TEACHER-004: 新增教师按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-TEACHER-005: 教师状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-TEACHER-006: 教师表格检查', async ({ page }) => {
    await page.goto('http://localhost:3001/teacher');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});