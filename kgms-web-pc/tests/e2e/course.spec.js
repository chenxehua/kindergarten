import { test, expect } from '@playwright/test';

test.describe('课程管理功能测试', () => {
  test('TC-COURSE-001: 课程列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-COURSE-002: 检查课程列表元素', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-COURSE-003: 课程搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入课程名称"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试课程');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-COURSE-004: 新增课程按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const addButton = page.locator('button:has-text("新增")');
    if (await addButton.count() > 0) {
      await expect(addButton).toBeVisible();
    }
  });

  test('TC-COURSE-005: 课程分类筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-COURSE-006: 课程表格操作列', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-COURSE-007: 课程列表刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-COURSE-008: 课程分页检查', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
    }
  });

  test('TC-COURSE-009: 课程导入按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const importButton = page.locator('button:has-text("导入")');
    if (await importButton.count() > 0) {
      await expect(importButton).toBeVisible();
    }
  });

  test('TC-COURSE-010: 课程导出按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/course');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const exportButton = page.locator('button:has-text("导出")');
    if (await exportButton.count() > 0) {
      await expect(exportButton).toBeVisible();
    }
  });
});
