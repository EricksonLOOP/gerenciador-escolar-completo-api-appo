package com.oppo.api.Opportunity.API.Controllers.EscolaController;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Services.EscolasService.EscolaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/escolas")
public class EscolaController {

    @Autowired
    private EscolaServices escolaServices;

    @PostMapping("/create")
    public ResponseEntity<?> createEscola(@RequestBody EscolasDTO escolasDTO) {
        return escolaServices.create(escolasDTO);
    }
}

