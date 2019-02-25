package com.yaya.baby.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaya.baby.R;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 自定义我的界面中条目
 */
public class MyItemLayout extends RelativeLayout {
    public MyItemLayout(Context context) {
        super(context);
    }

    public MyItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_item, this);
        TextView textView = view.findViewById(R.id.text);
        ImageView imageView = view.findViewById(R.id.image);

        //attrs.getAttributeResourceValue(R.styleable.ConstraintSet_android_layout_height,-1);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyItemLayout);
        textView.setText(ta.getString(R.styleable.MyItemLayout_text));
        imageView.setImageResource(ta.getResourceId(R.styleable.MyItemLayout_image, -1));
        ta.recycle();
    }

    public MyItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
