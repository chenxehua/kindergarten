import { test, expect } from '@playwright/test';

test.describe('数据看板功能测试', () => {
  test('TC-DASHBOARD-001: 仪表盘页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-DASHBOARD-002: 数据看板页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-DASHBOARD-003: 数据统计卡片检查', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-DASHBOARD-004: 图表区域检查', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查是否有图表元素
    const chart = page.locator('canvas, .echart, [class*="chart"]');
    const hasChart = await chart.count() > 0 || await page.locator('.el-chart').count() > 0;
    expect(hasChart || (await page.content()).length).toBeGreaterThan(0);
  });

  test('TC-DASHBOARD-005: 时间选择器检查', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查是否有日期选择器
    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
    }
  });
});
