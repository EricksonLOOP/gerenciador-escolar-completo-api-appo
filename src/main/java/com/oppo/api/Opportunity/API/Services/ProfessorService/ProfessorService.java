package com.oppo.api.Opportunity.API.Services.ProfessorService;

import com.oppo.api.Opportunity.API.DTOs.ProfessorDTOs.CriarProfessorDTO;
import org.springframework.http.ResponseEntity;

public interface ProfessorService {
    ResponseEntity<?> criarProfessor(CriarProfessorDTO criarProfessorDTO);
}
