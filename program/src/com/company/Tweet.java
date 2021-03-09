package com.company;

import java.util.Date;

public class Tweet
{
    Tweet (double Latitude, double Longitude, Date Datetime, String Text)
    {
        latitude = Latitude;
        longitude = Longitude;
        datetime = Datetime;
        text = Text;
    }
    // широта (x)
    double latitude;
    // долгота (y)
    double longitude;
    // дата и время
    Date datetime;
    // текст
    String text;

    public String toString()
    {
        return String.format("Latitude: " + latitude + "\nLongitude: " + longitude + "\nDate: " + datetime.toString() + "\nText: " + text.replace('%',' '));
    }
}
