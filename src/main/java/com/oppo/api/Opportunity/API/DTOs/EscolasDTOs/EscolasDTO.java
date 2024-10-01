package com.oppo.api.Opportunity.API.DTOs.EscolasDTOs;


import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

public record EscolasDTO(
        @NotNull String nome,
        @NotNull String CNPJ,
        @NotNull String senha,
        @NotNull String endereco,
        @NotNull String diretor
        ) implements Serializable {
}
