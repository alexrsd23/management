package com.rosendo.company.Controllers.FXMLControllers.Fornecedor;

import com.rosendo.company.Utils.ApiRequestUtil;
import com.rosendo.company.Utils.FornecedorUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class CadastroFornecedorController {

    @FXML
    public TextField fornecedor, telefone, celular, cnpj, emailFornecedor, representante, inscricao, endereco;
    @FXML
    public TextArea observacoes;
    @FXML
    public Button buttonAtualizarEndereco;

    public int idEndereco;

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    @FXML
    public void initialize(Stage primaryStage) throws Exception {
        loadFXMLWindow();
    }

    @FXML
    void registrarFornecedor(ActionEvent event) throws JSONException {
        JSONObject requestData = FornecedorUtils.buildFornecedorJSON(this);
        JSONObject response = ApiRequestUtil.sendPostRequest("/supplier", requestData);

        if (response != null && response.has("id")) {
            showAlert("Fornecedor registrado com sucesso.");
            closeWindow(event);
        } else {
            showAlert("Erro ao registrar fornecedor");
        }
    }

    @FXML
    void cancelarAcao(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void openFXML() {
        FornecedorUtils.openCadastroEnderecoWindow(this);
    }

    @FXML
    void atualizarEndereco(ActionEvent event) throws IOException, JSONException {
        FornecedorUtils.atualizarEndereco(this);
    }

    @FXML
    public void atualizarLayout() {
        buttonAtualizarEndereco.setVisible(true);
        buttonAtualizarEndereco.setLayoutX(625);
        buttonAtualizarEndereco.setLayoutY(330);
        buttonAtualizarEndereco.setPrefWidth(100);
        buttonAtualizarEndereco.setPrefHeight(34);
        endereco.setPrefWidth(350);
    }

    private void showAlert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void loadFXMLWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Fornecedor/cadastro-fornecedor.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/style-cadastro-fornecedor.css")).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
