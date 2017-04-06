package com.fwk.shcool30.network.api;

import android.app.Activity;
import android.content.Context;

import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.StationJiLuBean;
import com.fwk.shcool30.util.ToastUtil;

import java.io.IOException;

/**
 * Created by fanwenke on 2017/4/6.
 * 站点记录接口
 */

public class StationJiLuWork extends BaseNetWork {

    private Activity context;

    public static StationJiLuWork newInstance(Activity context) {
        return new StationJiLuWork(context);
    }

    private StationJiLuWork(Activity context) {
        this.context = context;
        initURL();
    }

    @Override
    public void setNetWorkListener(NetWorkListener listener) {
        super.setNetWorkListener(listener);
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.STATIONJILU) {
            if (cla != null) {
                StationJiLuBean bean = (StationJiLuBean) cla;
                if (bean.getSuccess() == 10000) {
                    StationCarJiLuData data = new StationCarJiLuData(context);
                    for (StationJiLuBean.RerurnValueBean valueBean : bean.getRerurnValue()){
                        if (!data.queryDangqian(sp.getInt(Keyword.BusOrderId), valueBean.getStationId())){
//                            data.add();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(IOException e) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show("网络错误");
            }
        });
    }
}
