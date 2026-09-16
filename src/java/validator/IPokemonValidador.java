package validator;

public interface IPokemonValidador {

    boolean validarPokemon(
            String nome,
            String tipo,
            String numero,
            String geracao
    );
}