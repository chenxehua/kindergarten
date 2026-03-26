// 活动管理测试
describe('活动管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/activity')
  })

  /**
   * TC-ACTIVITY-001: 活动列表页面正常显示
   */
  it('TC-ACTIVITY-001: 活动列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '活动管理')
    cy.get('.activity-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-ACTIVITY-002: 新增活动
   */
  it('TC-ACTIVITY-002: 应该能够新增活动', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增活动')
    cy.get('[placeholder="请输入活动名称"]').should('be.visible')
  })

  /**
   * TC-ACTIVITY-003: 编辑活动
   */
  it('TC-ACTIVITY-003: 应该能够编辑活动', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑活动')
  })

  /**
   * TC-ACTIVITY-004: 删除活动
   */
  it('TC-ACTIVITY-004: 应该能够删除活动', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })
})