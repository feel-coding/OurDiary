package sungshin.project.ourdiaryapplication;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<FrdrequestItem> frdrequest;

    public MyAdapter(Context context, ArrayList<FrdrequestItem> data) {
        mContext = context;
        frdrequest = data;
    }

    @Override
    public int getCount() {
        return frdrequest.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdrequest.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdrequest_item, viewGroup,false);
        }
        TextView frdrequest_frd = (TextView)view.findViewById(R.id.frdrequest_frd);
        ImageButton frdrequest_btn = (ImageButton) view.findViewById(R.id.frdrequest_btn);

        frdrequest_frd.setText(frdrequest.get(position).getFriend());
        frdrequest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Click!",Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}
