<template>
	<view class="login-container">
		<view class="logo-box">
			<image class="logo-image" :src="loginImg"></image>
			<view class="logo-btm-title">
				用药助手
			</view>
		</view>
		<view class="input-box">
			<input class="uni-input" type="number" placeholder="账号" placeholder-class="account" v-model="phone" />
			<input class="uni-input" password type="text" placeholder="密码" placeholder-class="password"
				v-model="password" />
		</view>

		<view class="forget-pwd-box">
			<view class="forget-text">
				忘记密码？
			</view>
			<navigator url="/pages/login/register" hover-class="navigator-hover">
				<view class="register-text">
					注册
				</view>
			</navigator>
		</view>
		<view class="login-btn">
			<button type="primary" @click="login()">登录</button>
		</view>
	</view>
</template>

<script>
	import url from '@/js/baseUrl.js'
	import switchTab from '@/js/switchTab.js'
	export default {
		data() {
			return {
				loginImg: '../../static/myLogo.png',
				phone: '',
				password: '',
			}
		},
		methods: {
			login() {
				uni.request({
					url: url + '/user/login',
					method: 'POST',
					data: {
						phone: this.phone,
						password: this.password
					},
					success: (res) => {
						let code = res.data.code
						if (code === 201) {
							uni.showToast({
								icon: 'success',
								title: res.data.message,
							})
							let token = 'Bearer ' + res.data.data
							sessionStorage.setItem('token', token)
							// switchTab('../home/home')
							uni.switchTab({
								url: '../home/home'
							})
						} else {
							uni.showToast({
								icon: 'error',
								title: res.data.message,
							})
						}
					},
					complete: () => {
						this.phone = ''
						this.password = ''
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.login-container {
		margin-top: 44px;
		width: 100%;

		.logo-box {
			text-align: center;

			.logo-image {
				margin-top: 10%;
				margin-bottom: 5%;
				width: 100px;
				height: 100px;
			}

			.logo-btm-title {
				font-size: 30px;
				font-weight: 500;
			}
		}

		.input-box {
			height: 120px;

			.uni-input {
				height: 30px;
				border-bottom: 1px solid rgba(0, 0, 0, 0.4);
				padding: 10px 10px;
				margin: 0 15px;
				margin-top: 40px;
			}

			.account {
				color: black;
			}

			.password {
				color: black;
			}
		}

		.forget-pwd-box {
			.forget-text {
				padding-top: 10%;
				float: right;
				color: rgba(0, 0, 0, 0.4);
				font-size: 13px;
			}

			.register-text {
				margin-right: 10px;
				padding-top: 10%;
				float: right;
				color: rgba(0, 0, 0, 0.4);
				font-size: 13px;
			}
		}

		.login-btn {
			width: 200px;
			padding: 0 25%;
			position: fixed;
			bottom: 25%;
		}
	}

	// ::v-deep .uni-input-input {
	// 	height: 100px;
	// }
</style>
