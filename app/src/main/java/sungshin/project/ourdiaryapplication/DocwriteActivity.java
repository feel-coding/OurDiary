package sungshin.project.ourdiaryapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

public class DocwriteActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    Button docwrite_gallerybtn;
    private ImageView docwrite_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docwrite);
        docwrite_gallerybtn = findViewById(R.id.docwrite_gallerybtn);
        docwrite_imageview = findViewById(R.id.docwrite_imageview);
        docwrite_gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                //갤러리에서 이미지 데이터 가져오기
                i.setDataAndType(MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(i,GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK
        && data != null && data.getData() != null) {
            //이미지 url 가져오기
            Uri selectedImageUri = data.getData();
            docwrite_imageview.setImageURI(selectedImageUri);

        }
    }
}
