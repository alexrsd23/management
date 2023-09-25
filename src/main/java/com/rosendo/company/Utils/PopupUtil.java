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
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public abstract class PopupUtil {

    protected TextField textField;
    protected Popup popup;
    protected String fxmlFileName;

    protected String cssFileName;
    protected TextField searchField; // Campo de pesquisa
    protected ListView<String> listView; // ListView
    protected ObservableList<String> itemNames; // Lista de itens

    protected ObservableList<String> filteredItemNames;


    // Construtor
    public PopupUtil(TextField textField, String fxmlFileName, TextField searchField, String cssFileName) {
        this.textField = textField;
        this.popup = new Popup();
        this.fxmlFileName = fxmlFileName;
        this.cssFileName = cssFileName;
        this.searchField = searchField;
        this.filteredItemNames = FXCollections.observableArrayList();
    }



    public void togglePopup() throws JSONException {
        if (popup == null || !popup.isShowing()) {
            createPopup();
            showPopup();
        } else {
            popup.hide();
        }
    }

    protected void createPopup() throws JSONException {
        popup = new Popup();
        AnchorPane blankPane = createBlankPane();
        Button addButton = createAddButton();
        ListView<String> listView = createListView();

        // Adicione o campo de busca
        searchField = createSearchField();
        blankPane.getChildren().addAll(addButton, searchField, listView);

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

    protected TextField createSearchField() {
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar...");
        searchField.setId("buscaLista");
        searchField.setLayoutX(2);
        searchField.setLayoutY(30); // Posição abaixo do botão e acima da lista
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterList(newValue);
        });
        return searchField;
    }

    protected ListView<String> createListView() throws JSONException {
        listView = new ListView<>();
        listView.setLayoutX(2);
        listView.setLayoutY(60); // Posição abaixo do campo de busca
        listView.setPrefSize(248, 175);

        String response = ApiRequestUtil.sendGetRequest(getApiEndpoint());

        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);
            itemNames = FXCollections.observableArrayList(); // Remova esta linha
            filteredItemNames = FXCollections.observableArrayList(); // Adicione esta linha

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String itemName = item.getString("name");
                itemNames.add(itemName);
                filteredItemNames.add(itemName); // Adicione o item à lista filtrada
            }

            listView.setItems(filteredItemNames); // Altere para usar a lista filtrada

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFileName));
            Parent root = loader.load();
            root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/"+ cssFileName)).toExternalForm());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(textField.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para filtrar a lista com base no texto do campo de busca
    private void filterList(String filter) {
        if (filter.isEmpty()) {
            // Se o filtro estiver vazio, exiba a lista completa
            listView.setItems(itemNames);
        } else {
            // Use a função de filtragem para exibir apenas os itens correspondentes
            filteredItemNames.clear();
            filteredItemNames.addAll(itemNames.filtered(item -> item.contains(filter)));
            listView.setItems(filteredItemNames);
        }
    }

}
