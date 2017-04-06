package com.fwk.shcool30.network.api;

import android.content.Context;

import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.AttendanceUserData;
import com.fwk.shcool30.modue.AttendanceUserBean;

import java.io.IOException;

/**
 * Created by fanwenke on 2017/3/31.
 */

public class AttendanceUserWork extends BaseNetWork {

    private Context mContext;

    public static AttendanceUserWork newInstance(Context context){
        return new AttendanceUserWork(context);
    }

    public AttendanceUserWork(Context context){
        this.mContext = context;
        initURL();
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.ATTENDANCEUSERFLAG){
            AttendanceUserBean bean = (AttendanceUserBean) cla;
            if (bean.getSuccess() == 10000){

                AttendanceUserData data = new AttendanceUserData(mContext);
                data.addData(bean.getRerurnValue());
            }
        }

    }

    @Override
    public void onFailure(IOException e) {

    }
}
