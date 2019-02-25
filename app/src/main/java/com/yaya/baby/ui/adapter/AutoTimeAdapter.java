package com.yaya.baby.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaya.baby.R;
import com.yaya.baby.bean.AutoTimeBean;
import com.yaya.baby.view.AutoTimeHorizontalView;

import java.util.List;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 自动选择时间的适配器
 */
public class AutoTimeAdapter extends RecyclerView.Adapter<AutoTimeAdapter.AgeViewHolder> implements AutoTimeHorizontalView.IAutoLocateHorizontalView {
    private Context context;
    private View view;
    private List<AutoTimeBean> list;
    public AutoTimeAdapter(Context context,List<AutoTimeBean>list){
        this.context = context;
        this.list = list;
    }

    @Override
    public AgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_time_item,parent,false);
        return new AgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgeViewHolder holder, int position) {
        holder.tvWeek.setText(list.get(position).getWeek());
        holder.tvDate.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    @Override
    public View getItemView() {
        return view;
    }

    @Override
    public void onViewSelected(boolean isSelected,int pos, RecyclerView.ViewHolder holder,int itemWidth) {
        if(isSelected) {
            ((AgeViewHolder) holder).tvWeek.setTextSize(14);
            ((AgeViewHolder) holder).tvDate.setTextSize(12);
            ((AgeViewHolder) holder).tvDate.setVisibility(View.VISIBLE);
            ((AgeViewHolder) holder).llTimeItem.setBackgroundResource(R.drawable.rectangle_auto_time);

        }else{
            ((AgeViewHolder) holder).tvWeek.setTextSize(10);
            ((AgeViewHolder) holder).tvDate.setVisibility(View.GONE);
            ((AgeViewHolder) holder).llTimeItem.setBackgroundResource(R.drawable.rectangle_white);
        }
    }

    static class AgeViewHolder extends RecyclerView.ViewHolder{
        TextView tvWeek;
        TextView tvDate;
        LinearLayout llTimeItem;
        AgeViewHolder(View itemView) {
            super(itemView);
            tvWeek = itemView.findViewById(R.id.tv_week);
            tvDate = itemView.findViewById(R.id.tv_date);
            llTimeItem = itemView.findViewById(R.id.ll_time_item);
        }
    }
}

