package com.oppo.api.Opportunity.API.DTOs.OppoManagement.ProfessorDTOs;

import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CriarProfessorDTO(
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
        @NotNull String pai,
        //Profissionais
        @NotNull String estadoCivil,
        @NotNull String naturalidade,
        @NotNull String cargo,
        @NotNull String departamento,
        @NotNull String tipoDeContrato,
        @NotNull String regimeDeTrabalho
) {
}
