package com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Date;

@Embeddable
@Data
public class InformacoesPessoaisModel {
    private String nome;
    private Date dataDeNascimento;
    private String cpf;
    private String rg;
    private String mae;
    private String pai;
}
