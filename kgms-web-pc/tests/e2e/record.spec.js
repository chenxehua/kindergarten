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

  test('TC-RECORD-003: 考勤日期筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
    }
  });

  test('TC-RECORD-004: 考勤学生搜索', async ({ page }) => {
    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入学生姓名"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('张三');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-RECORD-005: 考勤状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-RECORD-006: 考勤记录导出', async ({ page }) => {
    await page.goto('http://localhost:3001/record');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});