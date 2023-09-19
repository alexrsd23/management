package com.rosendo.company.Controllers.FXMLControllers.Endereco;

import com.rosendo.company.Controllers.FXMLControllers.Fornecedor.CadastroFornecedorController;
import com.rosendo.company.Domain.General.Adress.ConsultAdressDTO;
import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private int enderecoId;

    private CadastroFornecedorController parentController;

    public void setParentController(CadastroFornecedorController parentController) {
        this.parentController = parentController;
    }

    public int getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(int enderecoId) {
        this.enderecoId = enderecoId;
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

        JSONObject response = ApiRequestUtil.sendPostRequest(endpoint, requestData);

        if (response != null && response.has("id")) {
            setEnderecoId(response.getInt("id"));
            mostrarAlerta("Endereço registrado com sucesso.");
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

    public String obterPropriedadePeloId(int enderecoId, String propriedade) {
        List<ConsultAdressDTO> listaDeEnderecos = obterListaDeEnderecos();
        for (ConsultAdressDTO endereco : listaDeEnderecos) {
            if (endereco.getId() == enderecoId) {
                switch (propriedade) {
                    case "rua":
                        return endereco.getStreet();
                    case "numero":
                        return String.valueOf(endereco.getNumber());
                    case "bairro":
                        return endereco.getNeighborhood();
                    case "cidade":
                        return endereco.getCity();
                    // Adicione outros casos conforme necessário para mais propriedades
                }
            }
        }
        return "";
    }

    private List<ConsultAdressDTO> obterListaDeEnderecos() {
        String endpoint = "/adress";
        String resposta = ApiRequestUtil.sendGetRequest(endpoint);

        if (resposta != null) {
            try {
                JSONArray jsonArray = new JSONArray(resposta);
                List<ConsultAdressDTO> listaDeEnderecos = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject enderecoJson = jsonArray.getJSONObject(i);
                    Long id = (long) enderecoJson.getInt("id");
                    String nomeDaRua = enderecoJson.getString("street");
                    String bairro = enderecoJson.getString("neighborhood");
                    Integer numero = enderecoJson.getInt("number");
                    String estado = enderecoJson.getString("state");
                    String cidade = enderecoJson.getString("city");

                    ConsultAdressDTO endereco = new ConsultAdressDTO(id, nomeDaRua, bairro, numero, estado, cidade);

                    listaDeEnderecos.add(endereco);
                }

                return listaDeEnderecos;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }
}
