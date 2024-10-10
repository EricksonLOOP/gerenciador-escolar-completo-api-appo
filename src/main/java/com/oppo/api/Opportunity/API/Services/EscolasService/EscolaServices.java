package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.CriarEscolasDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface EscolaServices {
    ResponseEntity<?> create(CriarEscolasDTO criarEscolasDTO);
    ResponseEntity<?> update(CriarEscolasDTO criarEscolasDTO);

    ResponseEntity<?> addAluno(UUID idAluno, UUID idEscola);
    ResponseEntity<?> removeAluno(UUID idAluno, UUID idEscola);
    ResponseEntity<?> listarAlunos(UUID idEscola);

    ResponseEntity<?> addProfessor(CriarEscolasDTO criarEscolasDTO);
    ResponseEntity<?> removeProfessor(CriarEscolasDTO criarEscolasDTO);
    ResponseEntity<?> listarProfessor(CriarEscolasDTO criarEscolasDTO);


}
