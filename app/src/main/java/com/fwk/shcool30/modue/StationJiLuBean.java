package com.fwk.shcool30.modue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanwenke on 2017/4/6.
 * 从接口中获取发车记录
 */

public class StationJiLuBean implements Serializable {

    /**
     * Success : 10000
     * Message : 获取成功
     * RerurnValue : [{"BusStorpRecordId":1740,"BusOrderId":2148,"StationId":1,"SendTime":null,"ToStationTime":"2017-04-06T17:29:19","Status":1,"CreateDate":"2017-04-06T17:29:21.657"},{"BusStorpRecordId":1741,"BusOrderId":2148,"StationId":1,"SendTime":null,"ToStationTime":"2017-04-06T00:00:00","Status":1,"CreateDate":"2017-04-06T17:29:43.657"},{"BusStorpRecordId":1742,"BusOrderId":2148,"StationId":1,"SendTime":"2017-04-06T17:52:11","ToStationTime":"2017-04-06T17:31:02","Status":1,"CreateDate":"2017-04-06T17:31:04.22"},{"BusStorpRecordId":1743,"BusOrderId":2148,"StationId":2,"SendTime":"2017-04-06T18:22:58","ToStationTime":"2017-04-06T17:52:18","Status":1,"CreateDate":"2017-04-06T17:52:20.017"},{"BusStorpRecordId":1744,"BusOrderId":2148,"StationId":3,"SendTime":null,"ToStationTime":"2017-04-06T18:06:15","Status":1,"CreateDate":"2017-04-06T18:06:17.673"},{"BusStorpRecordId":1745,"BusOrderId":2148,"StationId":1,"SendTime":"2017-04-06T18:20:02","ToStationTime":"2017-04-06T18:18:05","Status":1,"CreateDate":"2017-04-06T18:18:07.53"},{"BusStorpRecordId":1746,"BusOrderId":2148,"StationId":1,"SendTime":"2017-04-06T19:16:29","ToStationTime":"2017-04-06T19:16:27","Status":1,"CreateDate":"2017-04-06T19:16:29.063"}]
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
         * BusStorpRecordId : 1740
         * BusOrderId : 2148
         * StationId : 1
         * SendTime : null
         * ToStationTime : 2017-04-06T17:29:19
         * Status : 1
         * CreateDate : 2017-04-06T17:29:21.657
         */

        private int BusStorpRecordId;
        private int BusOrderId;
        private int StationId;
        private Object SendTime;
        private String ToStationTime;
        private int Status;
        private String CreateDate;

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

        public Object getSendTime() {
            return SendTime;
        }

        public void setSendTime(Object SendTime) {
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
