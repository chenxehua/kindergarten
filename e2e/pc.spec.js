/**
 * 智慧幼儿园 PC 管理后台 E2E 测试
 */

const { test, expect } = require('@playwright/test');

test.describe('智慧幼儿园 PC 管理后台 E2E 测试', () => {
  
  // ==================== 登录模块 ====================
  test.describe('登录模块', () => {
    test('登录页面加载', async ({ page }) => {
      await page.goto('/login');
      await page.waitForTimeout(2000);
    });

    test('学生管理页面', async ({ page }) => {
      await page.goto('/student');
      await page.waitForTimeout(2000);
    });

    test('班级管理页面', async ({ page }) => {
      await page.goto('/class');
      await page.waitForTimeout(2000);
    });

    test('通知公告页面', async ({ page }) => {
      await page.goto('/notice');
      await page.waitForTimeout(2000);
    });

    test('食谱管理页面', async ({ page }) => {
      await page.goto('/food');
      await page.waitForTimeout(2000);
    });

    test('课程管理页面', async ({ page }) => {
      await page.goto('/course');
      await page.waitForTimeout(2000);
    });

    test('成长记录页面', async ({ page }) => {
      await page.goto('/record');
      await page.waitForTimeout(2000);
    });

    test('视频管理页面', async ({ page }) => {
      await page.goto('/video');
      await page.waitForTimeout(2000);
    });

    test('成长画像页面', async ({ page }) => {
      await page.goto('/growth');
      await page.waitForTimeout(2000);
    });
  });
});
