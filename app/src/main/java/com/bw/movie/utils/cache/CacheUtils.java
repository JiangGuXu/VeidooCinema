package com.bw.movie.utils.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bw.movie.utils.cache.database.CacheBeanDao;
import com.bw.movie.utils.cache.database.DaoMaster;

import java.util.List;

/**
 * author:AbnerMing
 * date:2018/11/9
 * 缓存工具类
 */
public class CacheUtils {
    private CacheBeanDao dao;

    private CacheUtils() {
    }

    private static CacheUtils mCacheUtils;

    public static CacheUtils getCacheUtils() {
        if (mCacheUtils == null) {
            mCacheUtils = new CacheUtils();
        }
        return mCacheUtils;
    }

    //初始化
    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cache");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        dao = daoMaster.newSession().getCacheBeanDao();
    }

    /***
     * data:为缓存的数据
     * type:为缓存的类型
     */
    public void insert(String data, String type) {
        String query = query(type);
        if(query!=null){
            delete(type);
        }
        CacheBean cacheBean = new CacheBean();
        cacheBean.setData(data);
        cacheBean.setType(type);
        dao.insert(cacheBean);
    }

    /**
     * 删除全部
     */
    public void deleteAll() {
        dao.deleteAll();
    }

    /**
     * 根据类型删除
     */
    public void delete(String type) {
        List<CacheBean> list = dao.loadAll();
        for (CacheBean bean : list) {
            if (type.equals(bean.getType())) {
                dao.delete(bean);
                break;
            }
        }
    }

    /**
     * 获取全部
     */
    public List<CacheBean> queryAll() {
        return dao.loadAll();
    }

    /**
     * 获取指定类型的缓存
     */
    public String query(String type) {
        CacheBean b = null;
        List<CacheBean> list = dao.loadAll();
        for (CacheBean bean : list) {
            if (type.equals(bean.getType())) {
                b = bean;
                break;
            }
        }
        if(b!=null){
            return b.getData();
        }else{
            return null;
        }
    }

    /**
     * 更新
     */
    public void update(String data, String type) {
        List<CacheBean> list = dao.loadAll();
        for (CacheBean bean : list) {
            if (type.equals(bean.getType())) {
                bean.setData(data);
                dao.update(bean);
                break;
            }
        }

    }
}
