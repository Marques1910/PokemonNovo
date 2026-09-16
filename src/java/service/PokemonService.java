package service;

import com.template.model.dao.PokemonDAO;
import com.template.model.dto.PokemonDTO;

import java.util.List;

public class PokemonService implements IPokemonService {

    private final PokemonDAO pokemonDAO;

    public PokemonService() {
        this.pokemonDAO = new PokemonDAO();
    }

    @Override
    public void cadastrarPokemon(PokemonDTO pokemon) {
        pokemonDAO.cadastrarPokemon(pokemon);
    }

    @Override
    public List<PokemonDTO> listarPokemons() {
        return pokemonDAO.listarPokemons();
    }

    @Override
    public void alterarPokemon(PokemonDTO pokemon, int numeroOriginal) {
        pokemonDAO.alterarPokemon(pokemon, numeroOriginal);
    }

    @Override
    public void excluirPokemon(int numero) {
        pokemonDAO.excluirPokemon(numero);
    }
}