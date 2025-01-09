package com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

public interface ValidacoesService {
    Boolean validarcpf(String cpf) throws Exception;
    Boolean validarcnpj(String cnpj) throws  Exception;
    Boolean verificarSeContaExistentePeloCpf(String cpf) throws Exception;
    Boolean verificarSeContaExistentePeloId(UUID id) throws Exception;
}

