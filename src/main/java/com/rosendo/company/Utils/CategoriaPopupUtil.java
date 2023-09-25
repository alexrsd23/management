package com.rosendo.company.Utils;

import javafx.scene.control.TextField;

public class CategoriaPopupUtil extends PopupUtil {

    public CategoriaPopupUtil(TextField textField, TextField searchField) {
        super(textField, "Cadastro/cadastro-categoria.fxml", searchField, "style-cadastro-adicionais.css");
    }

    @Override
    protected String getApiEndpoint() {
        return "/category";
    }

    @Override
    protected String getAddButtonText() {
        return "Adicionar nova categoria";
    }

    @Override
    protected String getAddButtonId() {
        return "abrirFxmlButton";
    }

    @Override
    protected double getDesiredX() {
        return 182.0;
    }

    @Override
    protected double getDesiredY() {
        return 240.0;
    }
}