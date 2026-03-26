// 通知管理测试
describe('通知管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/notice')
  })

  /**
   * TC-NOTICE-001: 通知列表页面正常显示
   */
  it('TC-NOTICE-001: 通知列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '通知公告')
    cy.get('.notice-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-NOTICE-002: 发布通知
   */
  it('TC-NOTICE-002: 应该能够发布新通知', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '发布通知')
    cy.get('[placeholder="请输入通知标题"]').should('be.visible')
  })

  /**
   * TC-NOTICE-003: 通知标题为空验证
   */
  it('TC-NOTICE-003: 通知标题为空时应该提示错误', () => {
    cy.get('.add-button').click()
    cy.get('.submit-button').click()
    cy.get('.el-form-item__error').should('contain', '通知标题不能为空')
  })

  /**
   * TC-NOTICE-004: 编辑通知
   */
  it('TC-NOTICE-004: 应该能够编辑通知', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑通知')
    cy.get('[placeholder="请输入通知标题"]').should('have.value', '测试通知')
  })

  /**
   * TC-NOTICE-005: 删除通知
   */
  it('TC-NOTICE-005: 应该能够删除通知', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })

  /**
   * TC-NOTICE-006: 查看通知详情
   */
  it('TC-NOTICE-006: 应该能够查看通知详情', () => {
    cy.get('.view-button').first().click()
    cy.get('.notice-detail-dialog').should('be.visible')
    cy.get('.notice-content').should('be.visible')
  })

  /**
   * TC-NOTICE-007: 搜索通知
   */
  it('TC-NOTICE-007: 应该能够搜索通知', () => {
    cy.get('[placeholder="请输入通知标题"]').type('测试')
    cy.get('.search-button').click()
    cy.wait(500)
    cy.get('.notice-table .el-table__row').should('have.length.gt', 0)
  })
})