package sample;

import BuisnessLogic.TweetParse;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Main extends Application {
    //Величина смещения камеры
    final public double camereq = 100.0;
    //Варинаты выбора combobox
    ObservableList<String> StateList = FXCollections.observableArrayList("California", "Snow", "Movie", "Family", "Shopping", "Football", "Texas");
    @Override
    public void start(Stage primaryStage) throws Exception{
        ArrayList<Canvas> canvasList = creator.DataBase();

        //Корневой элеиент
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setPrefWidth(1037);
        root.setPrefHeight(716);
        root.getChildren().add(canvasList.get(0));

        //Создание combobox
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setValue("California");
        comboBox.setItems(StateList);
        comboBox.setLayoutX(0);
        comboBox.setLayoutY(0);

        SubScene sub1 = new SubScene(root, 1037,716);
        SubScene sub2 = new SubScene(comboBox, 177, 30);

        AnchorPane pane = new AnchorPane(sub1);
        pane.setPrefWidth(1037);
        pane.setPrefHeight(716);

        //Создание панели для отображения текста
        Label lb = new Label();
        lb.prefHeight(100);
        lb.prefWidth(50);
        AnchorPane.setTopAnchor(lb, 10.0);
        AnchorPane.setBottomAnchor(lb, 600.0);
        AnchorPane.setRightAnchor(lb,10.0);
        lb.setText("State is not selected");
        SubScene lblScene = new SubScene(lb, 100,100);

        //Добавление на корневой элемент информации
        pane.getChildren().add(sub2);
        pane.getChildren().add(lb);
        BackgroundFill background_fill = new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        pane.setBackground(background);

        //Создате элемент для отображения
        Scene scene= new Scene(pane);
        primaryStage.setTitle("USA MAP");

        //Добавляет камеру на сцену с отображением карты
        PerspectiveCamera camera = new PerspectiveCamera();
        sub1.setCamera(camera);

        //Событие для обработки выбора combobox
        comboBox.setOnAction(event ->{
            if(comboBox.getValue().equals("Snow")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(1));
            }
            else if(comboBox.getValue().equals("Family")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(3));
            }
            else if(comboBox.getValue().equals("Movie")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(2));
            }
            else if(comboBox.getValue().equals("California")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(0));
            }
            else if(comboBox.getValue().equals("Shopping")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(4));
            }
            else if(comboBox.getValue().equals("Football")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(5));
            }
            else if(comboBox.getValue().equals("Texas")){
                root.getChildren().remove(1);
                root.getChildren().add(canvasList.get(6));
            }
        });

        //Событие для обработки щелчка мыши
        root.setOnMouseClicked(event ->{
            for(BuisnessLogic.State st: TweetParse.ChangeStates){
               for(Path2D.Double pt: st.ChangePoly){
                   if(pt.contains(event.getX(), event.getY())){
                       if(comboBox.getValue().equals("Snow")){
                            lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o2.get(st.Name));
                            break;}
                       else if(comboBox.getValue().equals("California")){
                           lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o1.get(st.Name));
                           break;}
                       else if(comboBox.getValue().equals("Movie")){
                           lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o3.get(st.Name));
                           break;}
                       else if(comboBox.getValue().equals("Family")){
                           lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o4.get(st.Name));
                           break;}
                       else if(comboBox.getValue().equals("Shopping")){
                           lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o5.get(st.Name));
                           break;}
                       else if(comboBox.getValue().equals("Football")){
                           lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o6.get(st.Name));
                           break;}
                       else if(comboBox.getValue().equals("Texas")){
                           lb.setText("State Name: " + st.Name + "\n" + "Average Opinion: " + creator.o7.get(st.Name));
                           break;}
                   }
               }
            }
        });

        //Событие для обработки нажатия мыши
        sub1.setOnMousePressed(pressEvent -> {

            //Событие для обработки перетаскивания мыши
            sub1.setOnMouseDragged(dragEvent -> {
                if(dragEvent.getX() < 1037 && dragEvent.getY() < 716 && dragEvent.getX() > 0 && dragEvent.getY() > 0){
                    camera.setLayoutX(pressEvent.getScreenX() - dragEvent.getScreenX());
                    camera.setLayoutY(pressEvent.getScreenY() - dragEvent.getScreenY());
                    }
            });
        });

        //Событие для прокрутки колесика ммыши
        sub1.setOnScroll(event ->{
            if(event.getDeltaY() > 0.0  && camera.getTranslateZ() < 1000){
                camera.setTranslateZ(camera.getTranslateZ() + camereq);
            };
            if(event.getDeltaY() < 0.0 && camera.getTranslateZ() > 0) {
                camera.setTranslateZ(camera.getTranslateZ() - camereq);
            };
        });

        //Выводит содержимое сцены на экран
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
