package com.fwk.shcool30.network.api;

import android.app.Activity;

import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.modue.ChildWorkJiLuBean;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.sp.SpLogin;

import java.io.IOException;

/**
 * Created by fanwenke on 2017/4/7.
 */

public class ChildWorkJiLuWork extends BaseNetWork {

    private Activity mActivity;

    public static ChildWorkJiLuWork newInastance(Activity mActivity) {
        return new ChildWorkJiLuWork(mActivity);
    }

    private ChildWorkJiLuWork(Activity mActivity) {
        this.mActivity = mActivity;
        initURL();
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (cla != null) {
            ChildWorkJiLuBean bean = (ChildWorkJiLuBean) cla;
            if (bean.getSuccess() == 10000) {
                UpAndDownRecordData data = new UpAndDownRecordData(mActivity);
                for (ChildWorkJiLuBean.RerurnValueBean valueBean : bean.getRerurnValue()){
                    if (valueBean.getConnectStation() != 0 && valueBean.getSendStation() != 0){
                        UpAndDownRecordBean udBean = new UpAndDownRecordBean();
                        udBean.setKgId(SpLogin.getKgId());
                        udBean.setClassId(valueBean.getClassInfoID());//班级ID
                        udBean.setChildId(valueBean.getChildId());//幼儿ID
                        udBean.setChildName(valueBean.getChildName());//幼儿姓名
                        udBean.setSACardNo(valueBean.getCardNo());//卡号
                        udBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));//发车单号
                        udBean.setShang(valueBean.getConnectStation());//上车站点
                        udBean.setXia(valueBean.getSendStation());//下车站点
                        udBean.setIsworkShang(1);//是否上传
                        udBean.setIsworkXia(1);//是否上传
                        udBean.setIsShang(1);
                        udBean.setIsXia(1);
                        data.add(udBean);
                    } else if (valueBean.getConnectStation() != 0 && valueBean.getSendStation() == 0){
                        UpAndDownRecordBean udBean = new UpAndDownRecordBean();
                        udBean.setKgId(SpLogin.getKgId());
                        udBean.setClassId(valueBean.getClassInfoID());//班级ID
                        udBean.setChildId(valueBean.getChildId());//幼儿ID
                        udBean.setChildName(valueBean.getChildName());//幼儿姓名
                        udBean.setSACardNo(valueBean.getCardNo());//卡号
                        udBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));//发车单号
                        udBean.setShang(valueBean.getConnectStation());//上车站点
                        udBean.setXia(0);//下车站点
                        udBean.setIsworkShang(1);//是否上传
                        udBean.setIsworkXia(0);//是否上传
                        udBean.setIsShang(1);
                        udBean.setIsXia(0);
                        data.add(udBean);
                    }
                }
                listener.NetWorkSuccess(flag);
            }
        }
    }

    @Override
    public void onFailure(IOException e) {

    }
}
