package sample.Service;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class Service {

    public float stringToFloatConverterValidator(TextField textField){
        float temp;
        String val = textField.getText();
        val = val.replace(',','.');
        ObservableList<String> styleClass = textField.getStyleClass();
        try {
            temp =  Float.parseFloat(val);
            styleClass.remove("error");
            return temp;
        } catch (Exception ex){
            textField.setTooltip(new Tooltip("Invalid input data"));
            if (!styleClass.contains("error")){
                styleClass.add("error");
            }
            return -1;
        }
    }

    public int stringToIntConverterValidator(TextField textField){
        int temp;
        String val = textField.getText();
        ObservableList<String> styleClass = textField.getStyleClass();
        try {
            temp =  Integer.parseInt(val);
            styleClass.remove("error");
            return temp;
        } catch (Exception ex){
            textField.setTooltip(new Tooltip("Invalid input data"));
            if (!styleClass.contains("error")){
                styleClass.add("error");
            }
            return -1;
        }
    }
}
