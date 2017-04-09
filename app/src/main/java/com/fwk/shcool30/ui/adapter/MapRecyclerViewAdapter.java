package com.fwk.shcool30.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fwk.shcool30.MyApp;
import com.fwk.shcool30.R;
import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.listener.DaoZhanListener;
import com.fwk.shcool30.modue.StaBean;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.modue.StationCarJiLuBean;
import com.fwk.shcool30.util.SharedPreferencesUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by fanwenke on 16/12/7.
 */

public class MapRecyclerViewAdapter extends BaseRecyclerAdapter implements View.OnClickListener {

    private Context mContext;
    private SharedPreferencesUtils sp;
    private List<StationBean.RerurnValueBean> list;
    private DisplayMetrics display;

    DaoZhanListener listener;

    private List<StationCarJiLuBean> stationCarJiLuBeen;

    public void setOnClickListener(DaoZhanListener listener) {
        this.listener = listener;
    }

    public MapRecyclerViewAdapter(DisplayMetrics display) {
        this.display = display;
        sp = new SharedPreferencesUtils();
        list = (List<StationBean.RerurnValueBean>) sp.queryForSharedToObject(Keyword.STAIDLIST);
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(mContext, R.layout.station_map_adapter, null);
        return new MapViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof MapViewHolder) {
            MapViewHolder viewHolder = (MapViewHolder) holder;
            setLayoutParams(viewHolder, 0.95f);

            viewHolder.name.setText(list.get(position).getStationName());
            viewHolder.daozhan.setVisibility(View.GONE);
            viewHolder.daozhan.setOnClickListener(this);
            viewHolder.start.setVisibility(View.VISIBLE);
            viewHolder.end.setVisibility(View.VISIBLE);
            viewHolder.sjsj.setVisibility(View.GONE);
            viewHolder.xiangqing.setVisibility(View.GONE);
            viewHolder.ring.setImageResource(R.drawable.ring);


            AnimationSet set = new AnimationSet(true);
            AlphaAnimation animation = new AlphaAnimation(1, 0.2f);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setDuration(1000);
            set.addAnimation(animation);

            //隐藏上下两个棍子
            if (position == 0) {
                viewHolder.start.setVisibility(View.GONE);
            }
            if (position == getItemCount() - 1) {
                viewHolder.end.setVisibility(View.GONE);
            }
            //第一次进入时，站点动画
            if (stationCarJiLuBeen == null && position == 0) {
                viewHolder.ring.setImageResource(R.drawable.ring2);
                viewHolder.start.setBackgroundColor(0xff669900);
                viewHolder.ring.setAnimation(set);
                viewHolder.daozhan.setVisibility(View.VISIBLE);
            }
            if (stationCarJiLuBeen != null) {
                StationCarJiLuBean bean = stationCarJiLuBeen.get(stationCarJiLuBeen.size() - 1);
                if (bean.getIsFache() == 0) {
                    //已到站，未发车
                    if (position < stationCarJiLuBeen.size() - 1) {
                        viewHolder.ring.setImageResource(R.drawable.ring2);
                        viewHolder.daozhan.setVisibility(View.GONE);
                        viewHolder.end.setBackgroundColor(0xff669900);
                        viewHolder.sjsj.setVisibility(View.VISIBLE);
                        viewHolder.sjsj.setText(stationCarJiLuBeen.get(position).getDatatime());
                        viewHolder.xiangqing.setVisibility(View.VISIBLE);
                        viewHolder.xiangqing.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                        viewHolder.xiangqing.getPaint().setAntiAlias(true);//抗锯齿
                    }
                    if (position == stationCarJiLuBeen.size() - 1) {
                        viewHolder.ring.setImageResource(R.drawable.ring2);
                        viewHolder.start.setBackgroundColor(0xff669900);
                        viewHolder.ring.setAnimation(set);
                        if (bean.getIsDaozhan() == 1) {
                            viewHolder.daozhan.setVisibility(View.VISIBLE);
                            viewHolder.daozhan.setText("已到站");
                        }
                    }
                } else {
                    //已到站，发车
                    if (position < stationCarJiLuBeen.size()) {
                        viewHolder.ring.setImageResource(R.drawable.ring2);
                        viewHolder.daozhan.setVisibility(View.GONE);
                        viewHolder.end.setBackgroundColor(0xff669900);
                        viewHolder.sjsj.setVisibility(View.VISIBLE);
                        viewHolder.sjsj.setText(stationCarJiLuBeen.get(position).getDatatime());
                        viewHolder.xiangqing.setVisibility(View.VISIBLE);
                        viewHolder.xiangqing.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                        viewHolder.xiangqing.getPaint().setAntiAlias(true);//抗锯齿
                    }
                    try {
                        if (position == stationCarJiLuBeen.size()) {
                            viewHolder.ring.setImageResource(R.drawable.ring2);
                            viewHolder.start.setBackgroundColor(0xff669900);
                            viewHolder.ring.setAnimation(set);
                            viewHolder.daozhan.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onClick(View view) {
        int position = 0;
        if (stationCarJiLuBeen != null) {
            if (stationCarJiLuBeen.get(stationCarJiLuBeen.size() - 1).getIsFache() == 0) {
                position = stationCarJiLuBeen.size() - 1;
            } else {
                position = stationCarJiLuBeen.size();
            }
        }
        listener.OnClickListener(position);
    }

    public class MapViewHolder extends ClickableViewHolder {

        private View start, end;
        private ImageView ring;
        private TextView name, ysc, yxc, sjsj, xiangqing;
        private CardView cardView;
        private RelativeLayout left;
        private TextView daozhan;

        public MapViewHolder(View itemView) {
            super(itemView);
            start = $(R.id.view_start);
            end = $(R.id.view_end);
            ring = $(R.id.ring);
            name = $(R.id.tx_name);
            cardView = $(R.id.cardview);
            left = $(R.id.rl_left);
            daozhan = $(R.id.btn_daozhan);

            sjsj = $(R.id.tv_sjsj);
            xiangqing = $(R.id.tv_xiangqing);
        }

    }

    private void setLayoutParams(MapViewHolder viewHolder, float widths) {
        //根据屏幕设置cardView的宽度
        int width = display.widthPixels;
        ViewGroup.LayoutParams layoutParams = viewHolder.cardView.getLayoutParams();
        layoutParams.width = (int) ((width - dp2px(50)) * widths);
        viewHolder.cardView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParam = viewHolder.name.getLayoutParams();
        layoutParam.width = layoutParams.width / 3;
        viewHolder.name.setLayoutParams(layoutParam);
    }

    public static int dp2px(float dpValue) {
        final float scale = MyApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void getList(List<StationCarJiLuBean> list) {
        this.stationCarJiLuBeen = list;
        notifyDataSetChanged();
    }
}
