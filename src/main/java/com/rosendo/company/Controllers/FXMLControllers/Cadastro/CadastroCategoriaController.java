package com.rosendo.company.Controllers.FXMLControllers.Cadastro;

import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class CadastroCategoriaController {

    @FXML
    private TextField nomeCategoriaField;

    @FXML
    private TextArea descricaoArea;

    @FXML
    private Button registrarButton;

    @FXML
    void registrarCategoria(ActionEvent event) throws JSONException {
        String endpoint = "/category";
        String nomeCategoria = nomeCategoriaField.getText();
        String descricao = descricaoArea.getText();

        JSONObject requestData = new JSONObject()
                .put("name", nomeCategoria)
                .put("description", descricao);

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
