package sample;

import BuisnessLogic.TweetParse;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

//Создаем шаблоны для отрисовки штатов
public class creator {
    //Словари для хранения мнений из твитов
    public static Dictionary o1;
    public static Dictionary o2;
    public static Dictionary o3;
    public static Dictionary o4;
    public static Dictionary o5;
    public static Dictionary o6;
    public static Dictionary o7;
    public static ArrayList<Canvas> DataBase(){

        ArrayList<Canvas> listcan = new ArrayList<>();
        //Создаем полотно для рисования
        Canvas CanvasMovie = new Canvas();
        CanvasMovie.setHeight(716);
        CanvasMovie.setWidth(1037);

        Canvas FamilyCanvas = new Canvas();
        FamilyCanvas.setHeight(716);
        FamilyCanvas.setWidth(1037);

        Canvas CaliCanvas = new Canvas();
        CaliCanvas.setHeight(716);
        CaliCanvas.setWidth(1037);

        Canvas ShoppingCanvas = new Canvas();
        ShoppingCanvas.setHeight(716);
        ShoppingCanvas.setWidth(1037);

        Canvas SnowCanvas = new Canvas();
        SnowCanvas.setHeight(716);
        SnowCanvas.setWidth(1037);

        Canvas FootballCanvas = new Canvas();
        FootballCanvas.setHeight(716);
        FootballCanvas.setWidth(1037);

        Canvas TexasCanvas = new Canvas();
        TexasCanvas.setHeight(716);
        TexasCanvas.setWidth(1037);

        //Изменение графичесокго контекста канваса
        GraphicsContext CaliGC = CaliCanvas.getGraphicsContext2D();
        MapCreator.st = "cali_tweets2014.txt";
        MapCreator.CoordWriter(CaliGC);
        MapCreator.getPeopleDots(CaliGC);
        o1 = TweetParse.o;
        listcan.add(CaliCanvas);

        GraphicsContext  SnowGC = SnowCanvas.getGraphicsContext2D();
        MapCreator.st = "snow_tweets2014.txt";
        MapCreator.CoordWriter(SnowGC);
        MapCreator.getPeopleDots(SnowGC);
        o2 = TweetParse.o;
        listcan.add(SnowCanvas);

        GraphicsContext MovieGC = CanvasMovie.getGraphicsContext2D();
        MapCreator.st = "movie_tweets2014.txt";
        MapCreator.CoordWriter(MovieGC);
        MapCreator.getPeopleDots(MovieGC);
        o3 = TweetParse.o;
        listcan.add(CanvasMovie);

        GraphicsContext FamilyGC = FamilyCanvas.getGraphicsContext2D();
        MapCreator.st = "family_tweets2014.txt";
        MapCreator.CoordWriter(FamilyGC);
        MapCreator.getPeopleDots(FamilyGC);
        o4 = TweetParse.o;
        listcan.add(FamilyCanvas);

        GraphicsContext ShoppingGC = ShoppingCanvas.getGraphicsContext2D();
        MapCreator.st = "shopping_tweets2014.txt";
        MapCreator.CoordWriter(ShoppingGC);
        MapCreator.getPeopleDots(ShoppingGC);
        o5 = TweetParse.o;
        listcan.add(ShoppingCanvas);

        GraphicsContext FootballGC = FootballCanvas.getGraphicsContext2D();
        MapCreator.st = "football_tweets2014.txt";
        MapCreator.CoordWriter(FootballGC);
        MapCreator.getPeopleDots(FootballGC);
        o6 = TweetParse.o;
        listcan.add(FootballCanvas);

        GraphicsContext TexasGC = TexasCanvas.getGraphicsContext2D();
        MapCreator.st = "texas_tweets2014.txt";
        MapCreator.CoordWriter(TexasGC);
        MapCreator.getPeopleDots(TexasGC);
        o7 = TweetParse.o;
        listcan.add(TexasCanvas);
        return listcan;
    }
}
