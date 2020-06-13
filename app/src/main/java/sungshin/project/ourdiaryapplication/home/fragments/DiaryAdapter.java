package sungshin.project.ourdiaryapplication.home.fragments;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigInteger;
import java.util.List;

import sungshin.project.ourdiaryapplication.DocumentActivity;
import sungshin.project.ourdiaryapplication.R;

public class DiaryAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Diary> list;
    private Context mContext;

    public DiaryAdapter(List<Diary> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
        BigInteger seq = list.get(position).seq;
        Integer likeCount = list.get(position).likeCount;
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
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DocumentActivity.class);
                intent.putExtra("diary_content", content);
                intent.putExtra("diary_title", title);
                intent.putExtra("diary_date", date);
                intent.putExtra("diary_seq", seq.toString());
                intent.putExtra("diary_likecount", likeCount.toString());
                intent.putStringArrayListExtra("diary_photo_url", list.get(position).photoList);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
