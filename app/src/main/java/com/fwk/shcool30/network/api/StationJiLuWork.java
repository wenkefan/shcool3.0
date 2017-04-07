package com.fwk.shcool30.network.api;

import android.app.Activity;

import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.StationWorkJiLuBean;
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
                StationWorkJiLuBean bean = (StationWorkJiLuBean) cla;
                if (bean.getSuccess() == 10000) {
                    StationCarJiLuData data = new StationCarJiLuData(context);
                    for (StationWorkJiLuBean.RerurnValueBean valueBean : bean.getRerurnValue()){
                        if (valueBean.getToStationTime() != null && valueBean.getSendTime() != null){
                            //到站发车了
                            String time = valueBean.getToStationTime().split("T")[1].split(":")[0] + ":" + valueBean.getToStationTime().split("T")[1].split(":")[1];
                            data.add(valueBean.getBusOrderId(),valueBean.getStationName(),valueBean.getStationId(),time,
                                    1,1,1,1);
                        } else if (valueBean.getToStationTime() != null && valueBean.getSendTime() == null){
                            //到站，未发车
                            String time = valueBean.getToStationTime().split("T")[1].split(":")[0] + ":" + valueBean.getToStationTime().split("T")[1].split(":")[1];
                            data.add(valueBean.getBusOrderId(),valueBean.getStationName(),valueBean.getStationId(),time,
                                    1,1,0,0);
                        }
                    }
                    listener.NetWorkSuccess(flag);
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
