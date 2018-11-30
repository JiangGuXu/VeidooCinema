package com.bw.movie.bean;

import java.util.List;

public class DetailsBannerBean {

    /**
     * result : [{"duration":"132分钟","fare":0.15,"id":4,"imageUrl":"http://172.17.8.100/images/movie/stills/drjzsdtw/drjzsdtw1.jpg","name":"狄仁杰之四大天王","releaseTime":1566835200000,"summary":"狄仁杰(赵又廷 饰）大破神都龙王案，获御赐亢龙锏，并掌管大理寺，使他成为武则天（刘嘉玲 饰）走向权力之路最大的威胁。武则天为了消灭眼中钉，命令尉迟真金（冯绍峰 饰）集结实力强劲的\u201c异人组\u201d，妄图夺取亢龙锏。在医官沙陀忠（林更新 饰）的协助下，狄仁杰既要守护亢龙锏，又要破获神秘奇案，还要面对武则天的步步紧逼，大唐江山陷入了空前的危机之中\u2026\u2026"},{"duration":"118分钟","fare":0.13,"id":3,"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf1.jpg","name":"西虹市首富","releaseTime":1564156800000,"summary":"故事发生在《夏洛特烦恼》中的\u201c特烦恼\u201d之城\u201c西虹市\u201d。混迹于丙级业余足球队的守门员王多鱼（沈腾 饰），因比赛失利被开除离队。正处于人生最低谷的他接受了神秘台湾财团\u201c一个月花光十亿资金\u201d的挑战。本以为快乐生活就此开始，王多鱼却第一次感到\u201c花钱特烦恼\u201d！想要人生反转走上巅峰，真的没有那么简单."},{"duration":"137分钟","fare":0.18,"id":5,"imageUrl":"http://172.17.8.100/images/movie/stills/xbyz/xbyz1.jpg","name":"邪不压正","releaseTime":1562947200000,"summary":"北洋年间，北京以北。习武少年李天然（彭于晏 饰）目睹师兄朱潜龙（廖凡 饰）勾结日本特务根本一郎，杀害师父全家。李天然侥幸从枪下逃脱，被美国医生亨德勒救下。李天然伤愈后，赴美学医多年，并同时接受特工训练。1937年初，李天然突然受命回国。\u201c七七事变\u201d前夜，北平，这座国际间谍之城，华洋混杂，山头林立。每时每刻充满诱惑与杀机。一心复仇的李天然，并不知道自己被卷入了一场阴谋，亦搅乱了一盘棋局。彼时彼刻，如"}]
     * message : 查询成功
     * status : 0000
     * 姜谷蓄
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
         * duration : 132分钟
         * fare : 0.15
         * id : 4
         * imageUrl : http://172.17.8.100/images/movie/stills/drjzsdtw/drjzsdtw1.jpg
         * name : 狄仁杰之四大天王
         * releaseTime : 1566835200000
         * summary : 狄仁杰(赵又廷 饰）大破神都龙王案，获御赐亢龙锏，并掌管大理寺，使他成为武则天（刘嘉玲 饰）走向权力之路最大的威胁。武则天为了消灭眼中钉，命令尉迟真金（冯绍峰 饰）集结实力强劲的“异人组”，妄图夺取亢龙锏。在医官沙陀忠（林更新 饰）的协助下，狄仁杰既要守护亢龙锏，又要破获神秘奇案，还要面对武则天的步步紧逼，大唐江山陷入了空前的危机之中……
         */

        private String duration;
        private double fare;
        private int id;
        private String imageUrl;
        private String name;
        private long releaseTime;
        private String summary;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public double getFare() {
            return fare;
        }

        public void setFare(double fare) {
            this.fare = fare;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
