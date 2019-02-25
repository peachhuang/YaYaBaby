package com.yaya.baby.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaya.baby.R;
import com.yaya.baby.bean.BluetoothEntity;
import com.yaya.baby.ui.common.RecycleViewItemClickListener;

import java.util.List;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 找到的设备的适配器
 */
public class ShowFindEquipmentAdapter extends RecyclerView.Adapter<ShowFindEquipmentAdapter.MyViewHolder> {

    private Context context;
    private List<BluetoothEntity> list;
    private RecycleViewItemClickListener listener;

    public ShowFindEquipmentAdapter(Context context, List<BluetoothEntity> list, RecycleViewItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_bluetooth_item, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvName.setText(list.get(i).getNameStr());
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(i,myViewHolder.tvStatues);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        LinearLayout linearLayout;
        TextView tvStatues;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_bluetooth_name);
            linearLayout = itemView.findViewById(R.id.ll_bluetooth_item);
            tvStatues = itemView.findViewById(R.id.tv_connection_statues);
        }
    }
}
