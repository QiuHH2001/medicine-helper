<template>
	<view class="calendar-content">
		<!-- 插入模式 -->
		<uni-calendar :selected="info.selected" :showMonth="false" @change="change" @monthSwitch="monthSwitch" />

		<!-- 今日用药清单 -->
		<view class="block"></view>
		<view id="des">今日用药清单</view>
		<!-- 显示清单并附带右滑操作 -->
		<uni-swipe-action ref="swipeAction" v-for="(item,index) in list" @click="showdetail(index)">
			<uni-swipe-action-item :right-options="options" @click="swipeClick($event, index)">
				<view id="today-list">
					<view id="icon" @click.stop="check(index)">
						<uni-icons :type="item.type" size="28"></uni-icons>
					</view>
					<view id="name" :style="item.style">{{item.medicineName}}</view>
					<view id="date" :style="item.style">{{item.time}}</view>
				</view>
			</uni-swipe-action-item>
		</uni-swipe-action>
		<!-- <uni-list id="medications" v-for="(item) in list">
			<view :style="item.style">
				<uni-list-item showArrow showExtraIcon="true" :extraIcon="item.check" :title="item.name"
					:rightText="item.date" link="navigateTo" to="/pages/setting/details" />
			</view>
		</uni-list> -->
	</view>
</template>

<script>
	import url from '@/js/baseUrl.js';


	/**
	 * 获取任意时间
	 */
	function getDate(date, AddDayCount = 0) {
		if (!date) {
			date = new Date()
		}
		if (typeof date !== 'object') {
			date = date.replace(/-/g, '/')
		}
		const dd = new Date(date)

		dd.setDate(dd.getDate() + AddDayCount) // 获取AddDayCount天后的日期

		const y = dd.getFullYear()
		const m = dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1 // 获取当前月份的日期，不足10补0
		const d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate() // 获取当前几号，不足10补0
		return {
			fullDate: y + '-' + m + '-' + d,
			year: y,
			month: m,
			date: d,
			day: dd.getDay()
		}
	}
	export default {


		data() {
			return {
				info: {
					selected: []
				},
				unchecked: {
					size: '22',
					type: 'circle'
				},
				checked: {
					size: '22',
					type: 'checkbox'
				},
				list: [],
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
				// count: 0,
				// dateE: '',
				detail: {},
				time: [],

				history: [],
				f: false
			}
		},
		onShow() {
			this.$nextTick(() => {

			})
			// TODO 模拟请求异步同步数据
			this.count
			uni.request({
				url: url + '/eat/month',
				header: {
					Authorization: sessionStorage.getItem('token')
				},
				method: 'GET',
				success: (res) => {
					if (res.data.code === 200) {
						const date = []
						for (let i = 0; i < res.data.data.length; i++) {
							if (res.data.data[i].isTaken === true) {
								let t = res.data.data[i].time;
								let dobj = {
									date: t.substring(0, t.indexOf(' ')),
									info: '已打卡'
								}
								date.push(dobj)
							}
						}
						this.info.selected = date;
					} else {
						uni.showToast({
							icon: 'error',
							title: res.data.message
						})
					}
				}
			});
			if (!this.f) {
				uni.request({
					url: url + '/eat/today',
					header: {
						Authorization: sessionStorage.getItem('token')
					},
					method: 'GET',
					success: (res) => {
						let code = res.data.code;
						let data = res.data.data;
						if (code === 200) {
							for (let i = 0; i < res.data.data.length; i++) {
								let idx = res.data.data[i].time.indexOf(' ')
								this.time.push(res.data.data[i].time)
								res.data.data[i].time = res.data.data[i].time.substring(0, idx)
								res.data.data[i].typeBefore = res.data.data[i].type
								res.data.data[i].type = 'circle'
								res.data.data[i].style = ''
								if (res.data.data[i].isTaken === false) {
									this.list.push(res.data.data[i])


								}
							}
							this.f = true
						} else {
							uni.showToast({
								icon: 'error',
								title: res.data.message
							})
						}
					}
				})
			}
		},
		methods: {
			// change(e) {
			// 	if (this.count === 0) {
			// 		this.dateE = e.fulldate;
			// 	}
			// 	this.count++;
			// 	if (this.count === 2 && this.dateE === e.fulldate) {
			// 		this.count = 0
			// 		uni.navigateTo({
			// 			// url: "./history-list?title="+encodeURIComponent(JSON.stringify(e.fulldate))
			// 			url: "./history-list?title=" + e.fulldate
			// 		})
			// 	} else if (this.dateE !== e.fulldate) {
			// 		this.count = 0
			// 	}
			// },
			change(e) {
				//点击的日期
				let click_date = e.fulldate;
				// uni.request({
				// 	url: url + '/eat/history',
				// 	method: 'GET',
				// 	header: {
				// 		Authorization: sessionStorage.getItem('token')
				// 	},
				// 	success: (res) => {
				// 		let code = res.data.code;
				// 		let data = res.data.data;
				// 		if (code === 200) {
				// 			for (let i = 0; i < data.length; i++) {
				// 				data[i].time = data[i].time.substring(0, data[i].time.indexOf(' '))
				// 				if (data[i].time === e.fulldate) {
				// 					this.history.push(data[i])
				// 				}
				// 			}
				// 		}
				// 	}
				// })

				//当前日期
				var date = new Date(),
					year = date.getFullYear(),
					month = date.getMonth() + 1,
					day = date.getDate();
				month >= 1 && month <= 9 ? (month = "0" + month) : "";
				day >= 0 && day <= 9 ? (day = "0" + day) : "";
				var timer = year + '-' + month + '-' + day;
				// console.log(timer)

				//点击的是过去的日期才显示历史清单
				if (click_date < timer) {
					uni.navigateTo({
						// url: "./history-list?title="+encodeURIComponent(JSON.stringify(e.fulldate))
						url: "./history-list?title=" + click_date
					})
				} else {
					console.log(e)
				}
			},
			monthSwitch(e) {
				uni.request({
					url: url + `/eat/month/${e.year}/${e.month}`,
					method: "GET",
					header: {
						Authorization: sessionStorage.getItem('token')
					},
					success: (res) => {
						let code = res.data.code
						let data = res.data.data
						if (code === 200) {
							for (let i = 0; i < data.length; i++) {
								if (data[i].isTaken) {
									let t = data[i].time;
									let f = true;
									let obj = {
										date: t.substring(0, t.indexOf(' ')),
										info: '已打卡'
									}
									for (let j = 0; j < this.info.selected.length; j++) {
										if (this.info.selected[j].date === obj.date) {
											f = false;
											break;
										}
									}
									if (f) {
										this.info.selected.push(obj);
									}
								}
							}
						}
					}
				})
			},
			check(index) {
				if (this.list[index].type == 'circle') {
					uni.request({
						url: url + "/eat/finish/" + this.list[index].id,
						method: "POST",
						header: {
							Authorization: sessionStorage.getItem('token')
						},
						success: (res) => {
							if (res.data.code === 200) {
								this.list.splice(index, 1)
							} else {
								uni.showToast({
									icon: 'error',
									title: res.data.message
								})
							}
						}
					})
					this.list[index].type = 'checkbox'
					this.list[index].style = "text-decoration: line-through;"
				} else {
					this.list[index].type = 'circle'
					this.list[index].style = ""
				}
			},
			showdetail(index) {
				let obj = this.list[index]
				obj.time = this.time[index]
				uni.navigateTo({
					url: `../setting/details?d=${encodeURIComponent(JSON.stringify(this.list[index]))}`
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
					let name = this.list[index].medicineName;
					let dose = this.list[index].dose;
					let date = this.list[index].time;
					// let property = this.list[index].property;
					let mode = this.list[index].way;
					let remainder = this.list[index].typeBefore;
					let remarks = this.list[index].description;
					uni.navigateTo({
						url: "../setting/edit-form?name=" + name + "&dose=" + dose + "&date=" + date + "&mode=" +
							mode + "&remainder=" + remainder + "&remarks=" + remarks
					})
				}
			},
			// alarm() {
			// 	const dcRichAlert = uni.requireNativePlugin('CRGG-Plugin');
			// 	dcRichAlert.setcalendar({
			// 		title: '我是提醒',
			// 		// location: '上海市普陀区',
			// 		allDay: '1',
			// 		description: '测试测试描述',
			// 		startDate: '2022-07-06 10:55:00',
			// 		endDate: '2022-07-06 10:56:00',
			// 		alarmArray_ios: ['-7.f', '-17.f', '-27.5f'],
			// 		alarmArray_android: [0]
			// 	}, result => {
			// 		if (result.type == '0') { //失败 
			// 			message
			// 		} else { //成功 
			// 		}
			// 	});
			// }
		}
	}
</script>

<style lang="scss">
	.block {
		width: 100%;
		height: 6px;
		background-color: #F8F8F8;
	}

	#des {
		margin: 10px auto 10px 10px;
		font-size: 17px;
	}

	#today-list {
		display: flex;
		font-size: 16px;
		height: 50px;
		border: 1px solid lightgray;

		/**子view垂直居中*/
		align-items: center;

		/**内容居中*/
		// justify-content: center;

		#icon {
			margin-left: 20px
		}

		#name {
			margin: auto;
			color: #3b4144;
		}

		#date {
			margin-right: 20px;
			font-size: 14px;
			color: #999;
		}
	}
</style>
