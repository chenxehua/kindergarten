import { test, expect } from '@playwright/test';

test('TC-LOGIN-001: 检查页面加载错误', async ({ page }) => {
  // 监听控制台错误
  const errors = [];
  page.on('console', msg => {
    if (msg.type() === 'error') {
      errors.push(msg.text());
    }
  });

  // 监听页面错误
  page.on('pageerror', err => {
    errors.push(err.message);
  });

  await page.goto('http://localhost:3001/login');
  await page.waitForTimeout(5000);

  console.log('Console errors:', errors);
});
