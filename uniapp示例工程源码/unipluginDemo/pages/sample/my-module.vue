<template>
	<view>
		<button type="primary" @click="testsyncFunc">测试消费</button>
		<button type="primary" @click="testsyncPrint">测试打印</button>
	</view>

</template>

<script>
	// 获取 module
	var testModule = uni.requireNativePlugin("JiaLianPay")
	export default {
		data() {
			return {}
		},
		onLoad() {

		},
		methods: {
			testsyncFunc() {
				// 调用异步方法
				const res = testModule.JiaLianFunc({
					appName: 'jlpaysdk',
					agentId: '',
					transId: 'Sale',
					isneedPrint: false,
					totalPrint: "1",
					forceLogin: true,
					isShowQRCode: true,
					transData: {
						amt: "1"
					}
				}, (ret) => {
					console.error(ret)
					uni.showModal({
						title: '提示',
						content: JSON.stringify(ret),

					});
				})

			},
			gotoNativePage() {
				testModule.gotoNativePage();
			},
			transIdPickerChange(e) {
				this.transId = e.detail.value
			},
			testsyncPrint(){
				const res = testModule.JiaLianFunc({
					appName: 'jlpaysdk',
					agentId: '',
					transId: 'Print',
					// isneedPrint: false,
					// totalPrint: "1",
					// forceLogin: true,
					isShowQRCode: true,
					printData: {"content":[{"text":"小字体，左靠","font":0,"align":0},{"text":"小字体，右靠","font":0,"align":1},{"text":"小字体，居中","font":0,"align":2},{"text":"正常字体，左靠","font":1,"align":0},{"text":"正常字体，右靠","font":1,"align":1},{"text":"正常字体，居中","font":1,"align":2},{"text":"大字体1，左靠","font":2,"align":0},{"text":"大字体1，右靠","font":2,"align":1},{"text":"大字体1，居中","font":2,"align":2},{"text":"大字体2，左靠","font":3,"align":0},{"text":"大字体2，右靠","font":3,"align":1},{"text":"大字体2，居中","font":3,"align":2},{"left":"左靠小字体","right":"右靠小字体","font":0},{"left":"左靠正常字体","right":"右靠正常字体","font":1},{"left":"左靠大字体1","right":"右靠大字体1","font":2},{"left":"左靠大字体2","right":"右靠大字体2","font":3},{"separator":0},{"separator":1}]}
				}, (ret) => {
					console.error(ret)
					uni.showModal({
						title: '提示',
						content: JSON.stringify(ret),
				
					});
				})
			}
		}
	}
</script>

<style>
</style>
