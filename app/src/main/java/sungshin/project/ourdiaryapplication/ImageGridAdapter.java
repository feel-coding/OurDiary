package sungshin.project.ourdiaryapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridAdapter extends BaseAdapter {

    Context context = null;
    int[] imageIDs = null;

    public ImageGridAdapter(Context context, int[] imageIDs) {
        this.context = context;
        this.imageIDs = imageIDs;
    }

    @Override
    public int getCount() {
        return (null!=imageIDs) ? imageIDs.length:0;
    }

    @Override
    public Object getItem(int i) {
        return (null!=imageIDs) ? imageIDs[i]:0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        if(convertView != null)
            imageView = (ImageView)convertView;
        else {
            //이미지 크기(줄여 메모리 부족 방지)
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bitmap = Bitmap.createScaledBitmap(bitmap,320,240,false);

            //GridView 구성 이미지뷰 정의
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(bitmap);

            ImageClickListener imageClickListener = new ImageClickListener(context, imageIDs[position]);
            imageView.setOnClickListener(imageClickListener);
        }
        return imageView;
    }
}
