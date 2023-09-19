package com.rosendo.company.Controllers.FXMLControllers.Endereco;

import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

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

        // Enviar a solicitação POST e obter a resposta usando a nova versão da ApiRequestUtil
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
}
