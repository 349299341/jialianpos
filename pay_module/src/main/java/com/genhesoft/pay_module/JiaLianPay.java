package com.genhesoft.pay_module;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;


import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class JiaLianPay extends UniModule {
    String TAG = "TestModule";
    public static int REQUEST_CODE = 1000;

    UniJSCallback unicallback;

    @JSMethod(uiThread = true)
    public void JiaLianFunc(JSONObject options, UniJSCallback callback){
        JSONObject return_data = new JSONObject();
        Log.e("TestModule", "--->"+options.getString("agentId"));
        //这里对必要参数校验
        if (!options.containsKey("appName")) {
            return_data.put("resultCode", -9901);
            return_data.put("resultMsg", "缺少属性appName");

        }
        if (!options.containsKey("transId")) {
            return_data.put("resultCode", -9902);
            return_data.put("resultMsg", "缺少属性transId");
            //return return_data;
        }
        if (options.getString("transId").equals("Sale") && !options.containsKey("transData")) {
            return_data.put("resultCode", -9903);
            return_data.put("resultMsg", "缺少属性transData");

        }
        if (options.getString("transId").equals("Print") && !options.containsKey("printData")) {
            return_data.put("resultCode", -9903);
            return_data.put("resultMsg", "缺少属性printData");

        }
        if (options.getString("transId").equals("Tradequery") && !options.containsKey("queryData")) {
            return_data.put("resultCode", -9903);
            return_data.put("resultMsg", "缺少属性queryData");

        }
        if(callback != null){
            this.unicallback = callback;
        }
        if(return_data.containsKey("resultCode")){
            if(callback != null) {
                callback.invoke(return_data);
            }
            return;
        }
//        Intent intent = new Intent(mUniSDKInstance.getContext(), TestActivity.class);
//        ((Activity)mUniSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE);
//        Log.e("TestModule", "startActivityForResult--->End");

        try {
            if (mUniSDKInstance != null && mUniSDKInstance.getContext() instanceof Activity) {
                Log.e("TestModule", "startActivityForResult--->Begin");
                Intent intent = new Intent();
                //ComponentName componentName = new ComponentName("com.genhesoft.pay_module", "com.xgd.paysdk.PayActivity");
                ComponentName componentName = new ComponentName("com.xgd.paysdk", "com.xgd.paysdk.PayActivity");
                intent.setComponent(componentName);
                Bundle bundle = new Bundle();
                //开始整理数据


                //应用名称
                bundle.putString("appName", options.getString("appName"));
                //交易类型
                bundle.putString("transId", options.getString("transId"));
                //代理商id
                if (options.containsKey("agentId")) {
                    bundle.putString("agentId", options.getString("agentId"));
                }
                //签购单打印
                if (options.containsKey("isneedPrint")) {
                    bundle.putBoolean("isneedPrint", options.getBoolean("isneedPrint"));
                }
                //打印联数	totalPrint	否	String(1)	1	取值范围：0-3，不填默认为2
                if (options.containsKey("totalPrint")) {
                    bundle.putString("totalPrint", options.getString("totalPrint"));
                }
                //强制签到	forceLogin	否	boolean	false	交易前是否强制签到（不填默认不签到)
                if (options.containsKey("forceLogin")) {
                    bundle.putBoolean("forceLogin", options.getBoolean("forceLogin"));
                }
                //强制输密	forcePin	否	boolean	false	交易时，是否强制输密（不填默认不强制)
                if (options.containsKey("forcePin")) {
                    bundle.putBoolean("forcePin", options.getBoolean("forcePin"));
                }
                //激活二维码	isShowQRCode	否	boolean	true	未激活时是否展示合伙人平台加机用的激活二维码；
                if (options.containsKey("isShowQRCode")) {
                    bundle.putBoolean("isShowQRCode", options.getBoolean("isShowQRCode"));
                }
                //是否启用前置摄像头	isUseFrontCamera	否	boolean	false	启用摄像头扫码时，
                if (options.containsKey("isUseFrontCamera")) {
                    bundle.putBoolean("isUseFrontCamera", options.getBoolean("isUseFrontCamera"));
                }
                //是否使用小字体打印 isprintSmall
                if (options.containsKey("isprintSmall")) {
                    bundle.putBoolean("isprintSmall", options.getBoolean("isprintSmall"));
                }

                if (options.containsKey("transData")) {
                    bundle.putString("transData", options.getString("transData"));
                }
                if (options.containsKey("printData")) {
                    bundle.putString("printData", options.getString("printData"));
                }


                intent.putExtras(bundle);  //此bundle由调用方组入参数据
                ((Activity) mUniSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE);

            } else {
                Log.e("TestModule", "startActivityForResult--->error");
            }
        }catch (Exception e){
            Log.e("TestModule", e.getMessage());
        }
        //return return_data;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TestModule", "支付返回 requestCode:"+requestCode+"  resultCode:"+resultCode);
        if(requestCode == REQUEST_CODE ) {
            JSONObject return_data = new JSONObject();
            int _code = data.getIntExtra("resultCode",-1);
            return_data.put("resultCode", _code);
            return_data.put("resultMsg", data.getStringExtra("resultMsg"));

            if(_code == 0){
                //商户名	merchantName	否	String(100)	“测试商户名”	商户名称
                return_data.put("merchantName", data.getStringExtra("merchantName"));
                //商户号	merchantNo	否	String(15)	“849440188889999”	商户号
                return_data.put("merchantNo", data.getStringExtra("merchantNo"));
                //终端号	terminalNo	否	String(8)	“60001234”	终端号
                return_data.put("terminalNo", data.getStringExtra("terminalNo"));
                //机身号	deviceSn	是	String	“N5000000000”	机身号
                return_data.put("deviceSn", data.getStringExtra("deviceSn"));
                //收单应用版本号	appVersionNo	是	int	2	本收单应用的版本号
                return_data.put("appVersionNo", data.getIntExtra("appVersionNo",0));
                //交易数据	transData
                if(data.hasExtra("transData"))
                {return_data.put("transData", data.getStringExtra("transData"));}
                if(data.hasExtra("transList"))
                {return_data.put("transList", data.getStringExtra("transList"));}
                if(data.hasExtra("tradeTotal"))
                {return_data.put("tradeTotal", data.getStringExtra("tradeTotal"));}

            }
            if(this.unicallback != null) {
                Log.e("TestModule", return_data.toJSONString());
                this.unicallback.invoke(return_data);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
