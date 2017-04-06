package com.fwk.shcool30.network.api;

import android.app.Activity;

import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.TeacherZT;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.TeacherZTBean;

import java.io.IOException;

/**
 * Created by fanwenke on 2017/3/29.
 */

public class TeacherZTWork extends BaseNetWork {

    private static Activity mActivity;

    public static TeacherZTWork newInstance(Activity activity){
        mActivity = activity;
        return new TeacherZTWork();
    }

    private TeacherZTWork(){
        initURL();
    }

    public void setNetWorkListener(NetWorkListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.TEACHERZTFLAG){
            TeacherZTBean bean = (TeacherZTBean) cla;
            if (bean.getSuccess() == 10000){
                //存储到数据库中
                TeacherZT teacherZT = new TeacherZT(mActivity);
                teacherZT.addRecord(bean.getRerurnValue().get(0));
                sp.setInt(Keyword.BusOrderId,bean.getRerurnValue().get(0).getBusOrderId());
                this.listener.NetWorkSuccess(Keyword.TEACHERZTSUEE);//获取到了发车数据
            } else if (bean.getSuccess() == 0){
                //没有状态
                this.listener.NetWorkSuccess(Keyword.TEACHERZTNULL);//没有发车记录
            }
        }
    }

    @Override
    public void onFailure(IOException e) {

    }
}
