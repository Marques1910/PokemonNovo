package com.template.controller;

import com.template.model.dto.PokemonDTO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import service.IPokemonService;
import validator.IPokemonValidador;

import static com.template.util.DialogUtil.showError;

public class MainController {

    private final IPokemonService pokemonService;

    private final IPokemonValidador pokemonValidador;

    public MainController(
            IPokemonService pokemonService,
            IPokemonValidador pokemonValidador
    ) {

        this.pokemonService = pokemonService;
        this.pokemonValidador = pokemonValidador;
    }

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtNum;

    @FXML
    private TextField txtGeracao;

    @FXML
    private TableView<PokemonDTO> tblPokemon;

    @FXML
    private TableColumn<PokemonDTO, Integer> colGeracao;

    @FXML
    private TableColumn<PokemonDTO, String> colTipo;

    @FXML
    private TableColumn<PokemonDTO, Integer> colNum;

    @FXML
    private TableColumn<PokemonDTO, String> colNome;


    @FXML
    private void initialize() {

        colGeracao.setCellValueFactory(
                new PropertyValueFactory<>("geracao")
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipo")
        );

        colNum.setCellValueFactory(
                new PropertyValueFactory<>("numero")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        /*
         * Quando o usuário seleciona um Pokémon,
         * os campos são preenchidos.
         */
        tblPokemon
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, selecionado) -> {

                            if (selecionado != null) {
                                carregarCampos(selecionado);
                            }
                        }
                );

        carregarTabela();
    }


    @FXML
    private void btnAdicionarClick() {

        if (!validarCampos()) {
            return;
        }

        PokemonDTO pokemon =
                criarPokemonDosCampos();

        pokemonService.cadastrarPokemon(
                pokemon
        );

        limparCampos();

        carregarTabela();
    }


    @FXML
    private void btnEditarClick() {

        PokemonDTO pokemonSelecionado =
                tblPokemon
                        .getSelectionModel()
                        .getSelectedItem();

        if (pokemonSelecionado == null) {

            showError(
                    "Selecione um Pokémon na tabela para editar."
            );

            return;
        }

        if (!validarCampos()) {
            return;
        }

        /*
         * Precisamos guardar o número antigo,
         * pois o usuário também pode alterar
         * o número do Pokémon.
         */
        int numeroOriginal =
                pokemonSelecionado.getNumero();

        PokemonDTO pokemonEditado =
                criarPokemonDosCampos();

        pokemonService.alterarPokemon(
                pokemonEditado,
                numeroOriginal
        );

        limparCampos();

        carregarTabela();
    }


    @FXML
    private void btnExcluirClick() {

        PokemonDTO pokemonSelecionado =
                tblPokemon
                        .getSelectionModel()
                        .getSelectedItem();

        if (pokemonSelecionado == null) {

            showError(
                    "Selecione um Pokémon na tabela para excluir."
            );

            return;
        }

        pokemonService.excluirPokemon(
                pokemonSelecionado.getNumero()
        );

        limparCampos();

        carregarTabela();
    }


    private boolean validarCampos() {

        return pokemonValidador.validarPokemon(
                txtNome.getText(),
                txtTipo.getText(),
                txtNum.getText(),
                txtGeracao.getText()
        );
    }


    private PokemonDTO criarPokemonDosCampos() {

        PokemonDTO pokemon =
                new PokemonDTO();

        pokemon.setNome(
                txtNome.getText()
        );

        pokemon.setTipo(
                txtTipo.getText()
        );

        pokemon.setNumero(
                Integer.parseInt(
                        txtNum.getText()
                )
        );

        pokemon.setGeracao(
                Integer.parseInt(
                        txtGeracao.getText()
                )
        );

        return pokemon;
    }


    private void carregarTabela() {

        tblPokemon.setItems(
                FXCollections.observableArrayList(
                        pokemonService.listarPokemons()
                )
        );
    }


    private void carregarCampos(
            PokemonDTO pokemon
    ) {

        txtNome.setText(
                pokemon.getNome()
        );

        txtTipo.setText(
                pokemon.getTipo()
        );

        txtNum.setText(
                String.valueOf(
                        pokemon.getNumero()
                )
        );

        txtGeracao.setText(
                String.valueOf(
                        pokemon.getGeracao()
                )
        );
    }


    private void limparCampos() {

        txtNome.clear();

        txtTipo.clear();

        txtNum.clear();

        txtGeracao.clear();

        tblPokemon
                .getSelectionModel()
                .clearSelection();
    }
}