package com.yaya.baby.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.yaya.baby.R;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 亲友关怀的界面
 *
 * @author Darcy
 * @date 2018-10-30
 */
public class FamilyCareActivity extends BaseActivity {
    @BindView(R.id.topView)
    TopView topView;
    @BindView(R.id.iv_QR_code)
    ImageView ivQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_family_care);
        topView.setTitle("亲友关怀");
        topView.setRightImageVis();
        createQRCode();
    }

    /**
     * 生成二维码
     */
    private void createQRCode() {
        Resources res = context.getResources();
        Bitmap bitmapLogo = BitmapFactory.decodeResource(res, R.mipmap.head);

        // 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
        Bitmap bitmap = EncodingUtils.createQRCode("卡哈科技",
                600, 600, bitmapLogo);
        // 设置图片
        ivQRCode.setImageBitmap(bitmap);
    }

    @OnClick({R.id.rl_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_image:
                skipPage(CaptureActivity.class);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_care;
    }
}
