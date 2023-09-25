package com.rosendo.company.Utils;

import javafx.scene.control.TextField;

public class FornecedorPopupUtil extends PopupUtil {

    public FornecedorPopupUtil(TextField textField, TextField searchField) {
        super(textField, "Fornecedor/cadastro-fornecedor.fxml", searchField, "style-cadastro-fornecedor.css");
    }


    @Override
    protected String getApiEndpoint() {
        return "/supplier";
    }

    @Override
    protected String getAddButtonText() {
        return "Adicionar novo Fornecedor";
    }

    @Override
    protected String getAddButtonId() {
        return "abrirFxmlButtonMarca";
    }

    @Override
    protected double getDesiredX() {
        return 182.0;
    }

    @Override
    protected double getDesiredY() {
        return 410.0;
    }


}