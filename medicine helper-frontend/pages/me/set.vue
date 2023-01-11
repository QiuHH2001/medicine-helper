<template>
	<view class="me-container">
		<uni-nav-bar :fixed="true" shadow  status-bar left-icon="left" left-text="返回"
			title="设置" @clickLeft="back" />
		<view class="list">
			<view class="l1">关于用药助手</view>
			<view class="l2">帮助与反馈</view>
		</view>
		<navigator url="/pages/login/login" hover-class="navigator-hover">
			<button class="quit" type="warn">退出登录</button>
		</navigator>
	</view>
</template>
	
<script>
	export default {
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			// clickLeft() {
			// 	uni.showToast({
			// 		title: '左侧按钮'
			// 	})
			// },
		},
	}
</script>

<style lang="scss">
	.me-container{
		width: 100%;
		
		.list{
			margin: 20px 30px 0px 30px;
			.l1{
				font-size: 18px;
			}
			.l2{
				margin-top: 20px;
				font-size: 18px;
			}
		}
		
		.quit{
			position: fixed;
			left: 30px;
			right: 30px;
			bottom: 5px;
		}
	}
</style>
