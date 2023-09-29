package com.rosendo.company.Utils;

import com.rosendo.company.Controllers.FXMLControllers.Endereco.CadastroEnderecoController;
import com.rosendo.company.Domain.General.Adress.ConsultAdressDTO;
import javafx.event.ActionEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnderecoUtils {

    public static JSONObject buildEnderecoJSON(CadastroEnderecoController controller) throws JSONException {
        return createEnderecoJSON(controller);
    }

    public static void atualizarEndereco(CadastroEnderecoController controller, int enderecoId, ActionEvent event) throws JSONException {
        updateEndereco(controller, enderecoId, event);
    }

    public static void atualizarEnderecoporId(CadastroEnderecoController controller, int idEndereco) throws JSONException {
        updateEnderecoPorId(controller, idEndereco);
    }

    public static String obterPropriedadePeloId(int enderecoId, String propriedade) {
        return retrievePropertyById(enderecoId, propriedade);
    }

    private static JSONObject createEnderecoJSON(CadastroEnderecoController controller) throws JSONException {
        String street = controller.rua.getText();
        String postalCode = controller.codigoPostal.getText();
        String state = controller.estado.getText();
        String country = controller.pais.getText();
        String number = controller.numero.getText();
        String neighborhood = controller.bairro.getText();
        String city = controller.cidade.getText();
        String complement = controller.complemento.getText();
        String observation = controller.observacao.getText();

        return new JSONObject()
                .put("street", street)
                .put("postalCode", postalCode)
                .put("state", state)
                .put("country", country)
                .put("number", number)
                .put("neighborhood", neighborhood)
                .put("city", city)
                .put("complement", complement)
                .put("observation", observation);
    }

    private static void updateEndereco(CadastroEnderecoController controller, int enderecoId, ActionEvent event) throws JSONException {
        JSONObject endereco = retrieveEnderecoById(enderecoId, controller);
        JSONObject requestData = createEnderecoJSON(controller);

        String endpointBuscado = "/adress";
        JSONObject response = ApiRequestUtil.sendPutRequest(endpointBuscado, enderecoId, requestData);

        if (response != null) {
            controller.mostrarAlerta("Endereço atualizado com sucesso.");
            controller.fecharJanela(event);
        } else {
            controller.mostrarAlerta("Erro ao atualizar endereço");
        }
    }

    private static void updateEnderecoPorId(CadastroEnderecoController controller, int idEndereco) throws JSONException {
        controller.setEnderecoId(String.valueOf(idEndereco));
        JSONObject endereco = retrieveEnderecoById(idEndereco, controller);

        controller.rua.setText(endereco.getString("street"));
        controller.codigoPostal.setText(endereco.getString("postalCode"));
        controller.estado.setText(endereco.getString("state"));
        controller.pais.setText(endereco.getString("country"));
        controller.numero.setText(endereco.getString("number"));
        controller.bairro.setText(endereco.getString("neighborhood"));
        controller.cidade.setText(endereco.getString("city"));
        controller.complemento.setText(endereco.getString("complement"));
        controller.observacao.setText(endereco.getString("observation"));
        controller.atualizarBotao(true);
    }

    private static String retrievePropertyById(int enderecoId, String propriedade) {
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
                }
            }
        }
        return "";
    }

    private static List<ConsultAdressDTO> obterListaDeEnderecos() {
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

    private static JSONObject retrieveEnderecoById(int enderecoId, CadastroEnderecoController controller) throws JSONException {
        String endpointBuscado = "/adress";
        return ApiRequestUtil.sendGetRequestForSingleObject(endpointBuscado, enderecoId);
    }
}
