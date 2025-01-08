package com.oppo.api.Opportunity.API.Controllers.OppoManagement.AdministradorOppoController;

import com.oppo.api.Opportunity.API.DTOs.OppoManagement.AdminDTOs.AdminDTO;
import com.oppo.api.Opportunity.API.Services.OppoManagement.AdministradorService.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
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
    @GetMapping("/listar/escolas")
    @CrossOrigin
    public ResponseEntity<?> listarEscolas() {
        ResponseEntity<?> escolas = administradorService.listarEscolas();


        // Retorne a lista diretamente como corpo da resposta
        return escolas;
    }
    @GetMapping("/infos/escola/{id}")
    public ResponseEntity<?> getInfosEscola(@PathVariable("id") UUID Id){
        return administradorService.getInfosEscola(Id);
    }

}
