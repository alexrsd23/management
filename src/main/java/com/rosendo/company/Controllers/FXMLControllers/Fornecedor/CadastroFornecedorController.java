package com.rosendo.company.Controllers.FXMLControllers.Fornecedor;

import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class CadastroFornecedorController {

    @FXML
    private TextField fornecedor;

    @FXML
    private TextField telefone;

    @FXML
    private TextField celular;

    @FXML
    private TextField cnpj;

    @FXML
    private TextField emailFornecedor;

    @FXML
    private TextField representante;

    @FXML
    private TextField inscricao;

    @FXML
    private TextField endereco;

    @FXML
    private TextArea observacoes;

    protected TextField textField;

    public void initialize(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Fornecedor/cadastro-fornecedor.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/style-cadastro-fornecedor.css")).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void registrarFornecedor(ActionEvent event) throws JSONException{
        String endpoint = "/supplier";
        String name = fornecedor.getText();

        String phoneOne = telefone.getText();

        String phoneTwo = celular.getText();

        String CNPJ = cnpj.getText();

        String email = emailFornecedor.getText();

        String representative = representante.getText();

        String stateRegistration = inscricao.getText();

        String adress = endereco.getText();

        String comments = observacoes.getText();

        JSONObject requestData = new JSONObject()
                .put("name", name)
                .put("phoneOne", phoneOne)
                .put("phoneTwo", phoneTwo)
                .put("CNPJ", CNPJ)
                .put("email", email)
                .put("representative", representative)
                .put("stateRegistration", stateRegistration)
                .put("adress", adress)
                .put("comments", comments);

        JSONObject response = ApiRequestUtil.sendPostRequest(endpoint, requestData);

        if (response != null && response.has("id")) {
            int enderecoId = response.getInt("id");
            mostrarAlerta("Endereço registrado com sucesso. ID do endereço: " + enderecoId);
            fecharJanela(event);
        } else {
            mostrarAlerta("Erro ao registrar endereço");
        }
    }

    @FXML
    void cancelarAcao(ActionEvent event) {
        fecharJanela(event);
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Endereco/cadastro-endereco.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/style-cadastro-endereco.css")).toExternalForm());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}