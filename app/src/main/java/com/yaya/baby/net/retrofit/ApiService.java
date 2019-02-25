package com.yaya.baby.net.retrofit;

import com.yaya.baby.bean.ResultEntity;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Darcy
 * @Date 2018-11-07
 */
public interface ApiService {

    //获取验证码
    @POST("func")
    Call<ResultEntity> getVerifyCode(@Query("param") String params);
}
