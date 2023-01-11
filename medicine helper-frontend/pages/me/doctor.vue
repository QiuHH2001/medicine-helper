<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="找医生" @clickLeft="back" />
	<view>
		<uni-list v-for="(item, index) in list" :index="index" :key="index">
			<uni-list-item :title="item.name" :note="item.detail" showArrow :thumb="item.avatar"
				:show-badge="item.isBadge" badge-text="已绑定" thumb-size="lg" link @click="choose(index)" />
		</uni-list>

		<view v-show="userFeedbackHidden == false" class="popup_content">
			<view class="info" scroll-y="true">
				<image :src="this.selectedimage" class="selectedimage"></image>
				<view class="name"><strong>{{selectedname}}</strong></view>
				<view class="career">{{selecteddetail}}</view>
				<view class="more">{{more}}</view>
			</view>
			<button class="cancel" type="default" size="default" @click="cancel()">取消</button>
			<button class="confirm" type="primary" size="default" @click="submitFeedback()">选择TA</button>
		</view>
		<view class="popup_overlay" v-show="userFeedbackHidden == false"></view>
	</view>
</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		onLoad() {
			uni.request({
				url: url + '/user',
				method: 'GET',
				header: {
					Authorization: sessionStorage.getItem('token')
				},
				success: (res) => {
					let code = res.data.code;
					let data = res.data.data
					if (code === 200) {
						this.doctor = data.doctorId
					} else {
						uni.showToast({
							icon: 'error',
							title: res.data.message
						})
					}
				}
			});
			setTimeout(() => {
				uni.request({
					url: url + '/user/doctor',
					method: 'GET',
					header: {
						Authorization: sessionStorage.getItem('token')
					},
					success: (res) => {
						let code = res.data.code;
						let data = res.data.data
						if (code === 200) {
							for (let i = 0; i < data.length; i++) {
								let obj = data[i]
								this.doctor === data[i].id ? obj.isBadge = true : obj.isBadge = false
								this.list.push(obj)
							}
						} else {
							uni.showToast({
								icon: 'error',
								title: res.data.message
							})
						}
					}
				});
			}, 500)


		},
		data() {
			return {
				userFeedbackHidden: true,
				list: [],
				selectedimage: '',
				selectedname: '',
				selectdetail: '',
				more: '',
				selected: '',
				doctor: -1,
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			choose(index) {
				this.userFeedbackHidden = false;
				this.selectedimage = this.list[index].avatar;
				this.selectedname = this.list[index].name;
				this.selecteddetail = this.list[index].detail;
				this.more = this.list[index].more;
				// console.log(index);
				this.selected = index
			},
			submitFeedback() {
				// var that = this;
				this.userFeedbackHidden = true;

				uni.showModal({
					title: '提示',
					content: '确认选择该医生？',
					success: res => {
						if (res.confirm) {
							uni.request({
								url: url + '/user/doctor/' + this.list[this.selected].id,
								method: "POST",
								header: {
									Authorization: sessionStorage.getItem('token')
								},
								success: (res) => {
									let code = res.data.code
									if (code === 200) {
										for (let i = 0; i < this.list.length; i++) {
											this.list[i].isBadge = false
										}
										this.list[this.selected].isBadge = true;
										uni.showToast({
											title: res.data.message,
											icon: 'success'
										});
									} else {
										uni.showToast({
											title: res.data.message,
											icon: 'error'
										});
									}
								}
							})
							uni.showToast({
								title: `绑定成功`,
								icon: 'right'
							});
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			cancel() {
				this.userFeedbackHidden = true;
			},
		},
	}
</script>

<style lang="scss">
	.popup_overlay {
		position: fixed;
		top: 0%;
		left: 0%;
		width: 100%;
		height: 100%;
		background-color: gray;
		z-index: 1001;
		-moz-opacity: 0.8;
		opacity: .80;
		filter: alpha(opacity=88);
	}

	.popup_content {
		position: fixed;
		width: 100%;
		top: 20%;
		background-color: white;
		z-index: 1002;
		overflow: auto;

		.info {
			height: 300px;
			width: 90%;
			margin-left: 5%;
			text-align: center;

			.selectedimage {
				height: 120px;
				width: 120px;
				margin-top: 20px;
				border-radius: 50%;
				border: 2px solid #1296db;
			}

			.name {
				margin-top: 20px;
				font-size: 30px;
			}

			.career {
				margin-top: 15px;
				font-size: 18px;
				color: gray;
			}

			.more {
				margin-top: 10px;
				font-size: 15px;
				color: gray;
			}
		}
	}

	.cancel {
		margin: 30rpx auto 20rpx auto;
		width: 40%;
		left: 10%;
		float: left;
	}

	.confirm {
		margin: 30rpx auto 20rpx 20rpx;
		width: 40%;
		left: 10%;
		float: left;
	}
</style>
