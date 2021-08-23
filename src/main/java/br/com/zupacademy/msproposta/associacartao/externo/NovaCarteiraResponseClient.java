package br.com.zupacademy.msproposta.associacartao.externo;

public class NovaCarteiraResponseClient {

    private String resultado;
    private String id;

    public NovaCarteiraResponseClient(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
