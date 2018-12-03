package com.bw.movie.bean;

import java.util.List;

public class DetailsComment {

    /**
     * result : [{"replyContent":"好看啊","replyHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","replyTime":1542427089000,"replyUserId":1873,"replyUserName":"sunmeng"},{"replyContent":"呵呵哒","replyHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","replyTime":1542426409000,"replyUserId":1864,"replyUserName":"11"},{"replyContent":"好看","replyHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-20/20180920082830.jpg","replyTime":1540290836000,"replyUserId":556,"replyUserName":"张大炮"},{"replyContent":"好看","replyHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-14/20180914112536.u","replyTime":1537340593000,"replyUserId":363,"replyUserName":"Giao人一等"},{"replyContent":"ok","replyHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-11/20180911142701.png","replyTime":1536974633000,"replyUserId":304,"replyUserName":"烊"}]
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
         * replyContent : 好看啊
         * replyHeadPic : http://172.17.8.100/images/movie/head_pic/bwjy.jpg
         * replyTime : 1542427089000
         * replyUserId : 1873
         * replyUserName : sunmeng
         */

        private String replyContent;
        private String replyHeadPic;
        private long replyTime;
        private int replyUserId;
        private String replyUserName;

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getReplyHeadPic() {
            return replyHeadPic;
        }

        public void setReplyHeadPic(String replyHeadPic) {
            this.replyHeadPic = replyHeadPic;
        }

        public long getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(long replyTime) {
            this.replyTime = replyTime;
        }

        public int getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(int replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getReplyUserName() {
            return replyUserName;
        }

        public void setReplyUserName(String replyUserName) {
            this.replyUserName = replyUserName;
        }
    }
}
