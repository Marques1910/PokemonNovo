package com.template;

import com.template.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import service.IPokemonService;
import service.PokemonService;

import validator.IPokemonValidador;
import validator.PokemonValidador;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        /*
         * Criamos as implementações concretas.
         */
        IPokemonService pokemonService =
                new PokemonService();

        IPokemonValidador pokemonValidador =
                new PokemonValidador();


        FXMLLoader loader =
                new FXMLLoader(
                        Main.class.getResource(
                                "/com/template/main.fxml"
                        )
                );


        /*
         * Quando o FXMLLoader precisar criar
         * o MainController, fornecemos as
         * dependências pelo construtor.
         */
        loader.setControllerFactory(
                controllerClass -> {

                    if (controllerClass
                            == MainController.class) {

                        return new MainController(
                                pokemonService,
                                pokemonValidador
                        );
                    }

                    try {

                        return controllerClass
                                .getDeclaredConstructor()
                                .newInstance();

                    } catch (Exception e) {

                        throw new RuntimeException(e);
                    }
                }
        );


        Scene scene =
                new Scene(
                        loader.load(),
                        600,
                        400
                );

        stage.setTitle(
                "PokémonNovo"
        );

        stage.setScene(scene);

        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}