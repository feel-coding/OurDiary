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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import sungshin.project.ourdiaryapplication.DiaryTitle;
import sungshin.project.ourdiaryapplication.DiaryTitleAdapter;
import sungshin.project.ourdiaryapplication.EventDecorator;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.SaturdayDecorator;
import sungshin.project.ourdiaryapplication.SundayDecorator;
import sungshin.project.ourdiaryapplication.oneDayDecorator;


public class CalendarFragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    ListView titleListView;
    DiaryTitleAdapter adapter;
    ArrayList<DiaryTitle> titleList;
    Context mContext;
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        materialCalendarView = view.findViewById(R.id.calendarView);
        titleListView = view.findViewById(R.id.titleList);
        titleList = new ArrayList<>();
        titleList.add(new DiaryTitle("김효은", "프로젝트 수업 팀플한 날"));
        titleList.add(new DiaryTitle("박설", "효은이와 떡볶이 먹은 날"));
        titleList.add(new DiaryTitle("김효은", "집 가다가 버스 놓침--"));
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

        String[] result = {"2020.3.5","2020.4.25","2020.4.27","0.0.0"};
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