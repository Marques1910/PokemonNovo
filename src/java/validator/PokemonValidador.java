package validator;

import java.util.ArrayList;
import java.util.List;

import static com.template.util.DialogUtil.showError;

public class PokemonValidador implements IPokemonValidador {

    @Override
    public boolean validarPokemon(
            String nome,
            String tipo,
            String numero,
            String geracao
    ) {

        List<Validador<String>> validadores = new ArrayList<>();

        validadores.add(
                new CampoObrigatorioValidador(
                        "Nome",
                        nome
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Tipo",
                        tipo
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Número",
                        numero
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Geração",
                        geracao
                )
        );

        validadores.add(
                new NumeroValidador(
                        "Número",
                        numero
                )
        );

        validadores.add(
                new NumeroValidador(
                        "Geração",
                        geracao
                )
        );

        for (Validador<String> validador : validadores) {

            if (!validador.validar(validador.getValor())) {

                showError(
                        validador.getMensagemErro()
                );

                return false;
            }
        }

        return true;
    }
}