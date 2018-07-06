package com.gl.education.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.payInfo.MyWXPayUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    //微信appId
    public static String APPID = "wxc3d63044c63e0b27";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zy_code", "WX Pay onCreate");
        api = WXAPIFactory.createWXAPI(this, APPID,true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i("zy_code", "WX Pay onResp");
        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){

            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK://成功
                    ToastUtils.showShort("支付成功");

                    Message msg = new Message();
                    msg.arg1 = 1;
                    MyWXPayUtils.wxHandler.sendMessage(msg);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://取消支付
                    ToastUtils.showShort("支付取消");
                    Message msg1 = new Message();
                    msg1.arg1 = 0;
                    MyWXPayUtils.wxHandler.sendMessage(msg1);
                    break;
                case BaseResp.ErrCode.ERR_COMM:// -1
                    ToastUtils.showShort("支付失败");
                    Message msg2 = new Message();
                    msg2.arg1 = 1;
                    MyWXPayUtils.wxHandler.sendMessage(msg2);
                    break;
                default:
                    ToastUtils.showShort("支付失败");
                    Message msg3 = new Message();
                    msg3.arg1 = 1;
                    MyWXPayUtils.wxHandler.sendMessage(msg3);
                    break;
            }


            finish();
        }
    }
}
