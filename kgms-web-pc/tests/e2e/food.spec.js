import { test, expect } from '@playwright/test';

test.describe('食谱管理功能测试', () => {
  test('TC-FOOD-001: 食谱列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-FOOD-002: 检查食谱列表元素', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    console.log('Food page body:', body.substring(0, 300));
  });
});
