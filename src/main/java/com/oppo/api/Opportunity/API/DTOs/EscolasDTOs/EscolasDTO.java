package com.oppo.api.Opportunity.API.DTOs.EscolasDTOs;


import com.oppo.api.Opportunity.API.Models.Alunos.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.Professores.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

public record EscolasDTO(
        @NotNull String nome,
        @NotNull String cnpj,
        @NotNull String senha,
        @NotNull String endereco,
        @NotNull String diretor,
        @NotNull TagsENUM role,
        AlunosEntity alunosEntity,
        ProfessoresEntity professoresEntity
        ) implements Serializable {
}
