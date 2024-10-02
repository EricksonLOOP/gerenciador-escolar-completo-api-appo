package com.oppo.api.Opportunity.API.Controllers.EscolaController;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Services.EscolasService.EscolaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/escolas")
public class EscolaController {

    @Autowired
    private EscolaServices escolaServices;

    @PostMapping("/create")
    public ResponseEntity<?> createEscola(@RequestBody EscolasDTO escolasDTO) {
        return escolaServices.create(escolasDTO);
    }
    @GetMapping("/aluno/listar")
    public ResponseEntity<?> listarAluno(@RequestBody EscolasDTO escolasDTO){
        return escolaServices.listarAluno(escolasDTO);
    }
    @GetMapping("/hello")
    public String helloWord(){
        return "Hello, world!";
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateEscola(@RequestBody EscolasDTO escolasDTO){
        return  escolaServices.update(escolasDTO);
    }
    @PutMapping("/aluno/add")
    public ResponseEntity<?> addAluno(@RequestBody EscolasDTO escolasDTO){
        return escolaServices.addAluno(escolasDTO);
    }
    @PutMapping("/aluno/remove")
    public ResponseEntity<?> removeAluno(@RequestBody EscolasDTO escolasDTO){
        return escolaServices.removeAluno(escolasDTO);
    }
    @PutMapping("/professor/add")
    public ResponseEntity<?> addProfessor(@RequestBody EscolasDTO escolasDTO){
        return escolaServices.addProfessor(escolasDTO);
    }
    @PutMapping("/professor/remove")
    public ResponseEntity<?> removeProfessor(@RequestBody EscolasDTO escolasDTO){
        return escolaServices.removeProfessor(escolasDTO);
    }
    @GetMapping("/professor/listar")
    public ResponseEntity<?> listarProfessor(@RequestBody EscolasDTO escolasDTO){
        return escolaServices.listarProfessor(escolasDTO);
    }
}

