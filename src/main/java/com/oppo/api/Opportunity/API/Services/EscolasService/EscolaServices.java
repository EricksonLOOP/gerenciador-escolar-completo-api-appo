package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import org.springframework.http.ResponseEntity;


public interface EscolaServices {
     ResponseEntity<?> create(EscolasDTO escolasDTO);

    ResponseEntity<?> update(EscolasDTO escolasDTO);

    ResponseEntity<?> addAluno(EscolasDTO escolasDTO);

    ResponseEntity<?> removeAluno(EscolasDTO escolasDTO);

    ResponseEntity<?> listarAluno(EscolasDTO escolasDTO);

    ResponseEntity<?> addProfessor(EscolasDTO escolasDTO);
    ResponseEntity<?> removeProfessor(EscolasDTO escolasDTO);

    ResponseEntity<?> listarProfessor(EscolasDTO escolasDTO);
}
