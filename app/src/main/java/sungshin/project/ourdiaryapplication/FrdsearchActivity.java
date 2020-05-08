package sungshin.project.ourdiaryapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class FrdsearchActivity extends AppCompatActivity {

    private ListView frdsearchList;
    FrdsearchAdapter frdsearchAdapter;
    ArrayList<FrdsearchItem> frdsearchItem;
    EditText editText_frdsearch;
    private ArrayList<FrdsearchItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdsearch);

        frdsearchList = findViewById(R.id.frdsearchList);
        frdsearchItem = new ArrayList<FrdsearchItem>();

        frdsearchItem.add(new FrdsearchItem("happy_eat","신용순"));
        frdsearchItem.add(new FrdsearchItem("silence99","김범"));

        //frdItem 데이터 arrayList에 복사
        arrayList = new ArrayList<FrdsearchItem>();
        arrayList.addAll(frdsearchItem);

        frdsearchAdapter = new FrdsearchAdapter(this,frdsearchItem);
        frdsearchList.setAdapter(frdsearchAdapter);


        //내용 입력시 이벤트
        editText_frdsearch = findViewById(R.id.editText_frdsearch);
        editText_frdsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editText_frdsearch.getText().toString();
                search(text);
            }
        });
    }

    //검색 수행
    public void search(String text) {
        //입력시 리스트 지우기
        frdsearchItem.clear();

        //입력 없을경우 데이터 모두 보여주기
        if(text.length()==0) {
            frdsearchItem.addAll(arrayList);
        }
        else {
            for(int i=0; i<arrayList.size(); i++) {
                //닉네임 찾아 일치하면 리스트에 추가
                if(arrayList.get(i).getNickname().toLowerCase().contains(text.toLowerCase())) {
                    frdsearchItem.add(arrayList.get(i));
                }
            }
        }
        frdsearchAdapter.notifyDataSetChanged();
    }
}
