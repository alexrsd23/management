package com.rosendo.company.Validations.FXMLControllers;

import javafx.scene.control.TextField;

public class CNPJFormatter {
    public static void formatCNPJTextField(TextField textField) {
        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            String formattedValue = formatCNPJ(newValue);
            textField.setText(formattedValue);
        });

        textField.setOnKeyTyped(event -> {
            // Obtém o texto atual e a posição do cursor
            String currentText = textField.getText();
            int caretPosition = textField.getCaretPosition();

            // Remove todos os caracteres especiais
            String strippedText = currentText.replaceAll("[^0-9]", "");

            // Formata o CNPJ
            String formattedCNPJ = formatCNPJ(strippedText);

            // Define o texto formatado
            textField.setText(formattedCNPJ);

            // Move o cursor para a posição correta
            if (caretPosition < formattedCNPJ.length()) {
                textField.positionCaret(caretPosition);
            } else {
                textField.positionCaret(formattedCNPJ.length());
            }

            // Impede que o evento seja propagado para evitar entrada indesejada
            event.consume();
        });
    }

    public static String formatCNPJ(String cnpj) {
        // Remove todos os caracteres não numéricos
        String digitsOnly = cnpj.replaceAll("[^0-9]", "");

        // Formata o CNPJ no formato 00.000.000/0000-00
        if (digitsOnly.length() >= 14) {
            return digitsOnly.substring(0, 2) + "." + digitsOnly.substring(2, 5) + "." + digitsOnly.substring(5, 8) + "/" +
                    digitsOnly.substring(8, 12) + "-" + digitsOnly.substring(12, 14);
        }

        // Se o CNPJ for menor que 14 dígitos, retorna apenas os dígitos
        return digitsOnly;
    }
}
