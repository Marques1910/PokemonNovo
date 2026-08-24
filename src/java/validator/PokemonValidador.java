package validator;
import java.util.regex.Pattern;
import com.template.model.dto.PokemonDTO;

import static com.template.util.DialogUtil.showError;

public class PokemonValidador {

    public boolean validaPokemon(String TxtGeracao,String TxtTipo, String TxtNome, String TxtNum) {
        if( TxtGeracao.isEmpty() || TxtNome.isEmpty() || TxtNum.isEmpty() || TxtTipo.isEmpty()) {
            return false;
        }
        return true;
    }


}
