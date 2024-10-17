package com.oppo.api.Opportunity.API.DTOs;

import java.util.Date;

public record CriarTurmaDTO(
        String nome,
        String codigo,
        String serie,
        String turno,
        Date ano


) {
}
