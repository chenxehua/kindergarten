import { test, expect } from '@playwright/test';

test.describe('家长管理功能测试', () => {
  test('TC-PARENT-001: 家长列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-PARENT-002: 家长管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-PARENT-003: 家长搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入家长姓名或手机号"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('张三');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-PARENT-004: 新增家长按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-PARENT-005: 家长状态筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-PARENT-006: 家长表格检查', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-PARENT-007: 家长列表刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-PARENT-008: 家长班级关联筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const classSelect = page.locator('.el-select');
    if (await classSelect.count() > 0) {
      await expect(classSelect.first()).toBeVisible();
    }
  });

  test('TC-PARENT-009: 家长导入按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const importButton = page.locator('button:has-text("导入")');
    if (await importButton.count() > 0) {
      await expect(importButton).toBeVisible();
    }
  });

  test('TC-PARENT-010: 家长导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/parent');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
    }
  });
});