<template>
  <view class="login-container">
    <view class="logo">
      <image src="../../static/logo.png" mode="aspectFit" class="logo-img"></image>
      <text class="title">智慧幼儿园</text>
    </view>
    
    <view class="login-form">
      <view class="input-group">
        <input type="number" v-model="phone" placeholder="请输入手机号" maxlength="11" />
      </view>
      <view class="input-group">
        <input type="password" v-model="password" placeholder="请输入密码" />
      </view>
      
      <button class="login-btn" @click="handleLogin">登录</button>
      
      <view class="wechat-login">
        <button class="wechat-btn" open-type="getPhoneNumber" @getphonenumber="handleWechatLogin">
          <text>微信一键登录</text>
        </button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue';
import { userApi } from '../../api/index';

const phone = ref('');
const password = ref('');

const handleLogin = async () => {
  if (!phone.value) {
    uni.showToast({ title: '请输入手机号', icon: 'none' });
    return;
  }
  if (!password.value) {
    uni.showToast({ title: '请输入密码', icon: 'none' });
    return;
  }
  
  try {
    uni.showLoading({ title: '登录中...' });
    const data = await userApi.login({ username: phone.value, password: password.value });
    uni.setStorageSync('token', data.token);
    uni.setStorageSync('userInfo', data);
    uni.hideLoading();
    uni.switchTab({ url: '/pages/home/home' });
  } catch (e) {
    uni.hideLoading();
  }
};

const handleWechatLogin = async (e) => {
  if (e.detail.errMsg !== 'getPhoneNumber:ok') return;
  
  try {
    uni.showLoading({ title: '登录中...' });
    // 获取code用于获取手机号
    const loginRes = await new Promise((resolve, reject) => {
      uni.login({
        provider: 'weixin',
        success: resolve,
        fail: reject
      });
    });
    
    const data = await userApi.loginByWechat({ 
      code: loginRes.code,
      userType: 1  // 家长
    });
    
    uni.setStorageSync('token', data.token);
    uni.setStorageSync('userInfo', data);
    uni.hideLoading();
    uni.switchTab({ url: '/pages/home/home' });
  } catch (err) {
    uni.hideLoading();
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60rpx;
  
  .logo {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 80rpx;
    
    .logo-img {
      width: 160rpx;
      height: 160rpx;
      border-radius: 20rpx;
      background: #fff;
    }
    
    .title {
      margin-top: 20rpx;
      font-size: 48rpx;
      color: #fff;
      font-weight: bold;
    }
  }
  
  .login-form {
    width: 100%;
    
    .input-group {
      background: #fff;
      border-radius: 50rpx;
      margin-bottom: 30rpx;
      padding: 20rpx 40rpx;
      
      input {
        height: 80rpx;
        font-size: 28rpx;
      }
    }
    
    .login-btn {
      width: 100%;
      height: 90rpx;
      background: #fff;
      color: #667eea;
      border-radius: 50rpx;
      font-size: 32rpx;
      font-weight: bold;
      margin-top: 40rpx;
    }
    
    .wechat-login {
      margin-top: 40rpx;
      
      .wechat-btn {
        width: 100%;
        height: 90rpx;
        background: #07c160;
        color: #fff;
        border-radius: 50rpx;
        font-size: 32rpx;
      }
    }
  }
}
</style>
