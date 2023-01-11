<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="过敏药物列表" @clickLeft="back" />
	<view class="container">
		<uni-swipe-action ref="swipeAction">
			<uni-swipe-action-item v-for="(item, index) in swipeList" :right-options="item.options" :key="item.id"
				@change="swipeChange($event, index)" @click="swipeClick($event, index)">
				<view class="content-box" @click="gotoDetail(index)">
					<text class="content-text">{{ item.content }}</text>
				</view>
			</uni-swipe-action-item>
		</uni-swipe-action>
	</view>

	<uni-fab ref="fab" horizontal="right" @click="clickAdd()" />

	<view v-show="userFeedbackHidden == false" class="popup_content">
		<view class="popup_title">编辑药物</view>
		<scroll-view class="info" scroll-y="true">
			<uni-forms ref="baseForm">
				<uni-forms-item label="名称" required>
					<uni-easyinput v-model="name" />
				</uni-forms-item>
				<uni-forms-item label="性状">
					<uni-easyinput v-model="property" />
				</uni-forms-item>
				<uni-forms-item label="过敏反应">
					<uni-easyinput v-model="hypersensitivity" />
				</uni-forms-item>
				<uni-forms-item label="备注" v-model="note">
					<uni-easyinput type="textarea" />
				</uni-forms-item>
			</uni-forms>
		</scroll-view>
		<button class="cancel" type="default" size="default" @click="cancel()">取消</button>
		<button class="confirm" type="primary" size="default" @click="submitFeedback()">确定</button>
	</view>
	<view class="popup_overlay" v-show="userFeedbackHidden == false"></view>
</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		onLoad() {
			uni.request({
				url: url + '/allergy/',
				method: 'GET',
				header: {
					Authorization: sessionStorage.getItem('token')
				},
				success: (res) => {
					let code = res.data.code
					let data = res.data.data
					if (code === 200) {
						for (let i = 0; i < data.length; i++) {
							this.medicineList.push(data[i])
							let obj = {
								options: [{
										text: '编辑'
									},
									{
										text: '删除',
										style: {
											backgroundColor: 'rgb(255,58,49)'
										}
									}
								],
								content: data[i].name
							}
							this.swipeList.push(obj)
						}
					} else {
						uni.showToast({
							icon: 'error',
							title: res.data.message,
						})
					}
				}
			})
		},
		data() {
			return {
				userFeedbackHidden: true,
				name: '',
				property: '',
				hypersensitivity: '',
				note: '',
				swipeList: [],
				medicineList: [],
				selected: ''
			};
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			clickAdd() {
				if (this.swipeList.length < 15) {
					this.swipeList.push({
						id: new Date().getTime(),
						options: [{
								text: '编辑'
							},
							{
								text: '删除',
								style: {
									backgroundColor: 'rgb(255,58,49)'
								}
							}
						],
						content: '新过敏药物' + (this.swipeList.length + 1)
					});
					uni.showToast({
						title: `添加成功`,
						icon: 'none'
					});
				} else {
					uni.showToast({
						title: `上限了>_<`,
						icon: 'none'
					});
				}
			},
			swipeChange(e, index) {
				console.log('返回：', e);
				console.log('当前索引：', index);
				this.selected = index;
			},
			swipeClick(e, index) {
				let {
					content
				} = e;
				if (content.text === '删除') {
					uni.showModal({
						title: '提示',
						content: '是否删除',
						success: res => {
							if (res.confirm) {
								this.swipeList.splice(index, 1);
								uni.showToast({
									title: `${e.content.text}成功`,
									icon: 'none'
								});
							} else if (res.cancel) {
								console.log('用户点击取消');
							}
						}
					});
				} else {
					this.showDiv();
				}
			},

			gotoDetail(index) {
				uni.navigateTo({
					url: `./allergic?de=${encodeURIComponent(JSON.stringify(this.medicineList[index]))}`,
					animationType: 'pop-in',
					animationDuration: 300
				})
			},

			showDiv() {
				this.userFeedbackHidden = false;
			},
			submitFeedback() {
				this.userFeedbackHidden = true;
				uni.showModal({
					title: '提示',
					content: '是否确定修改？',
					success: res => {
						if (res.confirm) {
							// console.log(this.name)
							this.swipeList[this.selected].content = this.name
							console.log(this.$attrs)
							uni.showToast({
								title: `编辑成功`,
								icon: 'none'
							});
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			cancel() {
				this.userFeedbackHidden = true;
			}
		}
	};
</script>

<style lang="scss">
	.content-box {
		flex: 1;
		/* #ifdef APP-NVUE */
		justify-content: center;
		/* #endif */
		height: 50px;
		line-height: 50px;
		padding: 0 15px;
		position: relative;
		background-color: #fff;
		border-bottom-color: #f5f5f5;
		border-bottom-width: 1px;
		border-bottom-style: solid;
	}

	.content-text {
		font-size: 17px;
	}

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
		}
	}

	.popup_title {
		margin: 20rpx auto 20rpx auto;
		width: 100%;
		text-align: center;
		font-size: 20px;
	}

	.popup_textarea {
		font-size: 18px;
		height: 40px;
		margin-left: 20px;
		margin-right: 20px;
		border: 1px solid #000000;
		border-radius: 5%;
	}

	.cancel {
		margin: auto auto 20rpx auto;
		width: 40%;
		left: 10%;
		float: left;
	}

	.confirm {
		margin: auto auto 20rpx 20rpx;
		width: 40%;
		left: 10%;
		float: left;
	}
</style>
