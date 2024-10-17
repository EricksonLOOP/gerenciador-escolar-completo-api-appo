package com.oppo.api.Opportunity.API.DTOs.AdminDTOs;

import com.oppo.api.Opportunity.API.Models.TagsENUM;

import java.util.Date;

public record AdminDTO(
        String nome,
        String rg,
        String cpf,
        String dataDeNascimento,
        String mae,
        String pai,
        String senha,
        TagsENUM role
                       ) {
}
