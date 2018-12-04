package com.bw.movie.bean;

import java.util.List;

public class UserSystemMessagesBean {

    /**
     * result : [{"content":"您已购买电影票,请尽快完成支付,以免影响到您的观影","id":6194,"pushTime":1543808583000,"status":0,"title":"系统通知","userId":1385},{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":6162,"pushTime":1543572939000,"status":0,"title":"系统通知","userId":1385},{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":6161,"pushTime":1543571232000,"status":0,"title":"系统通知","userId":1385},{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":6160,"pushTime":1543571167000,"status":0,"title":"系统通知","userId":1385},{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":6159,"pushTime":1543571044000,"status":0,"title":"系统通知","userId":1385}]
     * message : 查询成功
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
         * content : 您已购买电影票,请尽快完成支付,以免影响到您的观影
         * id : 6194
         * pushTime : 1543808583000
         * status : 0
         * title : 系统通知
         * userId : 1385
         */

        private String content;
        private int id;
        private long pushTime;
        private int status;
        private String title;
        private int userId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getPushTime() {
            return pushTime;
        }

        public void setPushTime(long pushTime) {
            this.pushTime = pushTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
