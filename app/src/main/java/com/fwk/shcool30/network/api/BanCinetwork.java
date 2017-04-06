package com.fwk.shcool30.network.api;


import android.app.Activity;


import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.BanciBean;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by fanwenke on 16/11/21.
 * 请求班次接口
 */

public class BanCinetwork extends BaseNetWork {


    private static Activity mActivity;

    private List<BanciBean.RerurnValueBean> list = new ArrayList<>();
    private List<BanciBean.RerurnValueBean> list1 = new ArrayList<>();

    public static BanCinetwork newInstance(Activity activity) {

        mActivity = activity;
        return new BanCinetwork();
    }

    private BanCinetwork() {
        initURL();
    }

    public void setNetWorkListener(NetWorkListener listener) {
        this.listener = listener;
    }


    @Override
    public void onSuccess(Object cla, int flag) {

        if (flag == Keyword.FLAGBANCI) {
            BanciBean bean = (BanciBean) cla;
            try {
                for (BanciBean.RerurnValueBean valueBean : bean.getRerurnValue()) {

                    if (valueBean.getTeacherId() == SpLogin.getWorkerExtensionId()) {
                        //表示为用户的班次
                        valueBean.setOriginal(true);
                        list1.add(0, valueBean);
                    } else {
                        //表示为非用户的班次
                        valueBean.setOriginal(false);
                        list.add(valueBean);
                    }
                }
            } catch (Exception o) {

                final BanciBean finalBean = bean;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show(finalBean.getMessage());
                    }
                });
                return;
            }
            //排序
            sortList(list1);
            sortList(list);
            for (int i = 0; i < list1.size(); i++) {
                list.add(i, list1.get(i));
            }
//                sp.saveToShared(Keyword.SP_BANCI, bean);
            sp.saveToShared(Keyword.SP_BANCI_LIST, list);
            bean = null;
            list = null;

            listener.NetWorkSuccess(Keyword.FLAGBANCI);

        }
    }


    NetWorkListener listener;

    @Override
    public void onFailure(IOException e) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show("网络错误");
            }
        });
    }

    private void sortList(List<BanciBean.RerurnValueBean> list) {
        Collections.sort(list, new Comparator<BanciBean.RerurnValueBean>() {
            @Override
            public int compare(BanciBean.RerurnValueBean o1, BanciBean.RerurnValueBean o2) {
                Double d = Double.parseDouble(GetDateTime.getHM2(o1.getSendStartTime()));
                Double d2 = Double.parseDouble(GetDateTime.getHM2(o2.getSendStartTime()));
                return d.compareTo(d2);
            }
        });
    }
}
