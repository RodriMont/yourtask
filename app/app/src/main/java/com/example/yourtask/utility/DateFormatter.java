package com.example.yourtask.utility;

public final class DateFormatter
{
    public static enum DateFormat
    {
        SLASH,
        TICK
    }

    public static String format(DateFormat format, String date)
    {
        if (format == DateFormat.SLASH)
        {
            String[] dateSplit = date.split("-");

            if (dateSplit.length == 3)
                return String.format("%s/%s/%s", dateSplit[2], dateSplit[1], dateSplit[0]);
        }
        else if (format == DateFormat.TICK)
        {
            String[] dateSplit = date.split("/");

            if (dateSplit.length == 3)
                return String.format("%s-%s-%s", dateSplit[2], dateSplit[1], dateSplit[0]);
        }

        return "";
    }
}
