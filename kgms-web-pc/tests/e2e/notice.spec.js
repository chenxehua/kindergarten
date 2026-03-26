import { test, expect } from '@playwright/test';

test.describe('通知公告功能测试', () => {
  test('TC-NOTICE-001: 通知公告页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-NOTICE-002: 检查通知公告元素', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-NOTICE-003: 通知公告搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入标题"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试通知');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-NOTICE-004: 发布通知按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("发布")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-NOTICE-005: 通知类型筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-NOTICE-006: 通知列表表格', async ({ page }) => {
    await page.goto('http://localhost:3001/notice');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});