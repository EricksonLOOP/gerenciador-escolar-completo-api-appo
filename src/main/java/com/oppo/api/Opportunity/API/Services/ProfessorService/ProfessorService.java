package com.oppo.api.Opportunity.API.Services.ProfessorService;

import com.oppo.api.Opportunity.API.DTOs.ProfessorDTOs.CriarProfessorDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ProfessorService {
    ResponseEntity<?> criarProfessor(CriarProfessorDTO criarProfessorDTO);
    ResponseEntity<?> avaliarAluno(UUID idAluno, UUID idProfessor, UUID idMateria, String nota);
    ResponseEntity<?> observarAluno(UUID idAluno, UUID idProfessor);
    ResponseEntity<?> diarioDeAula(UUID idProfessor);
    ResponseEntity<?> realizarChamada(UUID idProfessor, UUID idTurma);

}
