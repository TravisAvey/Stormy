package com.cypherbytes.stormy.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

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
}
