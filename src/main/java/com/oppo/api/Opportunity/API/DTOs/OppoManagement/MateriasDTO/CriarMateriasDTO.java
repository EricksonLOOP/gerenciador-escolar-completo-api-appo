package com.oppo.api.Opportunity.API.DTOs.OppoManagement.MateriasDTO;

import jakarta.validation.constraints.NotNull;

public record CriarMateriasDTO(
        @NotNull String materia,
        @NotNull String descricao
) {
}
