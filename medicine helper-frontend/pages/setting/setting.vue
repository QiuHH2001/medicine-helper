<template>
	<view class="setting-container">
		<!-- 清单列表 -->
		<uni-list id="medications" v-for="(item,index) in list">
			<!-- 右滑操作 -->
			<uni-swipe-action ref="swipeAction">
				<uni-swipe-action-item :right-options="options" @click="swipeClick($event, index)">
					<uni-list-item showArrow :title="item.name" :rightText="item.date" link="navigateTo"
						@click="gotoDetail(index)" />
				</uni-swipe-action-item>
			</uni-swipe-action>
		</uni-list>
	</view>

	<!-- 右下角悬浮按钮 -->
	<view>
		<uni-fab horizontal="right" @fabClick="fabClick()"></uni-fab>
	</view>
</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		onLoad() {
			uni.request({
				url: url + '/eat/',
				header: {
					Authorization: sessionStorage.getItem('token')
				},
				method: 'GET',
				success: (res) => {
					let code = res.data.code;
					let data = res.data.data;
					if (code === 200) {
						this.medicineList = data;
						for (let i = 0; i < data.length; i++) {
							let obj = {
								name: data[i].medicineName,
								date: data[i].time
							}
							this.list.push(obj)
						}
						console.log(this.list)
					} else {
						uni.showToast({
							icon: 'error',
							title: res.data.message
						})
					}
				}
			})
		},
		data() {
			return {
				unchecked: {
					size: '22',
					type: 'circle'
				},
				checked: {
					size: '22',
					type: 'checkbox'
				},
				list: [],
				medicineList: [],
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
			}
		},
		methods: {
			fabClick() {
				uni.navigateTo({
					url: "./switch"
				})
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
								this.list.splice(index, 1);
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
					let name = this.medicineList[index].medicineName;
					let dose = this.medicineList[index].dose;
					let date = this.medicineList[index].time;
					// let property = this.list[index].property;
					let mode = this.medicineList[index].way;
					let remainder = this.medicineList[index].typeBefore;
					let remarks = this.medicineList[index].description;
					uni.navigateTo({
						url: "./edit-form?name=" + name + "&dose=" + dose + "&date=" + date + "&mode=" +
							mode + "&remainder=" + remainder + "&remarks=" + remarks
					})
				}
			},
			gotoDetail(index) {
				uni.navigateTo({
					url: `./details?d=${encodeURIComponent(JSON.stringify(this.medicineList[index]))}`
				})
			}
		}
	}
</script>

<style lang="scss">
	#medications {
		height: 50px;
		justify-content: center;
	}
</style>
