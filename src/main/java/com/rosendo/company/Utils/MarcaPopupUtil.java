package com.rosendo.company.Utils;

import javafx.scene.control.TextField;

public class MarcaPopupUtil extends PopupUtil {

    public MarcaPopupUtil(TextField textField, TextField searchField) {
        super(textField, "cadastro-marca.fxml", searchField);
    }


    @Override
    protected String getApiEndpoint() {
        return "/brand";
    }

    @Override
    protected String getAddButtonText() {
        return "Adicionar nova marca";
    }

    @Override
    protected String getAddButtonId() {
        return "abrirFxmlButtonMarca";
    }

    @Override
    protected double getDesiredX() {
        return 432.0;
    }

    @Override
    protected double getDesiredY() {
        return 240.0;
    }


}