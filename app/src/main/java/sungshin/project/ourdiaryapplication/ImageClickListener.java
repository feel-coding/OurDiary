package sungshin.project.ourdiaryapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public class ImageClickListener implements View.OnClickListener {
    Context context;

    int imageID;

    public ImageClickListener(Context context, int imageID) {
        this.context = context;
        this.imageID = imageID;
    }

    public void onClick(View v) {
        Intent i = new Intent(context, ImageGridActivity.class);
        i.putExtra("imageID", imageID);
        Log.d("정보",imageID+"");
        context.startActivity(i);
    }
}
