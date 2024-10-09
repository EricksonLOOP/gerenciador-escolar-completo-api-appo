package com.oppo.api.Opportunity.API.DTOs.AdminDTOs;

import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.validation.constraints.NotNull;

public record AdminDTO( String nome,
                        String rg,
                        String cpf,
                        String senha,
                        TagsENUM role
                       ) {
}
