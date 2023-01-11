<template>
	<view class="sound-recording">
		<view class="time">{{status==0?'录音时长':(status==3?'录音已完成':'正在录音中')}}：{{time}} 秒</view>
		<view class="btn">
			<view :class="status==3?'show':'hide'" @tap="reset" hover-class="jump-hover">重新录制</view>
			<view :class="status==3 && playStatus==0?'show':'hide'" @tap="bofang" hover-class="jump-hover">
				{{playStatus==1?'录音播放中':'播放录音'}}</view>
		</view>
		<view class="progress">
			<text class="txt">最大录音时长（{{duration/60000}}分钟）</text>
			<progress :percent="time*(100/(duration/1000))" border-radius="10" color="green" stroke-width="10"
				backgroundColor="#fff" />
		</view>
		<view class="anniu">
			<view :class="status==0?'row':'no-clicking'" @click="kaishi" hover-class="jump-hover">开始</view>
			<view :class="status==1?'row':'no-clicking'" @click="zanting" hover-class="jump-hover">暂停</view>
			<view :class="status==2?'row':'no-clicking'" @tap="jixu" hover-class="jump-hover">继续</view>
			<view :class="status==1 || status==2?'row':'no-clicking'" @tap="tingzhi" hover-class="jump-hover">停止</view>
		</view>
	</view>
</template>

<script>
	const recorderManager = uni.getRecorderManager()
	const innerAudioContext = uni.createInnerAudioContext()
	var init
	export default {
		data() {
			return {
				time: 0, //录音时长
				duration: 60000, //录音最大值ms 60000/1分钟
				tempFilePath: "", //音频路径
				status: 0, //录音状态 0:未开始录音 1:正在录音 2:暂停录音 3:已完成录音
				playStatus: 0, //录音播放状态 0:未播放 1:正在播放
			}
		},
		methods: {
			kaishi: function() {
				clearInterval(init) //清除定时器
				//监听录音自动结束事件(如果不加，录音时间到最大值自动结束后，没获取到录音路径将无法正常进行播放)
				recorderManager.onStop((res) => {
					console.log('recorder stop', res)
					this.tempFilePath = res.tempFilePath
					this.$emit('record', {
						path: res.tempFilePath,
					})
					this.status = 3
					this.recordingTimer(this.time)
				})

				const options = {
					duration: this.duration, //指定录音的时长，单位 ms
					sampleRate: 16000, //采样率
					numberOfChannels: 1, //录音通道数
					encodeBitRate: 96000, //编码码率
					format: 'mp3', //音频格式，有效值 aac/mp3
					frameSize: 10, //指定帧大小，单位 KB
				}
				this.recordingTimer()
				recorderManager.start(options)
				// 监听音频开始事件
				// recorderManager.onStart((res) => {
				//     console.log('recorder start')
				//     this.status = 1
				// })
				this.status = 1
			},

			/**
			 * 暂停录音
			 */
			zanting: function() {
				// recorderManager.onPause(() => {
				//     console.log('recorder pause')
				//     this.status = 2
				// })
				this.status = 2
				this.recordingTimer(this.time)
				recorderManager.pause()
			},

			/**
			 * 继续录音
			 */
			jixu: function() {
				this.status = 1
				this.recordingTimer()
				recorderManager.resume()
			},

			/**
			 * 停止录音
			 */
			tingzhi: function() {
				recorderManager.onStop((res) => {
					console.log('recorder stop', res)
					this.tempFilePath = res.tempFilePath
					//向父组件发送录音路径
					this.$emit('record', {
						path: res.tempFilePath,
					})
					this.status = 3
				})
				this.recordingTimer(this.time)
				recorderManager.stop()
			},

			/**
			 * 播放录音
			 */
			bofang: function() {
				//音频地址
				console.log(this.tempFilePath);
				innerAudioContext.src = this.tempFilePath
				//在ios下静音时播放没有声音，默认为true，改为false就好了。
				// innerAudioContext.obeyMuteSwitch = false

				//点击播放
				if (this.playStatus == 0) {
					this.playStatus = 1
					innerAudioContext.play()
				}
				// //播放结束
				innerAudioContext.onEnded(() => {
					innerAudioContext.stop()
					this.playStatus = 0
				})
			},
			recordingTimer: function(time) {
				var that = this
				if (time == undefined) {
					//将计时器赋值给init
					init = setInterval(function() {
						var time = that.time + 1;
						that.time = time
					}, 1000);
				} else {
					clearInterval(init)
					console.log("暂停计时")
				}
			},

			/**
			 * 重新录制
			 */
			reset: function() {
				var that = this
				uni.showModal({
					title: "重新录音",
					content: "是否重新录制?",
					success(res) {
						if (res.confirm) {
							that.time = 0
							that.tempFilePath = ''
							that.status = 0
							that.playStatus = 0
							innerAudioContext.stop()
							//向父组件发送消息以清空录音路径
							that.$emit('clear', {
								path: '',
							})
						}
					}
				})
			}
		}
	}
</script>

<style>
	.sound-recording {
		background-color: rgb(234, 234, 234);
		margin: 10rpx 30rpx;
		border-radius: 20rpx;
		padding: 5rpx 0rpx;
	}

	.btn {
		margin: 0rpx 100rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.btn .show {
		padding: 10rpx;
		width: 200rpx;
		font-size: 25rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: rgb(178, 228, 228);
		border-radius: 20rpx;
		border: 5rpx solid rgb(127, 204, 214);
	}

	.btn .hide {
		padding: 10rpx;
		width: 200rpx;
		font-size: 25rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 20rpx;
		border: 5rpx solid #eee;
		pointer-events: none;
		background-color: rgba(167, 162, 162, 0.445);
	}

	.time {
		line-height: 70rpx;
		text-align: center;
		font-size: 30rpx;
	}

	.progress {
		margin: 20rpx;
	}

	.play {
		margin: 0rpx 20rpx;
	}

	.txt {
		display: flex;
		justify-content: center;
		line-height: 60rpx;
		font-size: 25rpx;
	}

	.anniu {
		margin: 10rpx 50rpx;
		display: flex;
		justify-content: space-between;
	}

	.row {
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
		font-size: 25rpx;
		width: 80rpx;
		height: 80rpx;
		background-color: rgb(178, 228, 228);
		border: 5rpx solid rgb(127, 204, 214);
	}

	.jump-hover {
		transform: scale(0.9);
	}

	/*禁止点击*/

	.anniu .no-clicking {
		pointer-events: none;
		background-color: rgba(167, 162, 162, 0.445);
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
		font-size: 25rpx;
		width: 80rpx;
		height: 80rpx;
		border: 5rpx solid rgb(241, 244, 245);
	}
</style>
