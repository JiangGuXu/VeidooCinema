package com.bw.movie.bean;

import java.util.List;

public class Commentsben {

    /**
     * result : [{"commentContent":"shg","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-11-23/20181123150015.unknown","commentId":623,"commentTime":1542348367000,"commentUserId":1338,"commentUserName":"Luye666","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"傻逼","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-11-26/20181126100056.jpg","commentId":620,"commentTime":1542281250000,"commentUserId":1726,"commentUserName":"majunbao","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"222","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-20/20180920082830.jpg","commentId":585,"commentTime":1540531877000,"commentUserId":556,"commentUserName":"张大炮","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"tydfghd","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-10-23/20181023143847.jpg","commentId":558,"commentTime":1540454777000,"commentUserId":886,"commentUserName":"李庆帅001","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"666","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-20/20180920095505.png","commentId":549,"commentTime":1540292808000,"commentUserId":413,"commentUserName":"皮皮猪之王","greatNum":1,"hotComment":0,"isGreat":0}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<Resultbean> result;

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

    public List<Resultbean> getResult() {
        return result;
    }

    public void setResult(List<Resultbean> result) {
        this.result = result;
    }

    public static class Resultbean {
        /**
         * commentContent : shg
         * commentHeadPic : http://172.17.8.100/images/movie/head_pic/2018-11-23/20181123150015.unknown
         * commentId : 623
         * commentTime : 1542348367000
         * commentUserId : 1338
         * commentUserName : Luye666
         * greatNum : 0
         * hotComment : 0
         * isGreat : 0
         */

        private String commentContent;
        private String commentHeadPic;
        private int commentId;
        private long commentTime;
        private int commentUserId;
        private String commentUserName;
        private int greatNum;
        private int hotComment;
        private int isGreat;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getHotComment() {
            return hotComment;
        }

        public void setHotComment(int hotComment) {
            this.hotComment = hotComment;
        }

        public int getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(int isGreat) {
            this.isGreat = isGreat;
        }
    }
}
