// 学生管理测试
describe('学生管理功能测试', () => {
  beforeEach(() => {
    // 先登录
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    // 跳转到学生管理页面
    cy.visit('/student')
  })

  /**
   * TC-STUDENT-001: 学生列表页面正常显示
   */
  it('TC-STUDENT-001: 学生列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '学生管理')
    cy.get('.search-form').should('be.visible')
    cy.get('.student-table').should('be.visible')
    cy.get('.add-button').should('be.visible')
  })

  /**
   * TC-STUDENT-002: 搜索学生
   */
  it('TC-STUDENT-002: 应该能够搜索学生', () => {
    cy.get('[placeholder="请输入学生姓名"]').type('张')
    cy.get('.search-button').click()
    cy.wait(500)
    cy.get('.student-table .el-table__row').should('have.length.gt', 0)
  })

  /**
   * TC-STUDENT-003: 新增学生 - 打开弹窗
   */
  it('TC-STUDENT-003: 点击新增按钮应该打开弹窗', () => {
    cy.get('.add-button').click()
    cy.get('.dialog-title').should('contain', '新增学生')
    cy.get('[placeholder="请输入学生姓名"]').should('be.visible')
  })

  /**
   * TC-STUDENT-004: 新增学生 - 表单验证
   */
  it('TC-STUDENT-004: 学生姓名为空时应该提示错误', () => {
    cy.get('.add-button').click()
    cy.get('.submit-button').click()
    cy.get('.el-form-item__error').should('contain', '学生姓名不能为空')
  })

  /**
   * TC-STUDENT-005: 编辑学生
   */
  it('TC-STUDENT-005: 应该能够编辑学生信息', () => {
    // 点击编辑按钮
    cy.get('.edit-button').first().click()
    cy.get('.dialog-title').should('contain', '编辑学生')
    cy.get('[placeholder="请输入学生姓名"]').should('have.value', '小明')
  })

  /**
   * TC-STUDENT-006: 删除学生
   */
  it('TC-STUDENT-006: 应该能够删除学生', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })
})