package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.R;

public class FrdsearchActivity extends AppCompatActivity {

    private ListView frdsearchList;
    FrdsearchAdapter frdsearchAdapter;
    ArrayList<FrdSearchItem> searchResultList;
    EditText friendSearchEdit;
    private ArrayList<FrdSearchItem> arrayList;
    private ServerApi serverApi;
    String nickname;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdsearch);
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        frdsearchList = findViewById(R.id.frdsearchList);
        searchResultList = new ArrayList<FrdSearchItem>();

        //frdItem 데이터 arrayList에 복사
        arrayList = new ArrayList<FrdSearchItem>();
        arrayList.addAll(searchResultList);

        frdsearchAdapter = new FrdsearchAdapter(this, searchResultList);
        frdsearchList.setAdapter(frdsearchAdapter);


        //내용 입력시 이벤트
        friendSearchEdit = findViewById(R.id.editText_frdsearch);
        friendSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                serverApi.getAllUsers(1, 15, charSequence.toString()).enqueue(new Callback<List<EachUser>>() {
                    @Override
                    public void onResponse(Call<List<EachUser>> call, Response<List<EachUser>> response) {
                        if(response.isSuccessful()) {
                            Log.d("searchsuccess", "성공");
                            for (int i = 0; i < response.body().size(); i++) {
                                nickname = response.body().get(i).getNick();
                                name = response.body().get(i).getName();
                                if(nickname == null) {
                                    searchResultList.add(new FrdSearchItem("닉네임 없는 사용자", name));
                                }
                                else
                                    searchResultList.add(new FrdSearchItem(nickname, name));
                            }
                            frdsearchAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d("searcherror", "onResponse까지는 성공했지만 " + response.code() + " error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EachUser>> call, Throwable t) {
                        Log.d("searcherror", t.getMessage());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = friendSearchEdit.getText().toString();
                search(text);
            }
        });
    }

    //검색 수행
    public void search(String text) {
        //입력시 리스트 지우기
        searchResultList.clear();

        //입력 없을경우 데이터 모두 보여주기
        if(text.length()==0) {
            searchResultList.addAll(arrayList);
        }
        else {
            for(int i=0; i<arrayList.size(); i++) {
                //닉네임 찾아 일치하면 리스트에 추가
                if(arrayList.get(i).getNickname().toLowerCase().contains(text.toLowerCase())) {
                    searchResultList.add(arrayList.get(i));
                }
            }
        }
        frdsearchAdapter.notifyDataSetChanged();
    }
}
