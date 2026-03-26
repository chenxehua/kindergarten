// 系统设置测试
describe('系统设置功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/settings')
  })

  /**
   * TC-SETTINGS-001: 系统设置页面正常显示
   */
  it('TC-SETTINGS-001: 系统设置页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '系统设置')
    cy.get('.settings-container').should('be.visible')
  })

  /**
   * TC-SETTINGS-002: 幼儿园信息设置
   */
  it('TC-SETTINGS-002: 应该能够设置幼儿园信息', () => {
    cy.get('.kg-info-tab').click()
    cy.get('[placeholder="请输入幼儿园名称"]').should('be.visible')
    cy.get('.save-button').should('be.visible')
  })

  /**
   * TC-SETTINGS-003: 保存设置
   */
  it('TC-SETTINGS-003: 应该能够保存设置', () => {
    cy.get('.save-button').click()
    cy.get('.el-message').should('contain', '保存成功')
  })

  /**
   * TC-SETTINGS-004: 用户管理
   */
  it('TC-SETTINGS-004: 应该能够管理用户', () => {
    cy.get('.user-management-tab').click()
    cy.get('.user-table').should('be.visible')
  })

  /**
   * TC-SETTINGS-005: 角色权限管理
   */
  it('TC-SETTINGS-005: 应该能够管理角色权限', () => {
    cy.get('.role-management-tab').click()
    cy.get('.role-table').should('be.visible')
  })
})