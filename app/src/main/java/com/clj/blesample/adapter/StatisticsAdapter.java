package com.clj.blesample.adapter;

import android.content.Context;

import com.clj.blesample.model.StatisticsDTO;

import java.util.List;

public class StatisticsAdapter {

    Context mContext;
    List<StatisticsDTO> statisticsDTOList;

    public StatisticsAdapter(Context mContext, List<StatisticsDTO> statisticsDTOList) {
        this.mContext = mContext;
        this.statisticsDTOList = statisticsDTOList;
    }


}
