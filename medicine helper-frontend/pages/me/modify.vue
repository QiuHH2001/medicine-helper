<template>
	<view class="modify-container">
		<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="个人信息" @clickLeft="back" />
		<view>
			<uni-list-item title="头像" link @click="open" class="profile">
				<template v-slot:footer>
					<image class="slot-image" :src="user.avatar" mode="aspectFit"></image>
				</template>
			</uni-list-item>
			<uni-list-item title="昵称" :rightText="user.nickname" link @click="inputDialogToggle" />
		</view>

		<view>
			<!-- 修改昵称 -->
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose" mode="input" title="新昵称" :value="user.nickname"
					@confirm="dialogInputConfirm">
				</uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		data() {
			return {
				// rightText: 'nickname',
				user: {
					id: '-1',
					nickname: 'none',
					uid: '%0%',
					phone: '00000000000',
					email: 'xxxxx@xxx.com',
					avatar: '../../static/images/medicine.png',
				},
			}
		},
		onLoad(option) {
			const user = JSON.parse(decodeURIComponent(option.user));
			this.user = user;
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			open() {
				uni.chooseImage({
					count: 1, //默认9
					success: function(res) {
						const path = res.tempFilePaths[0]
						console.log(path)
						uni.saveFile({
							tempFilePath: path,
							success: function(res) {
								const savedFilePath = res.savedFilePath;
								console.log(savedFilePath)
							},
							fail: () => {
								uni.showToast({
									icon: 'error',
									title: '上传头像失败'
								})
							},
						});
					}
				});
			},
			close() {
				this.$refs.popup.close()
			},
			inputDialogToggle() {
				this.$refs.inputDialog.open()
			},
			dialogInputConfirm(val) {
				uni.request({
					url: url + '/user/modify/' + this.user.uid,
					method: 'POST',
					header: {
						Authorization: sessionStorage.getItem('token')
					},
					data: {
						nickname: val
					},
					success: (res) => {
						let code = res.data.code
						if (code === 200) {
							uni.showToast({
								icon: 'success',
								title: '修改成功',
							})
							this.user.nickname = val;
							this.$refs.inputDialog.close();
						} else {
							uni.showToast({
								icon: 'error',
								title: res.data.message,
							})
						}
					}
				})
			},
		},
	}
</script>

<style lang="scss">
	.modify-container {
		width: 100%;

		.profile {
			line-height: 60px;
		}

		.slot-image {
			/* #ifndef APP-NVUE */
			display: block;
			/* #endif */
			margin-right: 10px;
			width: 60px;
			height: 60px;
		}
	}
</style>
