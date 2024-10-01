package com.oppo.api.Opportunity.API.DTOs.EscolasDTOs;


import com.oppo.api.Opportunity.API.Models.TagsENUM;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

public record EscolasDTO(
        @NotNull String nome,
        @NotNull String cnpj,
        @NotNull String senha,
        @NotNull String endereco,
        @NotNull String diretor,
        @NotNull TagsENUM role
        ) implements Serializable {
}
