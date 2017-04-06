package com.fwk.shcool30.network.api;

import android.app.Activity;


import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.modue.StationFADAOBean;

import java.io.IOException;


/**
 * Created by fanwenke on 16/11/22.
 * 发车接口
 */

public class CarFCNetWork extends BaseNetWork {

    private static Activity mActivity;
    private static int mFlag;

    public static CarFCNetWork newInstance(Activity activity){
        mActivity = activity;
        return new CarFCNetWork();
    }

    private CarFCNetWork(){
        initURL();
    }
    public void getFlag(int flag){
        mFlag = flag;
    }
    @Override
    public void onSuccess(Object cla, int flag) {
        if (flag == Keyword.FLAGFACHE){
            if (cla != null){

                StationFADAOBean fadaoBean = (StationFADAOBean) cla;

                listener.NetWorkSuccess(Keyword.FLAGFACHE);
            }
        }
    }

    @Override
    public void onFailure(IOException e) {

            listener.NetWorkError(Keyword.FLAGFACHEERROR);
    }
}
