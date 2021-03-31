package sample;

import BuisnessLogic.State;
import BuisnessLogic.Tweet;
import BuisnessLogic.TweetParse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;



public class MapCreator {
    //имя файла, который будет отправлен на прасинг
    static String st= "cali_tweets2014.txt";
    public static void CoordWriter(GraphicsContext canvas){
        TweetParse.TweetParsing(st);
        //Лист для сохранения изменных ккординат штатов на плосике
        ArrayList<State> states = new ArrayList<>();
        canvas.clearRect(0, 0, 1037, 716);
        canvas.setStroke(Color.BLACK);
        double[] coords = new double[2];
        for (State sta: TweetParse.states)
        {
            ArrayList<Path2D.Double> newPolygons = new ArrayList<>();
            Path2D.Double newPolygon = new Path2D.Double();
            changeStateColor((double)TweetParse.o.get(sta.Name), canvas);
            for(Path2D pt: sta.Polygons) {
                PathIterator pi = pt.getPathIterator(null, 1.0);
                pi.currentSegment(coords);
                coords = getFlatCoordinates(coords, canvas);
                canvas.beginPath();
                if(sta.Name.equals("PR")) {
                    canvas.moveTo(coords[0] - 200, coords[1]);
                    newPolygon.moveTo(coords[0] - 200, coords[1]);
                }
                else if(sta.Name.equals("AK")) {
                    canvas.moveTo(coords[0]*0.4 +400, coords[1]*0.4 + 600);
                    newPolygon.moveTo(coords[0]*0.4 +400, coords[1]*0.4 + 600);
                }
                else if(sta.Name.equals("HI")) {
                    canvas.moveTo(coords[0]+1100, coords[1]);
                    newPolygon.moveTo(coords[0] + 1100, coords[1]);
                    }
                else{
                    canvas.moveTo(coords[0], coords[1]);
                    newPolygon.moveTo(coords[0], coords[1]);
                }
                while (!pi.isDone()) {
                    pi.currentSegment(coords);
                    pi.next();
                    if (!pi.isDone()) {
                        coords = getFlatCoordinates(coords, canvas);
                        if(sta.Name.equals("AK")) {
                            canvas.lineTo(coords[0]*0.4 +400, coords[1]*0.4 + 600);
                            newPolygon.lineTo(coords[0]*0.4 +400, coords[1]*0.4 + 600);
                        }
                       else if(sta.Name.equals("PR")) {
                           canvas.lineTo(coords[0]-200, coords[1]);
                            newPolygon.lineTo(coords[0] - 200, coords[1]);
                        }
                        else if(sta.Name.equals("HI")) {
                            canvas.lineTo(coords[0]+1100, coords[1]);
                            newPolygon.lineTo(coords[0] + 1100, coords[1]);
                        }
                        else{
                            canvas.lineTo(coords[0], coords[1]);
                            newPolygon.lineTo(coords[0], coords[1]);
                        }
                    }
                }
                newPolygon.closePath();
                canvas.closePath();
                canvas.stroke();
                canvas.fill();
            }
            newPolygons.add(newPolygon);
            State state = new State(sta.Name, newPolygons);
            states.add(state);

        }
        TweetParse.ChangeStates = states;
        System.out.println();
    }
    //Класс, который добовляет твиты на карту
    public static void getPeopleDots(GraphicsContext canvas){
        canvas.setFill(Color.RED);
        canvas.setStroke(Color.BLACK);
        double[] mas;
        for(Tweet tw: TweetParse.tweets){
            mas = getFlatCoordinates(new double[]{tw.longitude, tw.latitude}, canvas);
            double h = tw.GetWeight(TweetParse.sentiments);
            changeDotColor(h, canvas);
            canvas.fillOval(mas[0], mas[1],5,5);
            canvas.strokeOval(mas[0], mas[1],5,5);
        }
    }

    //Класс илспользует проекцию Мерканта для получение плоских координат
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
        mas[0] = x*4 -930;
        return mas;
    }

    //Класс меняющий цвет твита в зависимости от мнения
    public static void changeDotColor(double op, GraphicsContext canvas){
        canvas.setFill(Color.BURLYWOOD);
        if(op < 0 && op >= -10){
            canvas.setFill(Color.LIGHTYELLOW);
        }
        if(op < -10 && op >= -20){
            canvas.setFill(Color.YELLOW);
        }
        if(op < -20){
            canvas.setFill(Color.GOLDENROD);
        }
        if(op > 0 && op < 10){
            canvas.setFill(Color.BROWN);
        }
        if(op >= 10 && op < 20){
            canvas.setFill(Color.GRAY);
        }
        if(op >= 20 ){
            canvas.setFill(Color.FLORALWHITE);
        }
        if(op == 0){
            canvas.setFill(Color.BLACK);
        }
    }

    //Класс меняющий цвет штата в зависимости от мнения
    public static void changeStateColor(double op, GraphicsContext canvas){
        canvas.setFill(Color.BURLYWOOD);
        if(op < 0 && op >= -10){
            canvas.setFill(Color.DARKRED);
        }
        if(op < -10 && op >= -20){
            canvas.setFill(Color.RED);
        }
        if(op < -20){
            canvas.setFill(Color.LIGHTCORAL);
        }
        if(op > 0 && op < 10){
            canvas.setFill(Color.DARKGREEN);
        }
        if(op >= 10 && op < 20){
            canvas.setFill(Color.GREEN);
        }
        if(op >= 20 ){
            canvas.setFill(Color.LIGHTGREEN);
        }
        if(op == 0){
            canvas.setFill(Color.GRAY);
        }
    }
}
