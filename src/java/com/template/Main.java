package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import validator.IPokemonValidador;
import validator.PokemonValidador;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        IPokemonValidador pokemonValidador = new PokemonValidador();

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