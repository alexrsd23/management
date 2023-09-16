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

public abstract class PopupUtil {

    protected TextField textField;
    protected Popup popup;

    protected String fxmlFileName; // Campo para armazenar o nome do arquivo FXML

    public PopupUtil(TextField textField, String fxmlFileName) {
        this.textField = textField;
        this.popup = new Popup();
        this.fxmlFileName = fxmlFileName;
    }

    public void togglePopup() throws JSONException {
        if (popup == null || !popup.isShowing()) {
            createPopup();
            showPopup(); // Chame o método protegido showPopup
        } else {
            popup.hide();
        }
    }

    protected void createPopup() throws JSONException {
        popup = new Popup();
        AnchorPane blankPane = createBlankPane();
        Button addButton = createAddButton();
        ListView<String> listView = createListView();

        blankPane.getChildren().addAll(addButton, listView);
        blankPane.setOnMouseClicked(e -> popup.hide());
        popup.setOnHidden(e -> destroyPopup());
        popup.getContent().add(blankPane);
    }

    protected AnchorPane createBlankPane() {
        AnchorPane blankPane = new AnchorPane();
        blankPane.setStyle("-fx-background-color: white; -fx-border-color:#d8d8d8");
        blankPane.setPrefSize(250, 216);
        return blankPane;
    }

    protected Button createAddButton() {
        Button addButton = new Button(getAddButtonText());
        addButton.setId(getAddButtonId());
        addButton.setLayoutX(2);
        addButton.setLayoutY(2);
        addButton.setOnAction(e -> openFXML());
        return addButton;
    }

    protected ListView<String> createListView() throws JSONException {
        ListView<String> listView = new ListView<>();
        listView.setLayoutX(2);
        listView.setLayoutY(40);
        listView.setPrefSize(246, 215);

        String response = ApiRequestUtil.sendGetRequest(getApiEndpoint());

        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);
            ObservableList<String> itemNames = FXCollections.observableArrayList();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String itemName = item.getString("name");
                itemNames.add(itemName);
            }

            listView.setItems(itemNames);

            listView.setOnMouseClicked(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    textField.setText(selectedItem);
                    if (popup != null && popup.isShowing()) {
                        popup.hide();
                    }
                }
            });
        }

        return listView;
    }

    protected void showPopup() {
        double desiredX = getDesiredX();
        double desiredY = getDesiredY();
        double currentX = textField.getLayoutX();
        double currentY = textField.getLayoutY();

        double diffX = desiredX - currentX;
        double diffY = desiredY - currentY;

        double popupX = textField.getScene().getWindow().getX() + currentX + diffX;
        double popupY = textField.getScene().getWindow().getY() + currentY + textField.getHeight() + diffY;

        popup.show(textField.getScene().getWindow(), popupX, popupY);
    }

    protected void destroyPopup() {
        popup = null;
    }

    protected abstract String getApiEndpoint();

    protected abstract String getAddButtonText();

    protected abstract String getAddButtonId();

    protected abstract double getDesiredX();

    protected abstract double getDesiredY();

    protected void openFXML() {
        try {
            if (popup != null && popup.isShowing()) {
                popup.hide();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cadastro/" + fxmlFileName));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(textField.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}