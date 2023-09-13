package com.rosendo.company.Controllers.Fxml.Cadastro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class CadastroProdutoController {
    @FXML private ImageView imageView;
    @FXML private Label messageLabel;
    @FXML private Label messageLabelOne;
    @FXML private AnchorPane anchorPane;

    private ColorAdjust grayscaleEffect = new ColorAdjust();

    public void initialize() {
        grayscaleEffect.setSaturation(-0.5);
        messageLabel.setVisible(false);
        messageLabelOne.setVisible(true);
        anchorPane.setOnMouseClicked(this::selectImage);
    }

    private void selectImage(MouseEvent event) {
        File selectedFile = showImageFileChooser();
        if (selectedFile != null) {
            setImage(selectedFile);
            hideLabelsAndRemoveEffect();
        }
    }

    private File showImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        return fileChooser.showOpenDialog(new Stage());
    }

    private void setImage(File file) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    private void hideLabelsAndRemoveEffect() {
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
}
