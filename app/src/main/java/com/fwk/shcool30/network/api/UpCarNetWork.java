package com.fwk.shcool30.network.api;

import android.app.Activity;


import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.modue.UpDownCar;

import java.io.IOException;


/**
 * Created by fanwenke on 16/11/22.
 * 上车接口
 */

public class UpCarNetWork extends BaseNetWork {
    private NetWorkSelectListener netWorkSelectListener;
    public void onSetSelectListener(NetWorkSelectListener netWorkSelectListener){
        this.netWorkSelectListener = netWorkSelectListener;
    }
    private static Activity mActivity;

    public static UpCarNetWork newInstance(Activity activity){
        mActivity = activity;
        return new UpCarNetWork();
    }

    private UpCarNetWork(){
        initURL();
    }

    @Override
    public void onSuccess(Object cla, int flag) {
            if (cla != null){

                UpDownCar upDownCar = (UpDownCar) cla;
                if (flag == Keyword.FLAGUPCAR) {
                    listener.NetWorkSuccess(flag);
                } else {
                    netWorkSelectListener.NetWorkSuccess(Keyword.FLAGUPCAR,flag - Keyword.FLAGUPCAR - Keyword.FLAGUPCAR);
                }
            }
    }

    @Override
    public void onFailure(IOException e) {
//        netWorkSelectListener.NetWorkError(Keyword.XiaURL);
        if (listener != null) {
            listener.NetWorkError(Keyword.FLAGUPCARERROR);
        }
    }

    public interface NetWorkSelectListener {

        public void NetWorkSuccess(int Flag,int key);

        public void NetWorkError(int Flag);

    }
}
