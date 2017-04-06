package com.fwk.shcool30.modue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanwenke on 2017/3/31.
 */

public class AttendanceUserBean implements Serializable {

    /**
     * Success : 10000
     * Message :
     * RerurnValue : [{"KgId":33,"WorkerExtensionId":4167,"UserId":0,"SACardNo":"999","CampusNo":"","UserType":2,"UserName":"aaa","Sex":1,"ClassInfoID":0,"HeadImage":"","BirthDay":"2016-12-01T00:00:00","State":1,"MonitorInfo":null,"AutoSendMsg":0,"Note":"教学部"}]
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

    public static class RerurnValueBean {
        /**
         * KgId : 33
         * WorkerExtensionId : 4167
         * UserId : 0
         * SACardNo : 999
         * CampusNo :
         * UserType : 2
         * UserName : aaa
         * Sex : 1
         * ClassInfoID : 0
         * HeadImage :
         * BirthDay : 2016-12-01T00:00:00
         * State : 1
         * MonitorInfo : null
         * AutoSendMsg : 0
         * Note : 教学部
         */

        private int KgId;
        private int WorkerExtensionId;
        private int UserId;
        private String SACardNo;
        private String CampusNo;
        private int UserType;
        private String UserName;
        private int Sex;
        private int ClassInfoID;
        private String HeadImage;
        private String BirthDay;
        private int State;
        private Object MonitorInfo;
        private int AutoSendMsg;
        private String Note;

        public int getKgId() {
            return KgId;
        }

        public void setKgId(int KgId) {
            this.KgId = KgId;
        }

        public int getWorkerExtensionId() {
            return WorkerExtensionId;
        }

        public void setWorkerExtensionId(int WorkerExtensionId) {
            this.WorkerExtensionId = WorkerExtensionId;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getSACardNo() {
            return SACardNo;
        }

        public void setSACardNo(String SACardNo) {
            this.SACardNo = SACardNo;
        }

        public String getCampusNo() {
            return CampusNo;
        }

        public void setCampusNo(String CampusNo) {
            this.CampusNo = CampusNo;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public int getClassInfoID() {
            return ClassInfoID;
        }

        public void setClassInfoID(int ClassInfoID) {
            this.ClassInfoID = ClassInfoID;
        }

        public String getHeadImage() {
            return HeadImage;
        }

        public void setHeadImage(String HeadImage) {
            this.HeadImage = HeadImage;
        }

        public String getBirthDay() {
            return BirthDay;
        }

        public void setBirthDay(String BirthDay) {
            this.BirthDay = BirthDay;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public Object getMonitorInfo() {
            return MonitorInfo;
        }

        public void setMonitorInfo(Object MonitorInfo) {
            this.MonitorInfo = MonitorInfo;
        }

        public int getAutoSendMsg() {
            return AutoSendMsg;
        }

        public void setAutoSendMsg(int AutoSendMsg) {
            this.AutoSendMsg = AutoSendMsg;
        }

        public String getNote() {
            return Note;
        }

        public void setNote(String Note) {
            this.Note = Note;
        }
    }
}
