package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FrdlistAcceptAdapter;

public class FrdlistActivity extends AppCompatActivity {

    private ListView frdrequest_list;
    FrdlistRequestAdapter frdlistRequestAdapter;
    ArrayList<FrdlistRequestItem> frdlist_requestItem;

    private ListView frdaccept_list;
    FrdlistAcceptAdapter frdlistAcceptAdapter;
    ArrayList<FrdlistAcceptItem> frdlist_acceptItem;

    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdlist);

        //친구 신청 listview
        frdrequest_list = (ListView)findViewById(R.id.frdrequest_list);
        frdlist_requestItem = new ArrayList<FrdlistRequestItem>();

        frdlist_requestItem.add(new FrdlistRequestItem("every1","박기범"));
        frdlist_requestItem.add(new FrdlistRequestItem("every2","원빈"));

        frdlistRequestAdapter = new FrdlistRequestAdapter(this,frdlist_requestItem);
        frdrequest_list.setAdapter(frdlistRequestAdapter);

        frdlistRequestAdapter.notifyDataSetChanged();

        //친구 요청 listview
        frdaccept_list = (ListView)findViewById(R.id.frdaccpet_list);
        frdlist_acceptItem = new ArrayList<FrdlistAcceptItem>();

        frdlist_acceptItem.add(new FrdlistAcceptItem("happy_eat","신용순"));
        frdlist_acceptItem.add(new FrdlistAcceptItem("silence99","김범"));

        frdlistAcceptAdapter = new FrdlistAcceptAdapter(this,frdlist_acceptItem);
        frdaccept_list.setAdapter(frdlistAcceptAdapter);

        frdlistAcceptAdapter.notifyDataSetChanged();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
