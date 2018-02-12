package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller2.stageThirdPage;

public class Controller3 implements Initializable {
    public ImageView image;
    public Button select;
    public RadioButton selectDetailCharacteristicOption;
    public RadioButton fillDetailCharacteristicOption;
    public TextField nat_frequency;
    public RadioButton selectMachineTool;
    public RadioButton fillMachineTool;
    public ComboBox machineToolType;
    public TextField spindleSpeed;
    public TextField path;
    public TextField stiffness;
    public TextField damping;



    public void handleSubmitButtonActionSelect(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(stageThirdPage);
        path.setText(selectedFile.toString());
    }


    public void frequencyValidation(KeyEvent keyEvent) {
    }

    public void stffnessValidation(KeyEvent keyEvent) {
    }

    public void dampingValidation(KeyEvent keyEvent) {
    }

    public void selectionMachineToolDone(ActionEvent actionEvent) {
    }

    public void handleSubmitButtonActionBack(ActionEvent actionEvent) {
        Controller2.stageThirdPage.close();
        Controller.stageSecondPage.show();
    }

    public void handleSubmitButtonActionNext(ActionEvent actionEvent) {
    }

    public void handleSubmitButtonActionCancel(ActionEvent actionEvent) {
        Controller2.stageThirdPage.close();
    }

    public void disableManualFilling(ActionEvent actionEvent) {
        select.setDisable(false);
        nat_frequency.setDisable(true);
        stiffness.setDisable(true);
        damping.setDisable(true);
    }

    public void disableSignalSelection(ActionEvent actionEvent) {
        select.setDisable(true);
        nat_frequency.setDisable(false);
        stiffness.setDisable(false);
        damping.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableManualFilling(new ActionEvent());
    }

    public void disableManualSelectionMashinetool(ActionEvent actionEvent) {
    }

    public void disableAutoSelectionMashinetool(ActionEvent actionEvent) {
    }
}
