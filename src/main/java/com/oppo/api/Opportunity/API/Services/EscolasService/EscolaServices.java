package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface EscolaServices {
     ResponseEntity<?> create(EscolasDTO escolasDTO);

    ResponseEntity<?> login(EscolasDTO escolasDTO);

    ResponseEntity<?> update(EscolasDTO escolasDTO);

    ResponseEntity<?> addAluno(EscolasDTO escolasDTO);

    ResponseEntity<?> removeAluno(EscolasDTO escolasDTO);

    ResponseEntity<?> listarAluno(EscolasDTO escolasDTO);

    ResponseEntity<?> addProfessor(EscolasDTO escolasDTO);
    ResponseEntity<?> removeProfessor(EscolasDTO escolasDTO);

    ResponseEntity<?> listarProfessor(EscolasDTO escolasDTO);
}
