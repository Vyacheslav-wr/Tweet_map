package sample;
//import com.sun.javafx.geom.Path2D;
import BuisnessLogic.State;
import BuisnessLogic.TweetParse;
import BuisnessLogic.Tweet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.ResourceBundle;

public class Controller<states> implements Initializable {
    @FXML
    private Pane contr;
    @FXML
    private Canvas CavasMy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CoordWriter();
        getPeopleDots();
    }
    public void CoordWriter(){
        GraphicsContext canvas = CavasMy.getGraphicsContext2D();
        canvas.setStroke(Color.BLACK);
        ArrayList<State> states = BuisnessLogic.TweetParse.StatesRead("states.json");
        Dictionary o = TweetParse.GetOpinion(TweetParse.TweetRead("cali_tweets2014.txt"), TweetParse.WordsRead("sentiments.csv"), TweetParse.StatesAndTweets(TweetParse.TweetRead("cali_tweets2014.txt"), TweetParse.StatesRead("states.json")));
        boolean flag = true;
        double[] coords = new double[2];
        for (State st: states)
        {
            changeStateColor((double)o.get(st.Name));
            for(Path2D pt: st.Polygons){
                PathIterator pi  = pt.getPathIterator(null ,1.0);
                pi.currentSegment(coords);
                coords = getFlatCoordinates(coords);
                canvas.beginPath();
                canvas.moveTo(coords[0], coords[1]);
                while(!pi.isDone() ){
                    pi.currentSegment(coords);
                    pi.next();
                    if(!pi.isDone()){ coords = getFlatCoordinates(coords);
                    canvas.lineTo(coords[0],coords[1]);}
                }
                canvas.closePath();
                canvas.stroke();
                canvas.fill();
            }
        }
    }
    public void getPeopleDots(){
        GraphicsContext canvas = CavasMy.getGraphicsContext2D();
        canvas.setFill(Color.RED);
        canvas.setStroke(Color.BLACK);
        double[] mas;
        ArrayList<Tweet> tweets = TweetParse.TweetRead("cali_tweets2014.txt");
        for(Tweet tw: tweets){
            mas = getFlatCoordinates(new double[]{tw.longitude, tw.latitude});
            canvas.fillOval(mas[0], mas[1],5,5);
            canvas.strokeOval(mas[0], mas[1],5,5);
        }
    }

    public double[] getFlatCoordinates(double[] mas){
        GraphicsContext canvas = CavasMy.getGraphicsContext2D();
        double latitude    = mas[1];
        double longitude   = mas[0];
        double mapWidth    = canvas.getCanvas().getWidth();
        double mapHeight   = canvas.getCanvas().getHeight();
        double x = (longitude+180)*(mapWidth/240);
        double latRad = latitude*Math.PI/180;
        double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
        double y = (mapHeight/2)-(mapWidth*mercN/(2*Math.PI)) ;
        mas[1] = y*1.8;
        mas[0] = x*1.8;
        return mas;
    }

    public void changeStateColor(double op){
        GraphicsContext canvas = CavasMy.getGraphicsContext2D();
        Dictionary o = TweetParse.GetOpinion(TweetParse.TweetRead("cali_tweets2014.txt"), TweetParse.WordsRead("sentiments.csv"), TweetParse.StatesAndTweets(TweetParse.TweetRead("cali_tweets2014.txt"), TweetParse.StatesRead("states.json")));
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
        if(op < min + var){
            canvas.setFill(Color.CADETBLUE);
        }
        if(op >= min + var && op < min + 2*var){
            canvas.setFill(Color.CORAL);
        }
        if(op >= min + 2*var && op < min + 3*var && op != 0.0){
            canvas.setFill(Color.DARKCYAN);
        }
        if(op >= min + 3*var && op < min + 4*var){
            canvas.setFill(Color.DARKMAGENTA);
        }
        if(op >= min +4*var && op < min + 5*var){
            canvas.setFill(Color.INDIGO);
        }
        if(op >= min +5*var && op < min + 6*var){
            canvas.setFill(Color.GOLDENROD);
        }
        if(op >= min+6*var && op < min + 7*var){
            canvas.setFill(Color.LIGHTSTEELBLUE);
        }
        if(op > min+ 7*var){
            canvas.setFill(Color.LIMEGREEN);
        }
    }
}
