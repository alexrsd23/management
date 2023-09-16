package com.rosendo.company.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MarcaPopupUtil {

    private Popup popupMarca;
    private TextField marcaTextField;

    public MarcaPopupUtil(TextField marcaTextField) {
        this.marcaTextField = marcaTextField;
    }

    public void alternarPopupMarca() throws JSONException {
        if (popupMarca == null || !popupMarca.isShowing()) {
            criarPopupMarca();
            mostrarPopupMarca();
        } else {
            popupMarca.hide();
        }
    }

    private void criarPopupMarca() throws JSONException {
        popupMarca = new Popup();
        AnchorPane paneEmBrancoMarca = criarPaneEmBrancoMarca();
        Button abrirFxmlButtonMarca = criarBotaoAdicionarMarca();
        ListView<String> listView = criarListViewMarcas();

        paneEmBrancoMarca.getChildren().addAll(abrirFxmlButtonMarca, listView);
        paneEmBrancoMarca.setOnMouseClicked(e -> popupMarca.hide());
        popupMarca.setOnHidden(e -> destruirPopupMarca());
        popupMarca.getContent().add(paneEmBrancoMarca);
    }

    private AnchorPane criarPaneEmBrancoMarca() {
        AnchorPane paneEmBranco = new AnchorPane();
        paneEmBranco.setStyle("-fx-background-color: white; -fx-border-color:#d8d8d8");
        paneEmBranco.setPrefSize(250, 216);
        return paneEmBranco;
    }

    private Button criarBotaoAdicionarMarca() {
        Button abrirFxmlButton = new Button("Adicionar nova marca");
        abrirFxmlButton.setId("abrirFxmlButtonMarca");
        abrirFxmlButton.setLayoutX(2);
        abrirFxmlButton.setLayoutY(2);
        abrirFxmlButton.setOnAction(e -> abrirOutroFXMLMarca());
        return abrirFxmlButton;
    }

    private ListView<String> criarListViewMarcas() throws JSONException {
        ListView<String> listView = new ListView<>();
        listView.setLayoutX(2);
        listView.setLayoutY(40);
        listView.setPrefSize(246, 215);

        String response = ApiRequestUtil.sendGetRequest("/brand");

        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);
            ObservableList<String> brandNames = FXCollections.observableArrayList();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject brand = jsonArray.getJSONObject(i);
                String brandName = brand.getString("name");
                brandNames.add(brandName);
            }

            listView.setItems(brandNames);

            // Adicione um evento de clique aos itens da ListView
            listView.setOnMouseClicked(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Defina o valor no TextField
                    marcaTextField.setText(selectedItem);
                    // Feche o popup (se necessário)
                    if (popupMarca != null && popupMarca.isShowing()) {
                        popupMarca.hide();
                    }
                }
            });
        }

        return listView;
    }

    private void mostrarPopupMarca() {
        double xDesejado = 432.0;
        double yDesejado = 240.0;
        double xAtual = marcaTextField.getLayoutX();
        double yAtual = marcaTextField.getLayoutY();

        double diferencaX = xDesejado - xAtual;
        double diferencaY = yDesejado - yAtual;

        double xPopup = marcaTextField.getScene().getWindow().getX() + xAtual + diferencaX;
        double yPopup = marcaTextField.getScene().getWindow().getY() + yAtual + marcaTextField.getHeight() + diferencaY;

        popupMarca.show(marcaTextField.getScene().getWindow(), xPopup, yPopup);
    }

    private void destruirPopupMarca() {
        popupMarca = null;
    }

    private void abrirOutroFXMLMarca() {
        try {
            // Verifica se o popup está aberto e o fecha
            if (popupMarca != null && popupMarca.isShowing()) {
                popupMarca.hide();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cadastro/cadastro-marca.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Configura a janela principal como proprietário da nova janela
            stage.initOwner(marcaTextField.getScene().getWindow());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
