<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="药箱管理" @clickLeft="back" />
	<scroll-view class="grid-dynamic-box" scroll-y="true">
		<uni-grid :column="2" :highlight="true">
			<uni-grid-item v-for="(item, index) in dynamicList" :index="index" :key="index">
				<view class="delete" @click=del(index)>
					<image v-if="dynamicList.length !== 0" src="/static/icon/delete.png" mode="aspectFit"></image>
				</view>
				<view class="grid-item-box" :style="{'backgroundColor':item.color}" @click="goto(index)">
					<image :src="item.url" class="image" mode="aspectFill" />
					<text class="text">{{ item.text }}</text>
					<!-- <text style="align-self: center;color: gray;font-size: 12px;">点击查看详情</text> -->
				</view>
			</uni-grid-item>
		</uni-grid>
	</scroll-view>

	<uni-fab ref="fab" :pattern="pattern" direction="horizontal" :content="content" horizontal="right"
		@trigger="trigger" />

	<view v-show="userFeedbackHidden == false" class="popup_content">
		<view class="popup_title">编辑药物</view>
		<view class="info" scroll-y="true">
			<uni-forms ref="baseForm">
				<uni-forms-item label="名称" required>
					<uni-easyinput v-model="name" />
				</uni-forms-item>
				<uni-forms-item label="用法" required>
					<uni-easyinput v-model="method" />
				</uni-forms-item>
				<uni-forms-item label="适用性" required>
					<uni-easyinput v-model="applicability" />
				</uni-forms-item>
				<view class="example-body">
					<uni-file-picker ref="files" file-mediatype="image" v-model="medicinePic" :limit="1" title="*选择一张图片"
						@select="select" :auto-upload="false" file-extname="png,jpg">
					</uni-file-picker>
				</view>
			</uni-forms>
		</view>
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
				url: url + '/medicineChest/',
				header: {
					Authorization: sessionStorage.getItem('token')
				},
				method: 'GET',
				success: (res) => {
					let code = res.data.code;
					let data = res.data.data;
					if (code === 200) {
						for (let i = 0; i < data.length; i++) {
							let obj = {
								id: data[i].id,
								url: '/static' + data[i].picture,
								text: data[i].name,
								color: '#f5f5f5',
								property: data[i].property,
								symptom: data[i].symptom,
								indication: data[i].indication,
								administration: data[i].administration,
							}
							this.dynamicList.push(obj);
						}
					} else {
						uni.showToast({
							icon: 'error',
							title: res.data.message
						})
					}
				},

			})
		},

		data() {
			return {
				dynamicList: [],
				userFeedbackHidden: true,
				name: '',
				method: '',
				applicability: '',
				medicinePic: '',
				content: [{
						iconPath: '/static/icon/upload.png',
						selectedIconPath: '/static/icon/upload.png',
						text: '导入'
					},
					{
						iconPath: '/static/icon/add.png',
						selectedIconPath: '/static/icon/add.png',
						text: '添加'
					}
				],
				pattern: {
					color: '#7A7E83',
					backgroundColor: '#fff',
					selectedColor: '#7A7E83'
				}
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			add() {
				if (this.dynamicList.length < 9) {
					this.dynamicList.push({
						url: `/static/imgaes/pic${this.dynamicList.length+1}.jpg`,
						text: `${this.name} `,
						color: this.dynamicList.length % 2 === 0 ? '#f5f5f5' : "#fff"
					})
				} else {
					uni.showToast({
						title: '最多添加9个',
						icon: 'none'
					});
				}
			},
			del(index) {
				var that = this;
				uni.showModal({
					title: '提示',
					content: `确认删除选中？`,
					success: function(res) {
						if (res.confirm) {
							// console.log('用户点击确定');
							uni.request({
								url: url + '/medicineChest/' + that.dynamicList[index].id,
								method: 'DELETE',
								header: {
									Authorization: sessionStorage.getItem('token')
								},
								success: (res) => {
									if (res.data.code === 200) {

									} else {
										uni.showToast({
											icon: 'error',
											title: res.data.message
										})
									}
								}
							})
							that.dynamicList.splice(index, 1)
						} else if (res.cancel) {
							// console.log('用户点击取消')
						}
					}
				})
			},
			trigger(e) {
				// console.log(e)
				this.content[e.index].active = !e.item.active
				if (e.item.text == "添加") {
					this.userFeedbackHidden = false;
				} else {
					uni.showToast({
						title: "功能开发中",
						icon: "error"
					})
				}
			},

			submitFeedback() {
				this.userFeedbackHidden = true;
				uni.showModal({
					title: '提示',
					content: '确认添加？',
					success: res => {
						if (res.confirm) {
							console.log(this.name)
							this.add();
							uni.showToast({
								title: `添加成功`,
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
			},

			goto(index) {
				uni.navigateTo({
					url: '/pages/me/box-detail?detail=' + encodeURIComponent(JSON.stringify(this.dynamicList[
						index])),
					animationType: 'pop-in',
					animationDuration: 300,
				})
			},
			select(e) {
				console.log('选择文件：', e)
			},
			upload() {
				this.$refs.files.upload()
			}
		}
	}
</script>

<style lang="scss">
	.grid-dynamic-box {
		height: auto;

		.delete {
			position: absolute;
			/* #ifndef APP-NVUE */
			display: flex;
			/* #endif */
			justify-content: center;
			align-items: center;
			height: 20px;
			width: 20px;
			z-index: 1000;
			top: 12px;
			right: 15px;
			padding: 5px;
		}
	}

	.grid-item-box {
		flex: 1;
		// position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		justify-content: center;
		padding: 15px 0;

		.image {
			width: 100px;
			height: 100px;
			align-self: center;
		}

		.text {
			font-size: 14px;
			margin-top: 5px;
			align-self: center;
		}

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

	.example-body {
		margin-right: 35px;
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
