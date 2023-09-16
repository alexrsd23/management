package com.rosendo.company.Utils;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImageSelectionUtil {

    private ImageView imageView;
    private Label messageLabel;
    private Label messageLabelOne;

    private AnchorPane anchorPane;
    private ColorAdjust grayscaleEffect = new ColorAdjust();

    public ImageSelectionUtil(ImageView imageView, Label messageLabel, Label messageLabelOne, AnchorPane anchorPane) {
        this.imageView = imageView;
        this.messageLabel = messageLabel;
        this.messageLabelOne = messageLabelOne;
        this.anchorPane = anchorPane; // Atribua a referência ao anchorPane
        grayscaleEffect.setSaturation(-0.5);
        anchorPane.setOnMouseClicked(this::selectImage);
    }

    public void initialize() {
        messageLabel.setVisible(false);
        messageLabelOne.setVisible(true);
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

    public void onMouseEntered() {
        if (imageView.getImage() != null) {
            imageView.setEffect(grayscaleEffect);
            messageLabel.setVisible(true);
            messageLabelOne.setVisible(false);
        }
    }

    public void onMouseExited() {
        if (imageView.getImage() != null) {
            imageView.setEffect(null);
            messageLabel.setVisible(false);
            messageLabelOne.setVisible(false);
        }
    }
}
