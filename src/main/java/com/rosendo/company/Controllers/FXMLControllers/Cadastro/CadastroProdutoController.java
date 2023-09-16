package com.rosendo.company.Controllers.FXMLControllers.Cadastro;

import com.rosendo.company.Utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;

public class CadastroProdutoController {
    // Declaração de elementos de interface de usuário
    @FXML
    private ImageView imageView;
    @FXML
    private Label messageLabel;
    @FXML
    private Label messageLabelOne;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField categoriaTextField;
    @FXML
    private TextField marcaTextField;

    @FXML
    private TextField unidadeTextField;

    // Efeitos e popups
    private ColorAdjust grayscaleEffect = new ColorAdjust();
    private Popup popup;
    private Popup popupMarca;
    private Popup popupUnidade;
    private Popup currentPopup;

    private ImageSelectionUtil imageSelectionUtil;
    private PopupUtil marcaPopupUtil;
    private PopupUtil categoriaPopupUtil;
    private PopupUtil unidadePopupUtil;
    @FXML
    private TextField marcaSearchField;
    @FXML
    private TextField categoriaSearchField;

    @FXML
    private TextField unidadeSearchField;

    // Método de inicialização
    public void initialize() {
        // Inicialize utilitários de seleção de imagem
        imageSelectionUtil = new ImageSelectionUtil(imageView, messageLabel, messageLabelOne, anchorPane);
        imageSelectionUtil.initialize();
        marcaPopupUtil = new MarcaPopupUtil(marcaTextField, marcaSearchField);
        categoriaPopupUtil = new CategoriaPopupUtil(categoriaTextField, categoriaSearchField);
        unidadePopupUtil = new UnidadePopupUtil(unidadeTextField, unidadeSearchField);
    }


    @FXML
    public void selectImage(MouseEvent event) {
        // Método acionado ao selecionar uma imagem
        imageSelectionUtil.selectImage(event);
    }

    @FXML
    public void setImage(File file) {
        // Define a imagem selecionada no ImageView
        imageSelectionUtil.setImage(file);
    }

    @FXML
    public void onMouseEntered(MouseEvent event) {
        // Método acionado quando o mouse entra na área da imagem
        imageSelectionUtil.onMouseEntered();
    }

    @FXML
    public void onMouseExited(MouseEvent event) {
        // Método acionado quando o mouse sai da área da imagem
        imageSelectionUtil.onMouseExited();
    }

    @FXML
    private void alternarPopupMarca(ActionEvent event) throws JSONException {
        marcaPopupUtil.togglePopup();
    }

    @FXML
    private void alternarPopupUnidade(ActionEvent event) throws JSONException {
        unidadePopupUtil.togglePopup();
    }

    @FXML
    private void alternarPopup(ActionEvent event) throws JSONException {
        categoriaPopupUtil.togglePopup();
    }

    // Método para cancelar ação
    @FXML
    void cancelarAcao(ActionEvent event) {
        // Obtém a referência ao nó (botão "Cancelar") que disparou o evento
        Node source = (Node) event.getSource();

        // Obtém a referência à janela atual
        Stage stage = (Stage) source.getScene().getWindow();

        // Fecha a janela
        stage.close();
    }
}
