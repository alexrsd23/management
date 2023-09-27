package com.rosendo.company.Controllers.FXMLControllers.Cadastro;

import com.rosendo.company.Utils.*;
import com.rosendo.company.Validations.FXMLControllers.NumericTextFieldValidator;
import com.rosendo.company.Validations.FXMLControllers.PriceTextFieldFormatter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;

public class CadastroProdutoController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label messageLabel, messageLabelOne;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField categoriaTextField, marcaTextField, fornecedorTextField, unidadeTextField, marcaSearchField, categoriaSearchField, unidadeSearchField, fornecedorSearchField, campoPesoLiquido, campoPesoBruto, campoCodigoBarras, campoCodigoReferencia, campoPrecoCusto, campoPrecoVenda, campoMargemLucro;

    private ColorAdjust grayscaleEffect = new ColorAdjust();

    private Popup popup, popupMarca, popupUnidade, popupFornecedor, currentPopup;
    private ImageSelectionUtil imageSelectionUtil;
    private PopupUtil marcaPopupUtil, categoriaPopupUtil, unidadePopupUtil, fornecedorPopupUtil;
    @FXML
    private CheckBox checkboxMargemLucro;

    boolean checkboxCondicao;

    public boolean isCheckboxCondicao() {
        return checkboxCondicao;
    }

    public void setCheckboxCondicao(boolean checkboxCondicao) {
        this.checkboxCondicao = checkboxCondicao;
    }

    public void initialize() {
        imageSelectionUtil = new ImageSelectionUtil(imageView, messageLabel, messageLabelOne, anchorPane);
        imageSelectionUtil.initialize();
        marcaPopupUtil = new MarcaPopupUtil(marcaTextField, marcaSearchField);
        categoriaPopupUtil = new CategoriaPopupUtil(categoriaTextField, categoriaSearchField);
        unidadePopupUtil = new UnidadePopupUtil(unidadeTextField, unidadeSearchField);
        fornecedorPopupUtil = new FornecedorPopupUtil(fornecedorTextField, fornecedorSearchField);
        validateNumericFields();
        validatePriceFields();
        setCheckboxCondicao(true);
        campoMargemLucro.setDisable(checkboxCondicao);
        campoPrecoVenda.setDisable(!checkboxCondicao);
    }

    @FXML
    public void selectBoxMargem(){
        if(checkboxMargemLucro.isSelected()){
            campoMargemLucro.setDisable(!checkboxCondicao);
            campoPrecoVenda.setDisable(checkboxCondicao);
        }else{
            campoMargemLucro.setDisable(checkboxCondicao);
            campoPrecoVenda.setDisable(!checkboxCondicao);
        }
    }

    @FXML
    public void calculoPrecoVenda(KeyEvent event){
        campoPrecoCusto.setOnKeyTyped(this::handleKeyTyped);
        campoMargemLucro.setOnKeyTyped(this::handleKeyTyped);
    }
    private void handleKeyTyped(KeyEvent event) {
        Platform.runLater(() -> {
            Long precoCusto = Long.valueOf(campoPrecoCusto.getText());
            Long margemVenda;
            if (checkboxMargemLucro.isSelected()){
                margemVenda = Long.valueOf(campoMargemLucro.getText());
            }else{
                margemVenda = 0l;
            }
            Long precoVenda = precoCusto + ((precoCusto * margemVenda) / 100);
            campoPrecoVenda.setText(String.valueOf(precoVenda));
        });
    }

    @FXML
    public void selectImage(MouseEvent event) {
        imageSelectionUtil.selectImage(event);
    }

    @FXML
    public void setImage(File file) {
        imageSelectionUtil.setImage(file);
    }

    @FXML
    public void onMouseEntered(MouseEvent event) {
        imageSelectionUtil.onMouseEntered();
    }

    @FXML
    public void onMouseExited(MouseEvent event) {
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
    private void alternarPopupFornecedor(ActionEvent event) throws JSONException {
        fornecedorPopupUtil.togglePopup();
    }

    @FXML
    private void alternarPopup(ActionEvent event) throws JSONException {
        categoriaPopupUtil.togglePopup();
    }

    private void validateNumericFields() {
        NumericTextFieldValidator.validateNumericTextField(campoPesoLiquido);
        NumericTextFieldValidator.validateNumericTextField(campoPesoBruto);
        NumericTextFieldValidator.validateNumericTextField(campoCodigoBarras);
        NumericTextFieldValidator.validateNumericTextField(campoCodigoReferencia);
    }

    private void validatePriceFields() {
        PriceTextFieldFormatter.formatPriceTextField(campoPrecoCusto);
        PriceTextFieldFormatter.formatPriceTextField(campoPrecoVenda);
        PriceTextFieldFormatter.formatPriceTextField(campoMargemLucro);
    }

    @FXML
    void cancelarAcao(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
