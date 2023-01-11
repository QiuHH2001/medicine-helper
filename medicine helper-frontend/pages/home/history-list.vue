<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" :title="title" @clickLeft="back" />
	<!-- 清单列表 -->
	<uni-list id="medications" v-for="(item,index) in list">
		<uni-list-item showArrow :title="item.name" :rightText="item.date" link="navigateTo"
			@click="gotoDetail(index)" />
	</uni-list>
</template>

<script>
	import url from '@/js/baseUrl.js'
	export default {
		data() {
			return {
				//赋值为首页点击的日期
				title: "",
				list: [],
				history: [],
			}
		},
		//将标题赋值为所点击的日期
		onLoad(options) {
			this.title = options.title
			uni.request({
				url: url + '/eat/history',
				method: "GET",
				header: {
					Authorization: sessionStorage.getItem('token')
				},
				success: (res) => {
					let code = res.data.code
					let data = res.data.data;
					if (code === 200) {
						for (let i = 0; i < data.length; i++) {
							data[i].time = data[i].time.substring(0, data[i].time.indexOf(' '))
							let obj = {
								date: data[i].time,
								name: data[i].name
							}
							if (obj.date === this.title) {
								this.list.push(obj)
								let mName = data[i].name
								let administration = data[i].administration
								data[i].medicineName = mName
								data[i].description = administration
								this.history.push(data[i]);
							}
						}
						console.log(this.history)
					}
				}
			})
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			gotoDetail(index) {
				uni.navigateTo({
					url: `../setting/details?d=${encodeURIComponent(JSON.stringify(this.history[index]))}`
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
