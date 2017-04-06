package com.fwk.shcool30.db.date;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fwk.shcool30.db.DbOpenHelper;
import com.fwk.shcool30.modue.AttendanceUserBean;
import com.fwk.shcool30.util.LogUtils;

import java.util.List;

/**
 * Created by fanwenke on 2017/3/31.
 */

public class AttendanceUserData {

    private Context mContext;
    private DbOpenHelper dbOpenHelper;

    public AttendanceUserData(Context context){
        this.mContext = context;
        this.dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    public boolean addData(List<AttendanceUserBean.RerurnValueBean> data){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            if (data != null){
                for (AttendanceUserBean.RerurnValueBean Bean : data){
                    if (Bean.getUserType() == 1) {
                        int kgid = Bean.getKgId();
                        int workerid = Bean.getWorkerExtensionId();
                        int userid = Bean.getUserId();
                        String sacardno = Bean.getSACardNo();
                        String campusno = Bean.getCampusNo();
                        int usertype = Bean.getUserType();
                        String username = Bean.getUserName();
                        int sex = Bean.getSex();
                        int classinfoid = Bean.getClassInfoID();
                        String headimage = Bean.getHeadImage();
                        String BirthDay = Bean.getBirthDay();
                        int state = Bean.getState();
                        int autoSend = Bean.getAutoSendMsg();
                        String note = Bean.getNote();
                        db.execSQL(
                                "insert into AttendanceUser(KgId,WorkerExtensionId,UserType,UserId,ClassInfoID,Note,UserName,Sex,HeadImage,BirthDay,AutoSendMsg,State,SACardNo,CampusNo) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                new Object[]{kgid, workerid, usertype, userid, classinfoid,
                                        note, username, sex, headimage, BirthDay,
                                        autoSend, state, sacardno, campusno});
                    }
                }
            }
            db.setTransactionSuccessful();
            LogUtils.d("用户表存储成功");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    public boolean queryCound(){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from AttendanceUser",null);
        if (cursor.getCount() > 0){
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
    //查询幼儿
    public AttendanceUserBean.RerurnValueBean queryChild(String sacardno){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from AttendanceUser where SACardNo = ? and State = ?",
                new String[]{sacardno,String.valueOf(1)});
        if (cursor != null){
            if (cursor.moveToNext()){
                AttendanceUserBean.RerurnValueBean bean = new AttendanceUserBean.RerurnValueBean();
                bean.setKgId(cursor.getInt(cursor.getColumnIndex("KgId")));
                bean.setWorkerExtensionId(cursor.getInt(cursor.getColumnIndex("WorkerExtensionId")));
                bean.setUserId(cursor.getInt(cursor.getColumnIndex("UserId")));
                bean.setSACardNo(cursor.getString(cursor.getColumnIndex("SACardNo")));
                bean.setCampusNo(cursor.getString(cursor.getColumnIndex("CampusNo")));
                bean.setUserType(cursor.getInt(cursor.getColumnIndex("UserType")));
                bean.setUserName(cursor.getString(cursor.getColumnIndex("UserName")));
                bean.setSex(cursor.getInt(cursor.getColumnIndex("Sex")));
                bean.setClassInfoID(cursor.getInt(cursor.getColumnIndex("ClassInfoID")));
                bean.setHeadImage(cursor.getString(cursor.getColumnIndex("HeadImage")));
                bean.setBirthDay(cursor.getString(cursor.getColumnIndex("BirthDay")));
                bean.setState(cursor.getInt(cursor.getColumnIndex("State")));
                bean.setAutoSendMsg(cursor.getInt(cursor.getColumnIndex("AutoSendMsg")));
                bean.setNote(cursor.getString(cursor.getColumnIndex("Note")));
                return bean;
            }
        }
        return null;
    }

}
