package sungshin.project.ourdiaryapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

public class DocwriteActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    Button docwrite_gallerybtn;
    private ImageView docwrite_imageview1, docwrite_imageview2, docwrite_imageview3, docwrite_imageview4, docwrite_imageview5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docwrite);
        docwrite_gallerybtn = findViewById(R.id.docwrite_gallerybtn);
        docwrite_imageview1 = findViewById(R.id.docwrite_imageview1);
        docwrite_imageview2 = findViewById(R.id.docwrite_imageview2);
        docwrite_imageview3 = findViewById(R.id.docwrite_imageview3);
        docwrite_imageview4 = findViewById(R.id.docwrite_imageview4);
        docwrite_imageview5 = findViewById(R.id.docwrite_imageview5);

        docwrite_gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                //갤러리에서 이미지 데이터 가져오기
                i.setDataAndType(MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI,"image/*");
              //  i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
              //  i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(i,GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null) {


            if (data.getClipData() == null) { // 멀티 선택을 지원하지 않는 기기
                //기존 이미지 지우기
                docwrite_imageview1.setImageResource(0);
                docwrite_imageview2.setImageResource(0);
                docwrite_imageview3.setImageResource(0);
                docwrite_imageview4.setImageResource(0);
                docwrite_imageview5.setImageResource(0);

                Log.d("정보", "111" + requestCode + " " + resultCode);
                //앨범에서 뒤로가기 누를 때 data 없음
                Uri uri = data.getData();
                docwrite_imageview1.setImageURI(uri);
             }
             else { //
              //  Log.d("정보",data.toString());
                Log.d("정보", "222" + requestCode + " " + resultCode);

                ClipData clipData = data.getClipData();

                 if (clipData != null) { // 여러개 선택
                    Log.d("정보",clipData.toString());
                    if (clipData.getItemCount() > 5) {
                        Toast.makeText(this, "사진은 5장까지 선택 가능합니다", Toast.LENGTH_LONG).show();
                    } else if (clipData.getItemCount() >= 1 && clipData.getItemCount() <= 5) {
                        //기존 이미지 지우기
                        docwrite_imageview1.setImageResource(0);
                        docwrite_imageview2.setImageResource(0);
                        docwrite_imageview3.setImageResource(0);
                        docwrite_imageview4.setImageResource(0);
                        docwrite_imageview5.setImageResource(0);

                       for (int i = 0; i < clipData.getItemCount(); i++) {
                           Uri uri1 = clipData.getItemAt(i).getUri();
                           Log.d("정보1", Integer.toString(i) + uri1.toString());

                           switch (i) {
                                case 0:
                                    docwrite_imageview1.setImageURI(uri1);
                                    break;
                                case 1:
                                    docwrite_imageview2.setImageURI(uri1);
                                    break;
                                case 2:
                                    docwrite_imageview3.setImageURI(uri1);
                                    break;
                                case 3:
                                    docwrite_imageview4.setImageURI(uri1);
                                    break;
                                case 4:
                                    docwrite_imageview5.setImageURI(uri1);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }

        }

    }
}
