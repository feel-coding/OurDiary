package sungshin.project.ourdiaryapplication.home.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sungshin.project.ourdiaryapplication.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView writer;
    public TextView date;
    public TextView content;
    public TextView withWhom;
    public final View mView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        title = itemView.findViewById(R.id.diaryTitle);
        writer = itemView.findViewById(R.id.diaryWriter);
        date = itemView.findViewById(R.id.diaryDate);
        content = itemView.findViewById(R.id.diaryContent);
        withWhom = itemView.findViewById(R.id.withWhom);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
