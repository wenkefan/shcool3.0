package com.fwk.shcool30.network.api;

import android.app.Activity;


import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.StaBean;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 16/11/21.
 * 站点接口
 */

public class StaionNetWork extends BaseNetWork {


    private static Activity mActivity;
    private List<StationBean.RerurnValueBean> list = new ArrayList<>();


    public static StaionNetWork newInstance(Activity activity) {
        mActivity = activity;
        return new StaionNetWork();
    }

    private StaionNetWork() {
        initURL();
    }

    @Override
    public void setNetWorkListener(NetWorkListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.FLAGSTATION) {

            if (cla != null) {

                StationBean bean = (StationBean) cla;
                try {
                    if (bean.getRerurnValue() != null) {
                        sp.saveToShared(Keyword.STAIDLIST, bean.getRerurnValue());
                    }

                } catch (Exception o) {

                    final StationBean finalBean = bean;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(finalBean.getMessage());
                        }
                    });

                }
                listener.NetWorkSuccess(Keyword.FLAGSTATION);
                bean = null;

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
