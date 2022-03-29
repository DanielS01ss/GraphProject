module com.example.graphen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires gxl;
    requires java.security.jgss;
    requires org.jdom2;
    requires jdom2;


    opens com.example.graphen to javafx.fxml;
    exports com.example.graphen;
}