package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.main.MainActivity;

public class AuthenticationActivity extends AppCompatActivity {

    Button completeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        completeBtn = findViewById(R.id.completeBtn);

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.this.finish();
            }
        });
    }
}
