package com.fwk.shcool30.network.api;

import android.app.Activity;

import com.fwk.shcool30.db.date.ClassInfoData;
import com.fwk.shcool30.modue.ClassMessage;
import com.fwk.shcool30.util.ToastUtil;

import java.io.IOException;

/**
 * Created by fanwenke on 2017/4/8.
 */

public class ClassInfoWork extends BaseNetWork {

    private Activity activity;

    private ClassInfoWork(Activity activity) {
        this.activity = activity;
        initURL();
    }

    public static ClassInfoWork newInstance(Activity activity){
        return new ClassInfoWork(activity);
    }

    @Override
    public void onSuccess(Object cla, int flag) {
        if (cla != null){
            ClassMessage message = (ClassMessage) cla;
            if (message.getSuccess() == 10000){
                ClassInfoData data = new ClassInfoData(activity);
                data.add(message.getRerurnValue());
//                listener.NetWorkSuccess(flag);
            }
        }
    }

    @Override
    public void onFailure(IOException e) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show("网络错误");
            }
        });
    }
}
