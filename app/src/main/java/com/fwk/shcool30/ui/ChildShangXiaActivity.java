package com.fwk.shcool30.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.AttendanceUserData;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.listener.FacheListener;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.AttendanceUserBean;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.modue.StationFADAOBean;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.modue.UpDownCar;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.CarFCNetWork;
import com.fwk.shcool30.network.api.DownCarNetWork;
import com.fwk.shcool30.network.api.UpCarNetWork;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.ui.adapter.ChildRecyAdapter;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;
import com.fwk.shcool30.weight.MainDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fanwenke on 2017/4/5.
 */

public class ChildShangXiaActivity extends NFCBaseActivity implements NetWorkListener, FacheListener {

    @BindView(R.id.rv_recyle_shang)
    RecyclerView shangRecycler;

    @BindView(R.id.rv_recyle_xia)
    RecyclerView xiaRecycler;

    @BindView(R.id.rv_recyle_car_child_number)
    RecyclerView childRecycler;

    @BindView(R.id.title_tv)
    TextView title;

    private SharedPreferencesUtils sp;
    private ChildRecyAdapter adapter1;
    private ChildRecyAdapter adapter2;
    private ChildRecyAdapter adapter3;
    private UpAndDownRecordData data;
    private StationBean.RerurnValueBean stationBean;

    @Override
    public int getLayoutId() {
        return R.layout.childeshangxiaactivity;
    }

    @Override
    protected void init() {
        sp = new SharedPreferencesUtils();
        data = new UpAndDownRecordData(this);
        Intent intent = getIntent();
        stationBean = (StationBean.RerurnValueBean) intent.getSerializableExtra(Keyword.SELECTSTA);
        title.setText(stationBean.getStationName());
        setShangRecycler();
        setXiaRecycler();
        setChildRecycler();
    }

    //上车recycler
    private void setShangRecycler() {
        List<UpAndDownRecordBean> list;
        list = getStationList("Shang");
        if (list != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            shangRecycler.setHasFixedSize(true);
            shangRecycler.setLayoutManager(linearLayoutManager);
            adapter1 = new ChildRecyAdapter(list,1);
            shangRecycler.setAdapter(adapter1);
        }

    }

    //下车recycler
    private void setXiaRecycler() {
        List<UpAndDownRecordBean> list;
        list = getStationList("Xia");
        if (list != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            xiaRecycler.setHasFixedSize(true);
            xiaRecycler.setLayoutManager(linearLayoutManager);
            adapter2 = new ChildRecyAdapter(list,2);
            xiaRecycler.setAdapter(adapter2);
        }
    }

    //车上recycler
    private void setChildRecycler() {
        List<UpAndDownRecordBean> list;
        list = getCardList();
        if (list != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            childRecycler.setHasFixedSize(true);
            childRecycler.setLayoutManager(linearLayoutManager);
            adapter3 = new ChildRecyAdapter(list,3);
            childRecycler.setAdapter(adapter3);
        }
    }

    //一站点获取
    private List<UpAndDownRecordBean> getStationList(String sx) {
        List<UpAndDownRecordBean> list = null;
        if (sx.equals("Shang")) {
            list = data.queryStationShangList(sp.getInt(Keyword.BusOrderId), stationBean.getStationId());
        } else if (sx.equals("Xia")) {
            list = data.queryStationXiaList(sp.getInt(Keyword.BusOrderId), stationBean.getStationId());
        }
        return list == null ? null : list;
    }

    //一车上剩余获取
    private List<UpAndDownRecordBean> getCardList() {
        List<UpAndDownRecordBean> list;
        list = data.queryCarList(sp.getInt(Keyword.BusOrderId), 1, 0);
        return list == null ? null : list;
    }

    private String CarId;
    private UpAndDownRecordBean selectBean;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        CarId = readICCardNo(intent);

        selectBean =data.queryToUpCar(sp.getInt(Keyword.BusOrderId), CarId);
        if (selectBean != null){
            ToastUtil.show(selectBean.getChildName() + "已经下车");
            return;
        }

        selectBean = data.queryChild(sp.getInt(Keyword.BusOrderId), CarId);
        if (selectBean != null) {
            if (selectBean.getShang() == stationBean.getStationId()) {
                //显示是否下车
                MainDialog.setBackListener(this);
                MainDialog.Dangzhanshangxiache(this,selectBean.getChildName());
                return;
            } else {
                //下车
                /**
                 * 字段：派车单号、幼儿编号、站点、时间、状态、kgid、上下车类型（1、上车；2、下车）
                 */
                String url = String.format(
                        HTTPURL.API_STUDENT_OPEN_DOWN,
                        sp.getInt(Keyword.BusOrderId),
                        selectBean.getChildId(),
                        stationBean.getStationId(),
                        GetDateTime.getdatetime(),
                        1,
                        SpLogin.getKgId(),
                        2);
                LogUtils.d("下车接口-----：" + url);
                UpCarNetWork upCarNetWork = UpCarNetWork.newInstance(this);
                upCarNetWork.setNetWorkListener(this);
                upCarNetWork.setUrl(Keyword.FLAGUPCAR, url, UpDownCar.class);
                return;
            }
        }
        AttendanceUserData attendanceUserData = new AttendanceUserData(this);
        AttendanceUserBean.RerurnValueBean bean;
        bean = attendanceUserData.queryChild(CarId);
        /**
         * 字段：派车单号、幼儿编号、站点、时间、状态、kgid、上下车类型（1、上车；2、下车）
         */
        if (bean != null) {
            String url = String.format(
                    HTTPURL.API_STUDENT_OPEN_DOWN,
                    sp.getInt(Keyword.BusOrderId),
                    bean.getUserId(),
                    stationBean.getStationId(),
                    GetDateTime.getdatetime(),
                    1,
                    SpLogin.getKgId(),
                    1);
            LogUtils.d("上车接口-----：" + url);
            DownCarNetWork downCarNetWork = DownCarNetWork.newInstance(this);
            downCarNetWork.setNetWorkListener(this);
            downCarNetWork.setUrl(Keyword.FLAGDOWNCAR, url, UpDownCar.class);
        } else {
            ToastUtil.show("没有找到幼儿");
        }
    }

    @OnClick(R.id.btn_fache)
    public void OnClick(View view) {
        String url = String.format(HTTPURL.API_PROCESS, SpLogin.getKgId(), stationBean.getStationId(), sp.getInt(Keyword.BusOrderId), 2, GetDateTime.getdatetime());
        LogUtils.d("到站URL：" + url);
        CarFCNetWork carFCNetWork = CarFCNetWork.newInstance(this);
        carFCNetWork.setNetWorkListener(this);
        carFCNetWork.setUrl(Keyword.FLAGFACHE, url, StationFADAOBean.class);
    }

    @Override
    public void NetWorkSuccess(int Flag) {
        switch (Flag) {
            case Keyword.FLAGFACHE://发车
                StationCarJiLuData sta = new StationCarJiLuData(this);
                sta.updataStation(sp.getInt(Keyword.BusOrderId), stationBean.getStationId());
                ToastUtil.show(stationBean.getStationName() + "开始发车");
                finish();
                break;
            case Keyword.FLAGDOWNCAR://上车
                AttendanceUserBean.RerurnValueBean bean;
                AttendanceUserData attendanceUserData = new AttendanceUserData(this);
                UpAndDownRecordBean udBean = new UpAndDownRecordBean();
                bean = attendanceUserData.queryChild(CarId);
                udBean.setKgId(bean.getKgId());
                udBean.setClassId(bean.getClassInfoID());//班级ID
                udBean.setChildId(bean.getUserId());//幼儿ID
                udBean.setChildName(bean.getUserName());//幼儿姓名
                udBean.setSACardNo(bean.getSACardNo());//卡号
                udBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));//发车单号
                udBean.setShang(stationBean.getStationId());//上车站点
                udBean.setXia(0);//下车站点
                udBean.setIsworkShang(1);//是否上传
                udBean.setIsworkXia(0);//是否上传
                udBean.setIsShang(1);
                udBean.setIsXia(0);
                data.add(udBean);
                ToastUtil.show(bean.getUserName() + "上车");
                adapter1.getDate(getStationList("Shang"));
                adapter3.getDate(getCardList());
                break;
            case Keyword.FLAGUPCAR://下车
                ToastUtil.show(selectBean.getChildName() + "下车");
                data.updateXia(stationBean.getStationId(),1,1,CarId,sp.getInt(Keyword.BusOrderId),1);
                adapter2.getDate(getStationList("Xia"));
                adapter3.getDate(getCardList());
                break;
        }
    }

    @Override
    public void NetWorkError(int Flag) {
        switch (Flag) {
            case Keyword.FLAGFACHEERROR://发车
                StationCarJiLuData sta = new StationCarJiLuData(this);
                sta.updataStation(sp.getInt(Keyword.BusOrderId), stationBean.getStationId(), 0);
                ToastUtil.show(stationBean.getStationName() + "开始发车");
                finish();
                break;
            case Keyword.ShangURL://上车
                AttendanceUserBean.RerurnValueBean bean;
                AttendanceUserData attendanceUserData = new AttendanceUserData(this);
                UpAndDownRecordBean udBean = new UpAndDownRecordBean();
                bean = attendanceUserData.queryChild(CarId);
                udBean.setKgId(bean.getKgId());
                udBean.setClassId(bean.getClassInfoID());//班级ID
                udBean.setChildId(bean.getUserId());//幼儿ID
                udBean.setChildName(bean.getUserName());//幼儿姓名
                udBean.setSACardNo(bean.getSACardNo());//卡号
                udBean.setBusOrderId(sp.getInt(Keyword.BusOrderId));//发车单号
                udBean.setShang(stationBean.getStationId());//上车站点
                udBean.setXia(0);//下车站点
                udBean.setIsworkShang(0);//是否上传
                udBean.setIsworkXia(0);//是否上传
                udBean.setIsShang(1);
                udBean.setIsXia(0);
                data.add(udBean);
                ToastUtil.show(bean.getUserName() + "上车");
                adapter1.getDate(getStationList("Shang"));
                adapter3.getDate(getCardList());
                break;
            case Keyword.FLAGUPCAR://下车
                ToastUtil.show(selectBean.getChildName() + "下车");
                data.updateXia(stationBean.getStationId(),1,0,CarId,sp.getInt(Keyword.BusOrderId),1);
                adapter2.getDate(getStationList("Xia"));
                adapter3.getDate(getCardList());
                break;
        }
    }

    @Override
    public void BackListener(Intent intent) {
        //下车
        /**
         * 字段：派车单号、幼儿编号、站点、时间、状态、kgid、上下车类型（1、上车；2、下车）
         */
        String url = String.format(
                HTTPURL.API_STUDENT_OPEN_DOWN,
                sp.getInt(Keyword.BusOrderId),
                selectBean.getChildId(),
                stationBean.getStationId(),
                GetDateTime.getdatetime(),
                1,
                SpLogin.getKgId(),
                2);
        LogUtils.d("下车接口-----：" + url);
        UpCarNetWork upCarNetWork = UpCarNetWork.newInstance(this);
        upCarNetWork.setNetWorkListener(this);
        upCarNetWork.setUrl(Keyword.FLAGUPCAR, url, UpDownCar.class);
    }
}
