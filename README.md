# jialianpos
适用用在uniapp中调用嘉联POS机的SDK

嘉联的POS机有如下这么多型号：

1) 新 国 都：N5、N3、N86、N6

2) 联 迪：A8、A7

3) 商 米：P2

3) 新 大 陆：N910、N920

## 本插件只支持“新国都”的机型
因为手里只有新国都的机器


### 使用方法
所需参数参考嘉联支付的开发文档：https://help.jlpay.com/api/docs/document/20191012000110

交易类型(transId)请全部使用英文不要使用汉字。
```
<template>
	<view>
		<button type="primary" @click="testsyncFunc">消费测试</button>
	</view>

</template>

<script>
	// 引用插件
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
			
		}
	}
</script>

<style>
</style>

```

