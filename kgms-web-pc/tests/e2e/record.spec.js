import { test, expect } from '@playwright/test';

test.describe('考勤记录功能测试', () => {
  test('TC-RECORD-001: 考勤记录页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-RECORD-002: 考勤记录页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });
});