package com.oppo.api.Opportunity.API.DTOs.MateriasDTO;

import jakarta.validation.constraints.NotNull;

public record CriarMateriasDTO(
        @NotNull String materia,
        @NotNull String descricao
) {
}
