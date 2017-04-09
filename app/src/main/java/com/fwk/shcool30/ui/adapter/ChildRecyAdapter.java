package com.fwk.shcool30.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.fwk.shcool30.R;
import com.fwk.shcool30.db.date.ClassInfoData;
import com.fwk.shcool30.modue.ChildBean;
import com.fwk.shcool30.modue.UpAndDownRecordBean;
import com.fwk.shcool30.sp.SpLogin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 16/12/26.
 */

public class ChildRecyAdapter extends BaseRecyclerAdapter {
    private Context context;
    private List<UpAndDownRecordBean> list;
    private int type;
    private boolean IsSelect = false;//是否显示圆圈选择
    private boolean Isquanxuan = false;//是否全选
    private SelectChildListener listener;
    private ClassInfoData classInfoData;

    public void onSetSelectChildListener(SelectChildListener listener) {
        this.listener = listener;
    }

    public ChildRecyAdapter(List<UpAndDownRecordBean> list, int type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        classInfoData = new ClassInfoData(context);
        return new ChildViewHolde(LayoutInflater.from(parent.getContext()).inflate(R.layout.childrecyadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, final int position) {
        if (holder instanceof ChildViewHolde) {
            final ChildViewHolde holde = (ChildViewHolde) holder;
            final boolean[] selectadapter = {false};//对单个的选择
            holde.tv.setText(list.get(position).getChildName());
            holde.name.setText(classInfoData.query(list.get(position).getKgId(),list.get(position).getClassId()));
            if (type == 1) {
                holde.iv.setBackgroundResource(R.mipmap.shangche);
            } else if (type == 2) {
                holde.iv.setBackgroundResource(R.mipmap.xiache);
            }
            if (type == 3) {
                if (IsSelect) {
                    if (Isquanxuan){
                        holde.xuanze.setImageResource(R.mipmap.xuanzhong);
                        selectadapter[0] = true;
                            listener.selectchildListener(position,true);
                    } else {
                        holde.xuanze.setImageResource(R.mipmap.yuanquan);
                        selectadapter[0] = false;
                            listener.selectchildListener(position,false);
                    }
                    holde.xuanze.setVisibility(View.VISIBLE);
                    holde.xuanze.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (selectadapter[0]) {
                                holde.xuanze.setImageResource(R.mipmap.yuanquan);
                                selectadapter[0] = false;
                            listener.selectchildListener(position,false);
                            } else {
                                holde.xuanze.setImageResource(R.mipmap.xuanzhong);
                                selectadapter[0] = true;
                            listener.selectchildListener(position,true);
                            }
                        }
                    });
                } else {
                    holde.xuanze.setVisibility(View.GONE);
                }
            }

        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChildViewHolde extends ClickableViewHolder {

        private TextView tv,name;
        private ImageView iv, xuanze;

        public ChildViewHolde(View itemView) {
            super(itemView);
            tv = $(R.id.tv_child_name);
            iv = $(R.id.iv_type_select);
            xuanze = $(R.id.iv_xuanxiang);
            name = $(R.id.tv_child_class_name);
        }
    }

    public void getDate(List<UpAndDownRecordBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelect(boolean isSelect) {
        this.IsSelect = isSelect;
        notifyDataSetChanged();
    }
    public void IsQuanxuan(boolean isquanxuan){
        this.Isquanxuan = isquanxuan;
        notifyDataSetChanged();
    }
    public interface SelectChildListener {
        void selectchildListener(int position, boolean isSelect);
    }
}
