package com.oppo.api.Opportunity.API.Controllers;

import com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices.ValidacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TestarServices {
    @Autowired
    ValidacoesService validacoesService;
    @PostMapping("/validarcpf")
    public Boolean validarCpf(@RequestBody String cpf) throws Exception {
        return validacoesService.validarcpf(cpf);
    }
    @PostMapping("/validarcnpj")
    public Boolean validarCnpj(@RequestBody String cnpj) throws Exception {
        return validacoesService.validarcnpj(cnpj);
    }
}
