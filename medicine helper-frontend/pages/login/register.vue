<template>
	<view class="register-container">
		<view class="logo-box">
			<image class="logo-image" :src="loginImg"></image>
			<view class="logo-btm-title">
				用药助手
			</view>
		</view>
		<view class="input-box">
			<input class="uni-input" type="number" placeholder="手机号" placeholder-class="account" v-model="phone" />
			<input class="uni-input" password type="text" placeholder="密码" placeholder-class="password"
				v-model="password" />
		</view>
		<view class="register-btn">
			<button type="primary" @click="register">注册</button>
		</view>
		<view class="login-pwd-box">
			<navigator url="/pages/login/login" hover-class="navigator-hover">
				<view class="login-text">
					已有账号？去登录
				</view>
			</navigator>
		</view>
	</view>
</template>

<script>
	import navigateTo from '@/js/navigateTo.js'
	import url from '@/js/baseUrl.js'

	export default {
		data() {
			return {
				loginImg: '../../static/myLogo.png',
				phone: '',
				password: '',
			}
		},
		methods: {
			register() {
				uni.request({
					url: url + '/user/register',
					method: 'POST',
					data: {
						phone: this.phone,
						password: this.password
					},
					success: (res) => {
						let code = res.data.code
						if (code === 202) {
							uni.showToast({
								icon: 'success',
								title: res.data.message,
							})
							navigateTo('./login')
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
	.register-container {
		margin-top: 44px;
		width: 100%;
		// position: relative;

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

		.register-btn {
			width: 200px;
			padding: 0 25%;
			position: fixed;
			margin-top: 60px;
		}

		.login-pwd-box {
			text-align: center;
			margin-top: 120px;

			.login-text {
				color: rgba(0, 0, 0, 0.4);
				font-size: 13px;
			}
		}
	}

	// ::v-deep .uni-input-input {
	// 	height: 100px;
	// }
</style>
