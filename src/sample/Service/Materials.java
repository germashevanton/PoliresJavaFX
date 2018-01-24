package sample.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Materials {

    public ObservableList<String> materialsList = FXCollections.observableArrayList("Gray cast iron",
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
}


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