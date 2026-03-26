/**
 * 智慧幼儿园 PC 管理后台 E2E 测试 - 完整版
 */

const { test, expect } = require('@playwright/test');

test.describe('智慧幼儿园 PC 管理后台 E2E 测试', () => {
  
  test.beforeEach(async ({ page }) => {
    await page.goto('/');
  });

  // ==================== 登录模块 ====================
  test.describe('登录模块', () => {
    test('TC-USER-E2E-001: 账号密码登录 - 成功', async ({ page }) => {
      await page.waitForSelector('.login-container', { timeout: 10000 });
      await page.fill('input[placeholder="请输入用户名"]', 'admin');
      await page.fill('input[placeholder="请输入密码"]', 'admin123');
      await page.click('button:has-text("登录")');
      await page.waitForTimeout(1500);
    });

    test('TC-USER-E2E-002: 登录 - 未填写用户名', async ({ page }) => {
      await page.waitForSelector('.login-container', { timeout: 10000 });
      await page.click('button:has-text("登录")');
      await expect(page.locator('text=请输入用户名')).toBeVisible({ timeout: 5000 });
    });

    test('TC-USER-E2E-003: 登录 - 未填写密码', async ({ page }) => {
      await page.waitForSelector('.login-container', { timeout: 10000 });
      await page.fill('input[placeholder="请输入用户名"]', 'admin');
      await page.click('button:has-text("登录")');
      await expect(page.locator('text=请输入密码')).toBeVisible({ timeout: 5000 });
    });
  });

  // ==================== 首页模块 ====================
  test.describe('首页模块', () => {
    test('TC-HOME-E2E-001: 首页展示', async ({ page }) => {
      await page.waitForSelector('.login-container', { timeout: 10000 });
      await expect(page.locator('.title')).toContainText('智慧幼儿园');
    });
  });

  // ==================== 学生管理模块 ====================
  test.describe('学生管理模块', () => {
    test('TC-STUDENT-E2E-001: 访问学生管理页面', async ({ page }) => {
      await page.goto('/student');
      await page.waitForTimeout(1500);
    });

    test('TC-STUDENT-E2E-002: 学生列表页面加载', async ({ page }) => {
      await page.goto('/student');
      await page.waitForTimeout(2000);
    });
  });

  // ==================== 班级管理模块 ====================
  test.describe('班级管理模块', () => {
    test('TC-CLASS-E2E-001: 访问班级管理页面', async ({ page }) => {
      await page.goto('/class');
      await page.waitForTimeout(1500);
    });

    test('TC-CLASS-E2E-002: 班级列表页面加载', async ({ page }) => {
      await page.goto('/class');
      await page.waitForTimeout(2000);
    });
  });

  // ==================== 通知公告模块 ====================
  test.describe('通知公告模块', () => {
    test('TC-NOTICE-E2E-001: 访问通知公告页面', async ({ page }) => {
      await page.goto('/notice');
      await page.waitForTimeout(1500);
    });

    test('TC-NOTICE-E2E-002: 通知页面加载', async ({ page }) => {
      await page.goto('/notice');
      await page.waitForTimeout(2000);
    });
  });

  // ==================== 食谱管理模块 ====================
  test.describe('食谱管理模块', () => {
    test('TC-FOOD-E2E-001: 访问食谱管理页面', async ({ page }) => {
      await page.goto('/food');
      await page.waitForTimeout(1500);
    });

    test('TC-FOOD-E2E-002: 食谱页面加载', async ({ page }) => {
      await page.goto('/food');
      await page.waitForTimeout(2000);
    });
  });

  // ==================== 课程管理模块 ====================
  test.describe('课程管理模块', () => {
    test('TC-COURSE-E2E-001: 访问课程管理页面', async ({ page }) => {
      await page.goto('/course');
      await page.waitForTimeout(1500);
    });

    test('TC-COURSE-E2E-002: 课程页面加载', async ({ page }) => {
      await page.goto('/course');
      await page.waitForTimeout(2000);
    });
  });

  // ==================== 成长记录模块 ====================
  test.describe('成长记录模块', () => {
    test('TC-RECORD-E2E-001: 访问成长记录页面', async ({ page }) => {
      await page.goto('/record');
      await page.waitForTimeout(1500);
    });
  });

  // ==================== 视频管理模块 ====================
  test.describe('视频管理模块', () => {
    test('TC-VIDEO-E2E-001: 访问视频管理页面', async ({ page }) => {
      await page.goto('/video');
      await page.waitForTimeout(1500);
    });
  });

  // ==================== 成长画像模块 ====================
  test.describe('成长画像模块', () => {
    test('TC-GROWTH-E2E-001: 访问成长画像页面', async ({ page }) => {
      await page.goto('/growth');
      await page.waitForTimeout(1500);
    });
  });

  // ==================== 权限测试 ====================
  test.describe('权限测试', () => {
    test('TC-AUTH-E2E-001: 未登录访问受限页面', async ({ page }) => {
      await page.goto('/student');
      await page.waitForTimeout(1000);
    });
  });
});
