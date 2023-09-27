package com.rosendo.company.Controllers.FXMLControllers.Cadastro;

import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class CadastroUnidadeController {

    @FXML
    private TextField nomeUnidadeField;

    @FXML
    private Button registrarButton;

    @FXML
    void registrarUnidade(ActionEvent event) throws JSONException {
        String endpoint = "/unity";
        String nomeUnidade = nomeUnidadeField.getText();

        JSONObject requestData = new JSONObject()
                .put("name", nomeUnidade);

        JSONObject response = ApiRequestUtil.sendPostRequest(endpoint, requestData);

        if (response != null && response.has("id")) {
            mostrarAlerta("Unidade registrada com sucesso." );
            fecharJanela(event);
        } else {
            mostrarAlerta("Erro ao registrar unidade");
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
