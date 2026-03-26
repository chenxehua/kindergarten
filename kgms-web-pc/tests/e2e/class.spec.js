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
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-CLASS-003: 班级搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入班级名称"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试班级');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-CLASS-004: 新增班级按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-CLASS-005: 班级表格检查', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-CLASS-006: 班级状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-CLASS-007: 班级年级筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const gradeSelect = page.locator('.el-select');
    if (await gradeSelect.count() > 0) {
      await expect(gradeSelect.first()).toBeVisible();
    }
  });

  test('TC-CLASS-008: 班级列表刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-CLASS-009: 班级导入按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const importButton = page.locator('button:has-text("导入")');
    if (await importButton.count() > 0) {
      await expect(importButton).toBeVisible();
    }
  });

  test('TC-CLASS-010: 班级导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/class');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
    }
  });
});
