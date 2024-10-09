package com.oppo.api.Opportunity.API.DTOs.EscolasDTOs;


import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record EscolasDTO(
         String nome,
         String cnpj,
         String senha,
         String endereco,
         String diretor,
         TagsENUM role,
         String nivelDeEnsino,
         int capMax,
         int numFuncionarios,
         List<String> turnos,
         int numSalas,
         int numLaboratorios,
         int numBiblioteca,
         boolean possuiInternet,
         String modeloEscolar,
         String telefone,
         String email,
         String rua,
         String numero,
         String bairro,
         String cidade,
         String estado,
         String cep
        ) implements Serializable {
}
