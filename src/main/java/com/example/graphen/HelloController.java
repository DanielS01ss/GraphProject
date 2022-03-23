package com.example.graphen;

import gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        GUI g = new GUI();
        Stage thisStage = (Stage) welcomeText.getScene().getWindow();
        g.start(thisStage);
    }
}