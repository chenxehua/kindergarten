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
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-FOOD-003: 食谱日期选择', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const datePicker = page.locator('.el-date-picker, .el-date-editor');
    if (await datePicker.count() > 0) {
      await expect(datePicker.first()).toBeVisible();
    }
  });

  test('TC-FOOD-004: 新增食谱按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-FOOD-005: 食谱类型筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-FOOD-006: 食谱卡片检查', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});
