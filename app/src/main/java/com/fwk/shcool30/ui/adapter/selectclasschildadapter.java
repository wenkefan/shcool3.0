package com.fwk.shcool30.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fwk.shcool30.R;
import com.fwk.shcool30.modue.AttendanceUserBean;

import java.util.List;

/**
 * Created by wenke on 2017/4/7.
 */

public class Selectclasschildadapter extends BaseRecyclerAdapter {
    private Context mContext;
    private List<AttendanceUserBean.RerurnValueBean> list;

    private SelectListener listener;

    public void setSelectListener(SelectListener listener) {
        this.listener = listener;
    }

    public Selectclasschildadapter(List<AttendanceUserBean.RerurnValueBean> list) {
        this.list = list;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(mContext, R.layout.selectclasschildadapter, null);
        return new SelectClassChildHolder(view);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, final int position) {
        if (holder instanceof SelectClassChildHolder) {
            SelectClassChildHolder viewholder = (SelectClassChildHolder) holder;
            viewholder.shangche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClickListener(position);
                }
            });
            viewholder.name.setText(list.get(position).getUserName());
        }
        super.onBindViewHolder(holder, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SelectClassChildHolder extends ClickableViewHolder {

        private TextView name;
        private Button shangche;

        public SelectClassChildHolder(View itemView) {
            super(itemView);
            name = $(R.id.tv_select_child_name);
            shangche = $(R.id.btn_shangche);
        }

    }

    public interface SelectListener {
        void OnClickListener(int position);
    }
}
