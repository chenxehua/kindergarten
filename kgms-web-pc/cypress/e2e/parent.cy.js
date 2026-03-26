// 家长管理测试
describe('家长管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/parent')
  })

  /**
   * TC-PARENT-001: 家长列表页面正常显示
   */
  it('TC-PARENT-001: 家长列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '家长管理')
    cy.get('.parent-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-PARENT-002: 搜索家长
   */
  it('TC-PARENT-002: 应该能够搜索家长', () => {
    cy.get('[placeholder="请输入家长姓名"]').type('张')
    cy.get('.search-button').click()
    cy.wait(500)
    cy.get('.parent-table .el-table__row').should('have.length.gt', 0)
  })

  /**
   * TC-PARENT-003: 新增家长
   */
  it('TC-PARENT-003: 应该能够新增家长', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增家长')
    cy.get('[placeholder="请输入家长姓名"]').should('be.visible')
  })

  /**
   * TC-PARENT-004: 编辑家长
   */
  it('TC-PARENT-004: 应该能够编辑家长信息', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑家长')
  })

  /**
   * TC-PARENT-005: 删除家长
   */
  it('TC-PARENT-005: 应该能够删除家长', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })

  /**
   * TC-PARENT-006: 查看关联学生
   */
  it('TC-PARENT-006: 应该能够查看家长关联的学生', () => {
    cy.get('.view-student-button').first().click()
    cy.get('.student-list-dialog').should('be.visible')
  })
})