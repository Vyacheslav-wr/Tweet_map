package sample;
//import com.sun.javafx.geom.Path2D;
import BuisnessLogic.State;
import BuisnessLogic.TweetParse;
import BuisnessLogic.Tweet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
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
    private Pane anchor;
    @FXML
    private Canvas CavasMy;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext canvas = CavasMy.getGraphicsContext2D();
        MapCreator.CoordWriter(canvas);
        MapCreator.getPeopleDots(canvas);
    }
}
