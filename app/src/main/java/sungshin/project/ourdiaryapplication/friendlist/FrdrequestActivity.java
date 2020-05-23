package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.friendlist.adapter.MyAdapter;
import sungshin.project.ourdiaryapplication.R;

public class FrdrequestActivity extends AppCompatActivity {

    private ListView frdList;
    MyAdapter myAdapter;
    ArrayList<FrdrequestItem> frdItem;
    EditText frdSearch;
    private ArrayList<FrdrequestItem> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdrequest);

        frdList = (ListView)findViewById(R.id.frdList);
        frdItem = new ArrayList<FrdrequestItem>();


        frdItem.add(new FrdrequestItem("Hong"));
        frdItem.add(new FrdrequestItem("Park"));
        frdItem.add(new FrdrequestItem("Gins"));
        frdItem.add(new FrdrequestItem("Geen"));

        //frdItem 데이터 arrayList에 복사
        arrayList = new ArrayList<FrdrequestItem>();
        arrayList.addAll(frdItem);


        myAdapter = new MyAdapter(this,frdItem);
        frdList.setAdapter(myAdapter);


        //내용 입력시 이벤트
        frdSearch = findViewById(R.id.editText);
        frdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = frdSearch.getText().toString();
                search(text);
            }
        });
    }

    //검색 수행
    public void search(String text) {
        //입력시 리스트 지우기
        frdItem.clear();

        //입력 없을경우 데이터 모두 보여주기
        if(text.length()==0) {
            frdItem.addAll(arrayList);
        }
        else {
            for(int i=0; i<arrayList.size(); i++) {
//                Log.d("text",arrayList.get(i).getFriend()+text);
                if(arrayList.get(i).getFriend().toLowerCase().contains(text.toLowerCase())) {
                    frdItem.add(arrayList.get(i));

                }
            }
        }
        myAdapter.notifyDataSetChanged();
    }
}
