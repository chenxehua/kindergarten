// 登录页面测试
describe('登录功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
  })

  /**
   * TC-LOGIN-001: 登录页面正常显示
   */
  it('TC-LOGIN-001: 登录页面应该正常显示', () => {
    cy.get('.login-container').should('be.visible')
    cy.get('.login-title').should('contain', '智慧幼儿园管理系统')
    cy.get('[placeholder="请输入手机号"]').should('be.visible')
    cy.get('[placeholder="请输入密码"]').should('be.visible')
    cy.get('.login-button').should('be.visible')
  })

  /**
   * TC-LOGIN-002: 手机号为空时登录
   */
  it('TC-LOGIN-002: 手机号为空时应该提示错误', () => {
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.get('.el-message').should('contain', '手机号不能为空')
  })

  /**
   * TC-LOGIN-003: 密码为空时登录
   */
  it('TC-LOGIN-003: 密码为空时应该提示错误', () => {
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('.login-button').click()
    cy.get('.el-message').should('contain', '密码不能为空')
  })

  /**
   * TC-LOGIN-004: 手机号格式错误
   */
  it('TC-LOGIN-004: 手机号格式错误时应该提示错误', () => {
    cy.get('[placeholder="请输入手机号"]').type('123')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.get('.el-message').should('contain', '手机号格式不正确')
  })

  /**
   * TC-LOGIN-005: 账号密码错误
   */
  it('TC-LOGIN-005: 账号密码错误时应该提示错误', () => {
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('wrongpassword')
    cy.get('.login-button').click()
    cy.get('.el-message').should('contain', '用户名或密码错误')
  })

  /**
   * TC-LOGIN-006: 登录成功
   */
  it('TC-LOGIN-006: 正确的账号密码应该登录成功', () => {
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    // 等待登录成功后跳转
    cy.url().should('include', '/dashboard')
    cy.get('.user-info').should('be.visible')
  })
})