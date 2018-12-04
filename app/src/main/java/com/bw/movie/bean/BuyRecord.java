package com.bw.movie.bean;

import java.util.List;

public class BuyRecord {

    /**
     * result : [{"amount":1,"beginTime":"22:10:00","cinemaName":"首都电影院","createTime":1543841119000,"endTime":"00:22:00","id":4414,"movieName":"狄仁杰之四大天王","orderId":"20181203204519801","price":0.15,"screeningHall":"4D厅","status":1,"userId":1418},{"amount":1,"beginTime":"17:00:00","cinemaName":"北京沃美影城（回龙观店）","createTime":1543836906000,"endTime":"04:56:00","id":4372,"movieName":"江湖儿女","orderId":"20181203193506082","price":0.28,"screeningHall":"4号厅","status":1,"userId":1418},{"amount":1,"beginTime":"14:30:00","cinemaName":"魔影国际影城","createTime":1543833327000,"endTime":"16:42:00","id":4347,"movieName":"狄仁杰之四大天王","orderId":"20181203183527179","price":0.15,"screeningHall":"2号厅","status":1,"userId":1418}]
     * message : 请求成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * amount : 1
         * beginTime : 22:10:00
         * cinemaName : 首都电影院
         * createTime : 1543841119000
         * endTime : 00:22:00
         * id : 4414
         * movieName : 狄仁杰之四大天王
         * orderId : 20181203204519801
         * price : 0.15
         * screeningHall : 4D厅
         * status : 1
         * userId : 1418
         */

        private int amount;
        private String beginTime;
        private String cinemaName;
        private long createTime;
        private String endTime;
        private int id;
        private String movieName;
        private String orderId;
        private double price;
        private String screeningHall;
        private int status;
        private int userId;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
