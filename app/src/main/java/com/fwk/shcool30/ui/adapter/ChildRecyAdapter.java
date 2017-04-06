package com.fwk.shcool30.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.fwk.shcool30.R;
import com.fwk.shcool30.modue.ChildBean;
import com.fwk.shcool30.modue.UpAndDownRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 16/12/26.
 */

public class ChildRecyAdapter extends BaseRecyclerAdapter {
    private Context context;
    private List<UpAndDownRecordBean> list;
    private int type;


    public ChildRecyAdapter(List<UpAndDownRecordBean> list, int type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ChildViewHolde(LayoutInflater.from(parent.getContext()).inflate(R.layout.childrecyadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ChildViewHolde) {
            ChildViewHolde holde = (ChildViewHolde) holder;
            holde.tv.setText(list.get(position).getChildName());
//            switch (list.get(position).getIsShang()) {
//                case 0:
//                    holde.iv.setBackgroundResource(R.mipmap.bianji);
//                    break;
//                case 1:
//                    holde.iv.setBackgroundResource(R.mipmap.shangche);
//                    break;
//                case 2:
//                    holde.iv.setBackgroundResource(R.mipmap.bingjia);
//                    break;
//                case 3:
//                    holde.iv.setBackgroundResource(R.mipmap.shijia);
//                    break;
//                case 4:
//                    holde.iv.setBackgroundResource(R.mipmap.jiazhangjiesong);
//                    break;
//                case 5:
//                    holde.iv.setBackgroundResource(R.mipmap.xiache);
//                    break;
//            }
            if (type == 1){
                holde.iv.setBackgroundResource(R.mipmap.shangche);
            } else if (type == 2){
                holde.iv.setBackgroundResource(R.mipmap.xiache);
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChildViewHolde extends ClickableViewHolder {

        private TextView tv;
        private ImageView iv;

        public ChildViewHolde(View itemView) {
            super(itemView);
            tv = $(R.id.tv_child_name);
            iv = $(R.id.iv_type_select);
        }
    }

    public void getDate(List<UpAndDownRecordBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
