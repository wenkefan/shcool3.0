package com.fwk.shcool30.weight;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.fwk.shcool30.constanat.Keyword;
import com.fwk.shcool30.listener.FacheListener;
import com.fwk.shcool30.modue.BanciBean;
import com.fwk.shcool30.modue.TeacherZTBean;
import com.fwk.shcool30.modue.ZuofeiBean;
import com.fwk.shcool30.network.HTTPURL;
import com.fwk.shcool30.network.api.ZuofeiNetWork;
import com.fwk.shcool30.ui.MainActivity;
import com.fwk.shcool30.ui.StaionActivity;
import com.fwk.shcool30.util.LogUtils;
import com.fwk.shcool30.util.SharedPreferencesUtils;
import com.fwk.shcool30.util.ToastUtil;

import java.util.List;


/**
 * Created by fanwenke on 16/12/14.
 */

public class MainDialog {
    static FacheListener listener;

    public static void setBackListener(FacheListener listeners) {
        listener = listeners;
    }

//    /**
//     * 是本人的班次  点击时的弹窗
//     *
//     * @param context
//     * @param name
//     * @param fangxiang
//     * @param position
//     */
//    public static void ShowJRBanci(final Activity context, String name, final int fangxiang, final int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("提示").setMessage("\"" + name + "\"" + "是否发车");
//        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent;
//                if (fangxiang == 1) {
//                    //接幼儿
////                    intent = new Intent(context, JieStationMapActivity.class);
//
//                } else {
//                    //送幼儿
////                    intent = new Intent(context, SongStationMapActivity.class);
//
//                }
//                listener.BackListener(intent);
//                dialogInterface.dismiss();
//            }
//        });
//        builder.show();
//    }
//
//    /**
//     * 点击别人的班次时的弹窗
//     */
//    public static void ShowDLBanci(final Activity context, String name, String teachername, final int fangxiang, final int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("警告").setMessage("\"" + name + "的随车老师是：" + teachername + "\n" + "是否代理发车");
//        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent;
//                if (fangxiang == 1) {
//                    //接幼儿
////                    intent = new Intent(context, JieStationMapActivity.class);
//
//                } else {
//                    //送幼儿
////                    intent = new Intent(context, SongStationMapActivity.class);
//
//                }
//
//                listener.BackListener(intent);
//                dialogInterface.dismiss();
//            }
//        });
//        builder.show();
//    }
//
    /**
     * 推出重新进入时
     */
    public static void Beagin(final MainActivity context, List<BanciBean.RerurnValueBean> bean, final TeacherZTBean.RerurnValueBean teacher) {
        String name = null;
        for (BanciBean.RerurnValueBean banci : bean){
            LogUtils.d("banci.getBusScheduleId()--" + banci.getBusScheduleId() + "teacher.getBusScheduleId()--" + teacher.getBusScheduleId());
            if (banci.getBusScheduleId() == teacher.getBusScheduleId()){
                name = banci.getBusScheduleName();
                break;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提示：").setMessage(name + "正在运行中...");
        builder.setNegativeButton("返回列表", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                context.requestBanci();
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, StaionActivity.class);
                intent.putExtra(Keyword.IntentBanCi1,teacher);
//                intent.putExtra(Keyword.POTIONIT, -1);
                context.startActivity(intent);
                context.finish();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
//
//    /**
//     * 未全部上车或者下车
//     */
//    public static boolean jixu = false;
//
//    public static void Shangxiac(final Activity context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setCancelable(false);
//        builder.setTitle("警告：").setMessage("还有学生没有上下车，是否发车？");
//        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                dialogInterface.dismiss();
//            }
//        });
//        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                jixu = true;
//                dialogInterface.dismiss();
//            }
//        });
//        builder.show();
//    }

    /**
     * 点击其他班次，去引导作废
     *
     * @param context
     * @param bean
     */
    public static void YYX(final Activity context, List<BanciBean.RerurnValueBean> bean, final TeacherZTBean.RerurnValueBean teacher) {
        String name = null;
        for (BanciBean.RerurnValueBean banci : bean){
            LogUtils.d("banci.getBusScheduleId()--" + banci.getBusScheduleId() + "teacher.getBusScheduleId()--" + teacher.getBusScheduleId());
            if (banci.getBusScheduleId() == teacher.getBusScheduleId()){
                name = banci.getBusScheduleName();
                break;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提示：").setMessage("\"" + name + "\"" + "正在运行中。" + "继续进行请注销\"" + name + "\"。");
        builder.setNegativeButton("前往注销", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, StaionActivity.class);
                intent.putExtra(Keyword.IntentBanCi1,teacher);
//                intent.putExtra(Keyword.POTIONIT, -1);
                context.startActivity(intent);
                context.finish();
                ToastUtil.show("前去注销");
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void ZF(final Activity context, final ZuofeiNetWork work) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("警告：").setMessage("注销本次记录？");
        builder.setNegativeButton("注销", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferencesUtils sp = new SharedPreferencesUtils();

                work.setUrl(Keyword.ZUOFEI, HTTPURL.API_ZUO_FEI + sp.getInt(Keyword.BusOrderId), ZuofeiBean.class);
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


//    public static void DaoZhan(final Activity context, String name, final SharedPreferencesUtils sp) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setCancelable(false);
//        builder.setTitle("提示：").setMessage("\"" + name + "\"" +"已结束！");
////        builder.setNegativeButton("返回列表", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                SharedPreferencesUtils sp = new SharedPreferencesUtils();
////
//////                work.setUrl(Keyword.ZUOFEI,HTTPURL.API_ZUO_FEI + sp.getInt(Keyword.SP_PAICHEDANHAO), ZuofeiBean.class);
////                dialogInterface.dismiss();
////            }
////        });
//        builder.setPositiveButton("返回列表", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                context.startActivity(new Intent(context,MainActivity.class));
//                context.finish();
//                sp.removData();
//                dialogInterface.dismiss();
//            }
//        });
//        builder.show();
//    }

    public static void Dangzhanshangxiache(final Activity context, String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提示：").setMessage("\"" + name + "\"" + "在本站上车，确定在本站下车？");
        builder.setNegativeButton("下车", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                listener.BackListener(null);
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("刷卡失误", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
