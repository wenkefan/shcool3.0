package com.fwk.shcool30.network.api;

import android.app.Activity;


import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.ChildBean;
import com.fwk.shcool30.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 16/11/21.
 * 幼儿接口
 */

public class ChildNetWork extends BaseNetWork {


    private static Activity mActivity;
    private List<ChildBean.RerurnValueBean> list = new ArrayList<>();

    public static ChildNetWork newInstance(Activity activity) {
        mActivity = activity;
        return new ChildNetWork();
    }

    private ChildNetWork() {
        initURL();
    }

    @Override
    public void setNetWorkListener(NetWorkListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.FLAGCHILD) {

            if (cla != null) {

                ChildBean bean = (ChildBean) cla;
                try {

                    list = bean.getRerurnValue();

                    for (ChildBean.RerurnValueBean valuebean : list) {
                        valuebean.setSelectid(0);
                        valuebean.setOperation(false);
                    }

                } catch (Exception o) {

                    final ChildBean finalBean = bean;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(finalBean.getMessage());
                        }
                    });

                    return;

                }
                sp.saveToShared(Keyword.SP_CHILD_LIST, list);
//                Stationutil stationutil = Stationutil.newInstance();
//                stationutil.setMaplist();
                listener.NetWorkSuccess(Keyword.FLAGCHILD);
                list = null;
                bean = null;

            }

        }
    }

    @Override
    public void onFailure(IOException e) {

    }

}
