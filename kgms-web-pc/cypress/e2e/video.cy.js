// 视频管理测试
describe('视频管理功能测试', () => {
  beforeEach(() => {
    cy.visit('/login')
    cy.get('[placeholder="请输入手机号"]').type('13800138000')
    cy.get('[placeholder="请输入密码"]').type('123456')
    cy.get('.login-button').click()
    cy.wait(1000)
    cy.visit('/video')
  })

  /**
   * TC-VIDEO-001: 视频列表页面正常显示
   */
  it('TC-VIDEO-001: 视频列表页面应该正常显示', () => {
    cy.get('.page-title').should('contain', '视频管理')
    cy.get('.video-grid').should('be.visible')
    cy.get('.upload-button').should('be.visible')
  })

  /**
   * TC-VIDEO-002: 上传视频
   */
  it('TC-VIDEO-002: 应该能够上传视频', () => {
    cy.get('.upload-button').click()
    cy.get('.upload-dialog').should('be.visible')
    cy.get('[placeholder="请输入视频标题"]').should('be.visible')
  })

  /**
   * TC-VIDEO-003: 视频标题为空验证
   */
  it('TC-VIDEO-003: 视频标题为空时应该提示错误', () => {
    cy.get('.upload-button').click()
    cy.get('.submit-button').click()
    cy.get('.el-form-item__error').should('contain', '视频标题不能为空')
  })

  /**
   * TC-VIDEO-004: 删除视频
   */
  it('TC-VIDEO-004: 应该能够删除视频', () => {
    cy.get('.delete-button').first().click()
    cy.get('.el-message-box').should('be.visible')
    cy.get('.el-message-box .el-button--primary').click()
    cy.get('.el-message').should('contain', '删除成功')
  })

  /**
   * TC-VIDEO-005: 播放视频
   */
  it('TC-VIDEO-005: 应该能够播放视频', () => {
    cy.get('.video-item').first().click()
    cy.get('.video-player').should('be.visible')
  })
})