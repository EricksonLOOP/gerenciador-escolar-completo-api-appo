package com.oppo.api.Opportunity.API.Controllers.OppoManagement.AlunoController;

import com.oppo.api.Opportunity.API.DTOs.OppoManagement.AlunoDTOs.CriarAlunoDTO;
import com.oppo.api.Opportunity.API.Services.OppoManagement.AlunosService.AlunosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aluno")
public class AlunoCotroller {
    @Autowired
    AlunosService alunosService;
    @PostMapping("/create")
    public ResponseEntity<?> criarAluno(@RequestBody CriarAlunoDTO alunoDTO){
            return alunosService.criarAluno(alunoDTO);
    }
}
