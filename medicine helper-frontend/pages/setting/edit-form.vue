<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="编辑提醒" @clickLeft="back()" />

	<!-- 显示默认数据并可修改 -->
	<uni-forms :rules="rules" :modelValue="formData" ref="form" validate-trigger="bind" err-show-type="undertext">
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
		<button class="cancel" type="default" size="default" @click="cancel()">取消</button>
		<button class="confirm" type="primary" size="default" @click="submitForm('form')">保存</button>
		<!-- <view id="submit">
			<button type="primary" @click="submitForm('form')">保存</button>
		</view>
		<view id="cancel">
			<button @click="cancel">取消</button>
		</view> -->
	</uni-forms>
</template>

<script>
	export default {
		data() {
			return {
				formData: {
					name: '',
					date: '',
					dose: '',
					property: "liquid",
					mode: "drop",
					reaminder: 2,
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
				}
			}
		},
		onLoad(options){
			this.formData.name = options.name;
			this.formData.dose = options.dose;
			this.formData.date = options.date;
			this.formData.property = options.property;
			this.formData.mode = options.mode;
			this.formData.remainder = options.reaminder;
			this.formData.remarks = options.remarks;
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			//验证表单
			submitForm(form) {
				var that = this
				this.$refs[form]
					.validate()
					.then(res => {
						console.log('表单的值：', res)
						uni.showToast({
							title: '保存成功',
							icon: "none"
						})
						uni.navigateBack({
							delta: 1
						})
					})
					.catch(errors => {
						console.error('保存失败：', errors)
					})
			},

			//重置表单
			cancel() {
				uni.navigateBack({
					delta: 1
				})
			}
		}
	}
</script>

<style lang="scss">
	// #submit {
	// 	width: 400rpx;
	// 	margin: auto;
	// 	margin-bottom: 20rpx;
	// }

	// #cancel {
	// 	width: 400rpx;
	// 	margin: auto;
	// }
	
	.cancel {
		margin: auto 20rpx auto -20rpx;
		width: 40%;
		left: 10%;
		float: left;
	}
	
	.confirm {
		width: 40%;
		left: 10%;
		float: left;
	}
</style>
