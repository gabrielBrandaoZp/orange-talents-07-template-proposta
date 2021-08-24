package br.com.zupacademy.msproposta.utils.compartilhado;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class Metodos {

    public static URI criarUri(UriComponentsBuilder uriBuilder, String path, Long id) {
        return uriBuilder
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }
}
