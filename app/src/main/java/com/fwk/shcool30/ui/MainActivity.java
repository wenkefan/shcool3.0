package com.fwk.shcool30.ui;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.AttendanceUserData;
import com.fwk.shcool30.db.date.ClassInfoData;
import com.fwk.shcool30.db.date.TeacherZT;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.AttendanceUserBean;
import com.fwk.shcool30.modue.BanciBean;
import com.fwk.shcool30.modue.ClassMessage;
import com.fwk.shcool30.modue.FristFaChe;
import com.fwk.shcool30.modue.TeacherZTBean;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.AttendanceUserWork;
import com.fwk.shcool30.network.api.BanCinetwork;
import com.fwk.shcool30.network.api.ClassInfoWork;
import com.fwk.shcool30.network.api.FristNetWork;
import com.fwk.shcool30.network.api.TeacherZTWork;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.ui.adapter.BaseRecyclerAdapter;
import com.fwk.shcool30.ui.adapter.MainRecyclerViewAdapter;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;
import com.fwk.shcool30.weight.MainDialog;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends NFCBaseActivityNo implements NetWorkListener, BaseRecyclerAdapter.OnItemListener {

    @BindView(R.id.man_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.title_tv)
    TextView title;

    private TeacherZTBean.RerurnValueBean SelectTeacherZT;//从数据库中获取到的发车状态

    List<BanciBean.RerurnValueBean> banciList;

    private MainRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferencesUtils sp;
    private TeacherZT teacherZT;

    private boolean isFcjl = false;//用户是否有发车flag

    @Override
    public int getLayoutId() {
        return com.fwk.shcool30.R.layout.activity_main;
    }

    /**
     * 1判断当前用户是否有发车记录
     * 如果有：1。从数据库中读取发车数据；
     * 2，数据库中没有记录，从服务器中获取数据
     * 没有发车记录，请求班次接口
     */
    @Override
    protected void init() {
        sp = new SharedPreferencesUtils();
        showDialog();
        getBanciUrl();
        //储存基础数据
        getAttendanceUser();
    }

    /**
     * 从数据库中
     */
    private boolean getDB() {
        teacherZT = new TeacherZT(this);
        SelectTeacherZT = teacherZT.queryRecord(SpLogin.getWorkerExtensionId(), 1, GetDateTime.gettime());
        if (SelectTeacherZT == null) {
            return false;
        } else {
            sp.setInt(Keyword.BusOrderId,SelectTeacherZT.getBusOrderId());
            return true;
        }
    }

    /**
     * 从服务器中获取
     */
    private void getIntenit() {
        String url = String.format(HTTPURL.fachejilu, SpLogin.getKgId(), SpLogin.getWorkerExtensionId(), GetDateTime.gettime());
        LogUtils.d("用户发车状态url---" + url);
        TeacherZTWork work = TeacherZTWork.newInstance(this);
        work.setNetWorkListener(this);
        work.setUrl(Keyword.TEACHERZTFLAG, url, TeacherZTBean.class);
    }

    /**
     * 请求班次接口
     */
    private void getBanciUrl() {
        BanCinetwork banCinetwork = BanCinetwork.newInstance(this);
        banCinetwork.setNetWorkListener(this);
        String url = String.format(HTTPURL.API_BANCI, SpLogin.getKgId());
        LogUtils.d("班次接口:" + url);
        banCinetwork.setUrl(Keyword.FLAGBANCI, url, BanciBean.class);
    }

    @Override
    public void NetWorkSuccess(int Flag) {
        switch (Flag) {
            case Keyword.TEACHERZTSUEE:
                closeDialog();
                //获取有发车记录
                if (getDB()){//这次判断为了从数据库中获取数据
                    MainDialog.Beagin(MainActivity.this, banciList, SelectTeacherZT);
                    isFcjl = true;
                } else {
                    getIntenit();
                }
                break;
            case Keyword.TEACHERZTNULL:
                //没有发车记录
                //请求班次接口
//                getBanciUrl();
                break;
            case Keyword.FLAGBANCI:
                handler.sendEmptyMessage(Keyword.FLAGBANCI);
                break;
            case Keyword.FLAGFIRSTFACHE://发车成功
                handler.sendEmptyMessage(Keyword.FLAGFIRSTFACHE);
                break;
        }
    }

    @Override
    public void NetWorkError(int Flag) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Keyword.FLAGBANCI:
                    closeDialog();
                    banciList = (List<BanciBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.SP_BANCI_LIST);
                    if (getDB()) {
                        //查询、加载数据库
                        MainDialog.Beagin(MainActivity.this, banciList, SelectTeacherZT);
                        isFcjl = true;
                    } else {
                        //从服务器获取
                        getIntenit();
                    }
                    //请求成功加载班次列表
                    recyclerView();
                    break;
                case Keyword.FLAGFIRSTFACHE:
                    //保存数据库
                    TeacherZTBean.RerurnValueBean teacherBean = new TeacherZTBean.RerurnValueBean();
                    teacherBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));
                    teacherBean.setLineId(banciBean.getLineId());
                    teacherBean.setTeacherId(SpLogin.getWorkerExtensionId());
                    teacherBean.setBusScheduleId(banciBean.getBusScheduleId());
                    teacherBean.setAttendanceDirections(banciBean.getAttendanceDirections());
                    teacherBean.setOrganizationId(SpLogin.getKgId());
                    teacherBean.setStartTime(GetDateTime.gettime());
                    teacherBean.setStatus(1);
                    teacherBean.setBusId(banciBean.getBusId());
                    teacherBean.setDriverId(banciBean.getDriverId());
                    teacherZT.addRecord(teacherBean);

                    sp.saveToShared(Keyword.SELECTBANCI,banciBean);
                    Intent intent = new Intent(MainActivity.this,StaionActivity.class);
                    intent.putExtra(Keyword.IntentBanCi0,banciBean);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    /**
     * 配置RecyclerView
     */
    private void recyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemListener(this);
    }
    private BanciBean.RerurnValueBean banciBean;
    @Override
    public void setOnItemListener(int position, BaseRecyclerAdapter.ClickableViewHolder holder) {
        banciBean = banciList.get(position);
        switch (banciBean.getStatus()){
            case 0:
                //判断用户是否有发车记录
                if (isFcjl) {
                    //有发车记录，提示去废除已发车的记录
                    MainDialog.YYX(MainActivity.this, banciList, SelectTeacherZT);
                } else {
                    //直接进入
                    //请求发车接口
                    /**
                     * 发车字段为：班次编号、kgid、发车时间、类型(1发车、2停车)
                     * 停车字段为：派车单号、kgid、发车时间、类型(1发车、2停车)
                     */
                    String url = String.format(HTTPURL.API_OPEN, banciBean.getBusScheduleId(), SpLogin.getKgId(), GetDateTime.getdatetime(),
                            1, SpLogin.getWorkerExtensionId());
                    LogUtils.d("发车接口:" + url);
                    FristNetWork fristNetWork = FristNetWork.newInstance(this);
                    fristNetWork.setNetWorkListener(this);
                    fristNetWork.setUrl(Keyword.FLAGFIRSTFACHE, url, FristFaChe.class);
                }
                break;
            case 1:
                if(teacherZT.queryFlag(banciBean,GetDateTime.gettime())){
                    Intent intent = new Intent(MainActivity.this,StaionActivity.class);
                    intent.putExtra(Keyword.IntentBanCi1,SelectTeacherZT);
                    startActivity(intent);
                    finish();
                    //是自己的  直接进去
                } else {
                    //不是自己的 不让进
                    ToastUtil.show("此班次由其他老师操作中...");
                }
                break;
            case 2:
                //此班次已结束
                ToastUtil.show("此班次已结束！");
                return;
        }

    }
    //获得基础用户数据表
    public void getAttendanceUser() {
        AttendanceUserData attData = new AttendanceUserData(this);
        if (!attData.queryCound()){
            String url = String.format(HTTPURL.youerbiao, SpLogin.getKgId(),"2000-01-01");
            LogUtils.d("用户表URL--" + url);
            AttendanceUserWork attuser = AttendanceUserWork.newInstance(this);
            attuser.setNetWorkListener(this);
            attuser.setUrl(Keyword.ATTENDANCEUSERFLAG,url, AttendanceUserBean.class);
        }
        ClassInfoData classdata = new ClassInfoData(this);
        if (!classdata.querAll(SpLogin.getKgId())) {
            String classinfo = HTTPURL.ClassInfo + SpLogin.getKgId();
            LogUtils.d("班级URLclass---:" + classinfo);
            ClassInfoWork classInfoWork = ClassInfoWork.newInstance(this);
            classInfoWork.setUrl(123456,classinfo, ClassMessage.class);
        }
    }
}
