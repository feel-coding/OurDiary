package sungshin.project.ourdiaryapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MySettinggroupActivity extends AppCompatActivity {

    TextView group_withdrawal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settinggroup);

        group_withdrawal = findViewById(R.id.group_withdrawal);
        group_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MySettinggroupActivity.this);
                builder.setMessage("그룹장을 위임한 후 그룹 탈퇴해주세요");
                builder.setPositiveButton("그룹장 위임",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MySettinggroupActivity.this,"그룹장 위임 선택",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MySettinggroupActivity.this,"취소 선택",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });
    }
}
