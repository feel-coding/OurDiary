package sungshin.project.ourdiaryapplication.home.fragments;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sungshin.project.ourdiaryapplication.R;

public class DiaryAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Diary> list;

    public DiaryAdapter(List<Diary> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = list.get(position).title;
        String writer = list.get(position).writer;
        String date = list.get(position).date;
        String content = list.get(position).content;
        String withWhom = list.get(position).withWhom;
        holder.title.setText(title);
        holder.writer.setText(writer);
        holder.date.setText(date);
        holder.content.setText(content);
        if(!withWhom.equals("")) {
            holder.withWhom.setText("함께 하는 친구: " + withWhom);
        }
        else {
            holder.withWhom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
