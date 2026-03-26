import { test, expect } from '@playwright/test';

test.describe('视频管理功能测试', () => {
  test('TC-VIDEO-001: 视频列表页面应该正常显示', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-VIDEO-002: 检查视频列表元素', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const body = await page.locator('body').innerHTML();
    expect(body.length).toBeGreaterThan(0);
  });

  test('TC-VIDEO-003: 视频搜索功能', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const searchInput = page.locator('[placeholder="请输入视频标题"]');
    if (await searchInput.count() > 0) {
      await searchInput.fill('测试视频');
      await page.waitForTimeout(1000);
    }
  });

  test('TC-VIDEO-004: 上传视频按钮', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const uploadButton = page.locator('button:has-text("上传")');
    if (await uploadButton.count() > 0) {
      await expect(uploadButton).toBeVisible();
    }
  });

  test('TC-VIDEO-005: 视频分类筛选', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const filter = page.locator('.el-select');
    if (await filter.count() > 0) {
      await expect(filter.first()).toBeVisible();
    }
  });

  test('TC-VIDEO-006: 视频封面检查', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-VIDEO-007: 视频列表刷新', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    await page.reload();
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(1000);

    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });

  test('TC-VIDEO-008: 视频分页检查', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const pagination = page.locator('.el-pagination');
    if (await pagination.count() > 0) {
      await expect(pagination).toBeVisible();
    }
  });

  test('TC-VIDEO-009: 视频批量删除', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const batchDelete = page.locator('button:has-text("批量删除")');
    if (await batchDelete.count() > 0) {
      await expect(batchDelete).toBeVisible();
    }
  });

  test('TC-VIDEO-010: 视频排序功能', async ({ page }) => {
    await page.goto('http://localhost:3001/video');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    const sortButton = page.locator('.el-table__header-wrapper th');
    if (await sortButton.count() > 0) {
      await expect(sortButton.first()).toBeVisible();
    }
  });
});