package com.fwk.shcool30.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.AttendanceUserData;
import com.fwk.shcool30.db.date.ClassInfoData;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.db.date.TeacherZT;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.listener.DaoZhanListener;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.AttendanceUserBean;
import com.fwk.shcool30.modue.BanciBean;
import com.fwk.shcool30.modue.ChildWorkJiLuBean;
import com.fwk.shcool30.modue.ClassMessage;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.modue.StationCarJiLuBean;
import com.fwk.shcool30.modue.StationFADAOBean;
import com.fwk.shcool30.modue.StationWorkJiLuBean;
import com.fwk.shcool30.modue.TeacherZTBean;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.AttendanceUserWork;
import com.fwk.shcool30.network.api.CarDZNetWork;
import com.fwk.shcool30.network.api.CarFCNetWork;
import com.fwk.shcool30.network.api.ChildWorkJiLuWork;
import com.fwk.shcool30.network.api.ClassInfoWork;
import com.fwk.shcool30.network.api.StaionNetWork;
import com.fwk.shcool30.network.api.StationJiLuWork;
import com.fwk.shcool30.network.api.ZuofeiNetWork;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.ui.adapter.BaseRecyclerAdapter;
import com.fwk.shcool30.ui.adapter.MapRecyclerViewAdapter;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;
import com.fwk.shcool30.weight.MainDialog;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fanwenke on 2017/3/31.
 */

public class StaionActivity extends NFCBaseActivityNo implements NetWorkListener, BaseRecyclerAdapter.OnItemListener, DaoZhanListener {

    @BindView(R.id.station_map_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_count)
    TextView count;

    @BindView(R.id.tv_chakan)
    TextView chakan;

    @BindView(R.id.title_right_iv)
    ImageView zuofei;

    @BindView(R.id.tv_station_name)
    TextView stationName;
    @BindView(R.id.tv_next_name)
    TextView stationNextName;
    @BindView(R.id.tv_yjtiem)
    TextView yjsj;
    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.tv_yjsj_title)
    TextView yjsjtitle;

    public static StaionActivity stationActivity = null;

    private int FCleixing;//接／送孩子类型
    private SharedPreferencesUtils sp;

    private MapRecyclerViewAdapter adapter;

    //班次状态为0时，传递过来的数据
    private BanciBean.RerurnValueBean banciBean;

    //班次状态为1时，传递过来的数据
    private TeacherZTBean.RerurnValueBean teacherBean;

    private DisplayMetrics display;

    private List<StationBean.RerurnValueBean> stationBean;//线路数据

    private StationCarJiLuData stationCarJiLuData;


    @Override
    public int getLayoutId() {
        return R.layout.station_map_activity2;
    }

    @Override
    protected void init() {
        stationActivity = this;
        sp = new SharedPreferencesUtils();
        stationCarJiLuData = new StationCarJiLuData(this);
        banciBean = (BanciBean.RerurnValueBean) getIntent().getSerializableExtra(Keyword.IntentBanCi0);
        teacherBean = (TeacherZTBean.RerurnValueBean) getIntent().getSerializableExtra(Keyword.IntentBanCi1);
        try {
            title.setText(banciBean.getBusScheduleName());
            sp.setString(Keyword.STATIONNAEM, banciBean.getBusScheduleName());
            if (banciBean != null && banciBean.getBusScheduleKindergartenList().size() > 1) {
                sp.setboolean(Keyword.JUANANDJI, true);
                for (int i = 0; i < banciBean.getBusScheduleKindergartenList().size(); i++) {
                    getAttendanceUser(banciBean.getBusScheduleKindergartenList().get(i).getOrganizationId());
                }
            }

        } catch (Exception e) {
            title.setText(sp.getString(Keyword.STATIONNAEM));
        }
        //请求线路
        stationBean = (List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST);
        if (stationBean != null) {
            recyclerInit();
        } else {
            if (banciBean != null && teacherBean == null) {
                getXianLu0();
            } else {
                getXianLu1();
            }
        }
        if (stationCarJiLuData.queryCount() == 0) {
            getStationJiLu();
        }
        UpAndDownRecordData upAndDownRecordData = new UpAndDownRecordData(this);
        if (!upAndDownRecordData.queryJiLu(sp.getInt(Keyword.BusOrderId))) {
            String url = HTTPURL.shangxiachejilu + sp.getInt(Keyword.BusOrderId);
            ChildWorkJiLuWork workJiLuWork = ChildWorkJiLuWork.newInastance(this);
            LogUtils.d("幼儿记录请求URL--" + url);
            workJiLuWork.setNetWorkListener(this);
            workJiLuWork.setUrl(Keyword.CHILDWORKWORK, url, ChildWorkJiLuBean.class);
        }
        chakan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        chakan.getPaint().setAntiAlias(true);//抗锯齿
        zuofei.setVisibility(View.VISIBLE);
    }

    //请求站点到站记录
    private void getStationJiLu() {
        if (stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId)) == null) {
            //请进记录接口
            String url = HTTPURL.daozhanjilu + sp.getInt(Keyword.BusOrderId);
            StationJiLuWork stationJiLuWork = StationJiLuWork.newInstance(this);
            stationJiLuWork.setNetWorkListener(this);
            stationJiLuWork.setUrl(Keyword.STATIONJILU, url, StationWorkJiLuBean.class);
            LogUtils.d("到站记录接口--" + url);
        }
    }

    private void getXianLu0() {
        String url = String.format(HTTPURL.API_ZHANDIAN, SpLogin.getKgId(),
                banciBean.getAttendanceDirections(), banciBean.getLineId());
        LogUtils.d("站点接口:" + url);
        StaionNetWork staionNetWork = StaionNetWork.newInstance(this);
        staionNetWork.setNetWorkListener(this);
        staionNetWork.setUrl(Keyword.FLAGSTATION, url, StationBean.class);
    }

    private void getXianLu1() {
        String url = String.format(HTTPURL.API_ZHANDIAN, SpLogin.getKgId(),
                teacherBean.getAttendanceDirections(), teacherBean.getLineId());
        LogUtils.d("站点接口:" + url);
        StaionNetWork staionNetWork = StaionNetWork.newInstance(this);
        staionNetWork.setNetWorkListener(this);
        staionNetWork.setUrl(Keyword.FLAGSTATION, url, StationBean.class);
    }

    private void recyclerInit() {
        display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MapRecyclerViewAdapter(display);
        mRecyclerView.setAdapter(adapter);
        adapter.getList(stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId)));
        adapter.setOnItemListener(this);
        adapter.setOnClickListener(this);
    }

    @Override
    public void NetWorkSuccess(int Flag) {
        switch (Flag) {
            case Keyword.FLAGSTATION:
                recyclerInit();
                stationBean = (List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST);
                setNextName();
                break;
            case Keyword.FLAGDAOZHAN:
                stationCarJiLuData.add(sp.getInt(Keyword.BusOrderId), bean, GetDateTime.getH() + ":" + GetDateTime.getM(), 1);
                adapter.getList(stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId)));
                Intent intent = new Intent(StaionActivity.this, ChildShangXiaActivity.class);
                intent.putExtra(Keyword.SELECTSTA, bean);
                startActivity(intent);
                break;
            case Keyword.ZUOFEI://作废成功
                AttendanceUserData attData = new AttendanceUserData(this);
                attData.dele();
                sp.removData();
                stationCarJiLuData.dele();
                TeacherZT teacherZT = new TeacherZT(this);
                teacherZT.dele();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case Keyword.STATIONJILU:
                if (adapter != null) {
                    adapter.getList(stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId)));
                } else {
                    recyclerInit();
                }
                break;
            case Keyword.CHILDWORKWORK:
                UpAndDownRecordData data = new UpAndDownRecordData(this);
                int number = data.queryCarList(sp.getInt(Keyword.BusOrderId), 1, 0).size();
                count.setText("" + number);
                break;
        }
    }

    @Override
    public void NetWorkError(int Flag) {
        switch (Flag) {
            case Keyword.FLAGDAOZHANERROR:
                stationCarJiLuData.add(sp.getInt(Keyword.BusOrderId), bean, GetDateTime.getH() + ":" + GetDateTime.getM(), 0);
                adapter.getList(stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId)));
                Intent intent = new Intent(StaionActivity.this, ChildShangXiaActivity.class);
                intent.putExtra(Keyword.SELECTSTA, bean);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setOnItemListener(int position, BaseRecyclerAdapter.ClickableViewHolder holder) {

        Intent intent = new Intent(this, ChakanStationAndChild.class);
        startActivity(intent);
    }

    private StationBean.RerurnValueBean bean;

    @Override
    public void OnClickListener(int position) {
        bean = stationBean.get(position);
        if (!stationCarJiLuData.queryDangqian(sp.getInt(Keyword.BusOrderId), bean.getStationId())) {
            String url = String.format(HTTPURL.API_PROCESS, SpLogin.getKgId(), bean.getStationId(), sp.getInt(Keyword.BusOrderId), 1, GetDateTime.getdatetime());
            LogUtils.d("到站URL：" + url);
            CarDZNetWork carDZNetWork = CarDZNetWork.newInstance(this);
            carDZNetWork.setNetWorkListener(this);
            carDZNetWork.setUrl(Keyword.FLAGDAOZHAN, url, StationFADAOBean.class);
        } else {
            Intent intent = new Intent(StaionActivity.this, ChildShangXiaActivity.class);
            intent.putExtra(Keyword.SELECTSTA, bean);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.getList(stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId)));
        }
        UpAndDownRecordData data = new UpAndDownRecordData(this);
        int number = data.queryCarList(sp.getInt(Keyword.BusOrderId), 1, 0).size();
        setNextName();
        count.setText("" + number);
    }

    @OnClick({R.id.tv_chakan, R.id.title_right_iv})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_chakan:
                startActivity(new Intent(this, CarShengyuChildActivity.class));
                break;
            case R.id.title_right_iv:
                ZuofeiNetWork work = ZuofeiNetWork.newInstance(this);
                work.setNetWorkListener(this);
                MainDialog.ZF(this, work);
                break;
        }
    }

    private void setNextName() {
        List<StationCarJiLuBean> list = stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId));
        if (stationBean != null) {
            if (list == null) {
                stationName.setText("下一站");
                stationNextName.setText(stationBean.get(0).getStationName());
                yjsjtitle.setText("预计时间");
                yjsj.setText(GetDateTime.getYJTime(stationBean.get(0).getDuration()));
            } else if (list.get(list.size() - 1).getIsDaozhan() == 1 && list.get(list.size() - 1).getIsFache() == 0) {
                stationName.setText("当前站");
                stationNextName.setText(stationBean.get(list.size() - 1).getStationName());
                yjsjtitle.setText("实际时间");
                yjsj.setText(list.get(list.size() - 1).getDatatime() + "");
            } else if (list.get(list.size() - 1).getIsFache() == 1) {
                stationName.setText("下一站");
                stationNextName.setText(stationBean.get(list.size()).getStationName());
                yjsj.setText(GetDateTime.getYJTime(stationBean.get(list.size()).getDuration()));
            }
        } else {
            stationBean = (List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST);
        }
    }

    //获得基础用户数据表
    public void getAttendanceUser(int kgid) {
        AttendanceUserData attData = new AttendanceUserData(this);
        if (!attData.queryKgidCound(kgid)) {
            String url = String.format(HTTPURL.youerbiao, kgid, "2000-01-01");
            LogUtils.d("用户表URL--" + url);
            AttendanceUserWork attuser = AttendanceUserWork.newInstance(this);
            attuser.setNetWorkListener(this);
            attuser.setUrl(Keyword.ATTENDANCEUSERFLAG, url, AttendanceUserBean.class);
        }
        ClassInfoData classdata = new ClassInfoData(this);
        if (!classdata.querAll(kgid)) {
            String classinfo = HTTPURL.ClassInfo + kgid;
            LogUtils.d("班级URLclass---:" + classinfo);
            ClassInfoWork classInfoWork = ClassInfoWork.newInstance(this);
            classInfoWork.setUrl(123456, classinfo, ClassMessage.class);
        }
    }
}
