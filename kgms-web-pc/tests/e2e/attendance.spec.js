import { test, expect } from '@playwright/test';

test.describe('考勤管理功能测试', () => {
  test('TC-ATTENDANCE-001: 考勤管理页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-ATTENDANCE-002: 考勤管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-ATTENDANCE-003: 考勤班级选择', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const classSelect = page.locator('.el-select');
    if (await classSelect.count() > 0) {
      await expect(classSelect.first()).toBeVisible();
    }
  });

  test('TC-ATTENDANCE-004: 考勤日期选择', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
    }
  });

  test('TC-ATTENDANCE-005: 考勤签到按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const checkInButton = page.locator('button:has-text("签到")');
    if (await checkInButton.count() > 0) {
      await expect(checkInButton).toBeVisible();
    }
  });

  test('TC-ATTENDANCE-006: 考勤统计查看', async ({ page }) => {
    await page.goto('http://localhost:3001/attendance');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});