package sungshin.project.ourdiaryapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import sungshin.project.ourdiaryapplication.R;

public class LockActivity extends AppCompatActivity {

    public final String SHARED_PREF_PASSWORD = "2000";
    StringBuilder inputPassword = new StringBuilder("");
    SharedPreferences sharedPref;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
        password = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
    }
    public void input(View v) {
        switch (v.getId()) {
            case R.id.lock_one:
                inputPassword.append("1");
                break;
            case R.id.lock_two:
                inputPassword.append("2");
                break;
            case R.id.lock_three:
                inputPassword.append("3");
                break;
            case R.id.lock_four:
                inputPassword.append("4");
                break;
            case R.id.lock_five:
                inputPassword.append("5");
                break;
            case R.id.lock_six:
                inputPassword.append("6");
                break;
            case R.id.lock_seven:
                inputPassword.append("7");
                break;
            case R.id.lock_eight:
                inputPassword.append("8");
                break;
            case R.id.lock_nine:
                inputPassword.append("9");
                break;
            case R.id.lock_zero:
                inputPassword.append("0");
                break;
            case R.id.lock_backspace:
                if(inputPassword.length() > 0) {
                    inputPassword.deleteCharAt(inputPassword.length() - 1);
                    if (inputPassword.length() == 0)
                        findViewById(R.id.lock_first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (inputPassword.length() == 1)
                        findViewById(R.id.lock_second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    else if (inputPassword.length() == 2)
                        findViewById(R.id.lock_third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                }
        }
        switch (inputPassword.length()) {
            case 1:
                findViewById(R.id.lock_first).setBackground(getDrawable(R.drawable.full_round));
                break;
            case 2:
                findViewById(R.id.lock_second).setBackground(getDrawable(R.drawable.full_round));
                break;
            case 3:
                findViewById(R.id.lock_third).setBackground(getDrawable(R.drawable.full_round));
                break;
            case 4:
                findViewById(R.id.lock_fourth).setBackground(getDrawable(R.drawable.full_round));
                if(inputPassword.toString().equals(password)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    findViewById(R.id.lock_first).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.lock_second).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.lock_third).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    findViewById(R.id.lock_fourth).setBackground(getDrawable(R.drawable.radio_button_unchecked));
                    inputPassword.delete(0, 3);
                }

        }
    }
}
