package sample;

import BuisnessLogic.State;
import BuisnessLogic.Tweet;
import BuisnessLogic.TweetParse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Dictionary;



public class MapCreator {
    static String st= "cali_tweets2014.txt";
    public static void CoordWriter(GraphicsContext canvas){
        canvas.setStroke(Color.BLACK);
        ArrayList<State> states = BuisnessLogic.TweetParse.StatesRead("states.json");
        Dictionary o = TweetParse.GetOpinion(TweetParse.TweetRead(st), TweetParse.WordsRead("sentiments.csv"), TweetParse.StatesAndTweets(TweetParse.TweetRead(st), TweetParse.StatesRead("states.json")));
        boolean flag = true;
        double[] coords = new double[2];
        for (State st: states)
        {
            changeStateColor((double)o.get(st.Name), canvas);
            for(Path2D pt: st.Polygons) {
                PathIterator pi = pt.getPathIterator(null, 1.0);
                pi.currentSegment(coords);
                coords = getFlatCoordinates(coords, canvas);
                canvas.beginPath();
                if(st.Name.equals("AK")) {
                    canvas.moveTo(coords[0]*0.4 +400, coords[1]*0.4 + 600);
                }
                else{
                    canvas.moveTo(coords[0], coords[1]);
                }
                while (!pi.isDone()) {
                    pi.currentSegment(coords);
                    pi.next();
                    if (!pi.isDone()) {
                        coords = getFlatCoordinates(coords, canvas);
                        if(st.Name.equals("AK")) {
                            canvas.lineTo(coords[0]*0.4 +400, coords[1]*0.4 + 600);
                        }
                        else{
                            canvas.lineTo(coords[0], coords[1]);
                        }
                    }
                }
                canvas.closePath();
                canvas.stroke();
                canvas.fill();
            }
        }
    }
    public static void getPeopleDots(GraphicsContext canvas){
        canvas.setFill(Color.RED);
        canvas.setStroke(Color.BLACK);
        double[] mas;
        ArrayList<Tweet> tweets = TweetParse.TweetRead(st);
        for(Tweet tw: tweets){
            mas = getFlatCoordinates(new double[]{tw.longitude, tw.latitude}, canvas);
            canvas.fillOval(mas[0], mas[1],5,5);
            canvas.strokeOval(mas[0], mas[1],5,5);
        }
    }

    public static double[] getFlatCoordinates(double[] mas, GraphicsContext canvas){
        double latitude    = mas[1];
        double longitude   = mas[0];
        double mapWidth    = canvas.getCanvas().getWidth();
        double mapHeight   = canvas.getCanvas().getHeight();
        double x = (longitude+180)*(mapWidth/240);
        double latRad = latitude*Math.PI/180;
        double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
        double y = (mapHeight/2)-(mapWidth*mercN/(2*Math.PI)) ;
        mas[1] = y*4 -650;
        mas[0] = x*4 -910;
        return mas;
    }

    public static void changeStateColor(double op, GraphicsContext canvas){
        Dictionary o = TweetParse.GetOpinion(TweetParse.TweetRead(st), TweetParse.WordsRead("sentiments.csv"), TweetParse.StatesAndTweets(TweetParse.TweetRead(st), TweetParse.StatesRead("states.json")));
        ArrayList<State> states = BuisnessLogic.TweetParse.StatesRead("states.json");
        double min = 0;
        double max = 0;
        double var;
        for (State s:
                states)
        {
            for (int i = 0; i < s.Polygons.size(); i++)
            {
                if(max < (double)o.get(s.Name)){
                    max = (double)o.get(s.Name);
                }
                if(min > (double)o.get(s.Name)){
                    min = (double)o.get(s.Name);
                }
            }
        }
        var  = Math.abs((min - max))/8;
        canvas.setFill(Color.BURLYWOOD);
        if(op < min + var && op != 0.0){
            canvas.setFill(Color.CADETBLUE);
        }
        if(op >= min + var && op < min + 2*var && op != 0.0){
            canvas.setFill(Color.CORAL);
        }
        if(op >= min + 2*var && op < min + 3*var && op != 0.0){
            canvas.setFill(Color.DARKCYAN);
        }
        if(op >= min + 3*var && op < min + 4*var && op != 0.0){
            canvas.setFill(Color.DARKMAGENTA);
        }
        if(op >= min +4*var && op < min + 5*var && op != 0.0){
            canvas.setFill(Color.INDIGO);
        }
        if(op >= min +5*var && op < min + 6*var && op != 0.0){
            canvas.setFill(Color.GOLDENROD);
        }
        if(op >= min+6*var && op < min + 7*var && op != 0.0){
            canvas.setFill(Color.LIGHTSTEELBLUE);
        }
        if(op > min+ 7*var && op != 0.0){
            canvas.setFill(Color.LIMEGREEN);
        }
    }
}
