package com.bw.movie.bean;

import java.util.List;

public class UserAttendanceBean1 {

    /**
     * result : {"cinemasList":[{"address":"东城区滨河路乙1号雍和航星园74-76号楼","followCinema":false,"id":1,"logo":"http://172.17.8.100/images/movie/logo/qcgx.jpg","name":"青春光线电影院","rank":0},{"address":"西城区前门大街大栅栏街36号","followCinema":false,"id":2,"logo":"http://172.17.8.100/images/movie/logo/dgl.jpg","name":"大观楼电影院","rank":0}],"headPic":"http://172.17.8.100/images/movie/head_pic/2018-07-21/20180721120945.jpg","integral":10,"movieList":[{"id":1,"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","name":"我不是药神","releaseTime":1530720000000,"summary":"一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药\u201c格列宁\u201d的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以\u201c药神\u201d的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......"},{"id":2,"imageUrl":"http://172.17.8.100/images/movie/stills/mtyj/mtyj1.jpg","name":"摩天营救","releaseTime":1532016000000,"summary":"在香港市中心，世界上最高、结构最复杂的摩天大楼遭到破坏，危机一触即发。威尔·索耶（道恩·强森 饰）的妻子萨拉（内芙·坎贝尔 饰）和孩子们在98层被劫为人质，直接暴露在火线上。威尔，这位战争英雄、前联邦调查局救援队员，作为大楼的安保顾问，却被诬陷纵火和谋杀。他必须奋力营救家人，为自己洗脱罪名，关乎生死存亡的高空任务就此展开。"}],"nickName":"你的益达","phone":"18600151568","userSignStatus":true}
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
         * cinemasList : [{"address":"东城区滨河路乙1号雍和航星园74-76号楼","followCinema":false,"id":1,"logo":"http://172.17.8.100/images/movie/logo/qcgx.jpg","name":"青春光线电影院","rank":0},{"address":"西城区前门大街大栅栏街36号","followCinema":false,"id":2,"logo":"http://172.17.8.100/images/movie/logo/dgl.jpg","name":"大观楼电影院","rank":0}]
         * headPic : http://172.17.8.100/images/movie/head_pic/2018-07-21/20180721120945.jpg
         * integral : 10
         * movieList : [{"id":1,"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","name":"我不是药神","releaseTime":1530720000000,"summary":"一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药\u201c格列宁\u201d的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以\u201c药神\u201d的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......"},{"id":2,"imageUrl":"http://172.17.8.100/images/movie/stills/mtyj/mtyj1.jpg","name":"摩天营救","releaseTime":1532016000000,"summary":"在香港市中心，世界上最高、结构最复杂的摩天大楼遭到破坏，危机一触即发。威尔·索耶（道恩·强森 饰）的妻子萨拉（内芙·坎贝尔 饰）和孩子们在98层被劫为人质，直接暴露在火线上。威尔，这位战争英雄、前联邦调查局救援队员，作为大楼的安保顾问，却被诬陷纵火和谋杀。他必须奋力营救家人，为自己洗脱罪名，关乎生死存亡的高空任务就此展开。"}]
         * nickName : 你的益达
         * phone : 18600151568
         * userSignStatus : true
         */

        private String headPic;
        private int integral;
        private String nickName;
        private String phone;
        private boolean userSignStatus;
        private List<CinemasListBean> cinemasList;
        private List<MovieListBean> movieList;

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
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

        public boolean isUserSignStatus() {
            return userSignStatus;
        }

        public void setUserSignStatus(boolean userSignStatus) {
            this.userSignStatus = userSignStatus;
        }

        public List<CinemasListBean> getCinemasList() {
            return cinemasList;
        }

        public void setCinemasList(List<CinemasListBean> cinemasList) {
            this.cinemasList = cinemasList;
        }

        public List<MovieListBean> getMovieList() {
            return movieList;
        }

        public void setMovieList(List<MovieListBean> movieList) {
            this.movieList = movieList;
        }

        public static class CinemasListBean {
            /**
             * address : 东城区滨河路乙1号雍和航星园74-76号楼
             * followCinema : false
             * id : 1
             * logo : http://172.17.8.100/images/movie/logo/qcgx.jpg
             * name : 青春光线电影院
             * rank : 0
             */

            private String address;
            private boolean followCinema;
            private int id;
            private String logo;
            private String name;
            private int rank;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public boolean isFollowCinema() {
                return followCinema;
            }

            public void setFollowCinema(boolean followCinema) {
                this.followCinema = followCinema;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }
        }

        public static class MovieListBean {
            /**
             * id : 1
             * imageUrl : http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg
             * name : 我不是药神
             * releaseTime : 1530720000000
             * summary : 一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药“格列宁”的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以“药神”的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......
             */

            private int id;
            private String imageUrl;
            private String name;
            private long releaseTime;
            private String summary;

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
}
