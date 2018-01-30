package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/firstPage.fxml"));

        Scene scene = new Scene(root, 480, 600);

        stage.setTitle("Advanced Cutting v1.1");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/medical-cnc.jpg")));
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
