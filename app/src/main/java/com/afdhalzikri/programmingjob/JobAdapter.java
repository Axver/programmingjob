package com.afdhalzikri.programmingjob;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by HP on 10/25/2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder>{

    List<JobEntity> data;
    JobHolderClickListener jobHolderClickListener;

    public void setData(List<JobEntity> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public List<JobEntity> getData(){
        return data;
    }

    public void setClickListener(JobHolderClickListener clickListener){
        this.jobHolderClickListener = clickListener;
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout_job, parent, false);
        JobHolder holder = new JobHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JobHolder holder, int position) {
        JobEntity job = data.get(position);
        holder.mTextPlace.setText(job.place);
        holder.mTextJob.setText(job.type_job);
        holder.mTextOpen.setText("Open Date: "+job.open);
        holder.mTextClose.setText("Close Date: "+job.close);
        holder.mTextUrl.setText(job.url);
    }

    @Override
    public int getItemCount() {
        if(data != null) {
            return data.size();
        }
        return 0;
    }


    public class JobHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView mTextPlace;
        TextView mTextJob;
        TextView mTextOpen;
        TextView mTextClose;
        TextView mTextUrl;

        public JobHolder(View itemView) {
            super(itemView);
            mTextPlace = itemView.findViewById(R.id.text_place);
            mTextJob = itemView.findViewById(R.id.text_job);
            mTextOpen = itemView.findViewById(R.id.text_open);
            mTextClose = itemView.findViewById(R.id.text_close);
            mTextUrl = itemView.findViewById(R.id.text_url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            jobHolderClickListener.onJobClick(position);
        }
    }
    public interface JobHolderClickListener{
        void onJobClick(int position);
    }
}