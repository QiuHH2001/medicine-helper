<template>
	<view class="me-container">
		<!-- <navigator :url="'/pages/me/modify?userId='+id" hover-class="navigator-hover"> -->
		<view @click="toModify" class="avatar-box">
			<image class="avatar" :src="user.avatar"></image>
			<view class="ueser">
				<view class="username">
					{{user.nickname}}
				</view>
				<view class="userid">
					ID:&nbsp{{user.uid}}
				</view>
			</view>
			<image class="more" src="../../static/icon/right.png"></image>
		</view>
		<!-- </navigator> -->
		<view class="block"></view>
		<navigator url="/pages/me/list" hover-class="navigator-hover">
			<view class="list">
				<image class="icon1" src="../../static/icon/list.png"></image>
				<view class="name1">
					过敏药物列表
				</view>
			</view>
		</navigator>
		<navigator url="/pages/me/history" hover-class="navigator-hover">
			<view class="history">
				<image class="icon2" src="../../static/icon/history.png"></image>
				<view class="name2">
					用药历史
				</view>
			</view>
		</navigator>
		<navigator url="/pages/me/box" hover-class="navigator-hover">
			<view class="manager">
				<image class="icon3" src="../../static/icon/manager.png"></image>
				<view class="name3">
					药箱管理
				</view>
			</view>
		</navigator>
		<navigator url="/pages/me/doctor" hover-class="navigator-hover">
			<view class="doctor">
				<image class="icon4" src="../../static/icon/doctor.png"></image>
				<view class="name4">
					找医生
				</view>
			</view>
		</navigator>
		<navigator url="/pages/me/set" hover-class="navigator-hover">
			<view class="set">
				<image class="icon5" src="../../static/icon/set.png"></image>
				<view class="name5">
					设置
				</view>
			</view>
		</navigator>
	</view>
</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		onShow() {
			uni.request({
				url: url + '/user',
				method: 'GET',
				header: {
					Authorization: sessionStorage.getItem('token'),
				},
				success: (res) => {
					if (res.data.code === 200) {
						this.user = res.data.data
						// this.user.avatar = '../../static' + res.data.data.avatar
					}
				}
			})
		},
		data() {
			return {
				user: {
					id: '-1',
					nickname: 'none',
					uid: '%0%',
					phone: '00000000000',
					email: 'xxxxx@xxx.com',
					avatar: '../../static/images/medicine.png',
				}
			}
		},
		methods: {
			toModify() {
				uni.navigateTo({
					url: `./modify?user=${encodeURIComponent(JSON.stringify(this.user))}`
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.me-container {
		width: 100%;

		.avatar-box {
			margin-top: 30px;
			height: 80px;

			// border: 1px solid red;
			.avatar {
				margin-left: 30px;
				width: 80px;
				height: 80px;
				border-radius: 10px;
				float: left;
				// border: 1px solid red;
			}

			.ueser {
				float: left;

				.username {
					padding: 25px 0px 0px 15px;
					font-size: 20px;
					// border: 1px solid red;
				}

				.userid {
					padding: 8px 0px 0px 15px;
					font-size: 15px;
					// border: 1px solid red;
				}
			}

			.more {
				float: right;
				margin: 60px 20px 0px 0px;
				width: 15px;
				height: 15px;
			}
		}

		.block {
			margin-top: 30px;
			width: 100%;
			height: 10px;
			background-color: #F8F8F8;
		}

		.list {
			margin: 10px 0px 0px 35px;
			height: 50px;
			// border: 1px solid gray;

			.icon1 {
				margin-top: 12px;
				width: 25px;
				height: 25px;
				float: left;
			}

			.name1 {
				padding: 13px 0px 0px 10px;
				float: left;
			}
		}

		.history {
			margin: 0px 0px 0px 35px;
			height: 50px;
			// border: 1px solid gray;

			.icon2 {
				margin-top: 12px;
				width: 25px;
				height: 25px;
				float: left;
			}

			.name2 {
				padding: 13px 0px 0px 10px;
				float: left;
			}
		}

		.manager {
			margin: 0px 0px 0px 35px;
			height: 50px;
			// border: 1px solid gray;

			.icon3 {
				margin-top: 12px;
				width: 25px;
				height: 25px;
				float: left;
			}

			.name3 {
				padding: 13px 0px 0px 10px;
				float: left;
			}
		}
		.doctor {
			margin: 0px 0px 0px 32px;
			height: 50px;
			// border: 1px solid gray;
		
			.icon4 {
				margin-top: 12px;
				width: 30px;
				height: 30px;
				float: left;
			}
		
			.name4 {
				padding: 13px 0px 0px 8px;
				float: left;
			}
		}

		.set {
			margin: 0px 0px 0px 35px;
			height: 50px;
			// border: 1px solid gray;

			.icon5 {
				margin-top: 12px;
				width: 25px;
				height: 25px;
				float: left;
			}

			.name5 {
				padding: 13px 0px 0px 10px;
				float: left;
			}
		}
	}
</style>
