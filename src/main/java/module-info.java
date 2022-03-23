module com.example.graphen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.graphen to javafx.fxml;
    exports com.example.graphen;
}