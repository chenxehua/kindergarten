/**
 * 智慧幼儿园小程序 E2E 测试
 * 使用 Playwright
 * 
 * 运行方式:
 *   npx playwright test
 *   npx playwright test --ui
 */

const { test, expect } = require('@playwright/test');

test.describe('智慧幼儿园小程序 E2E 测试', () => {
  
  test.beforeEach(async ({ page }) => {
    // 打开小程序页面（这里用本地开发服务器地址）
    await page.goto('http://localhost:8080');
  });

  test.describe('登录模块', () => {
    
    test('TC-USER-E2E-001: 手机号密码登录 - 成功', async ({ page }) => {
      // 1. 进入登录页面
      await page.click('text=账号密码登录');
      
      // 2. 输入手机号
      await page.fill('input[type="tel"]', '13800138000');
      
      // 3. 输入密码
      await page.fill('input[type="password"]', '123456');
      
      // 4. 点击登录按钮
      await page.click('button:text("登录")');
      
      // 5. 验证登录成功
      await expect(page).toHaveURL(/.*home/);
      await expect(page.locator('text=欢迎回来')).toBeVisible();
    });

    test('TC-USER-E2E-002: 手机号密码登录 - 密码错误', async ({ page }) => {
      // 1. 进入登录页面
      await page.click('text=账号密码登录');
      
      // 2. 输入手机号
      await page.fill('input[type="tel"]', '13800138000');
      
      // 3. 输入错误密码
      await page.fill('input[type="password"]', 'wrongpassword');
      
      // 4. 点击登录按钮
      await page.click('button:text("登录")');
      
      // 5. 验证提示错误信息
      await expect(page.locator('text=密码错误')).toBeVisible();
    });

    test('TC-USER-E2E-003: 微信授权登录', async ({ page }) => {
      // 1. 点击微信登录按钮
      await page.click('button:text("微信授权登录")');
      
      // 2. 验证跳转或弹窗（根据实际实现）
      // 这里验证微信授权流程
      await expect(page.locator('text=授权中')).toBeVisible();
    });
  });

  test.describe('首页模块', () => {
    
    test('TC-HOME-E2E-001: 首页展示孩子信息', async ({ page }) => {
      // 1. 已登录状态进入首页
      await page.goto('http://localhost:8080/home');
      
      // 2. 验证孩子姓名显示
      await expect(page.locator('text=张三')).toBeVisible();
      
      // 3. 验证班级信息
      await expect(page.locator('text=星星班')).toBeVisible();
    });

    test('TC-HOME-E2E-002: 切换孩子', async ({ page }) => {
      // 1. 已登录状态进入首页
      await page.goto('http://localhost:8080/home');
      
      // 2. 点击孩子头像/名称
      await page.click('.child-info');
      
      // 3. 选择另一个孩子
      await page.click('text=李四');
      
      // 4. 验证切换成功
      await expect(page.locator('text=李四')).toBeVisible();
    });
  });

  test.describe('成长记录模块', () => {
    
    test('TC-RECORD-E2E-001: 查看今日成长记录', async ({ page }) => {
      // 1. 进入成长记录页面
      await page.goto('http://localhost:8080/record');
      
      // 2. 验证日期显示
      await expect(page.locator('text=今日')).toBeVisible();
      
      // 3. 验证记录内容区域
      await expect(page.locator('.record-content')).toBeVisible();
    });

    test('TC-RECORD-E2E-002: 查看历史记录', async ({ page }) => {
      // 1. 进入成长记录页面
      await page.goto('http://localhost:8080/record');
      
      // 2. 点击历史记录tab
      await page.click('text=历史记录');
      
      // 3. 验证历史记录列表
      await expect(page.locator('.record-list')).toBeVisible();
    });
  });

  test.describe('通知公告模块', () => {
    
    test('TC-NOTICE-E2E-001: 查看通知列表', async ({ page }) => {
      // 1. 进入通知页面
      await page.goto('http://localhost:8080/notice');
      
      // 2. 验证通知列表
      await expect(page.locator('.notice-list')).toBeVisible();
    });

    test('TC-NOTICE-E2E-002: 查看通知详情', async ({ page }) => {
      // 1. 进入通知页面
      await page.goto('http://localhost:8080/notice');
      
      // 2. 点击第一条通知
      await page.click('.notice-item:first-child');
      
      // 3. 验证通知详情
      await expect(page.locator('.notice-detail')).toBeVisible();
      await expect(page.locator('.notice-title')).toBeVisible();
    });
  });

  test.describe('个人中心模块', () => {
    
    test('TC-PROFILE-E2E-001: 查看个人信息', async ({ page }) => {
      // 1. 进入个人中心
      await page.goto('http://localhost:8080/profile');
      
      // 2. 验证头像
      await expect(page.locator('.avatar')).toBeVisible();
      
      // 3. 验证昵称
      await expect(page.locator('.nickname')).toBeVisible();
    });

    test('TC-PROFILE-E2E-002: 退出登录', async ({ page }) => {
      // 1. 进入个人中心
      await page.goto('http://localhost:8080/profile');
      
      // 2. 点击设置按钮
      await page.click('.settings-btn');
      
      // 3. 点击退出登录
      await page.click('text=退出登录');
      
      // 4. 确认退出
      await page.click('button:text("确定")');
      
      // 5. 验证回到登录页
      await expect(page).toHaveURL(/.*login/);
    });
  });
});
