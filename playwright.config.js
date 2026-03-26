import { defineConfig, devices } from '@playwright/test';

module.exports = defineConfig({
  testDir: './e2e',
  fullyParallel: true,
  retries: 0,
  workers: 1,
  reporter: 'line',
  use: {
    baseURL: 'http://localhost:3001',
    trace: 'on-first-retry',
  },
  projects: [
    {
      name: 'pc-chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
