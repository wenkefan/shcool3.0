package com.fwk.shcool30.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.ui.adapter.ChankanChild;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fanwenke on 2017/4/7.
 */

public class CarShengyuChildActivity extends NFCBaseActivityNo {

    @BindView(R.id.title_tv)
    TextView title;

    @BindView(R.id.rv_childlist)
    RecyclerView recyclerView;

    @BindView(R.id.title_back_iv)
    ImageView back;

    private ChankanChild adapter;

    @Override
    public int getLayoutId() {
        return R.layout.chankanchild;
    }

    @Override
    protected void init() {
        title.setText("车上幼儿列表");
        back.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChankanChild(this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.title_back_iv)
    public void OnClick(View view){
        finish();
    }
}
