package com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class InformacoesPessoaisModel {
    private String nome;
    private Date dataDeNascimento;
    private String cpf;
    private String rg;
    private String mae;
    private String pai;
    public  InformacoesPessoaisModel(){

    }
}
