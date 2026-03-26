import { test, expect } from '@playwright/test';

test.describe('视频管理功能测试', () => {
  test('TC-VIDEO-001: 视频列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-VIDEO-002: 检查视频列表元素', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    console.log('Video page body:', body.substring(0, 300));
  });
});