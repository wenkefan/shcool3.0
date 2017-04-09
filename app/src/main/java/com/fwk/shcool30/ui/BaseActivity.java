package com.fwk.shcool30.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fanwenke on 2017/3/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        init();
    }

    public abstract int getLayoutId();

    protected abstract void init();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public void showDialog(){
        if (progressDialog == null){

            progressDialog = new ProgressDialog(this);

        }

        progressDialog.setMessage("正在加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void closeDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
