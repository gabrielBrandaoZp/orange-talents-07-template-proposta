package br.com.zupacademy.msproposta.novaproposta.externo;

import br.com.zupacademy.msproposta.novaproposta.StatusProposta;

public enum StatusSolicitacao {

    COM_RESTRICAO,
    SEM_RESTRICAO;

    public StatusProposta normaliza() {
        if (this.equals(COM_RESTRICAO)) {
            return StatusProposta.NAO_ELEGIVEL;
        }

        return StatusProposta.ELEGIVEL;
    }
}
