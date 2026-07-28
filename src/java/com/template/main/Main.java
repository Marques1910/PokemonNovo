package com.template.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        // Adicionada a barra "/" para buscar na raiz do resources
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/template/main.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}