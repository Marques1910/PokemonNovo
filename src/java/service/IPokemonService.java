package service;

import com.template.model.dto.PokemonDTO;

import java.util.List;

public interface IPokemonService {

    void cadastrarPokemon(PokemonDTO pokemon);

    List<PokemonDTO> listarPokemons();

    void alterarPokemon(
            PokemonDTO pokemon,
            int numeroOriginal
    );

    void excluirPokemon(int numero);
}