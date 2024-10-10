package com.oppo.api.Opportunity.API.Services.ValidacoesServices;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ValidacoesService {
    Boolean validarcpf(String cpf) throws Exception;
    Boolean validarcnpj(String cnpj) throws  Exception;
}
