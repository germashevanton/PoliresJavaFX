package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Service.*;


import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.Controller.stageSecondPage;

/**
 * Created by Anton on 11.01.2018.
 */
public class Controller2 implements Initializable {
    // Singleton
    private static Controller2 instance = new Controller2();
    public static Controller2 getInstance(){
        return instance;
    }



    Service service = new Service();
    Materials materials = new Materials();
    ToolContact toolContact = new ToolContact();
    Polires polires = new Polires();
    ForceCalculation forceCalculation = new ForceCalculation();

    //Images
    Image diameterFig = new Image(getClass().getResourceAsStream("images/d_mill.png"));
    Image ftFig = new Image(getClass().getResourceAsStream("images/fz.png"));
    Image ntFig = new Image(getClass().getResourceAsStream("images/ft.png"));
    Image radiusFig = new Image(getClass().getResourceAsStream("images/radiuus_ball.png"));
    Image apFig = new Image(getClass().getResourceAsStream("images/ap.png"));
    Image aeFig = new Image(getClass().getResourceAsStream("images/ae.png"));
    Image ballMillFif = new Image(getClass().getResourceAsStream("images/miil_radius.png"));
    Image bullMillFig = new Image(getClass().getResourceAsStream("images/mill_bull.png"));
    Image wFig = new Image(getClass().getResourceAsStream("images/w2.png"));
    Image tiltFig = new Image(getClass().getResourceAsStream("images/tilt.jpg"));
    Image upMillingFig = new Image(getClass().getResourceAsStream("images/up_milling.png"));
    Image downMillingFig = new Image(getClass().getResourceAsStream("images/down_milling.png"));

    //FXML Elements
    public static Stage stageThirdPage;
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

    // Class fields
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
    private double startAngle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processingMaterial.setItems(materials.materialsList);
        radius.setDisable(true);
        checkParamsToManageNextButton();

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
        stageSecondPage.close();
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



        startAngle = toolContact.startAngle(dMill, ae, ft, w, ap);


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

        Controller.stageSecondPage.close();


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("view/thirdPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root, 950, 600);
        scene.getStylesheets().add("sample/style/error.css");

        stageThirdPage = new Stage();
        stageThirdPage.getIcons().add(new Image(getClass().getResourceAsStream("images/medical-cnc.jpg")));
        stageThirdPage.setTitle("Advanced Cutting v1.1");
        stageThirdPage.setScene(scene);
        stageThirdPage.show();



        /*double contactAngle = toolContact.cuttingPeriod(dMill, ae, ft, w, ap);

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
        scene.getStylesheets().add("sample/style/Chart2.css");
        lineChart.getData().add(series);


        stage.setScene(scene);
        stage.show();*/
    }



    private void checkParamsToManageNextButton(){
        if(dMill != -1 && w != -1 && ae != -1 && ap != -1 && ft != -1 && nt != -1 && !processingMaterial.getSelectionModel().isEmpty()){
            next.setDisable(false);
        } else {
            next.setDisable(true);
        }
    }

    //validate input fields
    @FXML
    protected void diameterValidation(KeyEvent event){
        dMill = service.stringToFloatConverterValidator(diameter);
        checkParamsToManageNextButton();
    }

    @FXML
    public void radiusValidation(KeyEvent keyEvent) {
        r = service.stringToFloatConverterValidator(radius);
        checkParamsToManageNextButton();
    }

    @FXML
    public void halexValidation(KeyEvent keyEvent) {
        w = service.stringToFloatConverterValidator(halex);
        checkParamsToManageNextButton();
    }

    @FXML
    public void ntValidation(KeyEvent keyEvent) {
        nt = service.stringToIntConverterValidator(teeth_number);
        checkParamsToManageNextButton();
    }

    @FXML
    public void apValidation(KeyEvent keyEvent) {
        ap = service.stringToFloatConverterValidator(axial_depth);
        checkParamsToManageNextButton();
    }

    @FXML
    public void aeValidation(KeyEvent keyEvent) {
        ae = service.stringToFloatConverterValidator(radial_depth);
        checkParamsToManageNextButton();
    }

    @FXML
    public void feedValidation(KeyEvent keyEvent) {
        ft = service.stringToFloatConverterValidator(feed);
        checkParamsToManageNextButton();
    }

    @FXML
    public void tiltValidation(KeyEvent keyEvent) {
        tiltAngle = service.stringToFloatConverterValidator(tilt);
        checkParamsToManageNextButton();
    }

    @FXML
    public void selectionDone(ActionEvent actionEvent) {
        processingMaterial.getSelectionModel().getSelectedItem().toString();
        ObservableList<String> styleClass = processingMaterial.getStyleClass();
        if (styleClass.contains("error")){
            styleClass.remove("error");
        }
        checkParamsToManageNextButton();
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
        image.setImage(upMillingFig);
    }

    public void showUpMillingFig(MouseEvent mouseEvent) {
        image.setImage(downMillingFig);
    }

    public void showTiltFig(MouseEvent mouseEvent) {
        image.setImage(tiltFig);
    }

    // Build chart of poliresonance
    public void buildChart(double natFrequency, double stiffness, double dampRatio, int maxSpindleSpeed){
        double contactAngle = toolContact.cuttingPeriod(dMill, ae, ft, w, ap);

        double fMax = forceCalculation.forceCalc(2400, 60, ft, startAngle, ap);

        polires.foruerCoefCalc(102, nt, contactAngle);
        polires.poliresCalc(natFrequency, dampRatio, fMax, stiffness, nt, maxSpindleSpeed);
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

        XYChart.Series chatter = new XYChart.Series(); ////

        Scene scene = new Scene(lineChart, 1240, 800);
        scene.getStylesheets().add("sample/style/Chart2.css");
        lineChart.getData().add(series);


        stage.setScene(scene);
        stage.show();
    }
}
