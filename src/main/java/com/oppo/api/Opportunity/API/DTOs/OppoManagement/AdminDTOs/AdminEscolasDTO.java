package com.oppo.api.Opportunity.API.DTOs.OppoManagement.AdminDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public record AdminEscolasDTO(@JsonProperty("nome") String nome, @JsonProperty("id") UUID id) implements Serializable {
}
