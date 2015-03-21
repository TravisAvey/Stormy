package com.cypherbytes.stormy.UI;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cypherbytes.stormy.R;
import com.cypherbytes.stormy.weather.Current;
import com.cypherbytes.stormy.weather.Day;
import com.cypherbytes.stormy.weather.Forecast;
import com.cypherbytes.stormy.weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity
{

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";

    private Forecast mForecast;

    @InjectView(R.id.tempLabel) TextView mTempLabel;
    @InjectView(R.id.timeLable) TextView mTimeLabel;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.precipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.relativeLayout) RelativeLayout mRelativeLayout;
    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 36.1667;
        final double longitude = -86.7833;

        mRefreshView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getForecast(latitude, longitude);
            }
        });

        getForecast(latitude, longitude);

    }

    private void getForecast(double latitude, double longitude)
    {
        String apiKey = "1c6ef2a3777dde8b40dc0b4260e3d553";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey + "/" + latitude + "," + longitude;

        if (isNetworkAvailable())
        {

            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback()
            {
                @Override
                public void onFailure(Request request, IOException e)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful())
                        {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            alertNetworkError();
        }
    }

    private void toggleRefresh()
    {
        if(mProgressBar.getVisibility() == View.INVISIBLE)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshView.setVisibility(View.VISIBLE);
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateDisplay()
    {
        Current current = mForecast.getCurrent();
        mTempLabel.setText(current.getTemp() + "");
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());
        Drawable icon = getResources().getDrawable(current.getIconId());
        //mRelativeLayout.setBackgroundColor(current.setBackground());

        String bgIcon = current.getIcon();

        switch (bgIcon)
        {
            case("clear-day"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_sunny));
                break;
            case("clear-night"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_night));
                break;
            case("rain"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_rainy));
                break;
            case("snow"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_snow));
                break;
            case("sleet"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_snow));
                break;
            case("fog"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_fog));
                break;
            case("cloudy"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_fog));
                break;
            case("partly-cloudy-night"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_night));
                break;
            case("overcast"):
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_fog));
                break;
            default:
                mRelativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_sunny));
        }
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException
    {
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException
    {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i=0;i<data.length();i++)
        {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTempMax(jsonDay.getInt("temperatureMax"));
            day.setTempMin(jsonDay.getDouble("temperatureMin"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }

        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException
    {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for (int i=0;i<data.length();i++)
        {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setPrecipChance(jsonHour.getDouble("precipProbability"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }
        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException
    {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject current = forecast.getJSONObject("currently");

        Current currentWeather = new Current();
        currentWeather.setHumidity(current.getDouble("humidity"));
        currentWeather.setTemp(current.getInt("temperature"));
        currentWeather.setTime(current.getLong("time"));
        currentWeather.setPrecipChance(current.getDouble("precipProbability"));
        currentWeather.setSummary(current.getString("summary"));
        currentWeather.setIcon(current.getString("icon"));
        currentWeather.setTimeZone(timezone);

        return currentWeather;
    }

    private void alertNetworkError()
    {
        NetworkDialogFragment dialog = new NetworkDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError()
    {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view)
    {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }
}
