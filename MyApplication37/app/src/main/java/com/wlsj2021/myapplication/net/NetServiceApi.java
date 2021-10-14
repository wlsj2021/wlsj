package com.wlsj2021.myapplication.net;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

//所有的API都放在一起
public interface NetServiceApi {

    //首页的page
    //rxjava1 Observable
    @GET("article/list/{page}/json")
    Observable<HomeModel> getHomePage(@Path("page")int page);
    //注册
    @POST("user/register")
    Call<HomeModel>getRegister(@Query("username") String username,
                               @Query("password") String password,
                               @Query("repassword") String repassword);


    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @PUT("article/list/{page}/json")
    Call<HomeModel>getHomePage1(@Path("page")int page);

    @Headers("Cache-Control: max-age=640000")
    @DELETE("article/list/{page}/json")
    Call<HomeModel>getHomePage2(@Path("page")int page);

    @HTTP(method = "GET", path = "article/list/{page}/json", hasBody = false)
    Call<HomeModel> deleteObject(@Path("page")int page);
}
