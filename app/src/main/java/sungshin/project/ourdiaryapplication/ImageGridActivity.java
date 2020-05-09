package sungshin.project.ourdiaryapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_img_item);

        //확대 이미지 보여주기
        ImageView imageView = (ImageView)findViewById(R.id.img_item);
        setImage(imageView);
        }
    public void setImage(ImageView imageView) {
        Intent i = getIntent();

        int imageID = (Integer)i.getExtras().get("imageID");
        imageView.setImageResource(imageID);
    }
}
