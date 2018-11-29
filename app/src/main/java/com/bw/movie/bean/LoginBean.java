package com.bw.movie.bean;
/**
 * 用户登录的bean
 * 姜谷蓄
 */

public class LoginBean {

    /**
     * result : {"sessionId":"15433938785332096","userId":2096,"userInfo":{"birthday":811353600000,"headPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","id":2096,"lastLoginTime":1543393036000,"nickName":"\"焦浩康233\u201c","phone":"17718318084","sex":1}}
     * message : 登陆成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * sessionId : 15433938785332096
         * userId : 2096
         * userInfo : {"birthday":811353600000,"headPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","id":2096,"lastLoginTime":1543393036000,"nickName":"\"焦浩康233\u201c","phone":"17718318084","sex":1}
         */

        private String sessionId;
        private int userId;
        private UserInfoBean userInfo;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * birthday : 811353600000
             * headPic : http://172.17.8.100/images/movie/head_pic/bwjy.jpg
             * id : 2096
             * lastLoginTime : 1543393036000
             * nickName : "焦浩康233“
             * phone : 17718318084
             * sex : 1
             */

            private long birthday;
            private String headPic;
            private int id;
            private long lastLoginTime;
            private String nickName;
            private String phone;
            private int sex;

            public long getBirthday() {
                return birthday;
            }

            public void setBirthday(long birthday) {
                this.birthday = birthday;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(long lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }
        }
    }
}
