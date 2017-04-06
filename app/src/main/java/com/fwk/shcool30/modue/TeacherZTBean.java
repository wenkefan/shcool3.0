package com.fwk.shcool30.modue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanwenke on 2017/3/29.
 */

public class TeacherZTBean implements Serializable {


    /**
     * Success : 10000
     * Message : 获取成功
     * RerurnValue : [{"BusOrderId":2220,"TeacherId":4042,"LineId":104,"BusId":13,"BusScheduleId":84,"OrganizationId":33,"DriverId":3884,"AttendanceDirections":1,"Status":1,"RunningState":1,"StartTime":"2017-03-29T10:35:52.137","EndTime":null,"AuditStatus":null,"AuditId":null,"AuditDate":"2017-03-29T10:35:52.137","ModifierId":33,"ModifyDate":"2017-03-29T10:35:53","Remark":null}]
     */

    private int Success;
    private String Message;
    private List<RerurnValueBean> RerurnValue;

    public int getSuccess() {
        return Success;
    }

    public void setSuccess(int Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<RerurnValueBean> getRerurnValue() {
        return RerurnValue;
    }

    public void setRerurnValue(List<RerurnValueBean> RerurnValue) {
        this.RerurnValue = RerurnValue;
    }

    public static class RerurnValueBean implements Serializable {
        /**
         * BusOrderId : 2220
         * TeacherId : 4042
         * LineId : 104
         * BusId : 13
         * BusScheduleId : 84
         * OrganizationId : 33
         * DriverId : 3884
         * AttendanceDirections : 1
         * Status : 1
         * RunningState : 1
         * StartTime : 2017-03-29T10:35:52.137
         * EndTime : null
         * AuditStatus : null
         * AuditId : null
         * AuditDate : 2017-03-29T10:35:52.137
         * ModifierId : 33
         * ModifyDate : 2017-03-29T10:35:53
         * Remark : null
         */

        private int BusOrderId;
        private int TeacherId;
        private int LineId;
        private int BusId;
        private int BusScheduleId;
        private int OrganizationId;
        private int DriverId;
        private int AttendanceDirections;
        private int Status;
        private int RunningState;
        private String StartTime;
        private Object EndTime;
        private Object AuditStatus;
        private Object AuditId;
        private String AuditDate;
        private int ModifierId;
        private String ModifyDate;
        private Object Remark;

        public int getBusOrderId() {
            return BusOrderId;
        }

        public void setBusOrderId(int BusOrderId) {
            this.BusOrderId = BusOrderId;
        }

        public int getTeacherId() {
            return TeacherId;
        }

        public void setTeacherId(int TeacherId) {
            this.TeacherId = TeacherId;
        }

        public int getLineId() {
            return LineId;
        }

        public void setLineId(int LineId) {
            this.LineId = LineId;
        }

        public int getBusId() {
            return BusId;
        }

        public void setBusId(int BusId) {
            this.BusId = BusId;
        }

        public int getBusScheduleId() {
            return BusScheduleId;
        }

        public void setBusScheduleId(int BusScheduleId) {
            this.BusScheduleId = BusScheduleId;
        }

        public int getOrganizationId() {
            return OrganizationId;
        }

        public void setOrganizationId(int OrganizationId) {
            this.OrganizationId = OrganizationId;
        }

        public int getDriverId() {
            return DriverId;
        }

        public void setDriverId(int DriverId) {
            this.DriverId = DriverId;
        }

        public int getAttendanceDirections() {
            return AttendanceDirections;
        }

        public void setAttendanceDirections(int AttendanceDirections) {
            this.AttendanceDirections = AttendanceDirections;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getRunningState() {
            return RunningState;
        }

        public void setRunningState(int RunningState) {
            this.RunningState = RunningState;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public Object getEndTime() {
            return EndTime;
        }

        public void setEndTime(Object EndTime) {
            this.EndTime = EndTime;
        }

        public Object getAuditStatus() {
            return AuditStatus;
        }

        public void setAuditStatus(Object AuditStatus) {
            this.AuditStatus = AuditStatus;
        }

        public Object getAuditId() {
            return AuditId;
        }

        public void setAuditId(Object AuditId) {
            this.AuditId = AuditId;
        }

        public String getAuditDate() {
            return AuditDate;
        }

        public void setAuditDate(String AuditDate) {
            this.AuditDate = AuditDate;
        }

        public int getModifierId() {
            return ModifierId;
        }

        public void setModifierId(int ModifierId) {
            this.ModifierId = ModifierId;
        }

        public String getModifyDate() {
            return ModifyDate;
        }

        public void setModifyDate(String ModifyDate) {
            this.ModifyDate = ModifyDate;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }
    }
}
