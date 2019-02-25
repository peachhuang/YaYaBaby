package com.yaya.baby.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yaya.baby.R;
import com.yaya.baby.bean.ResultEntity;
import com.yaya.baby.net.RequestParams;
import com.yaya.baby.net.retrofit.ApiService;
import com.yaya.baby.net.retrofit.RetrofitClient;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.ui.common.config.ServerConst;
import com.yaya.baby.utils.CountDownCode;
import com.yaya.baby.view.CustomVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 登录界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.videoView)
    CustomVideoView customVideoView;

    //用户输入的手机号
    private String mobilePhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVideoView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    //初始化播放背景的VideoView
    private void initVideoView() {
        customVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            customVideoView.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
            }
        });
        //加载视频文件
        customVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dog));
        //播放
        customVideoView.start();
        //循环播放
        customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                customVideoView.start();
            }
        });
    }


    @OnClick({R.id.btn_next, R.id.tv_code})
    public void onClick(View view) {

        switch (view.getId()) {
            //登录按钮
            case R.id.btn_next:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            //验证码
            case R.id.tv_code:
                CountDownCode count = new CountDownCode(tvCode, 1000 * 30, 1000);
                count.start();
                getVerifyCode("17671714521");
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param mobile 用户输入的手机号
     * @return void
     * @Date 2018-11-07
     */
    private void getVerifyCode(String mobile) {
        ApiService api = RetrofitClient.getInstance(this).Api();
        int funcid = ServerConst.FUNC_ID_GET_VERRIFY_CODE;
        JSONObject params = new JSONObject();
        try {
            params.put("funcid", funcid);
            params.put("mobile", mobile);
            params.put("reqfuncid", ServerConst.FUNC_ID_GET_LOGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String buildParams = RequestParams.buildParams(params);

        Call<ResultEntity> call = api.getVerifyCode(buildParams);
        call.enqueue(new retrofit2.Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call,
                                   Response<ResultEntity> response) {

                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                int res = result.getCode();
                if (res == 1) {// 发送成功

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
