module com.example.graphen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires gxl;
<<<<<<< HEAD
    requires java.security.jgss;
    requires jdom2;
=======
    requires org.jdom2;
>>>>>>> Stanculescu-Daniel


    opens com.example.graphen to javafx.fxml;
    exports com.example.graphen;
}