package com.oppo.api.Opportunity.API.DTOs.AdminDTO;

import com.oppo.api.Opportunity.API.Models.TagsENUM;
import org.antlr.v4.runtime.misc.NotNull;

public record AdminDTO(@NotNull String nome,
                       @NotNull String rg,
                       @NotNull String cpf,
                       @NotNull String senha,
                       @NotNull TagsENUM role
                       ) {
}
