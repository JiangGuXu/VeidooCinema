package com.bw.movie.bean;

/*
 * 附近影院Nearbean
 * 2018年11月28日 19:20:35
 * 程丹妮
 * 创建了基本的这个bean
 * */

import java.util.List;

public class Nearbean {


    /**
     * result : {"nearbyCinemaList":[{"address":"东城区滨河路乙1号雍和航星园74-76号楼","commentTotal":64,"distance":0,"followCinema":false,"id":1,"logo":"http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg","name":"青春光线电影院"},{"address":"西城区前门大街大栅栏街36号","commentTotal":5,"distance":0,"followCinema":false,"id":2,"logo":"http://mobile.bwstudent.com/images/movie/logo/dgl.jpg","name":"大观楼电影院"},{"address":"北京市西城区天桥南大街3号楼一层、二层（天桥艺术大厦南侧）","commentTotal":25,"distance":0,"followCinema":false,"id":3,"logo":"http://mobile.bwstudent.com/images/movie/logo/sd.jpg","name":"首都电影院"},{"address":"北京市海淀区远大路1号B座5层魔影国际影城","commentTotal":11,"distance":0,"followCinema":false,"id":4,"logo":"http://mobile.bwstudent.com/images/movie/logo/mygj.jpg","name":"魔影国际影城"},{"address":"朝阳区湖景东路11号新奥购物中心B1(东A口)","commentTotal":11,"distance":0,"followCinema":false,"id":5,"logo":"http://mobile.bwstudent.com/images/movie/logo/CGVxx.jpg","name":"CGV星星影城"},{"address":"朝阳区建国路93号万达广场三层","commentTotal":6,"distance":0,"followCinema":false,"id":6,"logo":"http://mobile.bwstudent.com/images/movie/logo/wd.jpg","name":"北京CBD万达广场店"}]}
     * message : 查询成功
     * status : 0000
     */

    private Resultbean result;
    private String message;
    private String status;

    public Resultbean getResult() {
        return result;
    }

    public void setResult(Resultbean result) {
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

    public static class Resultbean {
        private List<NearbyCinemaListbean> nearbyCinemaList;

        public List<NearbyCinemaListbean> getNearbyCinemaList() {
            return nearbyCinemaList;
        }

        public void setNearbyCinemaList(List<NearbyCinemaListbean> nearbyCinemaList) {
            this.nearbyCinemaList = nearbyCinemaList;
        }

        public static class NearbyCinemaListbean {
            /**
             * address : 东城区滨河路乙1号雍和航星园74-76号楼
             * commentTotal : 64
             * distance : 0
             * followCinema : false
             * id : 1
             * logo : http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg
             * name : 青春光线电影院
             */

            private String address;
            private int commentTotal;
            private int distance;
            private boolean followCinema;
            private int id;
            private String logo;
            private String name;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCommentTotal() {
                return commentTotal;
            }

            public void setCommentTotal(int commentTotal) {
                this.commentTotal = commentTotal;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
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
        }
    }
}
