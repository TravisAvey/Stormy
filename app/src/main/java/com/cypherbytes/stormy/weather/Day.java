package com.cypherbytes.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Travis on 3/20/2015.
 */
public class Day implements Parcelable
{
    private long mTime;
    private String mSummary;
    private int mTempMax;
    private double mTempMin;
    private double mPrecipChance;
    private String mIcon;
    private String mTimezone;

    public long getTime()
    {
        return mTime;
    }

    public void setTime(long time)
    {
        mTime = time;
    }

    public String getSummary()
    {
        return mSummary;
    }

    public void setSummary(String summary)
    {
        mSummary = summary;
    }

    public int getTempMax()
    {
        return (int) Math.round(mTempMax);
    }

    public void setTempMax(int tempMax)
    {
        mTempMax = tempMax;
    }

    public double getTempMin()
    {
        return mTempMin;
    }

    public void setTempMin(double tempMin)
    {
        mTempMin = tempMin;
    }

    public double getPrecipChance()
    {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance)
    {
        mPrecipChance = precipChance;
    }

    public String getIcon()
    {
        return mIcon;
    }

    public void setIcon(String icon)
    {
        mIcon = icon;
    }

    public String getTimezone()
    {
        return mTimezone;
    }

    public void setTimezone(String timezone)
    {
        mTimezone = timezone;
    }

    public int getIconId()
    {
        return Forecast.getIconId(mIcon);
    }

    public String getDayOfWeek()
    {
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        format.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Date dateTime = new Date(mTime * 1000);
        return format.format(dateTime);
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeInt(mTempMax);
        dest.writeString(mIcon);
        dest.writeString(mTimezone);
    }

    private Day(Parcel in)
    {
        mTime = in.readLong();
        mSummary = in.readString();
        mTempMax = in.readInt();
        mIcon = in.readString();
        mTimezone = in.readString();
    }

    public Day() { }

    public static final Creator<Day> CREATOR = new Creator<Day>()
    {
        @Override
        public Day createFromParcel(Parcel source)
        {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size)
        {
            return new Day[size];
        }
    };
}
