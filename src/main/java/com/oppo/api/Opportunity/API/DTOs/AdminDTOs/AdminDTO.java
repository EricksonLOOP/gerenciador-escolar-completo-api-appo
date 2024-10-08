package com.oppo.api.Opportunity.API.DTOs.AdminDTOs;

import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.validation.constraints.NotNull;

public record AdminDTO(@NotNull String nome,
                       @NotNull String rg,
                       @NotNull String cpf,
                       @NotNull String senha,
                       @NotNull TagsENUM role
                       ) {
}
