package validator;

interface Validador<T> {
    boolean validar(T valorAtual);
    String getMensagemErro();
    T getValor();
}

public class CampoObrigatorioValidador implements Validador<String> {
    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {

        return this.valor != null && !this.valor.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo '" + nomeCampo + "' é obrigatório e deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

class NumeroValidador implements Validador<String> {
    private final String nomeCampo;
    private final String valor;

    public NumeroValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        return this.valor.matches("\\d+");
    }

    @Override
    public String getMensagemErro() {
        return "O campo '" + nomeCampo + "' deve conter apenas números válidos.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}