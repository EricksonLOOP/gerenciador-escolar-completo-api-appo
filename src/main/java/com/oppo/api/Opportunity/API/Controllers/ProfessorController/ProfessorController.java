package com.oppo.api.Opportunity.API.Controllers.ProfessorController;

import com.oppo.api.Opportunity.API.DTOs.ProfessorDTOs.CriarProfessorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {
    @PostMapping("/create")
    public ResponseEntity<?> responseEntity(@RequestBody CriarProfessorDTO criarProfessorDTO){
        return
    }

}
