package com.rosendo.company.Controllers.FXMLControllers.Fornecedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CadastroFornecedorController {
    public void initialize(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Fornecedor/cadastro-fornecedor.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/style-cadastro-fornecedor.css")).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
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
