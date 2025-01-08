package com.oppo.api.Opportunity.API.DTOs.OppoManagement.AlunoDTOs;

import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CriarAlunoDTO (
        //Basico
        @NotNull String senha,
        //Contato
        @NotNull String telefone,
        @NotNull String email,
        //Endereço
        @NotNull String rua,
        @NotNull String numero,
        @NotNull String bairro,
        @NotNull String cidade,
        @NotNull String estado,
        @NotNull String cep,
        //Pessoais
        @NotNull String nome,
        @NotNull String dataDeNascimento,
        @NotNull String cpf,
        @NotNull String rg,
        @NotNull String mae,
        @NotNull String pai
){
}
