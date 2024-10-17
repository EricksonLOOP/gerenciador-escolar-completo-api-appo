package com.oppo.api.Opportunity.API.Models.ObservacaoModel;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ObservacaoModel {
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
   private String professor;
   private String aluno;
   private String materia;
   private String observacao;
}
