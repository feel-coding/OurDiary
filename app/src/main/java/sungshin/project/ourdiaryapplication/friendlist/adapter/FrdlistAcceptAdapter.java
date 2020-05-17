package sungshin.project.ourdiaryapplication.friendlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.friendlist.FrdlistAcceptItem;
import sungshin.project.ourdiaryapplication.R;

public class FrdlistAcceptAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<FrdlistAcceptItem> frdlist_accept;

    public FrdlistAcceptAdapter(Context context, ArrayList<FrdlistAcceptItem> data) {
        mContext = context;
        frdlist_accept = data;
    }

    @Override
    public int getCount() {
        return frdlist_accept.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdlist_accept.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdlist_accept_item, viewGroup,false);
        }
        TextView frdlist_accept_nick = (TextView)view.findViewById(R.id.frdlist_accept_nick);
        TextView frdlist_accept_name = view.findViewById(R.id.frdlist_accept_name);
        Button frdlist_accept_btn1 = view.findViewById(R.id.frdlist_accept_btn1);
        Button frdlist_accept_btn2 = view.findViewById(R.id.frdlist_accept_btn2);

        frdlist_accept_nick.setText(frdlist_accept.get(position).getNickname());
        frdlist_accept_name.setText("("+frdlist_accept.get(position).getName()+")");

        frdlist_accept_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"수락 Click!",Toast.LENGTH_SHORT).show();
            }

        });
        frdlist_accept_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"거절 Click!",Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}