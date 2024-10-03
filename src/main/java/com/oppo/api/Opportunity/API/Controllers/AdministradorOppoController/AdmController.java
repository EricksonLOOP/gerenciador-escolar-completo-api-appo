package com.oppo.api.Opportunity.API.Controllers.AdministradorOppoController;

import com.oppo.api.Opportunity.API.DTOs.AdminDTO.AdminDTO;
import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Services.AdministradorService.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> putAdmin(@PathVariable("id") UUID id, @RequestBody AdminDTO adminDTO){
        return administradorService.update(id, adminDTO);

    }
}
