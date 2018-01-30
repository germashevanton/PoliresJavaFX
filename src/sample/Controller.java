package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {


    public static Stage stage = new Stage();

    public void handleContouringMilling(ActionEvent actionEvent) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add("sample/error.css");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/medical-cnc.jpg")));
        stage.setTitle("Advanced Cutting v1.1");
        stage.setScene(scene);
        stage.show();


    }



}
