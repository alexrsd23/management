package com.rosendo.company.Controllers.FXMLControllers.Endereco;

import com.rosendo.company.Controllers.FXMLControllers.Fornecedor.CadastroFornecedorController;
import com.rosendo.company.Utils.ApiRequestUtil;
import com.rosendo.company.Utils.EnderecoUtils;
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

public class CadastroEnderecoController {

    @FXML
    public TextField rua, codigoPostal, estado, pais, numero, bairro, cidade, complemento;
    @FXML
    public TextArea observacao;
    @FXML
    private Button buttonAction, buttonActionUpdate;

    private String enderecoId;
    private CadastroFornecedorController parentController;

    public void setParentController(CadastroFornecedorController parentController) {
        this.parentController = parentController;
    }

    public String getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    @FXML
    void registrarEndereco(ActionEvent event) throws JSONException {
        JSONObject requestData = EnderecoUtils.buildEnderecoJSON(this);
        JSONObject response = ApiRequestUtil.sendPostRequest("/adress", requestData);

        if (response != null && response.has("id")) {
            setEnderecoId(String.valueOf(response.getInt("id")));
            mostrarAlerta("Endereço registrado com sucesso.");
            fecharJanela(event);
        } else {
            mostrarAlerta("Erro ao registrar endereço");
        }
    }

    @FXML
    public void atualizarBotao(boolean condicao) {
        buttonAction.setVisible(!condicao);
        buttonActionUpdate.setVisible(condicao);
    }

    @FXML
    void atualizarEndereco(ActionEvent event) throws JSONException {
        EnderecoUtils.atualizarEndereco(this, Integer.parseInt(enderecoId), event);
    }

    @FXML
    public void atualizarEnderecoporId(int idEndereco) throws JSONException {
        EnderecoUtils.atualizarEnderecoporId(this, idEndereco);
    }

    @FXML
    void cancelarAcao(ActionEvent event) {
        fecharJanela(event);
    }

    public void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void fecharJanela(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public String obterPropriedadePeloId(int enderecoId, String propriedade) {
        return EnderecoUtils.obterPropriedadePeloId(enderecoId, propriedade);
    }
}
