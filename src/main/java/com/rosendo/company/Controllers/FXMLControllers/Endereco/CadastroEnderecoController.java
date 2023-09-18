package com.rosendo.company.Controllers.FXMLControllers.Endereco;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class CadastroEnderecoController {
    @FXML
    private TextField rua;
    @FXML
    private TextField codigoPostal;
    @FXML
    private TextField estado;
    @FXML
    private TextField país;
    @FXML
    private TextField número;
    @FXML
    private TextField bairro;
    @FXML
    private TextField cidade;
    @FXML
    private TextField complemento;
    @FXML
    private TextArea observação;

    public void initialize(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Endereco/cadastro-endereco.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/style-cadastro-endereco.css")).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void registrarEndereco(ActionEvent event) throws JSONException {
        String endpoint = "/adress";
        String street = rua.getText();
        String postalCode = codigoPostal.getText();
        String state = estado.getText();
        String country = país.getText();
        String number = número.getText();
        String neighborhood = bairro.getText();
        String city = cidade.getText();
        String complement = complemento.getText();
        String observation = observação.getText();

        JSONObject requestData = new JSONObject()
                .put("street", street)
                .put("postalCode", postalCode)
                .put("state", state)
                .put("country", country)
                .put("number", number)
                .put("neighborhood", neighborhood)
                .put("city", city)
                .put("complement", complement)
                .put("observation", observation);

        boolean registroBemSucedido = ApiRequestUtil.sendPostRequest(endpoint, requestData);

        if (registroBemSucedido) {
            mostrarAlerta("Registro realizado com sucesso");
            fecharJanela(event);
        } else {
            mostrarAlerta("Erro ao registrar categoria");
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
}
