package com.rosendo.company.Utils;

import com.rosendo.company.Controllers.FXMLControllers.Endereco.CadastroEnderecoController;
import com.rosendo.company.Controllers.FXMLControllers.Fornecedor.CadastroFornecedorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class FornecedorUtils {

    public static JSONObject buildFornecedorJSON(CadastroFornecedorController controller) throws JSONException {
        String endpointBuscado = "/adress";
        JSONObject endereco = ApiRequestUtil.sendGetRequestForSingleObject(endpointBuscado, controller.getIdEndereco());
        String name = controller.fornecedor.getText();
        String phoneOne = controller.telefone.getText();
        String phoneTwo = controller.celular.getText();
        String CNPJ = controller.cnpj.getText();
        String email = controller.emailFornecedor.getText();
        String representative = controller.representante.getText();
        String stateRegistration = controller.inscricao.getText();
        JSONObject address = endereco;
        String comments = controller.observacoes.getText();

        return new JSONObject()
                .put("name", name)
                .put("phoneOne", phoneOne)
                .put("phoneTwo", phoneTwo)
                .put("CNPJ", CNPJ)
                .put("email", email)
                .put("representative", representative)
                .put("stateRegistration", stateRegistration)
                .put("address", address)
                .put("comments", comments);
    }

    public static void openCadastroEnderecoWindow(CadastroFornecedorController controller) {
        try {
            FXMLLoader loader = loadFXML("/fxml/Endereco/cadastro-endereco.fxml");
            Parent root = loader.load();
            root.getStylesheets().add(Objects.requireNonNull(controller.getClass().getResource("/Css/style-cadastro-endereco.css")).toExternalForm());

            Stage stage = createStage(root);
            CadastroEnderecoController enderecoController = loader.getController();
            stage.showAndWait();

            updateControllerWithAddress(controller, enderecoController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarEndereco(CadastroFornecedorController controller) throws IOException, JSONException {
        FXMLLoader loader = loadFXML("/fxml/Endereco/cadastro-endereco.fxml");
        Parent root = loader.load();
        root.getStylesheets().add(Objects.requireNonNull(controller.getClass().getResource("/Css/style-cadastro-endereco.css")).toExternalForm());
        CadastroEnderecoController enderecoController = loader.getController();
        enderecoController.atualizarEnderecoporId(controller.idEndereco);

        Stage stage = createStage(root);
        stage.showAndWait();

        updateControllerWithAddress(controller, enderecoController);
    }

    private static FXMLLoader loadFXML(String fxmlPath) {
        return new FXMLLoader(FornecedorUtils.class.getResource(fxmlPath));
    }

    private static Stage createStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        return stage;
    }

    private static void updateControllerWithAddress(CadastroFornecedorController controller, CadastroEnderecoController enderecoController) {
        int enderecoId = enderecoController.getEnderecoId();
        controller.setIdEndereco(enderecoId);
        if (enderecoId != -1) {
            String rua = enderecoController.obterPropriedadePeloId(enderecoId, "rua");
            String numero = enderecoController.obterPropriedadePeloId(enderecoId, "numero");
            String bairro = enderecoController.obterPropriedadePeloId(enderecoId, "bairro");
            String cidade = enderecoController.obterPropriedadePeloId(enderecoId, "cidade");
            String enderecoCompleto = rua + ", Nº " + numero + ", " + bairro + " - " + cidade;
            controller.endereco.setText(enderecoCompleto);
            controller.atualizarLayout();
        }
    }
}
