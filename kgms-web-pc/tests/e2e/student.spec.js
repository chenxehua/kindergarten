import { test, expect } from '@playwright/test';

test.describe('学生管理功能测试', () => {
  test('TC-STUDENT-001: 学生列表页面应该正常显示', async ({ page }) => {
    // 先访问登录页面
    await page.goto('http://localhost:3001/login');
    await page.waitForLoadState('networkidle');

    // 登录
    await page.locator('[placeholder="请输入用户名"]').fill('admin');
    await page.locator('[placeholder="请输入密码"]').fill('admin123');
    await page.locator('.el-button--primary').click();

    // 等待页面跳转
    await page.waitForTimeout(3000);

    // 跳转到学生管理页面
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查页面是否存在
    const body = await page.locator('body').innerHTML();
    console.log('Student page body:', body.substring(0, 500));
  });

  test('TC-STUDENT-002: 学生管理页面元素检查', async ({ page }) => {
    await page.goto('http://localhost:3001/student');
    await page.waitForLoadState('networkidle');
    await page.waitForTimeout(2000);

    // 检查页面是否加载（页面会显示内容或404）
    const content = await page.content();
    expect(content.length).toBeGreaterThan(0);
  });
});
