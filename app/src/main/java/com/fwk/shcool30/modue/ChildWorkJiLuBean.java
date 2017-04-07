package com.fwk.shcool30.modue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanwenke on 2017/4/7.
 */

public class ChildWorkJiLuBean implements Serializable {

    /**
     * Success : 10000
     * Message : 获取成功
     * RerurnValue : [{"ClassInfoID":974,"ChildName":"苗虎豹","CardNo":null,"BusOrderDetailsId":17214,"BusOrderId":2151,"ChildId":2497,"ConnectStation":1,"GetInTime":"2017-04-07T12:02:27","SendStation":2,"GetOutTime":"2017-04-07T12:04:29","Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T12:04:31.517","Remark":""},{"ClassInfoID":971,"ChildName":"向永昕","CardNo":null,"BusOrderDetailsId":17215,"BusOrderId":2151,"ChildId":400,"ConnectStation":1,"GetInTime":"2017-04-07T12:02:35","SendStation":0,"GetOutTime":null,"Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T12:02:37.017","Remark":""},{"ClassInfoID":2293,"ChildName":"唐 浩","CardNo":"559AA7E0","BusOrderDetailsId":17216,"BusOrderId":2151,"ChildId":401,"ConnectStation":2,"GetInTime":"2017-04-07T12:09:50","SendStation":2,"GetOutTime":"2017-04-07T13:43:32","Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T13:43:33.953","Remark":""},{"ClassInfoID":2293,"ChildName":"赵 钰","CardNo":"658DAEE0","BusOrderDetailsId":17217,"BusOrderId":2151,"ChildId":393,"ConnectStation":2,"GetInTime":"2017-04-07T13:43:22","SendStation":2,"GetOutTime":"2017-04-07T13:43:37","Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T13:43:39.127","Remark":""},{"ClassInfoID":53,"ChildName":"余峻熙","CardNo":null,"BusOrderDetailsId":17218,"BusOrderId":2151,"ChildId":2602,"ConnectStation":2,"GetInTime":"2017-04-07T13:43:28","SendStation":2,"GetOutTime":"2017-04-07T13:43:41","Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T13:43:43.03","Remark":""},{"ClassInfoID":972,"ChildName":"杨方进","CardNo":null,"BusOrderDetailsId":17219,"BusOrderId":2151,"ChildId":2757,"ConnectStation":2,"GetInTime":"2017-04-07T13:43:58","SendStation":3,"GetOutTime":"2017-04-07T13:44:19","Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T13:44:20.937","Remark":""},{"ClassInfoID":971,"ChildName":"潘颖坤","CardNo":null,"BusOrderDetailsId":17220,"BusOrderId":2151,"ChildId":372,"ConnectStation":2,"GetInTime":"2017-04-07T13:44:03","SendStation":0,"GetOutTime":null,"Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T13:44:05.203","Remark":""},{"ClassInfoID":2293,"ChildName":"保芳坤","CardNo":"F57714F4","BusOrderDetailsId":17221,"BusOrderId":2151,"ChildId":436,"ConnectStation":2,"GetInTime":"2017-04-07T13:44:08","SendStation":3,"GetOutTime":"2017-04-07T13:46:25","Status":1,"ModifierId":0,"ModifyDate":"2017-04-07T13:46:26.827","Remark":""}]
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
         * ClassInfoID : 974
         * ChildName : 苗虎豹
         * CardNo : null
         * BusOrderDetailsId : 17214
         * BusOrderId : 2151
         * ChildId : 2497
         * ConnectStation : 1
         * GetInTime : 2017-04-07T12:02:27
         * SendStation : 2
         * GetOutTime : 2017-04-07T12:04:29
         * Status : 1
         * ModifierId : 0
         * ModifyDate : 2017-04-07T12:04:31.517
         * Remark :
         */

        private int ClassInfoID;
        private String ChildName;
        private String CardNo;
        private int BusOrderDetailsId;
        private int BusOrderId;
        private int ChildId;
        private int ConnectStation;
        private String GetInTime;
        private int SendStation;
        private String GetOutTime;
        private int Status;
        private int ModifierId;
        private String ModifyDate;
        private String Remark;

        public int getClassInfoID() {
            return ClassInfoID;
        }

        public void setClassInfoID(int ClassInfoID) {
            this.ClassInfoID = ClassInfoID;
        }

        public String getChildName() {
            return ChildName;
        }

        public void setChildName(String ChildName) {
            this.ChildName = ChildName;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public int getBusOrderDetailsId() {
            return BusOrderDetailsId;
        }

        public void setBusOrderDetailsId(int BusOrderDetailsId) {
            this.BusOrderDetailsId = BusOrderDetailsId;
        }

        public int getBusOrderId() {
            return BusOrderId;
        }

        public void setBusOrderId(int BusOrderId) {
            this.BusOrderId = BusOrderId;
        }

        public int getChildId() {
            return ChildId;
        }

        public void setChildId(int ChildId) {
            this.ChildId = ChildId;
        }

        public int getConnectStation() {
            return ConnectStation;
        }

        public void setConnectStation(int ConnectStation) {
            this.ConnectStation = ConnectStation;
        }

        public String getGetInTime() {
            return GetInTime;
        }

        public void setGetInTime(String GetInTime) {
            this.GetInTime = GetInTime;
        }

        public int getSendStation() {
            return SendStation;
        }

        public void setSendStation(int SendStation) {
            this.SendStation = SendStation;
        }

        public String getGetOutTime() {
            return GetOutTime;
        }

        public void setGetOutTime(String GetOutTime) {
            this.GetOutTime = GetOutTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
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

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
