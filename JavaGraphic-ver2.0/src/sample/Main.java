package sample;

import BuisnessLogic.Tweet;
import BuisnessLogic.TweetParse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application {
    double cenX = 518.5;
    double cenY = 358;
    double entX;
    double entY;
    double xOffset;
    double yOffset;
    final public double camereq = 100.0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("USA MAP");
        Scene scene = new Scene(root, 1037, 716, Color.TURQUOISE);
        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);
        scene.setOnMousePressed(pressEvent -> {
            scene.setOnMouseDragged(dragEvent -> {
                if(dragEvent.getX() < 1037 && dragEvent.getY() < 716 && dragEvent.getX() > 0 && dragEvent.getY() > 0){
                    camera.setTranslateX( pressEvent.getX() - dragEvent.getX());
                    camera.setTranslateY(pressEvent.getY() - dragEvent.getY());
                    System.out.println(dragEvent.getX());
                    System.out.println(dragEvent.getY());}
            });
        });
        scene.setOnScroll(evenet ->{
            if(evenet.getDeltaY() > 0.0  && camera.getTranslateZ() < 1000){
                camera.setTranslateZ(camera.getTranslateZ() + camereq);
            };
            if(evenet.getDeltaY() < 0.0 && camera.getTranslateZ() > 0) {
                camera.setTranslateZ(camera.getTranslateZ() - camereq);
            };
        });
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
