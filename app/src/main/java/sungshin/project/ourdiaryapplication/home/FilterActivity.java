package sungshin.project.ourdiaryapplication.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;

public class FilterActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;
    ArrayAdapter<String> adapter;
    ArrayList<String> friends = new ArrayList<>();
    int count = 0;
    Button filterApplyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        toolbar = findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.close_black);
        filterApplyBtn = findViewById(R.id.apply_filter_btn);
        friends.add("김효은");
        friends.add("박소영");
        friends.add("이주연");
        friends.add("윤여경");
        friends.add("박설");
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(this, R.style.AdapterTheme);
        adapter = new ArrayAdapter<>(themeWrapper, android.R.layout.simple_list_item_multiple_choice, friends);
        listView = findViewById(R.id.friendSelectLv);
        listView.setAdapter(adapter);
        filterApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayList<Integer> selectedPosition = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectedPosition.contains(i)) {
                    selectedPosition.remove((Integer)i);
                }
                else {
                    selectedPosition.add(i);
                }
                if(selectedPosition.size() > 0) {
                    filterApplyBtn.setVisibility(View.VISIBLE);
                }
                else {
                    filterApplyBtn.setVisibility(View.GONE);
                }
                Log.d("ctct", selectedPosition.size() + "개");
            }
        });
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b)
                    count++;
                else
                    count--;
                if(count > 0) {
                    filterApplyBtn.setVisibility(View.VISIBLE);
                    Log.d("ctct", "0보다 큼");
                }
                else {
                    filterApplyBtn.setVisibility(View.GONE);
                    Log.d("ctct", "0임");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual, menu);
                toolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                toolbar.setVisibility(View.VISIBLE);
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
