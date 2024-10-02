package com.oppo.api.Opportunity.API.Controllers.AdministradorOppoController;

import com.oppo.api.Opportunity.API.DTOs.AdminDTO.AdminDTO;
import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Services.AdministradorService.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdmController {
    @Autowired
    final private AdministradorService administradorService;

    public AdmController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping("/create/new/admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO adminDTO){
        return administradorService.create(adminDTO);

    }
}
