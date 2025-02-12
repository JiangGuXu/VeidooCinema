package com.bw.movie.utils.net;


import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * author:赵瑜峰
 * date:2018/11/27
 */
public interface BaseService {


    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> map, @HeaderMap Map<String, String> mapHead);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postHead(@Url String url, @FieldMap Map<String, String> map, @HeaderMap Map<String, String> mapHead);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postForm(@Url String url, @QueryMap Map<String, String> map, @HeaderMap Map<String, String> mapHead, @FieldMap Map<String, String> mapfrom);

    @FormUrlEncoded
    @POST
    @Headers({
            "ak:0110010010000",
            "Content-Type:application/x-www-form-urlencoded"
    })
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @Multipart
    @POST
    Observable<ResponseBody> part(@Url String url, @HeaderMap Map<String, String> map, @Part MultipartBody.Part part);


    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postUploadImage(String url, @FieldMap Map<String, File> map, @HeaderMap Map<String, String> mapHead);
}
