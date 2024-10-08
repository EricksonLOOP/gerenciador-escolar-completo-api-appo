package com.oppo.api.Opportunity.API.Controllers.AdministradorOppoController;

import com.oppo.api.Opportunity.API.DTOs.AdminDTOs.AdminDTO;
import com.oppo.api.Opportunity.API.DTOs.AdminDTOs.AdminEscolasDTO;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Services.AdministradorService.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<AdminEscolasDTO>> listarEscolas() {
        List<EscolasEntity> escolas = administradorService.listarEscolas();
        List<AdminEscolasDTO> admEscolasDTOS = escolas
                .stream()
                .map(escola -> new AdminEscolasDTO(escola.getInformacoesEscola().getNome(), escola.getId()))
                .collect(Collectors.toList());

        // Retorne a lista diretamente como corpo da resposta
        return ResponseEntity.ok(admEscolasDTOS);
    }
    @GetMapping("/infos/escola/{id}")
    public ResponseEntity<?> getInfosEscola(@PathVariable("id") UUID Id){
        return administradorService.getInfosEscola(Id);
    }

}
