package com.cypherbytes.stormy.weather;

/**
 * Created by Travis on 3/20/2015.
 */
public class Day
{
    private long mTime;
    private String mSummary;
    private double mTempMax;
    private double mTempMin;
    private double mPrecipChance;

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

    public double getTempMax()
    {
        return mTempMax;
    }

    public void setTempMax(double tempMax)
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

    private String mIcon;
    private String mTimezone;
}
