package com.cypherbytes.stormy.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.cypherbytes.stormy.R;
import com.cypherbytes.stormy.adapters.DayAdapter;
import com.cypherbytes.stormy.weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity
{
    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parelables, parelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        String dayOfTheWeek = mDays[position].getDayOfWeek();
        String conditions = mDays[position].getSummary();
        String highTem = mDays[position].getTempMax() + "";
        String message = String.format("On %s the high will be %s and it will be %s", dayOfTheWeek, conditions, highTem);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
