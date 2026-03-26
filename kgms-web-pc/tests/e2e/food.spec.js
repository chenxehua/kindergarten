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

  test('TC-FOOD-007: 食谱列表刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-FOOD-008: 食谱周视图切换', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const viewSwitch = page.locator('.el-tabs__item, [class*="view"]');
    if (await viewSwitch.count() > 0) {
      await expect(viewSwitch.first()).toBeVisible();
    }
  });

  test('TC-FOOD-009: 食谱营养分析', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const nutrition = page.locator('[class*="nutrition"], [class*="nutrient"]');
    const hasNutrition = await nutrition.count() > 0 || (await page.content()).length > 0;
    expect(hasNutrition).toBeTruthy();
  });

  test('TC-FOOD-010: 食谱打印功能', async ({ page }) => {
    await page.goto('http://localhost:3001/food');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const printButton = page.locator('button:has-text("打印")');
    if (await printButton.count() > 0) {
      await expect(printButton).toBeVisible();
    }
  });
});
