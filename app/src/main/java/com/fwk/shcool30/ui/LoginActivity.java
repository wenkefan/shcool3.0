package com.fwk.shcool30.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;


import com.fwk.shcool30.R;
import com.fwk.shcool30.listener.OnSucceedListener;
import com.fwk.shcool30.modue.LoginBean;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.OKHttp;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.util.DataVerifyUtils;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements OnSucceedListener {

    private static final int LOGION = 1;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String username;//登录名称
    private String password;//登录密码
    private LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.schoolcar4_activity_login;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void onResume() {
        if (SpLogin.getAlreadyLogin()) {
            //直接跳转
            callIntent();
        }
        super.onResume();
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        username = etUserName.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (DataVerifyUtils.VerifyData(username, password)) {
            String url = String.format(HTTPURL.API_LOGIN, username, password);
            require(LOGION, url, LoginBean.class);
            showDialog();
        }
    }

    private void require(int flag, String url, Class cla) {
        OKHttp okhttp = new OKHttp();
        okhttp.setListener(this);
        LogUtils.d("登录URL：" + url);
        okhttp.request(flag, url, cla);
    }


    @Override
    public <T> void OnSucceed(int flag, T cla, final String message) {
        if (cla != null){
            closeDialog();
            if (flag == LOGION){
                loginBean = (LoginBean) cla;
                if (loginBean.getRerurnValue() != null){
                    SpLogin.setAlreadyLogin(true);
                    SpLogin.save(username, password, loginBean.getRerurnValue().getKgId(), (String) loginBean.getRerurnValue().getKgName(), loginBean.getRerurnValue().getName(), loginBean.getRerurnValue().getUserId(), loginBean.getRerurnValue().getWorkerExtensionId());
                    handler.sendEmptyMessage(LOGION);
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(loginBean.getMessage());
                        }
                    });
                }
            }
        }else {
            closeDialog();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(message);
                }
            });
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOGION){
                callIntent();
            }
        }
    };

    @Override
    public void Error() {
        closeDialog();
    }

    private void callIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private ProgressDialog progressDialog;

//    private void showDialog(){
//        if (progressDialog == null){
//
//            progressDialog = new ProgressDialog(this);
//
//        }
//
//        progressDialog.setMessage("正在加载中...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//    }
//
//    private void closeDialog(){
//        if (progressDialog != null){
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (progressDialog != null){
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
}
