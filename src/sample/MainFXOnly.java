package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class MainFXOnly extends Application{
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("FXML Welcome");

        FlowPane rootNode  = new FlowPane(10,10);
        Image imageBack = new Image(getClass().getResourceAsStream("classicalMilling.png"));
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        //rootNode.setBackground(backgroundImage);
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 300,300);

        stage.setScene(scene);
        Image imageOk = new Image(getClass().getResourceAsStream("classicalMilling.png"));


        Button button = new Button("Some", new ImageView(imageOk));



        rootNode.getChildren().addAll(button);


        stage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
