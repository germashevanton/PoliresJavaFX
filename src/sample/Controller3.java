package sample;

import javafx.collections.ObservableList;
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
import sample.Service.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller2.stageThirdPage;

public class Controller3 implements Initializable {

    //Objects
    Materials machineToolList = new Materials();
    Service service = new Service();

    // fields
    private String filePath;
    private float frequency;
    private float partStiffness;
    private float dampingRatio;
    private  String machineTool;
    private int maxSpindleSpeed;

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
        service.filePathValidator(path);
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
        textInformation.setText("Chose file with the response signal to determine characteristic of the detail and Frequency Response Function");
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
        if (machineTool == null){
            machineToolType.getStyleClass().add("error");
        }
        spindleSpeed.getStyleClass().remove("error");
    }

    public void disableAutoSelectionMashinetool(ActionEvent actionEvent) {
        textInformation.setText("Put maximum spindle speed of the machine tool to determine range of calculation");
        machineToolType.setDisable(true);
        spindleSpeed.setDisable(false);
        service.stringToIntConverterValidator(spindleSpeed);
        machineToolType.getStyleClass().remove("error");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableManualFilling(new ActionEvent());
        machineToolType.setItems(machineToolList.machineToolList);
        spindleSpeed.setDisable(true);

        Field[] fields = getClass().getDeclaredFields();
        Object object = this;
        for (Field field : fields) {
            if (field.getType().getName().equals("javafx.scene.control.TextField")) {
                TextField textField = null;

                try {
                    textField = (TextField) field.get(object);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (textField != null && !textField.isDisable()) {
                    textField.getStyleClass().add("error");
                }
            } else if(field.getType().getName().equals("javafx.scene.control.ComboBox")){
                ComboBox comboBox = null;
                try {
                    comboBox = (ComboBox) field.get(object);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (comboBox != null && !comboBox.isDisable()) {
                    comboBox.getStyleClass().add("error");
                }
            }
        }
    }

    //Validate fields
    public void pathValidation(KeyEvent keyEvent) {
        filePath = service.filePathValidator(path);
    }

    public void spindleSpeedValidation(KeyEvent keyEvent) {
        maxSpindleSpeed = service.stringToIntConverterValidator(spindleSpeed);
    }

    public void frequencyValidation(KeyEvent keyEvent) {
        frequency = service.stringToFloatConverterValidator(nat_frequency);
    }

    public void stiffnessValidation(KeyEvent keyEvent) {
        partStiffness = service.stringToFloatConverterValidator(stiffness);
    }

    public void dampingValidation(KeyEvent keyEvent) {
        dampingRatio = service.stringToFloatConverterValidator(damping);
    }

    public void selectionMachineToolDone(ActionEvent actionEvent) {
        machineToolType.getStyleClass().remove("error");
        machineTool =  machineToolType.getSelectionModel().getSelectedItem().toString();
    }
}
