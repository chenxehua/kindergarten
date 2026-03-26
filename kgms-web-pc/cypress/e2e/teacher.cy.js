// 老师管理测试
describe('老师管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/teacher')
  })

  /**
   * TC-TEACHER-001: 老师列表页面正常显示
   */
  it('TC-TEACHER-001: 老师列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '老师管理')
    cy.get('.teacher-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-TEACHER-002: 新增老师
   */
  it('TC-TEACHER-002: 应该能够新增老师', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增老师')
    cy.get('[placeholder="请输入老师姓名"]').should('be.visible')
  })

  /**
   * TC-TEACHER-003: 编辑老师
   */
  it('TC-TEACHER-003: 应该能够编辑老师信息', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑老师')
  })

  /**
   * TC-TEACHER-004: 删除老师
   */
  it('TC-TEACHER-004: 应该能够删除老师', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })
})