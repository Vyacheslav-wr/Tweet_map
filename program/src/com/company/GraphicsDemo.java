package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Dictionary;

public class GraphicsDemo extends JPanel
{
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        // Список твитов
        ArrayList<Tweet> tweets = Main.TweetRead("cali_tweets2014.txt");

        // Словарь слов и весов <Word, Double>
        Dictionary sentiments = Main.WordsRead("sentiments.csv");

        // Список штатов
        ArrayList<State> states2 = Main.StatesRead2("states.json");
        ArrayList<State> states = Main.StatesRead("states.json");


        // Словарь штатов и твитов <State, ArrayList<Tweet>>
        Dictionary statestweets = Main.StatesAndTweets(tweets, states);

        // Словарь имени штатов и мнения <String, Double>
        Dictionary o = Main.GetOpinion(tweets, sentiments, statestweets);

        g2d.setColor(Color.black);
        double opp = 0;
        for (State s:
                states2)
        {
            for (int i = 0; i < s.Polygons.size(); i++)
            {
                opp += (double)o.get(s.Name);
            }
        }
        opp = opp / 52.0;

        final double Q = opp;
        for (State s:
             states2)
        {
            for (int i = 0; i < s.Polygons.size(); i++)
            {
                double op = (double)o.get(s.Name);
                if (op > Q) g2d.setColor(Color.GREEN);
                else if (op < -Q) g2d.setColor(Color.RED);
                else g2d.setColor(Color.GRAY);
                g2d.draw(s.Polygons.get(i));
                g2d.fill(s.Polygons.get(i));
            }
        }


        System.out.println("OK");




    }
}
