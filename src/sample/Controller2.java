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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.Controller.stage;

/**
 * Created by Anton on 11.01.2018.
 */
public class Controller2 implements Initializable {

    private ObservableList<String> materials = FXCollections.observableArrayList("Gray cast iron",
            "1020 carbon steel",
            "1035 carbon steel",
            "1045 carbon steel",
            "302 stainless steel",
            "4140/5140 alloy steel",
            "Ni-based Inconel X",
            "Ni-based Udimet 500",
            "Co-based L605",
            "Ti (6Al, 4 V)",
            "Al 7075-T6",
            "Al 6061-T6");

   /* private ObservableList<Material> materials = FXCollections.observableArrayList(
            new Material(200, "Gray cast iron"),
            new Material(200, "1020 carbon steel"),
            new Material(200, "1035 carbon steel"),
            new Material(200, "1045 carbon steel"),
            new Material(200, "302 stainless steel"),
            new Material(200, "4140/5140 alloy steel"),
            new Material(200, "Ni-based Inconel X"),
            new Material(200, "Co-based L605"),
            new Material(200, "Ti (6Al, 4 V)"),
            new Material(200, "Al 7075-T6"),
            new Material(200, "Al 6061-T6"));*/


    @FXML
    private Text actiontarget;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processingMaterial.setItems(materials);
    }

    @FXML
    protected void handleSubmitButtonAction1(ActionEvent event) {
        stage.close();
    }


    @FXML
    protected void handleSubmitButtonActionNext(ActionEvent actionEvent) {
        float dMill = Float.parseFloat(diameter.getText());
        float w = Float.parseFloat(halex.getText());
        int nt = Integer.parseInt(teeth_number.getText());
        float ae = Float.parseFloat(radial_depth.getText());
        float ap = Float.parseFloat(axial_depth.getText());
        float ft = Float.parseFloat(feed.getText());


        actiontarget.setText(diameter.getText() + " " + toggleGroup.getSelectedToggle().getUserData().toString() + processingMaterial.getSelectionModel().getSelectedItem());

        ToolContact toolContact = new ToolContact();
        Polires polires = new Polires();
        ForceCalculation forceCalculation = new ForceCalculation();

        double startAngle = toolContact.startAngle(dMill, ae, ft, w, ap);

        if (startAngle > 10){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Here...");
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

        Scene scene = new Scene(lineChart, 800, 600);
        scene.getStylesheets().add("sample/Chart2.css");
        lineChart.getData().add(series);


        stage.setScene(scene);
        stage.show();


    }

}
