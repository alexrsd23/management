package com.rosendo.company.Controllers.FXMLControllers.Login;

import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginControllerFxml {

    @FXML
    private Button closeWindow;

    @FXML
    private JFXToggleButton toggleButton;

    @FXML
    private Circle circle;

    private TranslateTransition transition;

    @FXML
    private void initialize() {
        transition = new TranslateTransition(Duration.seconds(0.3));
        transition.setByX(80);
    }

    @FXML
    private void handleToggleButton() {
        if (toggleButton.isSelected()) {
            transition.setToX(-80);
        } else {
            transition.setToX(0);
        }
        transition.play();
    }

    @FXML
    private void handleCloseButtonClick() {
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        stage.close();
    }
}
