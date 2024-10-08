package com.oppo.api.Opportunity.API.Models.InformacoesEscola;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class InformacoesEscola {
    private String website;
    private String nivelDeEnsino;
    private int capMax;
    private int numFuncionarios;
    @Size(min = 1, max = 3, message = "Uma escola tem que ter ao menos 1 turno.")
    private List<String> turnos;
    private int numSalas;
    private int numLaboratorios;
    private int numBibliotecas;
    private boolean possuiInternet;
    private String modeloEscolar;
    @Size(min = 8, max = 50, message = "Um nome tem que ter entre 8 a 50 digitos.")
    private String nome;
    @Size(min = 14, max = 14, message = "Um CNPJ tem que ter 14 digitos.")
    private String cnpj;
}
