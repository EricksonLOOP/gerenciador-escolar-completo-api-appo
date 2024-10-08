package com.oppo.api.Opportunity.API.Models.EnderecoModel;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class EnderecoModel {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    @Size(min = 8, max = 8)
    private String cep;
}
