<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="用药历史" @clickLeft="back" />
	<view class="container">
		<uni-list id="history" v-for="(item,index) in list">
			<uni-list-item showArrow :title="item.name" :rightText="item.date" link="navigateTo"
				@click="gotoDetail(index)" />
			<!-- <uni-list-item showArrow title="艾瑞昔布片" rightText="2022-07-01 19:58" link="navigateTo" to="/pages/me/history-detail"/> -->
		</uni-list>
	</view>

</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		onLoad() {
			uni.request({
				url: url + '/eat/history',
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
								date: data[i].time,
								name: data[i].name
							}
							this.list.push(obj)
						}
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
				medicineList: [],

				list: []
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			gotoDetail(index) {
				uni.navigateTo({
					url: `./history-detail?det=${encodeURIComponent(JSON.stringify(this.medicineList[index]))}`,
					animationType: 'pop-in',
					animationDuration: 300
				})
			}
		}
	}
</script>

<style lang="scss">
	#history {
		height: 50px;
		justify-content: center;
	}
</style>
