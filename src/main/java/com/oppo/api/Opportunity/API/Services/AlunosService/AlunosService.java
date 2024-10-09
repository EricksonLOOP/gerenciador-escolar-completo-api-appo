package com.oppo.api.Opportunity.API.Services.AlunosService;

import com.oppo.api.Opportunity.API.DTOs.AlunoDTOs.CriarAlunoDTO;
import org.springframework.http.ResponseEntity;

public interface AlunosService {
    ResponseEntity<?> criarAluno(CriarAlunoDTO alunoDTO);
}
