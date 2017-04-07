package com.fwk.shcool30.modue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanwenke on 2017/4/7.
 */

public class StationWorkJiLuBean implements Serializable {

    /**
     * Success : 10000
     * Message : 获取成功
     * RerurnValue : [{"StationName":"地铁上地站","BusStorpRecordId":1769,"BusOrderId":2151,"StationId":1,"SendTime":"2017-04-07T12:02:40","ToStationTime":"2017-04-07T12:01:03","Status":1,"CreateDate":"2017-04-07T12:01:05.187"},{"StationName":"上地佳园","BusStorpRecordId":1770,"BusOrderId":2151,"StationId":2,"SendTime":"2017-04-07T13:44:12","ToStationTime":"2017-04-07T12:03:17","Status":1,"CreateDate":"2017-04-07T12:03:19.077"},{"StationName":"上地五街","BusStorpRecordId":1771,"BusOrderId":2151,"StationId":3,"SendTime":null,"ToStationTime":"2017-04-07T13:44:14","Status":1,"CreateDate":"2017-04-07T13:44:15.877"}]
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

    public static class RerurnValueBean implements Serializable{
        /**
         * StationName : 地铁上地站
         * BusStorpRecordId : 1769
         * BusOrderId : 2151
         * StationId : 1
         * SendTime : 2017-04-07T12:02:40
         * ToStationTime : 2017-04-07T12:01:03
         * Status : 1
         * CreateDate : 2017-04-07T12:01:05.187
         */

        private String StationName;
        private int BusStorpRecordId;
        private int BusOrderId;
        private int StationId;
        private String SendTime;
        private String ToStationTime;
        private int Status;
        private String CreateDate;

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public int getBusStorpRecordId() {
            return BusStorpRecordId;
        }

        public void setBusStorpRecordId(int BusStorpRecordId) {
            this.BusStorpRecordId = BusStorpRecordId;
        }

        public int getBusOrderId() {
            return BusOrderId;
        }

        public void setBusOrderId(int BusOrderId) {
            this.BusOrderId = BusOrderId;
        }

        public int getStationId() {
            return StationId;
        }

        public void setStationId(int StationId) {
            this.StationId = StationId;
        }

        public String getSendTime() {
            return SendTime;
        }

        public void setSendTime(String SendTime) {
            this.SendTime = SendTime;
        }

        public String getToStationTime() {
            return ToStationTime;
        }

        public void setToStationTime(String ToStationTime) {
            this.ToStationTime = ToStationTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }
    }
}
