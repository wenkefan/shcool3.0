package com.fwk.shcool30.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.listener.DaoZhanListener;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.BanciBean;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.modue.StationFADAOBean;
import com.fwk.shcool30.modue.TeacherZTBean;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.CarDZNetWork;
import com.fwk.shcool30.network.api.CarFCNetWork;
import com.fwk.shcool30.network.api.StaionNetWork;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.ui.adapter.BaseRecyclerAdapter;
import com.fwk.shcool30.ui.adapter.MapRecyclerViewAdapter;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;


import java.util.List;

import butterknife.BindView;

/**
 * Created by fanwenke on 2017/3/31.
 */

public class StaionActivity extends NFCBaseActivityNo implements NetWorkListener, BaseRecyclerAdapter.OnItemListener, DaoZhanListener {

    @BindView(R.id.station_map_recycler)
    RecyclerView mRecyclerView;

    private int FCleixing;//接／送孩子类型
    private SharedPreferencesUtils sp;

    private MapRecyclerViewAdapter adapter;

    //班次状态为0时，传递过来的数据
    private BanciBean.RerurnValueBean banciBean;

    //班次状态为1时，传递过来的数据
    private TeacherZTBean.RerurnValueBean teacherBean;

    private DisplayMetrics display;

    private List<StationBean.RerurnValueBean> stationBean;

    @Override
    public int getLayoutId() {
        return R.layout.station_map_activity2;
    }

    @Override
    protected void init() {
        sp = new SharedPreferencesUtils();
        banciBean = (BanciBean.RerurnValueBean) getIntent().getSerializableExtra(Keyword.IntentBanCi0);
        teacherBean = (TeacherZTBean.RerurnValueBean) getIntent().getSerializableExtra(Keyword.IntentBanCi1);
        //请求线路
        stationBean = (List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST);
        if (stationBean != null) {
            getStationJiLu();
            recyclerInit();
        } else {
            if (banciBean != null && teacherBean == null) {
                getXianLu0();
            } else {
                getXianLu1();
                getStationJiLu();
            }
        }
    }

    private void getStationJiLu() {
        StationCarJiLuData data = new StationCarJiLuData(this);
        if (data.queryJiLu(sp.getInt(Keyword.BusOrderId)) == null) {
            //请进记录接口
            String url = HTTPURL.daozhanjilu + sp.getInt(Keyword.BusOrderId);
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
        adapter.setOnItemListener(this);
        adapter.setOnClickListener(this);
    }

    @Override
    public void NetWorkSuccess(int Flag) {
        switch (Flag) {
            case Keyword.FLAGSTATION:
                recyclerInit();
                stationBean = (List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST);
                break;
            case Keyword.FLAGDAOZHAN:

                StationCarJiLuData sta = new StationCarJiLuData(this);
                LogUtils.d("新添一站点");
                sta.add(sp.getInt(Keyword.BusOrderId), bean, GetDateTime.getH() + ":" + GetDateTime.getM(), 1);
                adapter.getList(sta.queryJiLu(sp.getInt(Keyword.BusOrderId)));
                Intent intent = new Intent(StaionActivity.this, ChildShangXiaActivity.class);
                intent.putExtra(Keyword.SELECTSTA, bean);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void NetWorkError(int Flag) {
        switch (Flag){
            case Keyword.FLAGDAOZHANERROR:
                StationCarJiLuData sta = new StationCarJiLuData(this);
                LogUtils.d("新添一站点");
                sta.add(sp.getInt(Keyword.BusOrderId), bean, GetDateTime.getH() + ":" + GetDateTime.getM(), 0);
                adapter.getList(sta.queryJiLu(sp.getInt(Keyword.BusOrderId)));
                Intent intent = new Intent(StaionActivity.this, ChildShangXiaActivity.class);
                intent.putExtra(Keyword.SELECTSTA, bean);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setOnItemListener(int position, BaseRecyclerAdapter.ClickableViewHolder holder) {

        ToastUtil.show("xuanzele");
    }
    private StationBean.RerurnValueBean bean;
    @Override
    public void OnClickListener(int position) {
        bean = stationBean.get(position);
        StationCarJiLuData sta = new StationCarJiLuData(this);
        LogUtils.d("这是一站点" + bean.getStationId());
        if (!sta.queryDangqian(sp.getInt(Keyword.BusOrderId), bean.getStationId())) {
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
            StationCarJiLuData sta = new StationCarJiLuData(this);
            adapter.getList(sta.queryJiLu(sp.getInt(Keyword.BusOrderId)));
        }
    }
}
