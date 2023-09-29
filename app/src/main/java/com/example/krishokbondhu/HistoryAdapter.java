package com.example.krishokbondhu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryDetailsViewHolder>{
    private Context mCtx;
    private List<HistoryDetails> historyList;
    public HistoryAdapter(Context mCtx, List<HistoryDetails> historyList) {
        this.mCtx = mCtx;
        this.historyList = historyList;
    }
    @NonNull
    @Override
    public HistoryAdapter.HistoryDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.historylist, null);
        return new HistoryAdapter.HistoryDetailsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryDetailsViewHolder holder, int position) {
        HistoryDetails h1 = historyList.get(position);
        holder.h2.setText(h1.getDate_history());// date
        holder.h3.setText(String.valueOf(h1.getTime_value())); // time value
        holder.h4.setText(String.valueOf(h1.getInfo_history())+" "+ h1.getPayment_value()+" দিয়ে ।"); // history message
        holder.h5.setText(String.valueOf(String.valueOf(h1.getStatus())));
    }
    @Override
    public int getItemCount() {return historyList.size();}
    public class HistoryDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView h2,h3,h4,h5;
        public HistoryDetailsViewHolder(View itemView) {
            super(itemView);
            h2 = itemView.findViewById(R.id.h_date);
            h3 = itemView.findViewById(R.id.h_date_timelimit);
            h4 = itemView.findViewById(R.id.h_message);
            h5 = itemView.findViewById(R.id.h_status);

        }
    }
}
