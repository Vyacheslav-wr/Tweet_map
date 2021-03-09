package com.company;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import com.google.gson.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Main
{
    // Твиты
    public static  ArrayList<Tweet> TweetRead(String filename)
    {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        // чтение из файла
        try
        {
            File file = new File("tweets//" + filename);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            while (true)
            {
                line = reader.readLine();
                if (line != null)
                {
                    Tweet tweet = TweetLineParse(line);
                    tweets.add(tweet);
                }
                else break;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return tweets;
    }

    public static Tweet TweetLineParse(String line)
    {
        int i = 1;

        StringBuilder sb = new StringBuilder();
        // [latitude,longitude]\t_\tyear-month-day hour:minute:second\ttext\n
        while (line.charAt(i) != ',')
        {
            sb.append(line.charAt(i));
            i++;
        }
        double latitude = Double.parseDouble(sb.toString());
        i+=2;

        sb = new StringBuilder();
        while (line.charAt(i) != ']')
        {
            sb.append(line.charAt(i));
            i++;
        }
        double longitude = Double.parseDouble(sb.toString());
        i+=4;

        int year = Integer.parseInt(line.substring(i, i+4));    i+=5;
        int month = Integer.parseInt(line.substring(i, i+2));   i+=3;
        int day = Integer.parseInt(line.substring(i, i+2));     i+=3;
        int hour = Integer.parseInt(line.substring(i, i+2));    i+=3;
        int minutes = Integer.parseInt(line.substring(i, i+2)); i+=3;
        int seconds = Integer.parseInt(line.substring(i, i+2)); i+=3;

        String text = line.substring(i, line.length());

        Date datetime = new Date();
        datetime.setYear(year - 1900);
        datetime.setMonth(month);
        datetime.setDate(day);
        datetime.setHours(hour);
        datetime.setMinutes(minutes);
        datetime.setSeconds(seconds);

        return new Tweet(latitude, longitude, datetime, text);
    }

    // Слова
    public static Dictionary WordsRead(String filename)
    {
        Dictionary d = new Hashtable();

        // чтение из файла
        try
        {
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            String word;
            double value;

            while (true)
            {
                line = reader.readLine();
                if (line != null)
                {
                    int i = 0;
                    StringBuilder sb = new StringBuilder();
                    while (line.charAt(i) != ',')
                    {
                        sb.append(line.charAt(i));
                        i++;
                    }
                    word = sb.toString();
                    value = Double.parseDouble(line.substring(i+1, line.length()));
                    d.put(word, value);
                }
                else break;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return d;
    }

    // объект Gson для парсинга json
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // штаты
    public static ArrayList<State> StatesRead (String filename)
    {
        // чтение из файла
        String line = "";
        try
        {
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            line = reader.readLine();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<State> states = new ArrayList<>();
        var h = GSON.fromJson(line, Hashtable.class);

        Enumeration<String> e = h.keys();

        // перебираем штаты
        while(e.hasMoreElements())
        {

            // название штата
            String name = e.nextElement();

            // полигоны штата
            var oldPolygons = (ArrayList) h.get(name);
            ArrayList<Path2D.Double> newPolygons = new ArrayList<>();

            if (oldPolygons.size() > 1) // если полигонов много
            {
                for (int i = 0; i < oldPolygons.size(); i++)
                {
                    var oldPolygon = (ArrayList)oldPolygons.get(i);
                    ArrayList<Point2D.Double> newPoints = new ArrayList<>();

                    for (int j = 0; j < oldPolygon.size(); j++)
                    {
                        var oldPoints = (ArrayList)oldPolygon.get(j);

                        for (int k = 0; k < oldPoints.size(); k++)
                        {
                            var oldPoint = (ArrayList)oldPoints.get(k);
                            Point2D.Double newPoint = new Point2D.Double((Double) oldPoint.get(0), (Double) oldPoint.get(1));
                            newPoints.add(newPoint);
                        }
                    }

                    Path2D.Double newPolygon = new Path2D.Double();

                    for (int q = 0; q < newPoints.size(); q++)
                    {
                        if (q == 0)
                            newPolygon.moveTo(newPoints.get(q).x, newPoints.get(q).y);
                        else
                            newPolygon.lineTo(newPoints.get(q).x, newPoints.get(q).y);
                    }
                    newPolygon.closePath();
                    newPolygons.add(newPolygon);
                }
            }
            else // если полигон 1
            {
                var oldPolygon = (ArrayList) oldPolygons.get(0);
                ArrayList<Point2D.Double> newPoints = new ArrayList<>();

                for (int i = 0; i < oldPolygon.size(); i++)
                {
                    var oldPoint = (ArrayList) oldPolygon.get(i);
                    Point2D.Double newPoint = new Point2D.Double((Double) oldPoint.get(0), (Double) oldPoint.get(1));
                    newPoints.add(newPoint);
                }

                Path2D.Double newPolygon = new Path2D.Double();

                for (int q = 0; q < newPoints.size(); q++)
                {
                    if (q == 0)
                        newPolygon.moveTo(newPoints.get(q).x, newPoints.get(q).y);
                    else
                        newPolygon.lineTo(newPoints.get(q).x, newPoints.get(q).y);
                }
                newPolygon.closePath();
                newPolygons.add(newPolygon);
            }
            State state = new State(name, newPolygons);
            states.add(state);
        }
        return states;
    }

    //
    public static Dictionary GetOpinion (ArrayList<Tweet> tweets, Dictionary sentiments, Dictionary statestweets)
    {
        double opinion = 0;
        Dictionary dic = new Hashtable();

        Enumeration<State> en = statestweets.keys();

        // перебираем штаты
        while(en.hasMoreElements())
        {
            opinion = 0;
            State s = en.nextElement();

            for (Tweet t:  (ArrayList<Tweet>)statestweets.get(s))
            {
                Enumeration<String> e = sentiments.keys();

                // перебираем слова
                while(e.hasMoreElements())
                {
                    String word = e.nextElement();

                    if (t.text.contains(word))
                        opinion += (Double)sentiments.get(word);
                }
            }
            dic.put(s.Name, opinion);
        }
        return dic;
    }

    // Штаты и соотв им твиты
    public static Dictionary StatesAndTweets (ArrayList<Tweet> tweets, ArrayList<State> states)
    {
        Dictionary f = new Hashtable();

        // перебираем штаты
        for (State s: states)
        {
            ArrayList<Tweet> T = new ArrayList<>();
            // перебираем полигоны штата
            for (Path2D.Double path: s.Polygons)
            {
                // перебираем твиты
                for (Tweet t: tweets)
                {
                    // если твит из этого штата
                    if (path.contains(t.longitude, t.latitude))
                    {
                        T.add(t);
                        System.out.println("OK");
                    }
                }
            }
            f.put(s, T);
        }
        return f;
    }

    public static void main(String[] args)
    {

        // Список твитов
        ArrayList<Tweet> tweets = TweetRead("cali_tweets2014.txt");

        // Словарь слов и весов <Word, Double>
        Dictionary sentiments = WordsRead("sentiments.csv");

        // Список штатов
        ArrayList<State> states = StatesRead("states.json");

        // Словарь штатов и твитов <State, ArrayList<Tweet>>
        Dictionary statestweets = StatesAndTweets(tweets, states);

        // Словарь имени штатов и мнения <String, Double>
        Dictionary o = GetOpinion(tweets, sentiments, statestweets);

        System.out.println("OK");
    }
}