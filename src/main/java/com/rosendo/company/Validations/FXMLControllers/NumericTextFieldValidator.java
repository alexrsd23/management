package com.rosendo.company.Validations.FXMLControllers;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class NumericTextFieldValidator {
    public static void validateNumericTextField(TextField textField) {
        Tooltip tooltip = new Tooltip("ESSE CAMPO ACEITA APENAS NÚMEROS");
        textField.setTooltip(tooltip);

        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tooltip.show(textField, textField.getScene().getWindow().getX() + textField.getLayoutX() + textField.getWidth(),
                        textField.getScene().getWindow().getY() + textField.getLayoutY() + textField.getHeight());
            } else {
                tooltip.hide();
            }
        });
    }
}
