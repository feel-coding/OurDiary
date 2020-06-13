package sungshin.project.ourdiaryapplication.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.DiaryTitle;
import sungshin.project.ourdiaryapplication.DiaryTitleAdapter;
import sungshin.project.ourdiaryapplication.EventDecorator;
import sungshin.project.ourdiaryapplication.Network.Diary;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.R;


public class CalendarFragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    ListView titleListView;
    DiaryTitleAdapter adapter;
    ArrayList<DiaryTitle> titleList = new ArrayList<>();
    Context mContext;
    Activity activity;
    ServerApi serverApi;
    ArrayList<String> diaryExistDates = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        serverApi = RetrofitManager.getInstance().getServerApi(activity);
        materialCalendarView = view.findViewById(R.id.calendarView);
        titleListView = view.findViewById(R.id.titleList);
//        titleList.add(new DiaryTitle("김효은", "프로젝트 수업 팀플한 날"));
//        titleList.add(new DiaryTitle("박설", "효은이와 떡볶이 먹은 날"));
//        titleList.add(new DiaryTitle("김효은", "집 가다가 버스 놓침--"));
        adapter = new DiaryTitleAdapter(view.getContext(), titleList, R.layout.title_row);
        titleListView.setAdapter(adapter);

        materialCalendarView = view.findViewById(R.id.calendarView);

        //달력 시작과 끝
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017,0,1))
                .setMaximumDate(CalendarDay.from(2030,12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //달력 Decorator
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new oneDayDecorator());
        serverApi.getDiaries("ALL").enqueue(new Callback<List<Diary>>() {
            @Override
            public void onResponse(Call<List<Diary>> call, Response<List<Diary>> response) {
                if (response.body() != null) {
                    for (Diary d : response.body()) {
                        if (d.getWantedDate() != null) {
                            Date date = d.getWantedDate();
                            diaryExistDates.add(date.getYear() + "." + date.getMonth() + 1 + "." + date.getDate());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Diary>> call, Throwable t) {

            }
        });
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                titleList.clear();
                int month = date.getMonth() + 1;
                int year = date.getYear();
                int day = date.getDay();
                Log.d("calendardate", year + "년 " + month + "월 " + day + "일");
                serverApi.getDiaries("ALL").enqueue(new Callback<List<Diary>>() {
                    @Override
                    public void onResponse(Call<List<Diary>> call, Response<List<Diary>> response) {
                        List<Diary> diaries = response.body();
                        for (Diary d : diaries) {
                            if(d.getWantedDate() != null && d.getWantedDate().getYear() == year && d.getWantedDate().getMonth() + 1 == month && d.getWantedDate().getDate() == day) {
                                titleList.add(new DiaryTitle(d.getUser().getName() + " (" + d.getUser().getNick() + ")", d.getTitle()));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Diary>> call, Throwable t) {

                    }
                });
            }
        });
        String[] result = diaryExistDates.toArray(new String[diaryExistDates.size()]);//{"2020.3.5","2020.4.25","2020.4.27","0.0.0"};
        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        return view;
    }

    //날짜 위에 점찍기
    public class ApiSimulator extends AsyncTask<Void,Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result) {
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(Void... voids) {
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            //특정 날짜 split 후 dates로 return
            for(int i = 0; i < Time_Result.length; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split("\\.");
                Log.d("정보", time[0]+time[1]+time[2]);

                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                dates.add(day);
                calendar.set(year,month-1,dayy);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if(activity.isFinishing()) {
                return;
            }

            //점찍기
            calendarDays.remove(CalendarDay.today());
            materialCalendarView.addDecorator(new EventDecorator(Color.RED,
                    calendarDays, getActivity()));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }

}