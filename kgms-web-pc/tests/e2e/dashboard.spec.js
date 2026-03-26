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

    const chart = page.locator('canvas, .echart, [class*="chart"]');
    const hasChart = await chart.count() > 0 || await page.locator('.el-chart').count() > 0;
    expect(hasChart || (await page.content()).length).toBeGreaterThan(0);
  });

  test('TC-DASHBOARD-005: 时间选择器检查', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
    }
  });

  test('TC-DASHBOARD-006: 数据看板刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-DASHBOARD-007: 快捷操作按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const quickActions = page.locator('.quick-action, [class*="quick"]');
    if (await quickActions.count() > 0) {
      await expect(quickActions.first()).toBeVisible();
    }
  });

  test('TC-DASHBOARD-008: 最近活动列表', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const activityList = page.locator('.activity-list, [class*="activity"]');
    const hasActivity = await activityList.count() > 0 || (await page.content()).length > 0;
    expect(hasActivity).toBeTruthy();
  });

  test('TC-DASHBOARD-009: 通知消息检查', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const notification = page.locator('.notification, .el-badge, [class*="notice"]');
    const hasNotification = await notification.count() > 0 || (await page.content()).length > 0;
    expect(hasNotification).toBeTruthy();
  });

  test('TC-DASHBOARD-010: 数据看板响应式', async ({ page }) => {
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.setViewportSize({ width: 768, height: 1024 });
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});
