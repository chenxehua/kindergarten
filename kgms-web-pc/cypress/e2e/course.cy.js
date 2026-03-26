// 课程管理测试
describe('课程管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/course')
  })

  /**
   * TC-COURSE-001: 课程列表页面正常显示
   */
  it('TC-COURSE-001: 课程列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '课程管理')
    cy.get('.course-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-COURSE-002: 新增课程
   */
  it('TC-COURSE-002: 应该能够新增课程', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增课程')
    cy.get('[placeholder="请输入课程名称"]').should('be.visible')
  })

  /**
   * TC-COURSE-003: 编辑课程
   */
  it('TC-COURSE-003: 应该能够编辑课程', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑课程')
  })

  /**
   * TC-COURSE-004: 删除课程
   */
  it('TC-COURSE-004: 应该能够删除课程', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })

  /**
   * TC-COURSE-005: 查看课程详情
   */
  it('TC-COURSE-005: 应该能够查看课程详情', () => {
    cy.get('.view-button').first().click()
    cy.get('.course-detail-dialog').should('be.visible')
  })
})