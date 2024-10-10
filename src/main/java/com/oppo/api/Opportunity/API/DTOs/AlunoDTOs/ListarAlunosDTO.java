package com.oppo.api.Opportunity.API.DTOs.AlunoDTOs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
public record ListarAlunosDTO(
        String nome,
        UUID id,
        String escolaNome) {
}
