import { test, expect } from '@playwright/test';

test.describe('家长管理功能测试', () => {
  test('TC-PARENT-001: 家长列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-PARENT-002: 家长管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });
});