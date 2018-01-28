package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Service.Materials;
import sample.Service.Service;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.Controller.stage;

/**
 * Created by Anton on 11.01.2018.
 */
public class Controller2 implements Initializable {



    Service service = new Service();
    Materials materials = new Materials();
    ToolContact toolContact = new ToolContact();
    Polires polires = new Polires();
    ForceCalculation forceCalculation = new ForceCalculation();
    Image diameterFig = new Image(getClass().getResourceAsStream("images/d_mill.png"));
    Image ftFig = new Image(getClass().getResourceAsStream("images/fz.png"));
    Image ntFig = new Image(getClass().getResourceAsStream("images/ft.png"));
    Image radiusFig = new Image(getClass().getResourceAsStream("images/radiuus_ball.png"));
    Image apFig = new Image(getClass().getResourceAsStream("images/ap.png"));
    Image aeFig = new Image(getClass().getResourceAsStream("images/ae.png"));
    Image ballMillFif = new Image(getClass().getResourceAsStream("images/miil_radius.png"));
    Image bullMillFig = new Image(getClass().getResourceAsStream("images/mill_bull.png"));
    Image wFig = new Image(getClass().getResourceAsStream("images/w.jpg"));
    Image tiltFig = new Image(getClass().getResourceAsStream("images/tilt.jpg"));


    @FXML
    public ImageView image;
    @FXML
    private Text actiontarget;
    @FXML
    private TextField radius;
    @FXML
    private TextField diameter;
    @FXML
    private TextField halex;
    @FXML
    private TextField teeth_number;
    @FXML
    private TextField axial_depth;
    @FXML
    private TextField radial_depth;
    @FXML
    private TextField feed;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ComboBox<String> processingMaterial;
    @FXML
    private Button cancel;
    @FXML
    private Button next;
    @FXML
    public TextField tilt;

    private float r = -1;
    private float dMill  = -1;
    private float w = -1;
    private int nt = -1;
    private float ae = -1;
    private float ap = -1;
    private float ft = -1;
    private float tiltAngle = -1;
    private float ks; //specific force coefficient
    private float beta; //cutting force angle

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processingMaterial.setItems(materials.materialsList);
        radius.setDisable(true);
        next.setDisable(true);

        Field[] fields = getClass().getDeclaredFields();
        Object object = this;
        for (Field field : fields) {
            if (field.getType().getName().equals("javafx.scene.control.TextField")){
                TextField textField = null;
                try {
                    textField = (TextField) field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (textField != null) {
                    textField.getStyleClass().add("error");
                }

            }
        }

        radius.getStyleClass().remove("error");

        processingMaterial.getStyleClass().add("error");
        image.setImage(diameterFig);
    }

    @FXML
    protected void handleSubmitButtonAction1(ActionEvent event) {
        stage.close();
    }

    @FXML
    protected void activateRadiusField(ActionEvent actionEvent){
        radius.setDisable(false);
        radius.getStyleClass().add("error");
    }

    @FXML
    protected  void disableRadiusField(ActionEvent actionEvent){
        radius.setText("");
        ObservableList<String> styleClass = radius.getStyleClass();
        if (styleClass.contains("error")){
            styleClass.remove("error");
        }
        radius.setDisable(true);


    }

    @FXML
    protected void handleSubmitButtonActionNext(ActionEvent actionEvent) {


        actiontarget.setText(diameter.getText() + " " + toggleGroup.getSelectedToggle().getUserData().toString() + processingMaterial.getSelectionModel().getSelectedItem());



        double startAngle = toolContact.startAngle(dMill, ae, ft, w, ap);


        // Check contact angle  
        if ((180 - startAngle) > (360/nt)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("I have a great message for you!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                return;
            }
        }

        double contactAngle = toolContact.cuttingPeriod(dMill, ae, ft, w, ap);

        double fMax = forceCalculation.forceCalc(2400, 60, ft, startAngle, ap);

        polires.foruerCoefCalc(102, nt, contactAngle);
        polires.poliresCalc(700, 60, fMax, 2400, nt);
        double[] f = polires.getF();
        double[] v = polires.getWwSpeed();

        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/medical-cnc.jpg")));
        stage.setTitle("Amplitude - frequency characteristic");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Spindle speed, rpm");
        yAxis.setLabel("Amplitude, ");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Poliresonanse");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("z = 6");
        //populating the series with data

        for (int i = 0; i < f.length; i++) {
            series.getData().add(new XYChart.Data(v[i], f[i]));
        }

        Scene scene = new Scene(lineChart, 1240, 800);
        scene.getStylesheets().add("sample/Chart2.css");
        lineChart.getData().add(series);


        stage.setScene(scene);
        stage.show();
    }



    private void checkParams(){
        if(dMill != -1 && w != -1 && ae != -1 && ap != -1 && ft != -1 && nt != -1 && !processingMaterial.getSelectionModel().isEmpty()){
            next.setDisable(false);
        }
    }

    @FXML
    protected void diameterValidation(KeyEvent event){
        dMill = service.stringToFloatConverterValidator(diameter);
        checkParams();
    }

    public void radiusValidation(KeyEvent keyEvent) {
        r = service.stringToFloatConverterValidator(radius);
        checkParams();
    }

    public void halexValidation(KeyEvent keyEvent) {
        w = service.stringToFloatConverterValidator(halex);
        checkParams();
    }

    public void ntValidation(KeyEvent keyEvent) {
        nt = service.stringToIntConverterValidator(teeth_number);
        checkParams();
    }

    public void apValidation(KeyEvent keyEvent) {
        ap = service.stringToFloatConverterValidator(axial_depth);
        checkParams();
    }

    public void aeValidation(KeyEvent keyEvent) {
        ae = service.stringToFloatConverterValidator(radial_depth);
        checkParams();
    }

    public void feedValidation(KeyEvent keyEvent) {
        ft = service.stringToFloatConverterValidator(feed);
        checkParams();
    }

    public void tiltValidation(KeyEvent keyEvent) {
        tiltAngle = service.stringToFloatConverterValidator(tilt);
        checkParams();
    }

    public void selectionDone(ActionEvent actionEvent) {
        processingMaterial.getSelectionModel().getSelectedItem().toString();
        ObservableList<String> styleClass = processingMaterial.getStyleClass();
        if (styleClass.contains("error")){
            styleClass.remove("error");
        }
        checkParams();
    }


    public void showFtFig(MouseEvent mouseEvent) {
        image.setImage(ftFig);
    }

    public void showDiamFig(MouseEvent mouseEvent) {
        image.setImage(diameterFig);
    }


    public void showBallMillFig(MouseEvent mouseEvent) {
        image.setImage(ballMillFif);
    }

    public void showBullMillFig(MouseEvent mouseEvent) {
        image.setImage(bullMillFig);
    }

    public void showRadiusFig(MouseEvent mouseEvent) {
        image.setImage(radiusFig);
    }

    public void showPictureHalex(MouseEvent mouseEvent) {
        image.setImage(wFig);
    }

    public void showPictureTeethNumber(MouseEvent mouseEvent) {
        image.setImage(ntFig);
    }

    public void showApFig(MouseEvent mouseEvent) {
        image.setImage(apFig);
    }

    public void ShowAeFig(MouseEvent mouseEvent) {
        image.setImage(aeFig);
    }

    public void showDownMillingFig(MouseEvent mouseEvent) {
    }

    public void showUpMillingFig(MouseEvent mouseEvent) {
    }

    public void showTiltFig(MouseEvent mouseEvent) {
        image.setImage(tiltFig);
    }
}
