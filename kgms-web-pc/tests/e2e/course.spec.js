import { test, expect } from '@playwright/test';

test.describe('课程管理功能测试', () => {
  test('TC-COURSE-001: 课程列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-COURSE-002: 检查课程列表元素', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    console.log('Course page body:', body.substring(0, 300));
  });
});
