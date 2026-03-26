// 成长记录测试
describe('成长记录功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/growth')
  })

  /**
   * TC-GROWTH-001: 成长记录页面正常显示
   */
  it('TC-GROWTH-001: 成长记录页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '成长记录')
    cy.get('.student-select').should('be.visible')
    cy.get('.growth-table').should('be.visible')
  })

  /**
   * TC-GROWTH-002: 选择学生
   */
  it('TC-GROWTH-002: 应该能够选择学生查看成长记录', () => {
    cy.get('.student-select').click()
    cy.get('.el-select-dropdown').should('be.visible')
    cy.get('.el-select-dropdown__item').first().click()
    cy.wait(500)
    cy.get('.growth-table .el-table__row').should('have.length.gt', 0)
  })

  /**
   * TC-GROWTH-003: 新增成长记录
   */
  it('TC-GROWTH-003: 应该能够新增成长记录', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增成长记录')
    cy.get('[placeholder="请选择记录类型"]').should('be.visible')
  })

  /**
   * TC-GROWTH-004: 编辑成长记录
   */
  it('TC-GROWTH-004: 应该能够编辑成长记录', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑成长记录')
  })

  /**
   * TC-GROWTH-005: 删除成长记录
   */
  it('TC-GROWTH-005: 应该能够删除成长记录', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })

  /**
   * TC-GROWTH-006: 查看成长趋势
   */
  it('TC-GROWTH-006: 应该能够查看成长趋势图表', () => {
    cy.get('.trend-button').click()
    cy.get('.trend-chart').should('be.visible')
  })
})