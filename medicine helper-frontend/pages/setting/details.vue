<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="用药详情" @clickLeft="back()" />
	<uni-list id="medications">
		<uni-list-item title="时间" :rightText="information.time" />
		<uni-list-item title="药品名称" :rightText="information.medicineName" />
		<uni-list-item title="剂量" :rightText="information.dose" />
		<!-- <uni-list-item title="性状" :rightText="information.property" /> -->
		<!-- <uni-list-item title="服用方式" :rightText="information.mode" /> -->
		<uni-list-item title="提醒方式" :rightText="information.way" />
		<uni-list-item title="备注" :rightText="information.description" />
	</uni-list>
</template>

<script>
	export default {
		onLoad(option) {
			const detail = JSON.parse(decodeURIComponent(option.d))
			this.information = detail
			switch (detail.way) {
				case 1:
					this.information.way = '语音';
					break;
				case 2:
					this.information.way = '闹钟';
					break;
				case 3:
					this.information.way = '弹窗';
					break;
				default:
					this.information.way = null;
					break;
			}
		},
		data() {
			return {
				information: {},
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			}
		}
	}
</script>

<style lang="scss">

</style>
