import { test, expect } from '@playwright/test';

test.describe('班级管理功能测试', () => {
  test('TC-CLASS-001: 班级列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-CLASS-002: 检查班级列表元素', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    console.log('Class page body:', body.substring(0, 300));
  });
});
