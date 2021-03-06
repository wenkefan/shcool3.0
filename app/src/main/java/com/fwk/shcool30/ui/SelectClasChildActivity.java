package com.fwk.shcool30.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.AttendanceUserData;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.AttendanceUserBean;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.modue.UpDownCar;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.DownCarNetWork;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.ui.adapter.Selectclasschildadapter;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wenke on 2017/4/7.
 */

public class SelectClasChildActivity extends NFCBaseActivityNo implements NetWorkListener, Selectclasschildadapter.SelectListener {

    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.rv_select_class_child)
    RecyclerView recyclerView;

    private SharedPreferencesUtils sp;

    private Selectclasschildadapter adapter;
    private int stationid;
    private List<AttendanceUserBean.RerurnValueBean> list;
    private int positions;

    public SelectClasChildActivity() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.selectclaschildactivity;
    }

    @Override
    protected void init() {
        sp = new SharedPreferencesUtils();
        String classinfoName = getIntent().getStringExtra("selectclassname");
        int classinfoid = getIntent().getIntExtra("selectclassid",0);
        stationid = getIntent().getIntExtra("stationid",0);
        AttendanceUserData data = new AttendanceUserData(this);
        UpAndDownRecordData upAndDownRecordData = new UpAndDownRecordData(this);
        list = data.queryChildList(SpLogin.getKgId(),classinfoid);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (upAndDownRecordData.queryShangche(SpLogin.getKgId(),sp.getInt(Keyword.BusOrderId),list.get(i).getUserId())){
                    list.remove(i);
                    i --;
                }
            }
        }
        title.setText(classinfoName);
        if (list == null || list.size() == 0){
            recyclerView.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("提示：").setMessage("本班没有幼儿或都已上车。");
            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SelectClasChildActivity.this.finish();
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else {
            initadapter(list);
        }
    }

    private void initadapter(final List<AttendanceUserBean.RerurnValueBean> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Selectclasschildadapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setSelectListener(SelectClasChildActivity.this);
    }

    @Override
    public void NetWorkSuccess(int Flag) {
        if (Flag == Keyword.FLAGDOWNCAR) {//上车
            closeDialog();
            UpAndDownRecordData data = new UpAndDownRecordData(this);
            UpAndDownRecordBean udBean = new UpAndDownRecordBean();
            udBean.setKgId(list.get(positions).getKgId());
            udBean.setClassId(list.get(positions).getClassInfoID());//班级ID
            udBean.setChildId(list.get(positions).getUserId());//幼儿ID
            udBean.setChildName(list.get(positions).getUserName());//幼儿姓名
            udBean.setSACardNo(list.get(positions).getSACardNo());//卡号
            udBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));//发车单号
            udBean.setShang(stationid);//上车站点
            udBean.setXia(0);//下车站点
            udBean.setIsworkShang(1);//是否上传
            udBean.setIsworkXia(0);//是否上传
            udBean.setIsShang(1);
            udBean.setIsXia(0);
            data.add(udBean);
            ToastUtil.show(list.get(positions).getUserName() + "上车");
            finish();
        }
    }

    @Override
    public void NetWorkError(int Flag) {
        if (Flag == Keyword.ShangURL){
            closeDialog();
            UpAndDownRecordData data = new UpAndDownRecordData(this);
            UpAndDownRecordBean udBean = new UpAndDownRecordBean();
            udBean.setKgId(list.get(positions).getKgId());
            udBean.setClassId(list.get(positions).getClassInfoID());//班级ID
            udBean.setChildId(list.get(positions).getUserId());//幼儿ID
            udBean.setChildName(list.get(positions).getUserName());//幼儿姓名
            udBean.setSACardNo(list.get(positions).getSACardNo());//卡号
            udBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));//发车单号
            udBean.setShang(stationid);//上车站点
            udBean.setXia(0);//下车站点
            udBean.setIsworkShang(0);//是否上传
            udBean.setIsworkXia(0);//是否上传
            udBean.setIsShang(1);
            udBean.setIsXia(0);
            data.add(udBean);
            ToastUtil.show(list.get(positions).getUserName() + "上车");
            finish();
        }
    }

    @Override
    public void OnClickListener(int position) {
        showDialog();
        this.positions = position;
        UpAndDownRecordData data = new UpAndDownRecordData(SelectClasChildActivity.this);
        if (!data.queryShangche(SpLogin.getKgId(),sp.getInt(Keyword.BusOrderId),list.get(position).getUserId())){
            String url = String.format(
                    HTTPURL.API_STUDENT_OPEN_DOWN,
                    sp.getInt(Keyword.BusOrderId),
                    list.get(position).getUserId(),
                    stationid,
                    GetDateTime.getdatetime(),
                    1,
                    SpLogin.getKgId(),
                    1);
            LogUtils.d("上车接口-----：" + url);
            DownCarNetWork downCarNetWork = DownCarNetWork.newInstance(SelectClasChildActivity.this);
            downCarNetWork.setNetWorkListener(SelectClasChildActivity.this);
            downCarNetWork.setUrl(Keyword.FLAGDOWNCAR, url, UpDownCar.class);
        } else {
            closeDialog();
            ToastUtil.show(list.get(position).getUserName() + "已经上车");
            finish();
        }
    }
}
