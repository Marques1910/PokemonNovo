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
import java.util.ArrayList;

public class MainController
{
    @FXML private Button btn_adicionar;
    @FXML private Button btn_editar;
    @FXML private Button btn_excluir;
    @FXML private Button btn_pesquisar;
    @FXML private TextField txtGeracao;
    @FXML private TextField txtTipo;
    @FXML private TextField txtNum;
    @FXML private TextField txtNome;


    @FXML private TableView<PokemonDTO> tblPokemon;
    @FXML private TableColumn<PokemonDTO, Integer> colGeracao;
    @FXML private TableColumn<PokemonDTO, String> colTipo;
    @FXML private TableColumn<PokemonDTO, Integer> colNum;
    @FXML private TableColumn<PokemonDTO, String> colNome;

    @FXML
    private void carregarPokemon()
    {
        PokemonDAO objPokemonDAO = new PokemonDAO();

        ArrayList<PokemonDTO> listaPokemon = objPokemonDAO.listarPokemons();
        tblPokemon.setItems(FXCollections.observableArrayList(listaPokemon));
    }

    @FXML
    private void btnAdicionarClick() {

        String nome = txtNome.getText();
        String tipo = txtTipo.getText();

        int numero = Integer.parseInt(txtNum.getText());
        int geracao = Integer.getInteger(txtGeracao.getText(), 1);
        PokemonDTO novoPokemon = new PokemonDTO();
        novoPokemon.setNome(nome);
        novoPokemon.setTipo(tipo);
        novoPokemon.setNumero(numero);
        novoPokemon.setGeracao(geracao);

        PokemonDAO objPokemonDAO = new PokemonDAO();
        objPokemonDAO.cadastrarPokemon(novoPokemon);

        txtNome.clear();
        txtTipo.clear();
        txtNum.clear();
        txtGeracao.clear();

        carregarPokemon();
    }

    @FXML
    private void btnEditarClick() {

        PokemonDTO pokemonSelecionado = tblPokemon.getSelectionModel().getSelectedItem();

        if (pokemonSelecionado != null) {
            try {

                pokemonSelecionado.setNome(txtNome.getText());
                pokemonSelecionado.setTipo(txtTipo.getText());
                pokemonSelecionado.setGeracao(Integer.parseInt(txtGeracao.getText()));



                PokemonDAO objPokemonDAO = new PokemonDAO();
                objPokemonDAO.alterarPokemon(pokemonSelecionado);


                limparCampos();
                carregarPokemon();
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

            limparCampos();
            carregarPokemon();
        } else {
            System.out.println("Por favor, selecione um Pokémon na tabela para excluir.");
        }
    }



    private void limparCampos() {
        txtNome.clear();
        txtTipo.clear();
        txtNum.clear();
        txtGeracao.clear();
    }

    @FXML
    private void initialize()
    {
        colGeracao.setCellValueFactory(new PropertyValueFactory<>("geracao"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));


        carregarPokemon();
    }

    @FXML
    private void carregarCampos()
    {

        PokemonDTO objPokemonDTO = tblPokemon.getSelectionModel().getSelectedItem();

        if (objPokemonDTO != null) {
            txtGeracao.setText(String.valueOf(objPokemonDTO.getGeracao()));
            txtTipo.setText(objPokemonDTO.getTipo());
            txtNum.setText(String.valueOf(objPokemonDTO.getNumero()));
            txtNome.setText(objPokemonDTO.getNome());
        }
    }
}