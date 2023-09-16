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

public class CategoriaPopupUtil {

    private Popup popup;
    private TextField categoriaTextField; // Certifique-se de ter esse campo no FXML

    public CategoriaPopupUtil(TextField categoriaTextField) {
        this.categoriaTextField = categoriaTextField;
    }

    public void alternarPopup() throws JSONException {
        if (popup == null || !popup.isShowing()) {
            criarPopup();
            mostrarPopup();
        } else {
            popup.hide();
        }
    }

    private void criarPopup() throws JSONException {
        popup = new Popup();
        AnchorPane paneEmBranco = criarPaneEmBranco();
        Button abrirFxmlButton = criarBotaoAdicionarCategoria();
        ListView<String> listView = criarListViewCategorias();

        paneEmBranco.getChildren().addAll(abrirFxmlButton, listView);
        paneEmBranco.setOnMouseClicked(e -> popup.hide());
        popup.setOnHidden(e -> destruirPopup());
        popup.getContent().add(paneEmBranco);
    }

    private AnchorPane criarPaneEmBranco() {
        AnchorPane paneEmBranco = new AnchorPane();
        paneEmBranco.setStyle("-fx-background-color: white; -fx-border-color:#d8d8d8");
        paneEmBranco.setPrefSize(250, 216);
        return paneEmBranco;
    }

    private Button criarBotaoAdicionarCategoria() {
        Button abrirFxmlButton = new Button("Adicionar nova categoria");
        abrirFxmlButton.setId("abrirFxmlButton");
        abrirFxmlButton.setLayoutX(2);
        abrirFxmlButton.setLayoutY(2);
        abrirFxmlButton.setOnAction(e -> abrirOutroFXML());
        return abrirFxmlButton;
    }

    private ListView<String> criarListViewCategorias() throws JSONException {
        ListView<String> listView = new ListView<>();
        listView.setLayoutX(2);
        listView.setLayoutY(40);
        listView.setPrefSize(246, 215);

        String response = ApiRequestUtil.sendGetRequest("/category");

        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);
            ObservableList<String> categoryNames = FXCollections.observableArrayList();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject category = jsonArray.getJSONObject(i);
                String categoryName = category.getString("name");
                categoryNames.add(categoryName);
            }

            listView.setItems(categoryNames);

            // Adicione um evento de clique aos itens da ListView
            listView.setOnMouseClicked(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Defina o valor no TextField
                    categoriaTextField.setText(selectedItem);
                    // Feche o popup (se necessário)
                    if (popup != null && popup.isShowing()) {
                        popup.hide();
                    }
                }
            });
        }

        return listView;
    }

    private void mostrarPopup() {
        double xDesejado = 182.0;
        double yDesejado = 240.0;
        double xAtual = categoriaTextField.getLayoutX();
        double yAtual = categoriaTextField.getLayoutY();

        double diferencaX = xDesejado - xAtual;
        double diferencaY = yDesejado - yAtual;

        double xPopup = categoriaTextField.getScene().getWindow().getX() + xAtual + diferencaX;
        double yPopup = categoriaTextField.getScene().getWindow().getY() + yAtual + categoriaTextField.getHeight() + diferencaY;

        popup.show(categoriaTextField.getScene().getWindow(), xPopup, yPopup);
    }

    private void destruirPopup() {
        popup = null;
    }

    private void abrirOutroFXML() {
        try {
            // Verifica se o popup está aberto e o fecha
            if (popup != null && popup.isShowing()) {
                popup.hide();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cadastro/cadastro-categoria.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Configura a janela principal como proprietário da nova janela
            stage.initOwner(categoriaTextField.getScene().getWindow());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
