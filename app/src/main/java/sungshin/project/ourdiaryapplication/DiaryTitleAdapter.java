package sungshin.project.ourdiaryapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryTitleAdapter extends BaseAdapter {

    Context mContext = null;
    ArrayList<DiaryTitle> titles;
    int layout;

    public DiaryTitleAdapter(Context mContext, ArrayList<DiaryTitle> titles, int layout) {
        this.mContext = mContext;
        this.titles = titles;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.title_row, viewGroup,false);
        }
        TextView writer = view.findViewById(R.id.writer);
        TextView title = view.findViewById(R.id.title);
        writer.setText(titles.get(i).writer);
        title.setText(titles.get(i).title);
        return view;
    }
}
