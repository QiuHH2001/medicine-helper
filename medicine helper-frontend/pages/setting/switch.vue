<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="添加提醒" @clickLeft="back()" />
	<view>
		<view class="uni-padding-wrap">
			<uni-segmented-control :current="current" :values="items" style-type="text" @clickItem="onClickItem" />
		</view>
		<view class="content">
			<!-- 表单添加 -->
			<view v-show="current === 0">
				<uni-forms :rules="rules" :modelValue="formData" ref="form" validate-trigger="bind"
					err-show-type="undertext">
					<uni-group title="" top="0">
						<uni-forms-item name="name" required label="药品名称">
							<uni-easyinput type="text" v-model="formData.name" placeholder="请输入药品名称">
							</uni-easyinput>
						</uni-forms-item>
						<uni-forms-item required name="dose" label="用药剂量">
							<uni-easyinput type="text" v-model="formData.dose" placeholder="请输入剂量(例:3片)">
							</uni-easyinput>
						</uni-forms-item>
						<uni-forms-item required name="date" label="用药时间">
							<uni-datetime-picker v-model="formData.date" :hide-second="true">
							</uni-datetime-picker>
						</uni-forms-item>
						<uni-forms-item name="property" label="药品性状">
							<uni-data-checkbox v-model="formData.property" :localdata="property" mode="tag">
							</uni-data-checkbox>
						</uni-forms-item>
						<uni-forms-item name="mode" label="用药方式">
							<uni-data-checkbox v-model="formData.mode" :localdata="mode" mode="tag">
							</uni-data-checkbox>
						</uni-forms-item>
						<uni-forms-item required name="remainder" label="提醒方式">
							<uni-data-checkbox v-model="formData.reaminder" :localdata="remainder">
							</uni-data-checkbox>
						</uni-forms-item>
						<uni-forms-item name="remarks" label="备注">
							<uni-easyinput type="textarea" v-model="formData.remarks" placeholder="请输入备注">
							</uni-easyinput>
						</uni-forms-item>
					</uni-group>
					<view id="submit">
						<button type="primary" @click="submitForm('form')">添加</button>
					</view>
					<view id="reset">
						<button id="reset" @click="resetForm">重置</button>
					</view>
				</uni-forms>
			</view>
			<!-- 药箱添加 -->
			<view v-show="current === 1">
				<view id="medication">
					<button class="selectbox" type="default" size="mini" @click="goto(url)">选择药箱</button>
					<text style="float: right;margin-right: 10%;">{{this.selectbox}}</text>
				</view>

				<uni-forms :rules="rules" :modelValue="formData" ref="form1" validate-trigger="bind"
					err-show-type="undertext">
					<uni-group title="" top="0">
						<uni-forms-item required name="date" label="用药时间">
							<uni-datetime-picker v-model="formData.date" :hide-second="true">
							</uni-datetime-picker>
						</uni-forms-item>
						<uni-forms-item required name="remainder" label="提醒方式">
							<uni-data-checkbox v-model="formData.remainder" :localdata="remainder">
							</uni-data-checkbox>
						</uni-forms-item>
					</uni-group>
					<view id="submit">
						<button type="primary" @click="submitForm('form1')">添加</button>
					</view>
				</uni-forms>
			</view>
			<!-- 语音添加 -->
			<view v-show="current === 2">
				<!-- 自定义录音机组件 -->
				<soundRecording @record="getPath" @clear="clearPath"></soundRecording>
				<view>
					<uni-forms :rules="rules" :modelValue="formData" ref="form2" validate-trigger="bind"
						err-show-type="undertext">
						<uni-group title="" top="0">
							<uni-forms-item required name="date" label="用药时间">
								<uni-datetime-picker v-model="formData.date" :hide-second="true">
								</uni-datetime-picker>
							</uni-forms-item>
						</uni-group>
						<view id="submit">
							<button type="primary" @click="submitForm('form2')">添加</button>
						</view>
					</uni-forms>
				</view>
			</view>
			<!-- 历史记录添加 -->
			<view v-show="current === 3">
				<scroll-view scroll-y="true" class="historybox">
					<view class="list-item" v-for="(item,index) in historylist">
						<view :style="item.selected">
							<uni-list-item showArrow :title="item.name" :rightText="item.date" link @click="tap(index)"
								@longpress="longpress" />
						</view>
					</view>
				</scroll-view>

				<uni-forms :rules="rules" :modelValue="formData" ref="form3" validate-trigger="bind"
					err-show-type="undertext">
					<uni-group title="" top="0">
						<uni-forms-item required name="date" label="用药时间">
							<uni-datetime-picker v-model="formData.date" :hide-second="true">
							</uni-datetime-picker>
						</uni-forms-item>
						<uni-forms-item required name="remainder" label="提醒方式">
							<uni-data-checkbox v-model="formData.reaminder" :localdata="remainder">
							</uni-data-checkbox>
						</uni-forms-item>
					</uni-group>
					<view id="submit">
						<button type="primary" @click="submitForm('form3')">添加</button>
					</view>
				</uni-forms>
			</view>
		</view>
	</view>
</template>

<script>
	//引入自定义录音组件
	import soundRecording from '@/components/sound-recording/sound-recording.vue'
	import url from '@/js/baseUrl.js'

	export default {
		components: {
			soundRecording
		},
		data() {
			return {
				voicePath: '',
				current: 0,
				items: ['表单', '药箱', '语音', '历史'],
				//表单
				formData: {
					name: '',
					date: '',
					dose: '',
					property: '',
					mode: '',
					remainder: '',
					remarks: '',
				},
				property: [{
						text: '液体',
						value: 'liquid'
					},
					{
						text: '颗粒',
						value: 'grain'
					},
					{
						text: '粉末',
						value: 'powder'
					},
					{
						text: '胶囊',
						value: 'capsule'
					}
				],
				mode: [{
						text: '口服',
						value: 'oral'
					},
					{
						text: '滴剂',
						value: 'drop'
					},
					{
						text: '外敷',
						value: 'external'
					}
				],
				remainder: [{
						text: '语音',
						value: 1
					},
					{
						text: '闹钟',
						value: 2
					},
					{
						text: '弹窗',
						value: 3
					}
				],
				rules: {
					name: {
						rules: [{
							required: true,
							errorMessage: '请输入药品名称'
						}]
					},
					dose: {
						rules: [{
							required: true,
							errorMessage: '请输入用药剂量'
						}]
					},
					remainder: {
						rules: [{
							required: true,
							errorMessage: '请选择提醒方式'
						}]
					},
					date: {
						rules: [{
							required: true,
							errorMessage: '请选择时间'
						}]
					},
				},
				selectbox: '未选择',
				select_history: -1,
				historylist: [{
						date: "2022-07-02 20:20",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					},
					{
						date: "2022-07-01 19:58",
						name: "艾瑞昔布片",
						selected: ''
					}
				]
			};
		},
		mounted() {
			//计算当前时间并显示
			setTimeout(() => {
				var date = new Date(),
					year = date.getFullYear(),
					month = date.getMonth() + 1,
					day = date.getDate(),
					hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours(),
					minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
				month >= 1 && month <= 9 ? (month = "0" + month) : "";
				day >= 0 && day <= 9 ? (day = "0" + day) : "";
				var timer = year + '-' + month + '-' + day + ' ' + hour + ':' + minute;
				this.formData.date = timer
			}, 3000)
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			//切换选项卡
			onClickItem(e) {
				if (this.current != e.currentIndex) {
					this.current = e.currentIndex;
				}
			},
			//获取子组件录音路径
			getPath(obj) {
				this.voicePath = obj.path;
				// console.log("获取录音路径",this.voicePath)
			},
			//重录时清空录音路径
			clearPath(obj) {
				this.voicePath = obj.path;
				// console.log("清空录音路径",this.voicePath)
			},
			//验证表单
			submitForm(form) {
				var that = this
				this.$refs[form]
					.validate()
					.then(res => {
						if (that.selectbox == "未选择" && that.current == 1) {
							uni.showToast({
								title: '请选择药品',
								icon: "error"
							})
						}
						// console.log("当前录音路径",that.voicePath)
						else if (that.voicePath == "" && that.current == 2) {
							uni.showToast({
								title: '请录入语音',
								icon: "error"
							})
						} else if (that.select_history == -1 && that.current == 3) {
							uni.showToast({
								title: '请选择历史记录',
								icon: "error"
							})
						} else {
							let data = this.formData
							uni.request({
								url: url + '/eat/FormAdd',
								method: 'POST',
								data: {
									userId: -1,
									name: data.name,
									dose: data.dose,
									administration: data.remarks,
									time: new Date().getTime(),
									property: data.property,
									type: this.current,
									way: data.remainder,
									description: data.remarks
								},
								header: {
									Authorization: sessionStorage.getItem('token')
								},
								success: (res) => {
									let code = res.data.code
									let data = res.data.data;
									if (code === 200) {
										console.log(data)
										uni.showToast({
											icon: 'success',
											title: res.data.message
										})
									} else {
										uni.showToast({
											icon: 'error',
											title: res.data.message
										})
									}
								}
							})
						}

					})
					.catch(errors => {
						console.error('验证失败：', errors)
					})
			},

			//重置表单
			resetForm() {
				this.$refs.form.resetFields()
			},
			goto(url) {
				uni.navigateTo({
					url: '/pages/setting/selectbox'
				})
			},
			longpress() {
				uni.navigateTo({
					url: '/pages/me/history-detail'
				})
			},
			tap(index) {
				// console.log(this.historylist[index].name, this.historylist[index].date);
				// for (var i = 0; i <= this.historylist.length - 1; i++) {
				// 	if (i == index) {
				// 		this.historylist[i].selected = "border: 2px solid #1296db";
				// 	} else {
				// 		this.historylist[i].selected = "";
				// 	}
				// }
				if (this.select_history == -1) {
					this.historylist[index].selected = "border: 2px solid #1296db";
					this.select_history = index;
				} else if (this.select_history != index) {
					this.historylist[index].selected = "border: 2px solid #1296db";
					this.historylist[this.select_history].selected = "";
					this.select_history = index
				}
			},
		}
	};
</script>

<style lang="scss">
	.uni-padding-wrap {
		margin: 20rpx auto 20rpx auto;
		padding: 0rpx 120rpx;
	}

	#submit {
		width: 400rpx;
		margin: auto;
		margin-bottom: 20rpx;
	}

	#reset {
		width: 400rpx;
		margin: auto;
	}

	.selectbox {
		left: 10%;
	}

	#medication {
		margin: 50rpx 80rpx 20rpx;
	}

	.historybox {
		margin: 40rpx auto 20rpx auto;
		height: 690rpx;
		background-color: #F8F8F8;
	}
</style>
