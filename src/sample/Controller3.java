package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.Service.Materials;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller2.stageThirdPage;

public class Controller3 implements Initializable {
    //Objects
    Materials machineToolList = new Materials();

    //FXML elements
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
    public Text textInformation;


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
        textInformation.setText("Chose file with detail response to determine characteristic of the detail and Frequency Response Function");
        path.setDisable(false);
        select.setDisable(false);
        nat_frequency.setDisable(true);
        stiffness.setDisable(true);
        damping.setDisable(true);
    }

    public void disableSignalSelection(ActionEvent actionEvent) {
        textInformation.setText("Put characteristics of the detail manually to determine Frequency Response Function");
        path.setDisable(true);
        select.setDisable(true);
        nat_frequency.setDisable(false);
        stiffness.setDisable(false);
        damping.setDisable(false);
    }

    public void disableManualSelectionMashinetool(ActionEvent actionEvent) {
        textInformation.setText("Select machine tool from the list to determine its characteristics and spindle speed range of the calculation");
        spindleSpeed.setDisable(true);
        machineToolType.setDisable(false);
    }

    public void disableAutoSelectionMashinetool(ActionEvent actionEvent) {
        textInformation.setText("Put maximum spindle speed of the machine tool to determine range of calculation");
        machineToolType.setDisable(true);
        spindleSpeed.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableManualFilling(new ActionEvent());
        machineToolType.setItems(machineToolList.machineToolList);
        spindleSpeed.setDisable(true);
    }
}
