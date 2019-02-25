package com.yaya.baby.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaya.baby.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 自定义topView
 */
public class TopView extends RelativeLayout {

    //    @BindView(R.id.rl_back)
//    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_image)
    RelativeLayout rlRight;
    @BindView(R.id.iv_right_image)
    ImageView ivRightImage;

    public TopView(Context context) {
        super(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout
                .head, this);
        ButterKnife.bind(this, view);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick({R.id.rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
                break;
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置右边的图片
     *
     * @param id 图片的id
     */
    public void setRightImage(int id) {
        ivRightImage.setImageResource(id);
    }

    /**
     * 设置右边的布局可见
     */
    public void setRightImageVis() {
        rlRight.setVisibility(VISIBLE);
    }
}
