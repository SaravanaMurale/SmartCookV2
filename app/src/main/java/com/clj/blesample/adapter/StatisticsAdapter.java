package com.clj.blesample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clj.blesample.R;
import com.clj.blesample.model.StatisticsDTO;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {

    Context mContext;
    List<StatisticsDTO> statisticsDTOList;

    public StatisticsAdapter(Context mContext, List<StatisticsDTO> statisticsDTOList) {
        this.mContext = mContext;
        this.statisticsDTOList = statisticsDTOList;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view=layoutInflater.inflate(R.layout.layout_statistics_report,viewGroup,false);
        StatisticsViewHolder statisticsViewHolder=new StatisticsViewHolder(view);
        return statisticsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder statisticsViewHolder, int i) {

        statisticsViewHolder.st_Date.setText(statisticsDTOList.get(i).getCookingDate());
        statisticsViewHolder.st_StartTime.setText(statisticsDTOList.get(i).getCookingStartTime());
        statisticsViewHolder.st_EndTime.setText(statisticsDTOList.get(i).getCookingEndTime());
        statisticsViewHolder.st_Duration.setText(statisticsDTOList.get(i).getTotalCookingDuration());
        statisticsViewHolder.st_Burner.setText(statisticsDTOList.get(i).getBurnerNo());


    }

    @Override
    public int getItemCount() {
        return statisticsDTOList == null ? 0 : statisticsDTOList.size();
    }


    class StatisticsViewHolder extends RecyclerView.ViewHolder {

        TextView st_Date,st_StartTime,st_EndTime,st_Duration,st_Burner;


        public StatisticsViewHolder(@NonNull View itemView) {
            super(itemView);

            st_Date=(TextView)itemView.findViewById(R.id.st_date);
            st_StartTime=(TextView)itemView.findViewById(R.id.st_starttime);
            st_EndTime=(TextView)itemView.findViewById(R.id.st_endtime);
            st_Duration=(TextView)itemView.findViewById(R.id.st_duration);
            st_Burner=(TextView)itemView.findViewById(R.id.st_burner);


        }
    }


}
