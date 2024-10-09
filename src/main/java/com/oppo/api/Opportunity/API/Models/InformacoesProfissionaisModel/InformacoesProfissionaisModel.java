package com.oppo.api.Opportunity.API.Models.InformacoesProfissionaisModel;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class InformacoesProfissionaisModel {
    private String estadoCivil;
    private String naturalidade;
    private String cargo;
    private String departamento;
    private String tipoDeContrato;
    private String regimeDeTrabalho;
    public InformacoesProfissionaisModel(){

    }
}
