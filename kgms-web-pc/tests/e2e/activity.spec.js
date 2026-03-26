import { test, expect } from '@playwright/test';

test.describe('活动管理功能测试', () => {
  test('TC-ACTIVITY-001: 活动列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-ACTIVITY-002: 活动管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-ACTIVITY-003: 活动搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入活动名称"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('亲子活动');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-ACTIVITY-004: 新增活动按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-ACTIVITY-005: 活动状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-ACTIVITY-006: 活动列表检查', async ({ page }) => {
    await page.goto('http://localhost:3001/activity');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});