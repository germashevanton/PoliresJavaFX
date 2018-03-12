package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {



    public static Stage stageSecondPage = new Stage();
    public static FXMLLoader loader = new FXMLLoader();

    public void handleContouringMilling(ActionEvent actionEvent) {

        Parent root = null;
        try {
            root = loader.load(getClass().getResource("view/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root, 950, 600);
        scene.getStylesheets().add("sample/style/error.css");
        stageSecondPage.getIcons().add(new Image(getClass().getResourceAsStream("images/medical-cnc.jpg")));
        stageSecondPage.setTitle("Advanced Cutting v1.1");
        stageSecondPage.setScene(scene);
        stageSecondPage.show();


    }



}
