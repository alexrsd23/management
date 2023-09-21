package com.rosendo.company.Controllers.FXMLControllers.Endereco;

import com.rosendo.company.Controllers.FXMLControllers.Fornecedor.CadastroFornecedorController;
import com.rosendo.company.Domain.General.Adress.ConsultAdressDTO;
import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private TextField pais;
    @FXML
    private TextField numero;
    @FXML
    private TextField bairro;
    @FXML
    private TextField cidade;
    @FXML
    private TextField complemento;
    @FXML
    private TextArea observacao;

    @FXML
    private Button buttonAction;

    @FXML
    private Button buttonActionUpdate;

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
        String country = pais.getText();
        String number = numero.getText();
        String neighborhood = bairro.getText();
        String city = cidade.getText();
        String complement = complemento.getText();
        String observation = observacao.getText();

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
    void atualizarBotao(boolean condicao){
        buttonAction.setVisible(!condicao);
        buttonActionUpdate.setVisible(condicao);
    }

    @FXML
    void atualizarEndereco(ActionEvent event) throws JSONException {

        String endpointBuscado = "/adress";
        JSONObject endereco = ApiRequestUtil.sendGetRequestForSingleObject(endpointBuscado, enderecoId);
        String street = rua.getText();
        String postalCode = codigoPostal.getText();
        String state = estado.getText();
        String country = pais.getText();
        String number = numero.getText();
        String neighborhood = bairro.getText();
        String city = cidade.getText();
        String complement = complemento.getText();
        String observation = observacao.getText();

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

        JSONObject response = ApiRequestUtil.sendPutRequest(endpointBuscado, enderecoId, requestData);

        if (response != null) {
            mostrarAlerta("Endereço atualizado com sucesso.");
            fecharJanela(event);
        } else {
            mostrarAlerta("Erro ao atualizar endereço");
        }
    }

    @FXML
    public void atualizarEnderecoporId(ActionEvent event, int idEndereco) throws JSONException {
        System.out.println("entrei aqui no metodo");
        System.out.println(idEndereco);
        setEnderecoId(idEndereco);
        String endpointBuscado = "/adress";
        JSONObject endereco = ApiRequestUtil.sendGetRequestForSingleObject(endpointBuscado, idEndereco);
        System.out.println("recebi o objeto");
        System.out.println(endereco);
        rua.setText(endereco.getString("street"));
        codigoPostal.setText(endereco.getString("postalCode"));
        estado.setText(endereco.getString("state"));
        pais.setText(endereco.getString("country"));
        numero.setText(endereco.getString("number"));
        bairro.setText(endereco.getString("neighborhood"));
        cidade.setText(endereco.getString("city"));
        complemento.setText(endereco.getString("complement"));
        observacao.setText(endereco.getString("observation"));
        atualizarBotao(true);
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