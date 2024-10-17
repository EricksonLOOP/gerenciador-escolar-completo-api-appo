package com.oppo.api.Opportunity.API.DTOs.EscolasDTOs;


import com.oppo.api.Opportunity.API.Models.TagsENUM;

import java.io.Serializable;
import java.util.List;

public record CriarEscolasDTO(
         String nome,
         String cnpj,
         String senha,
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
