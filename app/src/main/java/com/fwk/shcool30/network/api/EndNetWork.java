package com.fwk.shcool30.network.api;

import android.app.Activity;


import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.util.ToastUtil;

import java.io.IOException;


/**
 * Created by fanwenke on 16/11/22.
 * 结束接口
 */

public class EndNetWork extends BaseNetWork {

    private static Activity mActivity;

    public static EndNetWork newInstance(Activity activity) {
        mActivity = activity;
        return new EndNetWork();
    }

    private EndNetWork() {
        initURL();
    }

    @Override
    public void setNetWorkListener(NetWorkListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.FLAGENDDAOZHAN) {

            if (cla != null) {

                listener.NetWorkSuccess(Keyword.FLAGENDDAOZHAN);

            }
        }
    }

    @Override
    public void onFailure(IOException e) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show("网络错误");
            }
        });
    }

}
