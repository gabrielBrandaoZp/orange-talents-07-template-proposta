package br.com.zupacademy.msproposta.utils.handler;

/**
 * Classe não implementada ainda.
 * @param <E> E define o tipo de exception que vai ser trabalhada
 * @param <S> S define o tipo de objeto de sucesso
 */
public class Resultado<E extends Exception, S>{

    private S sucesso;
    private E exception;

    public boolean temErro() {
        return exception != null;
    }

    public E getExcecao() {
        return exception;
    }

    public S getSucesso() {
        return sucesso;
    }

    public static <T> Resultado<Exception, T> sucesso(T objeto) {
        Resultado<Exception, T> resultado = new Resultado<>();
        resultado.sucesso = objeto;
        return resultado;
    }

    public static <E extends Exception, T> Resultado<E, T> erro(E excecao) {
        Resultado<E, T> resultado = new Resultado<>();
        resultado.exception = excecao;
        return resultado;
    }
}
