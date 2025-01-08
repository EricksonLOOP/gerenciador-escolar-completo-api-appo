package com.oppo.api.Opportunity.API.Services.OppoManagement.AlunosService;

import com.oppo.api.Opportunity.API.DTOs.OppoManagement.AlunoDTOs.CriarAlunoDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AlunosService {

    ResponseEntity<?> criarAluno(CriarAlunoDTO alunoDTO);
    ResponseEntity<?> deletarAluno(CriarAlunoDTO alunoDTO, UUID id);
}
