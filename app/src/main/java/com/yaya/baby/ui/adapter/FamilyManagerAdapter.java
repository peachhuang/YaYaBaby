package com.yaya.baby.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaya.baby.R;
import com.yaya.baby.ui.activity.FamilyMemberActivity;

import java.util.List;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 家庭成员管理的适配器
 */
public class FamilyManagerAdapter extends RecyclerView.Adapter<FamilyManagerAdapter.MyViewHolder> {

    private List<String> list;

    private Context context;

    public FamilyManagerAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.layout_family_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        String s = list.get(i);
        myViewHolder.tvName.setText(s);

        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FamilyMemberActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        LinearLayout llItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_family_item);
        }
    }
}
