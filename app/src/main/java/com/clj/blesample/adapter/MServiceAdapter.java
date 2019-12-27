package com.clj.blesample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clj.blesample.R;
import com.clj.blesample.model.MaintenaceServiceDTO;

import java.util.List;

public class MServiceAdapter extends RecyclerView.Adapter<MServiceAdapter.MServiceViewHolder> {

    Context context;
    List<MaintenaceServiceDTO> mServiceList;

    public MServiceAdapter(Context context, List<MaintenaceServiceDTO> mServiceList) {
        this.context = context;
        this.mServiceList = mServiceList;
    }

    @NonNull
    @Override
    public MServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_maintenance_service, viewGroup, false);

        MServiceViewHolder mServiceViewHolder = new MServiceViewHolder(view);


        return mServiceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MServiceViewHolder mServiceViewHolder, int i) {

        mServiceViewHolder.mDate.setText(mServiceList.get(i).getmServiceDate());
        mServiceViewHolder.mPerson.setText(mServiceList.get(i).getmServiceDName());
        mServiceViewHolder.mIssue.setText(mServiceList.get(i).getmServiceIssue());



    }

    @Override
    public int getItemCount() {


        return mServiceList == null ? 0 : mServiceList.size();
    }

    class MServiceViewHolder extends RecyclerView.ViewHolder {

        TextView mDate, mPerson, mIssue;


        public MServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            mDate = (TextView) itemView.findViewById(R.id.mServiceDateAdapter);
            mPerson = (TextView) itemView.findViewById(R.id.mServiceNameAdapter);
            mIssue = (TextView) itemView.findViewById(R.id.mServiceIssueAdapter);


        }
    }

}
