package validator;

public interface IPokemonValidador {
    boolean validarPokemon(String txtGeracao, String txtTipo, String txtNome, String txtNum);
}