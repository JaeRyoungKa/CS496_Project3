package com.project.cs496.weather_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

/**
 * Created by q on 2016-07-13.
 */
public class FetchTravelInfo extends AppCompatActivity {
    String curDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_date);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                curDate =String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayOfMonth);
            }
        });

        Button terminate = (Button)findViewById(R.id.return_);
        terminate.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TravelActivity.class);
                intent.putExtra("date",curDate);
                startActivity(intent);
                finish(); // 액티비티를 종료합니다.
                }
        });
    }
}
