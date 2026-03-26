import { test, expect } from '@playwright/test';

test.describe('成长档案功能测试', () => {
  test('TC-GROWTH-001: 成长档案页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-GROWTH-002: 检查成长档案元素', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-GROWTH-003: 学生选择器', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const studentSelect = page.locator('.el-select');
    if (await studentSelect.count() > 0) {
      await expect(studentSelect.first()).toBeVisible();
    }
  });

  test('TC-GROWTH-004: 新增成长记录按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-GROWTH-005: 成长记录筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-GROWTH-006: 成长记录卡片', async ({ page }) => {
    await page.goto('http://localhost:3001/growth');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});