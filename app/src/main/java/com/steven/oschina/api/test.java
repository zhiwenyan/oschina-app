package com.steven.oschina.api;

import java.util.List;

/**
 * Description:
 * Data：11/29/2018-12:04 PM
 *
 * @author yanzhiwen
 */
public class test {

    /**
     * message : 数据请求成功
     * code : 200
     * result : {"borrowInfo":[{"id":"32","money":"1000","duration":"14","repayment_type":"1","rate":"9.00","interest":"3.50","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"9.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285317","xuqi_day":"0","type":"1","total":"1013.80"},{"id":"33","money":"1000","duration":"14","repayment_type":"1","rate":"9.00","interest":"3.50","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"9.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285370","xuqi_day":"0","type":"1","total":"1013.80"},{"id":"30","money":"1200","duration":"14","repayment_type":"1","rate":"9.00","interest":"1.75","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"11.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285043","xuqi_day":"0","type":"1","total":"1214.05"},{"id":"31","money":"1200","duration":"14","repayment_type":"1","rate":"9.00","interest":"1.75","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"11.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285232","xuqi_day":"0","type":"1","total":"1214.05"}],"money":[{"money":"1000"},{"money":"1200"}],"duration":[{"duration":"14"}],"orHaveCreateFee":1}
     */

    private String message;
    private String code;
    private ResultBean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * borrowInfo : [{"id":"32","money":"1000","duration":"14","repayment_type":"1","rate":"9.00","interest":"3.50","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"9.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285317","xuqi_day":"0","type":"1","total":"1013.80"},{"id":"33","money":"1000","duration":"14","repayment_type":"1","rate":"9.00","interest":"3.50","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"9.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285370","xuqi_day":"0","type":"1","total":"1013.80"},{"id":"30","money":"1200","duration":"14","repayment_type":"1","rate":"9.00","interest":"1.75","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"11.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285043","xuqi_day":"0","type":"1","total":"1214.05"},{"id":"31","money":"1200","duration":"14","repayment_type":"1","rate":"9.00","interest":"1.75","audit":"0.00","audit_rate":"0.00","created":"0.00","created_rate":"0.00","enbled":"0.00","enabled_rate":"11.30","pay_fee":"1.00","pay_rate":"0.00","total_rate":"0.00","renewal_fee":"20.00","due_rate":"0.1000","late_rate":"1.0000","is_on":"1","renewal_day":"14","add_time":"1543285232","xuqi_day":"0","type":"1","total":"1214.05"}]
         * money : [{"money":"1000"},{"money":"1200"}]
         * duration : [{"duration":"14"}]
         * orHaveCreateFee : 1
         */

        private int orHaveCreateFee;
        private List<BorrowInfoBean> borrowInfo;
        private List<MoneyBean> money;
        private List<DurationBean> duration;

        public int getOrHaveCreateFee() {
            return orHaveCreateFee;
        }

        public void setOrHaveCreateFee(int orHaveCreateFee) {
            this.orHaveCreateFee = orHaveCreateFee;
        }

        public List<BorrowInfoBean> getBorrowInfo() {
            return borrowInfo;
        }

        public void setBorrowInfo(List<BorrowInfoBean> borrowInfo) {
            this.borrowInfo = borrowInfo;
        }

        public List<MoneyBean> getMoney() {
            return money;
        }

        public void setMoney(List<MoneyBean> money) {
            this.money = money;
        }

        public List<DurationBean> getDuration() {
            return duration;
        }

        public void setDuration(List<DurationBean> duration) {
            this.duration = duration;
        }

        public static class BorrowInfoBean {
            /**
             * id : 32
             * money : 1000
             * duration : 14
             * repayment_type : 1
             * rate : 9.00
             * interest : 3.50
             * audit : 0.00
             * audit_rate : 0.00
             * created : 0.00
             * created_rate : 0.00
             * enbled : 0.00
             * enabled_rate : 9.30
             * pay_fee : 1.00
             * pay_rate : 0.00
             * total_rate : 0.00
             * renewal_fee : 20.00
             * due_rate : 0.1000
             * late_rate : 1.0000
             * is_on : 1
             * renewal_day : 14
             * add_time : 1543285317
             * xuqi_day : 0
             * type : 1
             * total : 1013.80
             */

            private String id;
            private String money;
            private String duration;
            private String repayment_type;
            private String rate;
            private String interest;
            private String audit;
            private String audit_rate;
            private String created;
            private String created_rate;
            private String enbled;
            private String enabled_rate;
            private String pay_fee;
            private String pay_rate;
            private String total_rate;
            private String renewal_fee;
            private String due_rate;
            private String late_rate;
            private String is_on;
            private String renewal_day;
            private String add_time;
            private String xuqi_day;
            private String type;
            private String total;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getRepayment_type() {
                return repayment_type;
            }

            public void setRepayment_type(String repayment_type) {
                this.repayment_type = repayment_type;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public String getAudit() {
                return audit;
            }

            public void setAudit(String audit) {
                this.audit = audit;
            }

            public String getAudit_rate() {
                return audit_rate;
            }

            public void setAudit_rate(String audit_rate) {
                this.audit_rate = audit_rate;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getCreated_rate() {
                return created_rate;
            }

            public void setCreated_rate(String created_rate) {
                this.created_rate = created_rate;
            }

            public String getEnbled() {
                return enbled;
            }

            public void setEnbled(String enbled) {
                this.enbled = enbled;
            }

            public String getEnabled_rate() {
                return enabled_rate;
            }

            public void setEnabled_rate(String enabled_rate) {
                this.enabled_rate = enabled_rate;
            }

            public String getPay_fee() {
                return pay_fee;
            }

            public void setPay_fee(String pay_fee) {
                this.pay_fee = pay_fee;
            }

            public String getPay_rate() {
                return pay_rate;
            }

            public void setPay_rate(String pay_rate) {
                this.pay_rate = pay_rate;
            }

            public String getTotal_rate() {
                return total_rate;
            }

            public void setTotal_rate(String total_rate) {
                this.total_rate = total_rate;
            }

            public String getRenewal_fee() {
                return renewal_fee;
            }

            public void setRenewal_fee(String renewal_fee) {
                this.renewal_fee = renewal_fee;
            }

            public String getDue_rate() {
                return due_rate;
            }

            public void setDue_rate(String due_rate) {
                this.due_rate = due_rate;
            }

            public String getLate_rate() {
                return late_rate;
            }

            public void setLate_rate(String late_rate) {
                this.late_rate = late_rate;
            }

            public String getIs_on() {
                return is_on;
            }

            public void setIs_on(String is_on) {
                this.is_on = is_on;
            }

            public String getRenewal_day() {
                return renewal_day;
            }

            public void setRenewal_day(String renewal_day) {
                this.renewal_day = renewal_day;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getXuqi_day() {
                return xuqi_day;
            }

            public void setXuqi_day(String xuqi_day) {
                this.xuqi_day = xuqi_day;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }

        public static class MoneyBean {
            /**
             * money : 1000
             */

            private String money;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }

        public static class DurationBean {
            /**
             * duration : 14
             */

            private String duration;

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }
        }
    }
}
