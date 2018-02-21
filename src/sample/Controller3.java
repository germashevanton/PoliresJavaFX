package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private float frequency = -1;
    private float partStiffness = -1;
    private float dampingRatio = -1;
    private  String machineTool;
    private int maxSpindleSpeed = -1;

    //FXML elements
    public ToggleGroup toggleGroupMachineTool;
    public ToggleGroup toggleGroupSelectFrfFile;
    public ImageView image;
    public Button select;
    public Button next;
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
        //Validate
        service.filePathValidator(path);
        checkParamsToManageNextButton();
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
        //show text
        textInformation.setText("Chose file with the response signal to determine characteristic of the detail and Frequency Response Function");
        //disable
        nat_frequency.setDisable(true);
        stiffness.setDisable(true);
        damping.setDisable(true);
        //activate
        path.setDisable(false);
        select.setDisable(false);
        // remove error from opposite check box
        nat_frequency.getStyleClass().remove("error");
        stiffness.getStyleClass().remove("error");
        damping.getStyleClass().remove("error");
        // check params
        service.checkFieldNotNull(path);
        service.filePathValidator(path);
        checkParamsToManageNextButton();
    }

    public void disableSignalSelection(ActionEvent actionEvent) {
        // show text
        textInformation.setText("Put characteristics of the detail manually to determine Frequency Response Function");
        // disable
        path.setDisable(true);
        select.setDisable(true);
        //activate
        nat_frequency.setDisable(false);
        stiffness.setDisable(false);
        damping.setDisable(false);
        // remove error from opposite check box
        path.getStyleClass().remove("error");
        //check params
        service.checkFieldNotNull(nat_frequency);
        service.checkFieldNotNull(stiffness);
        service.checkFieldNotNull(damping);
        service.stringToFloatConverterValidator(nat_frequency);
        service.stringToFloatConverterValidator(stiffness);
        service.stringToFloatConverterValidator(damping);
        checkParamsToManageNextButton();
    }

    public void disableManualSelectionMachineTool(ActionEvent actionEvent) {
        // show text
        textInformation.setText("Select machine tool from the list to determine its characteristics and spindle speed range of the calculation");
        // disable
        spindleSpeed.setDisable(true);
        //activate
        machineToolType.setDisable(false);
        // remove error from opposite check box
        if (machineTool == null){
            machineToolType.getStyleClass().add("error");
        }
        spindleSpeed.getStyleClass().remove("error");
        //check params
        checkParamsToManageNextButton();
    }

    public void disableAutoSelectionMachineTool(ActionEvent actionEvent) {
        // show text
        textInformation.setText("Put maximum spindle speed of the machine tool to determine range of calculation");
        // disable
        machineToolType.setDisable(true);
        //activate
        spindleSpeed.setDisable(false);
        // remove error from opposite check box
        machineToolType.getStyleClass().remove("error");
        //check params
        service.stringToIntConverterValidator(spindleSpeed);
        checkParamsToManageNextButton();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableManualFilling(new ActionEvent());
        machineToolType.setItems(machineToolList.machineToolList);
        spindleSpeed.setDisable(true);
        next.setDisable(true);

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
        checkParamsToManageNextButton();
    }

    public void spindleSpeedValidation(KeyEvent keyEvent) {
        maxSpindleSpeed = service.stringToIntConverterValidator(spindleSpeed);
        checkParamsToManageNextButton();
    }

    public void frequencyValidation(KeyEvent keyEvent) {
        frequency = service.stringToFloatConverterValidator(nat_frequency);
        checkParamsToManageNextButton();
    }

    public void stiffnessValidation(KeyEvent keyEvent) {
        partStiffness = service.stringToFloatConverterValidator(stiffness);
        checkParamsToManageNextButton();
    }

    public void dampingValidation(KeyEvent keyEvent) {
        dampingRatio = service.stringToFloatConverterValidator(damping);
        checkParamsToManageNextButton();
    }

    public void selectionMachineToolDone(ActionEvent actionEvent) {
        machineToolType.getStyleClass().remove("error");
        machineTool =  machineToolType.getSelectionModel().getSelectedItem().toString();
        checkParamsToManageNextButton();
    }

    private void checkParamsToManageNextButton(){
        String toggleSelectFRF = toggleGroupSelectFrfFile.getSelectedToggle().getUserData().toString();
        String toggleSelectMachineTool = toggleGroupMachineTool.getSelectedToggle().getUserData().toString();
        if(((frequency != -1 && partStiffness != -1 && dampingRatio != -1 && toggleSelectFRF.equals("Manual"))
                || (toggleSelectFRF.equals("Auto") && new File(path.getText()).isFile())) &&
                (machineTool != null && toggleSelectMachineTool.equals("Auto"))
                || maxSpindleSpeed != -1 && toggleSelectMachineTool.equals("Manual")){
            next.setDisable(false);
        } else {
            next.setDisable(true);
        }
    }
}