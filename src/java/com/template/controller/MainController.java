package com.template.controller;

import com.template.model.dto.PokemonDTO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javafx.scene.control.cell.PropertyValueFactory;

import service.IPokemonService;
import validator.IPokemonValidador;

import java.util.Locale;

import static com.template.util.DialogUtil.showConfirmation;

public class MainController {

    private final IPokemonService pokemonService;

    private final IPokemonValidador pokemonValidador;

    private final ObservableList<PokemonDTO> listaPokemon =
            FXCollections.observableArrayList();

    private final FilteredList<PokemonDTO> listaFiltrada =
            new FilteredList<>(
                    listaPokemon,
                    pokemon -> true
            );

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNum;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private Spinner<Integer> spGeracao;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private Label lblMensagem;

    @FXML
    private Label lblContador;

    @FXML
    private Label lblModo;

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

    public MainController(
            IPokemonService pokemonService,
            IPokemonValidador pokemonValidador
    ) {

        this.pokemonService = pokemonService;
        this.pokemonValidador = pokemonValidador;
    }

    @FXML
    private void initialize() {

        configurarTabela();
        configurarTipos();
        configurarGeracao();
        configurarCampoNumero();
        configurarPesquisa();
        configurarSelecao();

        tblPokemon.setItems(listaFiltrada);

        configurarModoCadastro();

        carregarTabela();

        Platform.runLater(
                txtNome::requestFocus
        );
    }

    private void configurarTabela() {

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
    }

    private void configurarTipos() {

        cbTipo.setItems(
                FXCollections.observableArrayList(
                        "Normal",
                        "Fogo",
                        "Água",
                        "Elétrico",
                        "Grama",
                        "Gelo",
                        "Lutador",
                        "Veneno",
                        "Terra",
                        "Voador",
                        "Psíquico",
                        "Inseto",
                        "Pedra",
                        "Fantasma",
                        "Dragão",
                        "Sombrio",
                        "Aço",
                        "Fada",
                        "Grama/Veneno",
                        "Fogo/Voador",
                        "Água/Voador"
                )
        );

        /*
         * Permite escolher um tipo da lista,
         * mas também permite combinações
         * como Água/Terra.
         */
        cbTipo.setEditable(true);
    }

    private void configurarGeracao() {

        spGeracao.setValueFactory(
                new SpinnerValueFactory
                        .IntegerSpinnerValueFactory(
                        1,
                        9,
                        1
                )
        );
    }

    private void configurarCampoNumero() {

        txtNum.setTextFormatter(
                new TextFormatter<String>(
                        change -> {

                            String novoTexto =
                                    change.getControlNewText();

                            if (novoTexto.matches("\\d{0,4}")) {
                                return change;
                            }

                            return null;
                        }
                )
        );
    }

    private void configurarPesquisa() {

        txtPesquisar
                .textProperty()
                .addListener(
                        (
                                observable,
                                textoAnterior,
                                textoAtual
                        ) -> aplicarFiltro(textoAtual)
                );
    }

    private void configurarSelecao() {

        tblPokemon
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (
                                observable,
                                anterior,
                                selecionado
                        ) -> {

                            if (selecionado != null) {

                                carregarCampos(selecionado);

                                configurarModoEdicao(
                                        selecionado
                                );

                            } else {

                                configurarModoCadastro();
                            }
                        }
                );
    }

    @FXML
    private void btnAdicionarClick() {

        if (!validarCampos()) {

            mostrarErro(
                    "Revise os dados informados."
            );

            return;
        }

        try {

            PokemonDTO pokemon =
                    criarPokemonDosCampos();

            pokemonService.cadastrarPokemon(
                    pokemon
            );

            carregarTabela();
            limparCampos();

            mostrarSucesso(
                    "Pokémon cadastrado com sucesso!"
            );

        } catch (RuntimeException e) {

            mostrarErro(
                    mensagemDaExcecao(
                            e,
                            "Não foi possível cadastrar o Pokémon."
                    )
            );
        }
    }

    @FXML
    private void btnEditarClick() {

        PokemonDTO pokemonSelecionado =
                tblPokemon
                        .getSelectionModel()
                        .getSelectedItem();

        if (pokemonSelecionado == null) {

            mostrarErro(
                    "Selecione um Pokémon para editar."
            );

            return;
        }

        if (!validarCampos()) {

            mostrarErro(
                    "Revise os dados informados."
            );

            return;
        }

        boolean confirmou =
                showConfirmation(
                        "Confirmar atualização",
                        "Deseja realmente atualizar "
                                + pokemonSelecionado.getNome()
                                + "?"
                );

        if (!confirmou) {
            return;
        }

        try {

            int numeroOriginal =
                    pokemonSelecionado.getNumero();

            PokemonDTO pokemonEditado =
                    criarPokemonDosCampos();

            pokemonService.alterarPokemon(
                    pokemonEditado,
                    numeroOriginal
            );

            carregarTabela();
            limparCampos();

            mostrarSucesso(
                    "Pokémon atualizado com sucesso!"
            );

        } catch (RuntimeException e) {

            mostrarErro(
                    mensagemDaExcecao(
                            e,
                            "Não foi possível atualizar o Pokémon."
                    )
            );
        }
    }

    @FXML
    private void btnExcluirClick() {

        PokemonDTO pokemonSelecionado =
                tblPokemon
                        .getSelectionModel()
                        .getSelectedItem();

        if (pokemonSelecionado == null) {

            mostrarErro(
                    "Selecione um Pokémon para excluir."
            );

            return;
        }

        boolean confirmou =
                showConfirmation(
                        "Confirmar exclusão",
                        "Deseja realmente excluir "
                                + pokemonSelecionado.getNome()
                                + "?"
                );

        if (!confirmou) {
            return;
        }

        try {

            pokemonService.excluirPokemon(
                    pokemonSelecionado.getNumero()
            );

            carregarTabela();
            limparCampos();

            mostrarSucesso(
                    "Pokémon excluído com sucesso!"
            );

        } catch (RuntimeException e) {

            mostrarErro(
                    mensagemDaExcecao(
                            e,
                            "Não foi possível excluir o Pokémon."
                    )
            );
        }
    }

    @FXML
    private void btnLimparClick() {

        limparCampos();

        mostrarInformacao(
                "Campos limpos. Pronto para um novo cadastro."
        );
    }

    private boolean validarCampos() {

        return pokemonValidador.validarPokemon(
                txtNome.getText(),
                obterTipo(),
                txtNum.getText(),
                String.valueOf(
                        spGeracao.getValue()
                )
        );
    }

    private PokemonDTO criarPokemonDosCampos() {

        PokemonDTO pokemon =
                new PokemonDTO();

        pokemon.setNome(
                txtNome
                        .getText()
                        .trim()
        );

        pokemon.setTipo(
                obterTipo()
        );

        pokemon.setNumero(
                Integer.parseInt(
                        txtNum
                                .getText()
                                .trim()
                )
        );

        pokemon.setGeracao(
                spGeracao.getValue()
        );

        return pokemon;
    }

    private String obterTipo() {

        String tipo =
                cbTipo
                        .getEditor()
                        .getText();

        if (tipo == null) {
            return "";
        }

        return tipo.trim();
    }

    private void carregarTabela() {

        try {

            listaPokemon.setAll(
                    pokemonService.listarPokemons()
            );

            aplicarFiltro(
                    txtPesquisar.getText()
            );

            atualizarContador();

        } catch (RuntimeException e) {

            mostrarErro(
                    mensagemDaExcecao(
                            e,
                            "Não foi possível carregar os Pokémon."
                    )
            );
        }
    }

    private void aplicarFiltro(
            String pesquisa
    ) {

        String busca =
                pesquisa == null
                        ? ""
                        : pesquisa
                        .trim()
                        .toLowerCase(
                                Locale.ROOT
                        );

        listaFiltrada.setPredicate(
                pokemon -> {

                    if (busca.isEmpty()) {
                        return true;
                    }

                    String nome =
                            pokemon
                                    .getNome()
                                    .toLowerCase(
                                            Locale.ROOT
                                    );

                    String tipo =
                            pokemon
                                    .getTipo()
                                    .toLowerCase(
                                            Locale.ROOT
                                    );

                    String numero =
                            String.valueOf(
                                    pokemon.getNumero()
                            );

                    String geracao =
                            String.valueOf(
                                    pokemon.getGeracao()
                            );

                    return nome.contains(busca)
                            || tipo.contains(busca)
                            || numero.contains(busca)
                            || geracao.contains(busca);
                }
        );

        atualizarContador();
    }

    private void atualizarContador() {

        int total =
                listaPokemon.size();

        int exibidos =
                listaFiltrada.size();

        if (total == exibidos) {

            lblContador.setText(
                    total + " Pokémon"
            );

        } else {

            lblContador.setText(
                    exibidos
                            + " de "
                            + total
                            + " Pokémon"
            );
        }
    }

    private void carregarCampos(
            PokemonDTO pokemon
    ) {

        txtNome.setText(
                pokemon.getNome()
        );

        txtNum.setText(
                String.valueOf(
                        pokemon.getNumero()
                )
        );

        cbTipo.setValue(
                pokemon.getTipo()
        );

        spGeracao
                .getValueFactory()
                .setValue(
                        pokemon.getGeracao()
                );
    }

    private void configurarModoEdicao(
            PokemonDTO pokemon
    ) {

        /*
         * O número funciona como ID.
         * Durante a edição ele não pode
         * ser alterado.
         */
        txtNum.setDisable(true);

        btnAdicionar.setDisable(true);
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);

        lblModo.setText(
                "Editando: "
                        + pokemon.getNome()
        );
    }

    private void configurarModoCadastro() {

        txtNum.setDisable(false);

        btnAdicionar.setDisable(false);
        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);

        lblModo.setText(
                "Novo cadastro"
        );
    }

    private void limparCampos() {

        tblPokemon
                .getSelectionModel()
                .clearSelection();

        txtNome.clear();
        txtNum.clear();

        cbTipo
                .getSelectionModel()
                .clearSelection();

        cbTipo
                .getEditor()
                .clear();

        spGeracao
                .getValueFactory()
                .setValue(1);

        configurarModoCadastro();

        Platform.runLater(
                txtNome::requestFocus
        );
    }

    private void mostrarSucesso(
            String mensagem
    ) {

        configurarMensagem(
                mensagem,
                "status-success"
        );
    }

    private void mostrarErro(
            String mensagem
    ) {

        configurarMensagem(
                mensagem,
                "status-error"
        );
    }

    private void mostrarInformacao(
            String mensagem
    ) {

        configurarMensagem(
                mensagem,
                "status-info"
        );
    }

    private void configurarMensagem(
            String mensagem,
            String classeCss
    ) {

        lblMensagem.setText(mensagem);

        lblMensagem
                .getStyleClass()
                .removeAll(
                        "status-success",
                        "status-error",
                        "status-info"
                );

        lblMensagem
                .getStyleClass()
                .add(classeCss);
    }

    private String mensagemDaExcecao(
            RuntimeException e,
            String mensagemPadrao
    ) {

        if (e.getMessage() == null
                || e.getMessage().isBlank()) {

            return mensagemPadrao;
        }

        return e.getMessage();
    }
}