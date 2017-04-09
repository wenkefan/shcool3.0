package com.fwk.shcool30.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.db.date.ClassInfoData;
import com.fwk.shcool30.db.date.UpAndDownRecordData;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.util.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by fanwenke on 2017/4/7.
 */

public class ChakanChild extends BaseRecyclerAdapter {



//    private Context mContext;
    private List<UpAndDownRecordBean> list;
    private SharedPreferencesUtils sp;
    private ClassInfoData classInfoData;

    public ChakanChild(Context context, List<UpAndDownRecordBean> listdata){
        sp = new SharedPreferencesUtils();
        list = listdata;
        classInfoData = new ClassInfoData(context);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChaKanHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.childrecyadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ChaKanHolder){
            ChaKanHolder holde = (ChaKanHolder) holder;
            if (list.size() == 0){
                holde.tv.setVisibility(View.GONE);
                holde.classname.setVisibility(View.GONE);
                holde.no.setVisibility(View.VISIBLE);
            } else {
                holde.no.setVisibility(View.GONE);
                holde.tv.setText(list.get(position).getChildName());
                holde.classname.setText(classInfoData.query(SpLogin.getKgId(), list.get(position).getClassId()));
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 1 : list.size();
    }

    public class ChaKanHolder extends ClickableViewHolder{
        private TextView tv,classname,no;
        public ChaKanHolder(View itemView) {
            super(itemView);
            tv = $(R.id.tv_child_name);
            classname = $(R.id.tv_child_class_name);
            no = $(R.id.tv_child_no_name);
        }
    }
}
