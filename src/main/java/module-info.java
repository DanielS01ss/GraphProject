module com.example.graphen {
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires jdom2;


    opens gui to javafx.fxml;
    exports gui;
}