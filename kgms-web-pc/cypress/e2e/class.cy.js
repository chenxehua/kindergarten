// 班级管理测试
describe('班级管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/class')
  })

  /**
   * TC-CLASS-001: 班级列表页面正常显示
   */
  it('TC-CLASS-001: 班级列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '班级管理')
    cy.get('.class-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-CLASS-002: 搜索班级
   */
  it('TC-CLASS-002: 应该能够搜索班级', () => {
    cy.get('[placeholder="请输入班级名称"]').type('大一')
    cy.get('.search-button').click()
    cy.wait(500)
    cy.get('.class-table .el-table__row').should('have.length.gt', 0)
  })

  /**
   * TC-CLASS-003: 新增班级 - 打开弹窗
   */
  it('TC-CLASS-003: 点击新增按钮应该打开弹窗', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增班级')
    cy.get('[placeholder="请输入班级名称"]').should('be.visible')
  })

  /**
   * TC-CLASS-004: 新增班级 - 表单验证
   */
  it('TC-CLASS-004: 班级名称为空时应该提示错误', () => {
    cy.get('.add-button').click()
    cy.get('.submit-button').click()
    cy.get('.el-form-item__error').should('contain', '班级名称不能为空')
  })

  /**
   * TC-CLASS-005: 编辑班级
   */
  it('TC-CLASS-005: 应该能够编辑班级信息', () => {
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑班级')
    cy.get('[placeholder="请输入班级名称"]').should('have.value', '大一班')
  })

  /**
   * TC-CLASS-006: 删除班级
   */
  it('TC-CLASS-006: 应该能够删除班级', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })

  /**
   * TC-CLASS-007: 查看班级学生
   */
  it('TC-CLASS-007: 应该能够查看班级学生列表', () => {
    cy.get('.student-button').first().click()
    cy.get('.student-list-dialog').should('be.visible')
    cy.get('.student-list .el-table__row').should('have.length.gt', 0)
  })

  /**
   * TC-CLASS-008: 查看班级老师
   */
  it('TC-CLASS-008: 应该能够查看班级老师列表', () => {
    cy.get('.teacher-button').first().click()
    cy.get('.teacher-list-dialog').should('be.visible')
    cy.get('.teacher-list .el-table__row').should('have.length.gt', 0)
  })
})