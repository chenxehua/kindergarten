// 仪表盘和数据统计测试
describe('数据看板功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/dashboard')
  })

  /**
   * TC-DASHBOARD-001: 仪表盘页面正常显示
   */
  it('TC-DASHBOARD-001: 仪表盘页面应该正常显示', () => {
    cy.get('.dashboard-container').should('be.visible')
    cy.get('.stat-cards').should('be.visible')
    cy.get('.chart-container').should('be.visible')
  })

  /**
   * TC-DASHBOARD-002: 显示学生统计数据
   */
  it('TC-DASHBOARD-002: 应该显示学生统计数据', () => {
    cy.get('.stat-card').contains('在园幼儿').should('be.visible')
    cy.get('.stat-card').contains('今日出勤率').should('be.visible')
    cy.get('.stat-card').contains('教师数量').should('be.visible')
  })

  /**
   * TC-DASHBOARD-003: 显示班级统计
   */
  it('TC-DASHBOARD-003: 应该显示班级统计数据', () => {
    cy.get('.class-statistics').should('be.visible')
    cy.get('.class-list').should('be.visible')
  })
})

// 考勤管理测试
describe('考勤管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/attendance')
  })

  /**
   * TC-ATTENDANCE-001: 考勤记录页面正常显示
   */
  it('TC-ATTENDANCE-001: 考勤记录页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '考勤管理')
    cy.get('.attendance-table').should('be.visible')
    cy.get('.date-picker').should('be.visible')
  })

  /**
   * TC-ATTENDANCE-002: 按日期查询考勤记录
   */
  it('TC-ATTENDANCE-002: 应该能够按日期查询考勤记录', () => {
    cy.get('.date-picker').click()
    cy.get('.el-date-picker__header-label').first().click()
    cy.get('.el-date-table__cell').first().click()
    cy.wait(500)
    cy.get('.attendance-table .el-table__row').should('have.length.gt', 0)
  })

  /**
   * TC-ATTENDANCE-003: 考勤统计
   */
  it('TC-ATTENDANCE-003: 应该显示考勤统计数据', () => {
    cy.get('.attendance-statistics').should('be.visible')
    cy.get('.stat-item').contains('应到').should('be.visible')
    cy.get('.stat-item').contains('实到').should('be.visible')
  })
})

// 食谱管理测试
describe('食谱管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/food')
  })

  /**
   * TC-FOOD-001: 食谱管理页面正常显示
   */
  it('TC-FOOD-001: 食谱管理页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '食谱管理')
    cy.get('.recipe-list').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-FOOD-002: 新增食谱
   */
  it('TC-FOOD-002: 应该能够新增食谱', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增食谱')
    cy.get('[placeholder="请输入食谱名称"]').should('be.visible')
  })
})