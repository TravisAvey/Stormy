package com.cypherbytes.stormy;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by travis on 3/17/15.
 */
public class CurrentWeather
{
    private String mIcon;
    private long mTime;
    private double mTemp;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon()
    {
        return mIcon;
    }

    public void setIcon(String icon)
    {
        mIcon = icon;
    }

    public int getIconId()
    {
        int iconId = R.mipmap.clear_day;
        if(mIcon.equals("clear-day"))
        {
            iconId = R.mipmap.clear_day;
        } else if (mIcon.equals("clear-night"))
        {
            iconId = R.mipmap.clear_night;
        } else if (mIcon.equals("rain"))
        {
            iconId = R.mipmap.rain;
        } else if (mIcon.equals("snow"))
        {
            iconId = R.mipmap.snow;
        } else if (mIcon.equals("sleet"))
        {
            iconId = R.mipmap.sleet;
        } else if (mIcon.equals("wind"))
        {
            iconId = R.mipmap.wind;
        } else if (mIcon.equals("fog"))
        {
            iconId = R.mipmap.fog;
        } else if (mIcon.equals("partly-cloudy-day"))
        {
            iconId = R.mipmap.partly_cloudy;
        } else if (mIcon.equals("partly-cloudy-night"))
        {
            iconId = R.mipmap.cloudy_night;
        } else if (mIcon.equals("cloudy"))
        {
            iconId = R.mipmap.cloudy;
        }
        return iconId;
    }

    public long getTime()
    {
        return mTime;
    }

    public void setTime(long time)
    {
        mTime = time;
    }

    public String getFormattedTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        format.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = format.format(dateTime);
        return timeString;
    }

    public int setBackground()
    {
        int color = Color.parseColor("#fffc970b");
        if(mIcon.equals("clear-day"))
        {
            color = Color.parseColor("#FF00B5FC");
        } else if (mIcon.equals("clear-night"))
        {
            color = Color.parseColor("#FF030125");
        } else if (mIcon.equals("rain"))
        {
            color = Color.parseColor("#FF12FC2B");
        } else if (mIcon.equals("snow"))
        {
            color = Color.parseColor("#FFFC55D6");
        } else if (mIcon.equals("sleet"))
        {
            color = Color.parseColor("#FF401FFC");
        } else if (mIcon.equals("wind"))
        {
            color = Color.parseColor("#FF00FCE1");
        } else if (mIcon.equals("fog") || mIcon.equals("cloudy"))
        {
            color = Color.parseColor("#FF969696");
        } else if (mIcon.equals("partly-cloudy-day"))
        {
            color = Color.parseColor("#FF008486");
        } else if (mIcon.equals("partly-cloudy-night"))
        {
            color = Color.parseColor("#FF1F1E1F");
        }
        return color;
    }

    public int getTemp()
    {
        return (int)Math.round(mTemp);
    }

    public void setTemp(int temp)
    {
        mTemp = temp;
    }

    public double getHumidity()
    {
        return mHumidity;
    }

    public void setHumidity(double humidity)
    {
        mHumidity = humidity;
    }

    public int getPrecipChance()
    {
        double precipPercent = mPrecipChance * 100;
        return (int) Math.round(precipPercent);
    }

    public void setPrecipChance(double precipChance)
    {
        mPrecipChance = precipChance;
    }

    public String getSummary()
    {
        return mSummary;
    }

    public void setSummary(String summary)
    {
        mSummary = summary;
    }
}
