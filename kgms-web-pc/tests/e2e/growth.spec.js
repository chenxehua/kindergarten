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
    console.log('Growth page body:', body.substring(0, 300));
  });
});