<template>
	<view class="home-container">
		<view id="calendar">
			<tmt-calendar :show="true" @changeDate="changeDate"></tmt-calendar>
		</view>
	</view>
</template>

<script>
	export default {

		onLoad() {

		},

		methods: {
			changeDate(e) {
				console.log(e)
			}
		}
	}
</script>

<style lang="scss" scoped>
	// #calendar {
	// 	height: 200rpx;
	// }

	::v-deep .uni-calendar__content {
		// width: 100px;
		height: 100px;
	}
</style>
