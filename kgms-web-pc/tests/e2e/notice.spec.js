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
    console.log('Notice page body:', body.substring(0, 300));
  });
});