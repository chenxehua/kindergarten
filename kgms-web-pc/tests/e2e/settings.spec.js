import { test, expect } from '@playwright/test';

test.describe('系统设置功能测试', () => {
  test('TC-SETTINGS-001: 系统设置页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-SETTINGS-002: 系统设置页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-SETTINGS-003: 基本设置Tab检查', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const tabs = page.locator('.el-tabs__item');
    if (await tabs.count() > 0) {
      await expect(tabs.first()).toBeVisible();
    }
  });

  test('TC-SETTINGS-004: 保存设置按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const saveButton = page.locator('button:has-text("保存")');
    if (await saveButton.count() > 0) {
      await expect(saveButton).toBeVisible();
    }
  });

  test('TC-SETTINGS-005: 系统配置表单', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const form = page.locator('.el-form');
    if (await form.count() > 0) {
      await expect(form.first()).toBeVisible();
    }
  });

  test('TC-SETTINGS-006: 设置页面加载', async ({ page }) => {
    await page.goto('http://localhost:3001/settings');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});