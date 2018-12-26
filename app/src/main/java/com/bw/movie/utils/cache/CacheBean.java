package com.bw.movie.utils.cache;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author:AbnerMing
 * date:2018/11/9
 * 缓存类
 */
@Entity
public class CacheBean {
    @Id(autoincrement = true)
    private Long id;
    private String data;
    private String type;
    @Generated(hash = 1400961908)
    public CacheBean(Long id, String data, String type) {
        this.id = id;
        this.data = data;
        this.type = type;
    }
    @Generated(hash = 573552170)
    public CacheBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
   
}
