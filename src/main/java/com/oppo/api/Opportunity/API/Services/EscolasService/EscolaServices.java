package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface EscolaServices {
    ResponseEntity<?> create(EscolasDTO escolasDTO);
    ResponseEntity<?> update(EscolasDTO escolasDTO);

    ResponseEntity<?> addAluno(UUID idAluno, UUID idEscola);
    ResponseEntity<?> removeAluno(UUID idAluno, UUID idEscola);
    ResponseEntity<?> listarAlunos(UUID idEscola);

    ResponseEntity<?> addProfessor(EscolasDTO escolasDTO);
    ResponseEntity<?> removeProfessor(EscolasDTO escolasDTO);
    ResponseEntity<?> listarProfessor(EscolasDTO escolasDTO);


}
