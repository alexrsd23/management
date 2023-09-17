package com.rosendo.company.Utils;

import javafx.scene.control.TextField;

public class UnidadePopupUtil extends PopupUtil {

    public UnidadePopupUtil(TextField textField, TextField searchField) {
        super(textField, "cadastro-unidade.fxml", searchField, "style-cadastro-adicionais.css");
    }


    @Override
    protected String getApiEndpoint() {
        return "/unity";
    }

    @Override
    protected String getAddButtonText() {
        return "Adicionar nova unidade";
    }

    @Override
    protected String getAddButtonId() {
        return "abrirFxmlButtonUnidade";
    }

    @Override
    protected double getDesiredX() {
        return 682.0;
    }

    @Override
    protected double getDesiredY() {
        return 240.0;
    }


}