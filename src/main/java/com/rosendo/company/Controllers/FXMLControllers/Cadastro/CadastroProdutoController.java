package com.rosendo.company.Controllers.FXMLControllers.Cadastro;

import com.rosendo.company.Utils.ApiRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class CadastroProdutoController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label messageLabel;
    @FXML
    private Label messageLabelOne;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField categoriaTextField;

    private ColorAdjust grayscaleEffect = new ColorAdjust();
    private Popup popup;

    public void initialize() {
        grayscaleEffect.setSaturation(-0.5);
        messageLabel.setVisible(false);
        messageLabelOne.setVisible(true);
        anchorPane.setOnMouseClicked(this::selectImage);
    }

    public void selectImage(MouseEvent event) {
        File selectedFile = showImageFileChooser();
        if (selectedFile != null) {
            setImage(selectedFile);
            hideLabelsAndRemoveEffect();
        }
    }

    public File showImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        return fileChooser.showOpenDialog(new Stage());
    }

    public void setImage(File file) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    public void hideLabelsAndRemoveEffect() {
        messageLabel.setVisible(false);
        messageLabelOne.setVisible(false);
        imageView.setEffect(null);
    }

    @FXML
    public void onMouseEntered(MouseEvent event) {
        if (imageView.getImage() != null) {
            imageView.setEffect(grayscaleEffect);
            messageLabel.setVisible(true);
            messageLabelOne.setVisible(false);
        }
    }

    @FXML
    public void onMouseExited(MouseEvent event) {
        if (imageView.getImage() != null) {
            imageView.setEffect(null);
            messageLabel.setVisible(false);
            messageLabelOne.setVisible(false);
        }
    }

    @FXML
    private void alternarPopup(ActionEvent event) throws JSONException {
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

    @FXML
    void cancelarAcao(ActionEvent event) {
        // Obtém a referência ao nó (botão "Cancelar") que disparou o evento
        Node source = (Node) event.getSource();

        // Obtém a referência à janela atual
        Stage stage = (Stage) source.getScene().getWindow();

        // Fecha a janela
        stage.close();
    }
}
