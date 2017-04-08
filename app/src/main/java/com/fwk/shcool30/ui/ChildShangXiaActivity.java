package com.fwk.shcool30.ui;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.AttendanceUserData;
import com.fwk.shcool30.db.date.ClassInfoData;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.db.date.TeacherZT;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.listener.FacheListener;
import com.fwk.shcool30.listener.NetWorkListener;
import com.fwk.shcool30.modue.AttendanceUserBean;
import com.fwk.shcool30.modue.FristFaChe;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.modue.StationFADAOBean;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.modue.UpDownCar;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.CarFCNetWork;
import com.fwk.shcool30.network.api.DownCarNetWork;
import com.fwk.shcool30.network.api.FristNetWork;
import com.fwk.shcool30.network.api.UpCarNetWork;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.ui.adapter.BaseRecyclerAdapter;
import com.fwk.shcool30.ui.adapter.ChildRecyAdapter;
import com.fwk.shcool30.util.GetDateTime;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;
import com.fwk.shcool30.weight.MainDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fanwenke on 2017/4/5.
 */

public class ChildShangXiaActivity extends NFCBaseActivity implements NetWorkListener, FacheListener, ChildRecyAdapter.SelectChildListener, UpCarNetWork.NetWorkSelectListener, BaseRecyclerAdapter.OnItemListener {

    @BindView(R.id.rv_recyle_shang)
    RecyclerView shangRecycler;

    @BindView(R.id.rv_recyle_xia)
    RecyclerView xiaRecycler;

    @BindView(R.id.rv_recyle_car_child_number)
    RecyclerView childRecycler;

    @BindView(R.id.title_tv)
    TextView title;

    @BindView(R.id.btn_queding)
    Button queding;

    @BindView(R.id.btn_qiliangxuanze)
    Button qlxz;

    @BindView(R.id.tv_quanxuan)
    TextView quanxuan;

    @BindView(R.id.btn_fache)
    Button fache;

    private SharedPreferencesUtils sp;
    private ChildRecyAdapter adapter1;
    private ChildRecyAdapter adapter2;
    private ChildRecyAdapter adapter3;
    private UpAndDownRecordData data;
    private StationBean.RerurnValueBean stationBean;
    private boolean IsQuanxuan = false;
    private boolean IsSKXZ = false;//下车是刷卡还是选择
    private boolean IsQunOurDan = false;//是群下还是单下
    private int stationCount;//站点的个数
    private int dangqianstationCount;
    private StationCarJiLuData stationCarJiLuData = new StationCarJiLuData(this);

    @Override
    public int getLayoutId() {
        return R.layout.childeshangxiaactivity;
    }

    @Override
    protected void init() {
        sp = new SharedPreferencesUtils();
        stationCount = ((List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST)).size();
        data = new UpAndDownRecordData(this);
        Intent intent = getIntent();
        stationBean = (StationBean.RerurnValueBean) intent.getSerializableExtra(Keyword.SELECTSTA);
        title.setText(stationBean.getStationName());
        dangqianstationCount = stationCarJiLuData.queryCount();
        if (stationCount == dangqianstationCount) {
            fache.setText("结束");
        }
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
            adapter1 = new ChildRecyAdapter(list, 1);
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
            adapter2 = new ChildRecyAdapter(list, 2);
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
            adapter3 = new ChildRecyAdapter(list, 3);
            childRecycler.setAdapter(adapter3);
            adapter3.setOnItemListener(this);
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

        selectBean = data.queryToUpCar(sp.getInt(Keyword.BusOrderId), CarId);
        if (selectBean != null) {
            ToastUtil.show(selectBean.getChildName() + "已经下车");
            return;
        }

        selectBean = data.queryChild(sp.getInt(Keyword.BusOrderId), CarId);
        if (selectBean != null) {
            if (selectBean.getShang() == stationBean.getStationId()) {
                //显示是否下车
                MainDialog.setBackListener(this);
                MainDialog.Dangzhanshangxiache(this, selectBean.getChildName());
                return;
            } else {
                IsSKXZ = false;
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
                upCarNetWork.onSetSelectListener(this);
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

    private List<UpAndDownRecordBean> selectChildlist;

    @OnClick({R.id.btn_fache, R.id.select_child, R.id.btn_queding, R.id.btn_qiliangxuanze, R.id.tv_quanxuan})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fache:
                if (dangqianstationCount == stationCount) {
                    //最后一站 要结束
                    int number = data.queryCarList(sp.getInt(Keyword.BusOrderId), 1, 0).size();
                    if (number == 0) {
                        /**
                         * 发车字段为：班次编号、kgid、发车时间、类型(1发车、2停车)
                         * 停车字段为：派车单号、kgid、发车时间、类型(1发车、2停车)
                         */
                        String url = String.format(HTTPURL.API_OPEN, sp.getInt(Keyword.BusOrderId), SpLogin.getKgId(), GetDateTime.getdatetime(),
                                2, SpLogin.getWorkerExtensionId());
                        LogUtils.d("结束接口:" + url);
                        FristNetWork fristNetWork = FristNetWork.newInstance(this);
                        fristNetWork.setNetWorkListener(this);
                        fristNetWork.setUrl(Keyword.FLAGFIRSTFACHE, url, FristFaChe.class);
                    } else {
                        ToastUtil.show("还有幼儿没有下车，请查看！");
                        return;
                    }

                } else {
                    String url = String.format(HTTPURL.API_PROCESS, SpLogin.getKgId(), stationBean.getStationId(), sp.getInt(Keyword.BusOrderId), 2, GetDateTime.getdatetime());
                    LogUtils.d("到站URL：" + url);
                    CarFCNetWork carFCNetWork = CarFCNetWork.newInstance(this);
                    carFCNetWork.setNetWorkListener(this);
                    carFCNetWork.setUrl(Keyword.FLAGFACHE, url, StationFADAOBean.class);
                }
                break;
            case R.id.select_child:
                AttendanceUserData attendanceUserData = new AttendanceUserData(this);
                ClassInfoData classInfoData = new ClassInfoData(this);
                List clazList = attendanceUserData.queryClass(SpLogin.getKgId());
                final String[] clasList = new String[clazList.size()];
                for (int i = 0; i < clazList.size(); i++) {
                    String classname = classInfoData.query(SpLogin.getKgId(),(int)clazList.get(i));
                    clasList[i] = classname;
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("请选择班级");
                dialog.setIcon(R.mipmap.classicon);
                dialog.setItems(clasList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.show("选择了" + clasList[which]);
                        Intent intent = new Intent(ChildShangXiaActivity.this, SelectClasChildActivity.class);
                        intent.putExtra("selectclass", clasList[which]);
                        intent.putExtra("stationid", stationBean.getStationId());
                        startActivity(intent);
                    }
                });
                dialog.create();
                dialog.show();
                break;
            case R.id.btn_queding:
                List<Integer> itme = new ArrayList<>();
                for (String key : map.keySet()) {
                    if (map.get(key).equals("yes")) {
                        itme.add(Integer.valueOf(key));
                        LogUtils.d("key--" + key);
                    }
                }
                selectChildlist = getCardList();
                for (Integer key : itme) {

                    IsSKXZ = true;

                    ToastUtil.show(selectChildlist.get(key).getChildName() + "下车");
                    data.upSelectdateXia(stationBean.getStationId(), 1, 0, selectChildlist.get(key).getChildId(), sp.getInt(Keyword.BusOrderId), 1);
                    adapter2.getDate(getStationList("Xia"));
                    adapter3.getDate(getCardList());
                }
                urlsetXia(itme);
                adapter2.getDate(getStationList("Xia"));
                adapter3.getDate(getCardList());
                qlxz.setVisibility(View.VISIBLE);
                quanxuan.setVisibility(View.GONE);
                queding.setVisibility(View.GONE);
                adapter3.setSelect(false);
                adapter3.setOnItemListener(this);
                break;
            case R.id.btn_qiliangxuanze:
                qlxz.setVisibility(View.GONE);
                quanxuan.setVisibility(View.VISIBLE);
                queding.setVisibility(View.VISIBLE);
                adapter3.setSelect(true);
                adapter3.onSetSelectChildListener(this);
                adapter3.setOnItemListener(null);
                break;
            case R.id.tv_quanxuan:
                if (IsQuanxuan) {
                    Drawable nav_up = getResources().getDrawable(R.mipmap.yuanquan);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    quanxuan.setCompoundDrawables(nav_up, null, null, null);
                    IsQuanxuan = false;
                    adapter3.IsQuanxuan(false);
                } else {
                    Drawable nav_up = getResources().getDrawable(R.mipmap.xuanzhong);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    quanxuan.setCompoundDrawables(nav_up, null, null, null);
                    IsQuanxuan = true;
                    adapter3.IsQuanxuan(true);
                }
                break;
        }
    }

    //群选之后的下车请求
    private void urlsetXia(List<Integer> itme) {
        for (Integer key : itme) {
            IsSKXZ = true;
            /**
             * 字段：派车单号、幼儿编号、站点、时间、状态、kgid、上下车类型（1、上车；2、下车）
             */
            String url1 = String.format(
                    HTTPURL.API_STUDENT_OPEN_DOWN,
                    sp.getInt(Keyword.BusOrderId),
                    selectChildlist.get(key).getChildId(),
                    stationBean.getStationId(),
                    GetDateTime.getdatetime(),
                    1,
                    SpLogin.getKgId(),
                    2);
            LogUtils.d("下车接口-----：" + url1);
            UpCarNetWork upCarNetWork = UpCarNetWork.newInstance(this);
            upCarNetWork.onSetSelectListener(ChildShangXiaActivity.this);
            upCarNetWork.setUrl(Keyword.FLAGUPCAR + Keyword.FLAGUPCAR + key, url1, UpDownCar.class);
        }

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
            case Keyword.FLAGFIRSTFACHE:
                sp.removData();
                stationCarJiLuData.dele();
                TeacherZT teacherZT = new TeacherZT(this);
                teacherZT.dele();
                StaionActivity.stationActivity.finish();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case Keyword.FLAGUPCAR:
                if (IsSKXZ){
                    ToastUtil.show(selectBean.getChildName() + "下车");
                    data.upSelectdateXia(stationBean.getStationId(), 1, 0, ChildId, sp.getInt(Keyword.BusOrderId), 1);
                    adapter2.getDate(getStationList("Xia"));
                    adapter3.getDate(getCardList());
                } else {
                    ToastUtil.show(selectBean.getChildName() + "下车");
                    data.updateXia(stationBean.getStationId(), 1, 1, CarId, sp.getInt(Keyword.BusOrderId), 1);
                    adapter2.getDate(getStationList("Xia"));
                    adapter3.getDate(getCardList());
                }
                break;
        }
    }

    @Override
    public void NetWorkSuccess(int Flag, int key) {

        data.upSelectdateXia(stationBean.getStationId(), 1, 1, selectChildlist.get(key).getChildId(), sp.getInt(Keyword.BusOrderId), 1);
        adapter2.getDate(getStationList("Xia"));
        adapter3.getDate(getCardList());
        map.remove("" + key);
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
            case Keyword.FLAGUPCARERROR://下车
                if (IsSKXZ) {
                    if (IsQunOurDan) {
                        //单下
                        ToastUtil.show(selectBean.getChildName() + "下车");
                        data.upSelectdateXia(stationBean.getStationId(), 1, 0, ChildId, sp.getInt(Keyword.BusOrderId), 1);
                        adapter2.getDate(getStationList("Xia"));
                        adapter3.getDate(getCardList());
                        IsQunOurDan = false;
                    }

                } else {
                    ToastUtil.show(selectBean.getChildName() + "下车");
                    data.updateXia(stationBean.getStationId(), 1, 0, CarId, sp.getInt(Keyword.BusOrderId), 1);
                    adapter2.getDate(getStationList("Xia"));
                    adapter3.getDate(getCardList());
                }
                break;
        }
    }

    @Override
    public void BackListener(Intent intent) {
        //下车
        IsSKXZ = false;
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

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter1 != null && adapter2 != null && adapter3 != null) {
            adapter1.getDate(getStationList("Shang"));
            adapter2.getDate(getStationList("Xia"));
            adapter3.getDate(getCardList());
        }
    }

    private Map<String, String> map = new HashMap<>();

    @Override
    public void selectchildListener(int position, boolean isSelect) {
        String key = position + "";
        String value;
        if (isSelect) {
            value = "yes";
        } else {
            value = "no";
        }
        map.put(key, value);
    }

    private int ChildId;//选择的ChildID

    @Override
    public void setOnItemListener(final int position, BaseRecyclerAdapter.ClickableViewHolder holder) {
        LogUtils.d("position--" + position);
        selectChildlist = getCardList();
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("提示：").setMessage(selectChildlist.get(position).getChildName() + "确定下车？");
        builder.setNegativeButton("下车", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                IsSKXZ = true;
                IsQunOurDan = true;
                selectBean = selectChildlist.get(position);
                /**
                 * 字段：派车单号、幼儿编号、站点、时间、状态、kgid、上下车类型（1、上车；2、下车）
                 */
                ChildId = selectChildlist.get(position).getChildId();
                String url = String.format(
                        HTTPURL.API_STUDENT_OPEN_DOWN,
                        sp.getInt(Keyword.BusOrderId),
                        ChildId,
                        stationBean.getStationId(),
                        GetDateTime.getdatetime(),
                        1,
                        SpLogin.getKgId(),
                        2);
                LogUtils.d("下车接口-----：" + url);
                UpCarNetWork upCarNetWork = UpCarNetWork.newInstance(ChildShangXiaActivity.this);
                upCarNetWork.setNetWorkListener(ChildShangXiaActivity.this);
                upCarNetWork.setUrl(Keyword.FLAGUPCAR + position, url, UpDownCar.class);
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
