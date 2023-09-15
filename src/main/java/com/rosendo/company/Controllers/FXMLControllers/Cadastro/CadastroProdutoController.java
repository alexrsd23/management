package com.rosendo.company.Controllers.FXMLControllers.Cadastro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CadastroProdutoController {
    @FXML private ImageView imageView;
    @FXML private Label messageLabel;
    @FXML private Label messageLabelOne;
    @FXML private AnchorPane anchorPane;

    private ColorAdjust grayscaleEffect = new ColorAdjust();
    private Popup popup;
    private boolean popupVisible = false;

    @FXML private TextField categoriaTextField;

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
    private void alternarPopup(ActionEvent event) {
        if (popup == null || !popup.isShowing()) {
            criarPopup();
            mostrarPopup();
        } else {
            popup.hide();
        }
    }

    private void criarPopup() {
        popup = new Popup();
        AnchorPane paneEmBranco = new AnchorPane();
        paneEmBranco.setStyle("-fx-background-color: white; -fx-border-color:#d8d8d8");
        paneEmBranco.setPrefSize(200, 100);

        Button abrirFxmlButton = new Button("Abrir Outro FXML");
        abrirFxmlButton.setLayoutX(10.0);
        abrirFxmlButton.setLayoutY(10.0);
        abrirFxmlButton.setOnAction(e -> abrirOutroFXML());

        paneEmBranco.getChildren().add(abrirFxmlButton);
        paneEmBranco.setOnMouseClicked(e -> popup.hide());
        popup.setOnHidden(e -> destruirPopup()); // Destruir o popup quando ele for fechado
        popup.getContent().add(paneEmBranco);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cadastro/cadastro-categoria.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}