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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Anton on 11.01.2018.
 */
public class Controller2 implements Initializable {

    private ObservableList<String> materials = FXCollections.observableArrayList("one", "two", "three");

    @FXML
    private Text actiontarget;

    @FXML
    private TextField diameter;

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

    @FXML protected void handleSubmitButtonAction1(ActionEvent event) {
        Controller.stage.close();
    }


    @FXML protected void handleSubmitButtonActionNext(ActionEvent actionEvent) {
        actiontarget.setText(diameter.getText() + " "  + toggleGroup.getSelectedToggle().getUserData().toString() + processingMaterial.getSelectionModel().getSelectedItem()) ;

        Polires polires = new Polires();
        ForceCalculation forceCalculation = new ForceCalculation();
        double fMax = forceCalculation.forceCalc(2400, 60, 0.05, 154, 2);

        polires.foruerCoefCalc(102, 6, 0.07);
        polires.poliresCalc(700, 60,102, 2400, 6);
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
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Poliresonanse");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("z = 6");
        //populating the series with data

        for (int i = 0; i < f.length; i++) {
            series.getData().add(new XYChart.Data(v[i], f[i]));
        }

        Scene scene  = new Scene(lineChart,800,600);
        scene.getStylesheets().add("sample/Chart2.css");
        lineChart.getData().add(series);


        stage.setScene(scene);
        stage.show();



    }

}
