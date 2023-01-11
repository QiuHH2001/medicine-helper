<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="药物详情" @clickLeft="back" />
	<uni-list>
		<uni-list-item title="药品名称" :rightText="information.name" />
		<uni-list-item title="性状" :rightText="information.property" />
		<uni-list-item title="过敏反应" :rightText="information.symptom" />
		<uni-list-item title="备注" :rightText="information.administration" />
	</uni-list>
</template>

<script>
	export default {
		onLoad(option) {
			const medicine = JSON.parse(decodeURIComponent(option.de))
			this.information = medicine
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
