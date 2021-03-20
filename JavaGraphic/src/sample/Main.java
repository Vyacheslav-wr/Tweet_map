package sample;

import BuisnessLogic.TweetParse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Чаво я наделал");
        primaryStage.sizeToScene();
        primaryStage.setScene(new Scene(root, 1037, 716));
        primaryStage.show();
        TweetParse.TweetParsing();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
