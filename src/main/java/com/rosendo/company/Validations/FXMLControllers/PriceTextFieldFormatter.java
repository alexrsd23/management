package com.rosendo.company.Validations.FXMLControllers;

import javafx.scene.control.TextField;

public class PriceTextFieldFormatter {

    public static void formatPriceTextField(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,9}(\\.\\d{0,2})?")) {
                textField.setText(oldValue);
            }
        });

        textField.setOnKeyTyped(event -> {
            String currentText = textField.getText();
            int caretPosition = textField.getCaretPosition();

            if (currentText.contains(".")) {
                int indexOfDecimal = currentText.indexOf(".");
                if (caretPosition > indexOfDecimal && currentText.length() - indexOfDecimal > 2) {
                    event.consume();
                } else {
                    textField.positionCaret(currentText.length());
                }
            } else if (currentText.length() >= 9) {
                event.consume();
            } else if (currentText.length() == 0 && event.getCharacter().equals(".")) {
                textField.setText("0.");
                textField.positionCaret(2);
            } else if (currentText.length() == 1 && currentText.equals("0") && event.getCharacter().equals(".")) {
                textField.positionCaret(2);
            } else if (currentText.length() == 1) {
                if (Character.isDigit(event.getCharacter().charAt(0))) {
                    textField.positionCaret(1);
                } else {
                    event.consume();
                }
            }
        });
    }
}
