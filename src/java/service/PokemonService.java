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



    public void carregarPokemon(TableView<PokemonDTO> tblPokemon)
    {
        PokemonDAO objPokemonDAO = new PokemonDAO();

        ArrayList<PokemonDTO> listaPokemon = objPokemonDAO.listarPokemons();
        tblPokemon.setItems(FXCollections.observableArrayList(listaPokemon));
    }

}
