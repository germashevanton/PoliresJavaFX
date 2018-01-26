package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {


    public static Stage stage = new Stage();

    public void handleContouringMilling(ActionEvent actionEvent) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root, 700, 600);
        scene.getStylesheets().add("sample/error.css");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/medical-cnc.jpg")));
        stage.setTitle("Advanced Cutting v1.1");
        stage.setScene(scene);
        stage.show();


    }



}
