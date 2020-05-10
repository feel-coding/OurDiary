package sungshin.project.ourdiaryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FrdsearchAdapter extends BaseAdapter{

    Context mContext = null;
    ArrayList<FrdsearchItem> frdsearch;

    public FrdsearchAdapter(Context context, ArrayList<FrdsearchItem> data) {
        mContext = context;
        frdsearch = data;
    }

    @Override
    public int getCount() {
        return frdsearch.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdsearch.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdsearch_item, viewGroup,false);
        }
        TextView frdsearch_nick = (TextView)view.findViewById(R.id.frdsearch_nick);
        TextView frdsearch_name = view.findViewById(R.id.frdsearch_name);

        frdsearch_nick.setText(frdsearch.get(position).getNickname());
        frdsearch_name.setText("("+frdsearch.get(position).getName()+")");

        return view;
    }
}


