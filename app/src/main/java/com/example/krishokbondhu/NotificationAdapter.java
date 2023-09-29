package com.example.krishokbondhu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.NotificationDetailsViewHolder>{
    private Context mCtx;
    private List<NotificationDetails> notificationList;
    public NotificationAdapter(Context mCtx, List<NotificationDetails> notificationList) {
        this.mCtx = mCtx;
        this.notificationList = notificationList;
    }
    @NonNull
    @Override
    public NotificationAdapter.NotificationDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.notificationlist, null);
        return new NotificationAdapter.NotificationDetailsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationDetailsViewHolder holder, int position) {
        NotificationDetails n1 = notificationList.get(position);
        holder.n_date.setText(n1.getDate());
        holder.n_info.setText(String.valueOf(n1.getInfo()));
    }
    @Override
    public int getItemCount() {return notificationList.size();}
    public class NotificationDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView n_date, n_info;
        public NotificationDetailsViewHolder(View itemView) {
            super(itemView);
            n_date = itemView.findViewById(R.id.date);
            n_info = itemView.findViewById(R.id.n_message);
        }
    }
}
