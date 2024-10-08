package com.oppo.api.Opportunity.API.DTOs.EscolasDTOs;


import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record EscolasDTO(
        @NotNull String nome,
        @NotNull String cnpj,
        @NotNull String senha,
        @NotNull String endereco,
        @NotNull String diretor,
        @NotNull TagsENUM role,
        @NotNull String nivelDeEnsino,
        @NotNull int capMax,
        @NotNull int numFuncionarios,
        @NotNull List<String> turnos,
        @NotNull int numSalas,
        @NotNull int numLaboratorios,
        @NotNull int numBiblioteca,
        @NotNull boolean possuiInternet,
        @NotNull String modeloEscolar,
        @NotNull String telefone,
        @NotNull String email,
        @NotNull String rua,
        @NotNull String numero,
        @NotNull String bairro,
        @NotNull String cidade,
        @NotNull String estado,
        @NotNull String cep,
        String website,
        AlunosEntity alunosEntity,
        ProfessoresEntity professoresEntity
        ) implements Serializable {
}
