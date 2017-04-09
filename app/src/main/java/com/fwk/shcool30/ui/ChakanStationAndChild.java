package com.fwk.shcool30.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.modue.StationCarJiLuBean;
import com.fwk.shcool30.ui.adapter.ChakanStation;
import com.fwk.shcool30.ui.adapter.MapRecyclerViewAdapter;
import com.fwk.shcool30.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fanwenke on 2017/4/9.
 */

public class ChakanStationAndChild extends NFCBaseActivityNo {

    @BindView(R.id.rv_recyle_station)
    RecyclerView rv_station;

    @BindView(R.id.title_tv)
    TextView title;

    private SharedPreferencesUtils sp;

    private com.fwk.shcool30.ui.adapter.ChakanStation adapter;

    @Override
    public int getLayoutId() {
        return R.layout.chakanstationandchild;
    }

    @Override
    protected void init() {
        sp = new SharedPreferencesUtils();
        title.setText("查看");
        StationCarJiLuData stationCarJiLuData = new StationCarJiLuData(this);
        List<StationCarJiLuBean> list = stationCarJiLuData.queryJiLu(sp.getInt(Keyword.BusOrderId));
        List<ChakanStation> chakanStationList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getIsDaozhan() == 1){
                ChakanStation chakanStation = new ChakanStation();
                chakanStation.setStationid(list.get(i).getStationId());
                chakanStation.setStationName(list.get(i).getStationName() + "--上车");
                chakanStation.setShangXia(1);
                chakanStationList.add(chakanStation);
                ChakanStation chakanStation1 = new ChakanStation();
                chakanStation1.setStationid(list.get(i).getStationId());
                chakanStation1.setStationName(list.get(i).getStationName() + "--下车");
                chakanStation1.setShangXia(2);
                chakanStationList.add(chakanStation1);
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_station.setLayoutManager(layoutManager);
        adapter = new com.fwk.shcool30.ui.adapter.ChakanStation(chakanStationList);
        rv_station.setAdapter(adapter);
    }

    public class ChakanStation{
        private int stationid;
        private String stationName;
        private int ShangXia;

        public int getShangXia() {
            return ShangXia;
        }

        public void setShangXia(int shangXia) {
            ShangXia = shangXia;
        }

        public int getStationid() {
            return stationid;
        }

        public void setStationid(int stationid) {
            this.stationid = stationid;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }
    }
}
