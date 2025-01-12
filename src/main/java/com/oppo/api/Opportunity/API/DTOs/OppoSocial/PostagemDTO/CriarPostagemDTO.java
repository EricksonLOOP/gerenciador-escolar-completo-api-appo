package com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO;

import jakarta.validation.constraints.NotNull;

public record CriarPostagemDTO (
        @NotNull String content
) {
}
