package com.bw.movie.bean;

import java.util.List;

public class UserAttentionFilmAdapterBean {

    /**
     * result : [{"fare":0,"id":12,"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/fyz/fyz1.jpg","name":"风语咒","releaseTime":1536336000000,"summary":"生活在孝阳岗的少年郎明怀揣侠岚梦想，但双眼失明的他却只能靠招摇撞骗混于市井之中。直到有一天，罗刹袭击孝阳岗，与他相依为命的母亲突然失踪，郎明迫不得已踏上了找寻真相之路。一波未平一波又起，上古神兽饕餮现世让人间危在旦夕，传说中的侠岚们也出现在眼前，郎明也踏上了改变一生的冒险之旅\u2026\u2026"}]
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
         * fare : 0
         * id : 12
         * imageUrl : http://mobile.bwstudent.com/images/movie/stills/fyz/fyz1.jpg
         * name : 风语咒
         * releaseTime : 1536336000000
         * summary : 生活在孝阳岗的少年郎明怀揣侠岚梦想，但双眼失明的他却只能靠招摇撞骗混于市井之中。直到有一天，罗刹袭击孝阳岗，与他相依为命的母亲突然失踪，郎明迫不得已踏上了找寻真相之路。一波未平一波又起，上古神兽饕餮现世让人间危在旦夕，传说中的侠岚们也出现在眼前，郎明也踏上了改变一生的冒险之旅……
         */

        private double fare;
        private int id;
        private String imageUrl;
        private String name;
        private long releaseTime;
        private String summary;

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
