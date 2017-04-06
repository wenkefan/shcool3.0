package com.fwk.shcool30.db.date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fwk.shcool30.db.DbOpenHelper;
import com.fwk.shcool30.modue.BanciBean;
import com.fwk.shcool30.modue.TeacherZTBean;
import com.fwk.shcool30.sp.SpLogin;
import com.fwk.shcool30.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 2017/3/29.
 */

public class TeacherZT {

    private Context mContext;
    private DbOpenHelper dbOpenHelper;
    private List<TeacherZTBean.RerurnValueBean> data;

    public TeacherZT(Context context) {
        this.mContext = context;
        this.dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    public TeacherZT(Context context, List<TeacherZTBean.RerurnValueBean> data) {
        this.mContext = context;
        dbOpenHelper = DbOpenHelper.getInstance(context);
        this.data = data;
    }

    //保存发车记录
    public void addRecord(TeacherZTBean.RerurnValueBean bean) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            if (bean != null) {
                    int busOrderId = bean.getBusOrderId();
                    int teacherId = bean.getTeacherId();
                    int lineId = bean.getLineId();
                    int busId = bean.getBusId();
                    int busScheduleId = bean.getBusScheduleId();
                    int organizationId = bean.getOrganizationId();
                    int driverId = bean.getDriverId();
                    int attendanceDirections = bean.getAttendanceDirections();
                    int status = bean.getStatus();
                    int runningState = bean.getRunningState();
                    int modifierId = bean.getModifierId();
                    LogUtils.d("bean.getStartTime().split(T)[0]" + bean.getStartTime().split("T")[0]);
                    String startTime = bean.getStartTime().split("T")[0];
                    String endTime = (String) bean.getEndTime();
                    int auditStatus;
                    if (bean.getAuditStatus() == null) {
                        auditStatus = 0;
                    } else {
                        auditStatus = (int) bean.getAuditStatus();
                    }
                    int auditId;
                    if (bean.getAuditId() == null) {
                        auditId = 0;
                    } else {
                        auditId = (int) bean.getAuditId();
                    }
                    String auditDate = bean.getAuditDate();
                    String modifyDate = bean.getModifyDate();
                    int state = 1;
                    String remark = (String) bean.getRemark();
                    db.execSQL(
                            "insert into TeacherZT(BusOrderId,TeacherId,LineId,BusId,BusScheduleId,OrganizationId,DriverId,AttendanceDirections,Status,RunningState,ModifierId,StartTime,EndTime,AuditStatus,AuditId,AuditDate,ModifyDate,State,Remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[]{busOrderId, teacherId, lineId, busId, busScheduleId, organizationId, driverId, attendanceDirections, status, runningState, modifierId, startTime, endTime, auditStatus, auditId, auditDate, modifyDate, state, remark});
                }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("错误sql --");
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    //查询是否有哪天哪个班次的发车记录
    public TeacherZTBean.RerurnValueBean queryRecord(int teacherId, int state, String startTime) {
        TeacherZTBean.RerurnValueBean queryData = new TeacherZTBean.RerurnValueBean();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from TeacherZT where TeacherId = ? and State = ? and StartTime = ?",
                new String[]{String.valueOf(teacherId), String.valueOf(state), startTime});
        LogUtils.d("cursor.getCount()---" + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                String time = cursor.getString(cursor.getColumnIndex("StartTime"));
                LogUtils.d("times---" + time);
                queryData.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));
                queryData.setTeacherId(cursor.getInt(cursor.getColumnIndex("TeacherId")));
                queryData.setBusScheduleId(cursor.getInt(cursor.getColumnIndex("BusScheduleId")));
                queryData.setAttendanceDirections(cursor.getInt(cursor.getColumnIndex("AttendanceDirections")));
                queryData.setLineId(cursor.getInt(cursor.getColumnIndex("LineId")));
            }
        } else {
            queryData = null;
        }
        cursor.close();
        db.close();
        return queryData;
    }

    /**
     * 是否为自己的发车记录
     * @param bean
     * @param gettime
     * @return
     */
    public boolean queryFlag(BanciBean.RerurnValueBean bean, String gettime) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from TeacherZT where BusScheduleId = ? and TeacherId = ? and State = ? and StartTime = ?",
                new String[]{String.valueOf(bean.getBusScheduleId()), String.valueOf(SpLogin.getWorkerExtensionId()), String.valueOf(1), gettime});
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}
