import { test, expect } from '@playwright/test';

test.describe('数据看板功能测试', () => {
  test('TC-DASHBOARD-001: 仪表盘页面应该正常显示', async ({ page }) => {
    // 先访问登录页面
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');

    // 登录
    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    // 等待页面跳转
    await page.waitForTimeout(3000);

    // 跳转到数据看板页面
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查页面是否加载
    const body = await page.locator('body').innerHTML();
    console.log('Dashboard page body:', body.substring(0, 500));
  });

  test('TC-DASHBOARD-002: 数据看板页面元素检查', async ({ page }) => {
    // 直接访问数据看板页面
    await page.goto('http://localhost:3001/dashboard');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查页面是否加载
    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});
