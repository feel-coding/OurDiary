package sungshin.project.ourdiaryapplication.friendlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;

public class FrdlistRequestAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<FrdlistRequestItem> frdlist_request;

    public FrdlistRequestAdapter(Context context, ArrayList<FrdlistRequestItem> data) {
        mContext = context;
        frdlist_request = data;
    }

    @Override
    public int getCount() {
        return frdlist_request.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdlist_request.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdlist_request_item, viewGroup,false);
        }
        TextView frdlist_request_nick = (TextView)view.findViewById(R.id.frdlist_request_nick);
        TextView frdlist_request_name = view.findViewById(R.id.frdlist_request_name);

        frdlist_request_nick.setText(frdlist_request.get(position).getNickname());
        frdlist_request_name.setText("("+frdlist_request.get(position).getName()+")");

        return view;
    }
}
