package sungshin.project.ourdiaryapplication.friendlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;

public class LeaveGroupActivity extends AppCompatActivity {

    ListView leaveGroupLv;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_group);
        Toolbar toolbar = findViewById(R.id.leave_group_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.close_black);
        leaveGroupLv = findViewById(R.id.leaveGroupLv);
        list.add("그룹 탈퇴하기");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        leaveGroupLv.setAdapter(adapter);
        leaveGroupLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveGroupActivity.this);
                builder.setTitle("그룹 탈퇴")
                        .setMessage("그룹을 정말 탈퇴하시겠습니까?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(LeaveGroupActivity.this, "탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
