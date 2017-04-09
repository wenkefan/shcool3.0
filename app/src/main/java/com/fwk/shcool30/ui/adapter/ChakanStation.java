package com.fwk.shcool30.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.StationCarJiLuData;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.ui.ChakanStationAndChild;
import com.fwk.shcool30.util.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by fanwenke on 2017/4/9.
 */

public class ChakanStation extends BaseRecyclerAdapter {

    private Context context;

    private List<ChakanStationAndChild.ChakanStation> list;
    private SharedPreferencesUtils sp;

    public ChakanStation(List<ChakanStationAndChild.ChakanStation> list) {
        this.list = list;
        sp = new SharedPreferencesUtils();
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ChakanStationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chakanstation, parent, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ChakanStationHolder) {
            ChakanStationHolder holde = (ChakanStationHolder) holder;
            holde.stationname.setText(list.get(position).getStationName());
            UpAndDownRecordData upAndDownRecordData = new UpAndDownRecordData(context);
            List<UpAndDownRecordBean> listdata = null;
            if (list.get(position).getShangXia() == 1) {
                listdata = upAndDownRecordData.queryStationShangList(sp.getInt(Keyword.BusOrderId), list.get(position).getStationid());
            } else if (list.get(position).getShangXia() == 2) {
                listdata = upAndDownRecordData.queryStationXiaList(sp.getInt(Keyword.BusOrderId), list.get(position).getStationid());
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            holde.child.setLayoutManager(layoutManager);
            ChakanChild adapter = new ChakanChild(context,listdata);
            holde.child.setAdapter(adapter);

        }
        super.onBindViewHolder(holder, position);
    }

    public class ChakanStationHolder extends ClickableViewHolder {

        private TextView stationname;
        private RecyclerView child;

        public ChakanStationHolder(View itemView) {
            super(itemView);
            stationname = $(R.id.tv_name);
            child = $(R.id.rv_recyle_child);
        }
    }
}
