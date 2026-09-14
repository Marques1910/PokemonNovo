package validator;

import java.util.ArrayList;
import java.util.List;
import static com.template.util.DialogUtil.showError;

public class PokemonValidador implements IPokemonValidador {

    @Override
    public boolean validarPokemon(String txtGeracao, String txtTipo, String txtNome, String txtNum) { // <-- NOME CORRIGIDO AQUI

        List<Validador<String>> validadores = new ArrayList<>();

        validadores.add(new CampoObrigatorioValidador("Geração", txtGeracao));
        validadores.add(new CampoObrigatorioValidador("Tipo", txtTipo));
        validadores.add(new CampoObrigatorioValidador("Nome", txtNome));
        validadores.add(new CampoObrigatorioValidador("Número", txtNum));

        validadores.add(new NumeroValidador("Geração", txtGeracao));
        validadores.add(new NumeroValidador("Número", txtNum));

        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                showError(validador.getMensagemErro());
                return false;
            }
        }

        return true;
    }
}