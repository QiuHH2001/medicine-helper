<template>
	<uni-nav-bar :fixed="true" shadow status-bar left-icon="left" left-text="返回" title="选择药箱" @clickLeft="back" />
	<scroll-view class="grid-dynamic-box" scroll-y="true">
		<uni-grid :column="2" :highlight="true">
			<uni-grid-item v-for="(item, index) in dynamicList" :index="index" :key="index">
				<view class="select" @click=select(index)>
					<image v-if="dynamicList.length !== 0" src="/static/icon/select.png" mode="aspectFit"></image>
				</view>
				<view class="grid-item-box" :style="{'backgroundColor':item.color}" @click="goto()">
					<image :src="item.url" class="image" mode="aspectFill" />
					<text class="text">{{ item.text }}</text>
					<!-- <text style="align-self: center;color: gray;font-size: 12px;">点击查看详情</text> -->
				</view>
			</uni-grid-item>
		</uni-grid>
	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {
				dynamicList: [{
					url: `/static/imgaes/pic2.jpg`,
					text: `艾瑞西布片 `,
					color: '#f5f5f5'
				}],
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},

			goto() {
				uni.navigateTo({
					url: '/pages/me/box-detail',
					animationType: 'pop-in',
					animationDuration: 300
				})
			},

			select(index) {
				var a = this.dynamicList[index].text;
				var that = this;
				uni.showModal({
					title: '提示',
					content: `确认选择该药品？`,
					success: function(res) {
						if (res.confirm) {
							console.log('用户点击确定');
							let pages = getCurrentPages(); //获取所有页面栈实例列表
							let nowPage = pages[pages.length - 1]; //当前页
							let prevPage = pages[pages.length - 2]; //上一页
							prevPage.$vm.selectbox = a; //修改上一页data里面的selectbox
							that.back();
						} else if (res.cancel) {
							console.log('用户点击取消')
						}
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.grid-dynamic-box {
		height: auto;

		.select {
			position: absolute;
			/* #ifndef APP-NVUE */
			display: flex;
			/* #endif */
			justify-content: center;
			align-items: center;
			height: 22px;
			width: 22px;
			z-index: 1000;
			top: 10px;
			right: 10px;
			background-color: white;
			border-radius: 50%;
		}
	}

	.grid-item-box {
		flex: 1;
		// position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		justify-content: center;
		padding: 15px 0;

		.image {
			width: 100px;
			height: 100px;
			align-self: center;
		}

		.text {
			font-size: 14px;
			margin-top: 5px;
			align-self: center;
		}

	}
</style>
