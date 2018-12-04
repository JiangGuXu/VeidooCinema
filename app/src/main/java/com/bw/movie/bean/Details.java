package com.bw.movie.bean;

import java.util.List;

public class Details {

     /**
     * result : {"director":"刘阔","duration":"105分钟","followMovie":true,"id":12,"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz1.jpg","movieTypes":"冒险 / 科幻 / 动画","name":"风语咒","placeOrigin":"中国大陆","posterList":["http://172.17.8.100/images/movie/stills/fyz/fyz1.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz2.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz3.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz4.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz5.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz6.jpg"],"rank":0,"shortFilmList":[{"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz3.jpg","videoUrl":"http://172.17.8.100/video/movie/fyz/fyz1.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz2.jpg","videoUrl":"http://172.17.8.100/video/movie/fyz/fyz2.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz4.jpg","videoUrl":"http://172.17.8.100/video/movie/fyz/fyz3.ts"}],"starring":"路知行,阎萌萌,褚珺","summary":"生活在孝阳岗的少年郎明怀揣侠岚梦想，但双眼失明的他却只能靠招摇撞骗混于市井之中。直到有一天，罗刹袭击孝阳岗，与他相依为命的母亲突然失踪，郎明迫不得已踏上了找寻真相之路。一波未平一波又起，上古神兽饕餮现世让人间危在旦夕，传说中的侠岚们也出现在眼前，郎明也踏上了改变一生的冒险之旅\u2026\u2026"}
     * message : 查询成功
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
         * director : 刘阔
         * duration : 105分钟
         * followMovie : true
         * id : 12
         * imageUrl : http://172.17.8.100/images/movie/stills/fyz/fyz1.jpg
         * movieTypes : 冒险 / 科幻 / 动画
         * name : 风语咒
         * placeOrigin : 中国大陆
         * posterList : ["http://172.17.8.100/images/movie/stills/fyz/fyz1.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz2.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz3.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz4.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz5.jpg","http://172.17.8.100/images/movie/stills/fyz/fyz6.jpg"]
         * rank : 0
         * shortFilmList : [{"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz3.jpg","videoUrl":"http://172.17.8.100/video/movie/fyz/fyz1.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz2.jpg","videoUrl":"http://172.17.8.100/video/movie/fyz/fyz2.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/fyz/fyz4.jpg","videoUrl":"http://172.17.8.100/video/movie/fyz/fyz3.ts"}]
         * starring : 路知行,阎萌萌,褚珺
         * summary : 生活在孝阳岗的少年郎明怀揣侠岚梦想，但双眼失明的他却只能靠招摇撞骗混于市井之中。直到有一天，罗刹袭击孝阳岗，与他相依为命的母亲突然失踪，郎明迫不得已踏上了找寻真相之路。一波未平一波又起，上古神兽饕餮现世让人间危在旦夕，传说中的侠岚们也出现在眼前，郎明也踏上了改变一生的冒险之旅……
         */

        private String director;
        private String duration;
        private boolean followMovie;
        private int id;
        private String imageUrl;
        private String movieTypes;
        private String name;
        private String placeOrigin;
        private int rank;
        private String starring;
        private String summary;
        private List<String> posterList;
        private List<ShortFilmListBean> shortFilmList;

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public boolean isFollowMovie() {
            return followMovie;
        }

        public void setFollowMovie(boolean followMovie) {
            this.followMovie = followMovie;
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

        public String getMovieTypes() {
            return movieTypes;
        }

        public void setMovieTypes(String movieTypes) {
            this.movieTypes = movieTypes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceOrigin() {
            return placeOrigin;
        }

        public void setPlaceOrigin(String placeOrigin) {
            this.placeOrigin = placeOrigin;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getStarring() {
            return starring;
        }

        public void setStarring(String starring) {
            this.starring = starring;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getPosterList() {
            return posterList;
        }

        public void setPosterList(List<String> posterList) {
            this.posterList = posterList;
        }

        public List<ShortFilmListBean> getShortFilmList() {
            return shortFilmList;
        }

        public void setShortFilmList(List<ShortFilmListBean> shortFilmList) {
            this.shortFilmList = shortFilmList;
        }

        public static class ShortFilmListBean {
            /**
             * imageUrl : http://172.17.8.100/images/movie/stills/fyz/fyz3.jpg
             * videoUrl : http://172.17.8.100/video/movie/fyz/fyz1.ts
             */

            private String imageUrl;
            private String videoUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }
        }
    }
}
