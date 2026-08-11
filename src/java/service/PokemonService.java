package service;

import com.template.model.dao.PokemonDAO;
import com.template.model.dto.PokemonDTO;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class PokemonService{

    public ArrayList<PokemonDTO> listarPokemons() {
        PokemonDAO objPokemonDAO = new PokemonDAO();
        return objPokemonDAO.listarPokemons();
    }

    public void limparCampos(TextField txtNome, TextField txtTipo, TextField txtNum, TextField txtGeracao) {
        txtNome.clear();
        txtTipo.clear();
        txtNum.clear();
        txtGeracao.clear();
    }

    public void carregarCampos(TableView<PokemonDTO> tblPokemon, TextField txtNome, TextField txtTipo, TextField txtNum, TextField txtGeracao)
    {
        PokemonDTO objPokemonDTO = tblPokemon.getSelectionModel().getSelectedItem();

        if (objPokemonDTO != null) {
            txtGeracao.setText(String.valueOf(objPokemonDTO.getGeracao()));
            txtTipo.setText(objPokemonDTO.getTipo());
            txtNum.setText(String.valueOf(objPokemonDTO.getNumero()));
            txtNome.setText(objPokemonDTO.getNome());
        }
    }

    public void carregarPokemon(TableView<PokemonDTO> tblPokemon)
    {
        PokemonDAO objPokemonDAO = new PokemonDAO();

        ArrayList<PokemonDTO> listaPokemon = objPokemonDAO.listarPokemons();
        tblPokemon.setItems(FXCollections.observableArrayList(listaPokemon));
    }

}
