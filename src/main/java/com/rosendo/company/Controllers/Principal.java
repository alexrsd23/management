package com.rosendo.company.Controllers;


import com.rosendo.company.Controllers.FXMLControllers.Fornecedor.CadastroFornecedorController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Cadastro/cadastro-produto.fxml")));
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Css/style-cadastro-produto.css")).toExternalForm());
//
//        primaryStage.setResizable(false);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        CadastroFornecedorController cadastroFornecedorController = new CadastroFornecedorController();
        cadastroFornecedorController.initialize(primaryStage );
    }
}