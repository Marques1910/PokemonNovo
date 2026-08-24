package com.template.controller;

import com.template.model.dao.PokemonDAO;
import com.template.model.dto.PokemonDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.PokemonService;

import java.util.ArrayList;

public class MainController
{
    @FXML private Button btn_adicionar;
    @FXML private Button btn_editar;
    @FXML private Button btn_excluir;
    @FXML private Button btn_pesquisar;
    @FXML private TextField txtNome;
    @FXML private TextField txtTipo;
    @FXML private TextField txtNum;
    @FXML private TextField txtGeracao;
    @FXML private TableView<PokemonDTO> tblPokemon;

    private PokemonService pokemonService = new PokemonService();

    @FXML private TableColumn<PokemonDTO, Integer> colGeracao;
    @FXML private TableColumn<PokemonDTO, String> colTipo;
    @FXML private TableColumn<PokemonDTO, Integer> colNum;
    @FXML private TableColumn<PokemonDTO, String> colNome;

    @FXML
    private void btnAdicionarClick() {

        String nome = txtNome.getText();
        String tipo = txtTipo.getText();

        int numero = Integer.parseInt(txtNum.getText());
        int geracao = Integer.getInteger(txtGeracao.getText());

        PokemonDTO novoPokemon = new PokemonDTO();
        novoPokemon.setNome(nome);
        novoPokemon.setTipo(tipo);
        novoPokemon.setNumero(numero);
        novoPokemon.setGeracao(geracao);

        PokemonDAO objPokemonDAO = new PokemonDAO();
        objPokemonDAO.cadastrarPokemon(novoPokemon);


        pokemonService.limparCampos(txtNome, txtTipo, txtNum, txtGeracao);
        carregarCampos(tblPokemon, txtNome, txtTipo, txtNum, txtGeracao);
    }

    @FXML
    private void btnEditarClick() {

        PokemonDTO pokemonSelecionado = tblPokemon.getSelectionModel().getSelectedItem();

        if (pokemonSelecionado != null) {
            try {

                pokemonSelecionado.setNome(txtNome.getText());
                pokemonSelecionado.setTipo(txtTipo.getText());
                pokemonSelecionado.setTipo(txtNum.getText());
                pokemonSelecionado.setGeracao(Integer.parseInt(txtGeracao.getText()));

                PokemonDAO objPokemonDAO = new PokemonDAO();
                objPokemonDAO.alterarPokemon(pokemonSelecionado);

                pokemonService.limparCampos(txtNome, txtTipo, txtNum, txtGeracao);
                pokemonService.carregarPokemon(tblPokemon);
                System.out.println("Pokémon editado com sucesso!");

             } catch (NumberFormatException e) {
                System.err.println("Erro: Verifique se os campos numéricos estão corretos.");
            }
        } else {
            System.out.println("Por favor, selecione um Pokémon na tabela para editar.");
        }
    }

    @FXML
    private void btnExcluirClick() {

        PokemonDTO pokemonSelecionado = tblPokemon.getSelectionModel().getSelectedItem();

        if (pokemonSelecionado != null) {

            PokemonDAO objPokemonDAO = new PokemonDAO();
            objPokemonDAO.excluirPokemon(pokemonSelecionado.getNumero());

            pokemonService.limparCampos(txtNome, txtTipo, txtNum, txtGeracao);
            pokemonService.carregarPokemon(tblPokemon);
        } else {
            System.out.println("Por favor, selecione um Pokémon na tabela para excluir.");
        }
    }


    @FXML
    private void initialize() {
        colGeracao.setCellValueFactory(new PropertyValueFactory<>("geracao"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));


        pokemonService.carregarPokemon(tblPokemon);
    }

    @FXML
    public void carregarCampos(TableView<PokemonDTO> tblPokemon, TextField txtNome, TextField txtTipo, TextField txtNum, TextField txtGeracao) {
        PokemonDTO objPokemonDTO = tblPokemon.getSelectionModel().getSelectedItem();

        if (objPokemonDTO != null) {
            txtGeracao.setText(String.valueOf(objPokemonDTO.getGeracao()));
            txtTipo.setText(objPokemonDTO.getTipo());
            txtNum.setText(String.valueOf(objPokemonDTO.getNumero()));
            txtNome.setText(objPokemonDTO.getNome());
        }
    }
}