package com.bw.movie.utils.net;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
/**
 *
 * author:赵瑜峰
 * date:2018/11/27
 */
public interface BaseService {


    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> map);
    @FormUrlEncoded
    @POST
    @Headers({
            "ak:0110010010000",
            "Content-Type:application/x-www-form-urlencoded"
    })
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);
    @Multipart
    @POST
    Observable<ResponseBody> part(@Url String url, @QueryMap Map<String, String> map, @Part MultipartBody.Part part);
}
