<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="药物详情" @clickLeft="back" />
	<uni-list id="medications">
		<!-- <uni-list-item title="时间" :rightText="information.date" /> -->
		<uni-list-item title="药品名称" :rightText="information.text" />
		<!-- <uni-list-item title="剂量" :rightText="information.dose" /> -->
		<uni-list-item title="性状" :rightText="information.property" />
		<uni-list-item title="适用症" :rightText="information.indication" />
		<uni-list-item title="服用方式" :rightText="information.administration" />
		<uni-list-item title="可能的过敏症状" :rightText="information.symptom" />
	</uni-list>

</template>

<script>
	export default {
		onLoad(option) {
			const detail = JSON.parse(decodeURIComponent(option.detail))
			this.information = detail
		},
		data() {
			return {
				information: {
					id: '-1',
					url: 'static/images/medicine.png',
					text: "null",
					color: "null",
					property: "null",
					symptom: "null",
					indication: "null",
					administration: "null",
				},
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
