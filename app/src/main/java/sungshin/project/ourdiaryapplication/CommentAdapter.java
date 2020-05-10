package sungshin.project.ourdiaryapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<CommentItem> comment;

    public CommentAdapter(Context context, ArrayList<CommentItem> data) {
        mContext = context;
        comment = data;
    }

    @Override
    public int getCount() {
        return comment.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return comment.get(position);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.comment_item, viewGroup,false);
        }
        TextView comment_nick = (TextView)view.findViewById(R.id.comment_nick);
        TextView comment_name = view.findViewById(R.id.comment_name);
        TextView comment_content = view.findViewById(R.id.comment_content);
        TextView comment_date = view.findViewById(R.id.comment_date);

        ImageButton comment_imgbtn = view.findViewById(R.id.comment_imgbtn);

        comment_nick.setText(comment.get(position).getNickname());
        comment_name.setText("("+comment.get(position).getName()+")");
        comment_content.setText(comment.get(position).getContent());
        comment_date.setText(comment.get(position).getDate());

        comment_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(position);
            }

        });

        return view;
    }

    //삭제 버튼 클릭시 AlertDialog
    void show(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("댓글을 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext,"댓글을 삭제합니다",Toast.LENGTH_SHORT).show();
                        comment.remove(position);
                        notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext,"댓글 삭제를 취소합니다",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
}

