package com.oppo.api.Opportunity.API.Controllers.EscolaController;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.CriarEscolasDTO;
import com.oppo.api.Opportunity.API.Services.EscolasService.EscolaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/escolas")
@CrossOrigin("http://localhost:5173/")
public class EscolaController {

    @Autowired
    private EscolaServices escolaServices;
    @GetMapping("/hello")
    public String helloWord(){
        return "Hello, world!";
    }
    @PostMapping("/create")
    public ResponseEntity<?> createEscola(@RequestBody CriarEscolasDTO criarEscolasDTO) {
        return escolaServices.create(criarEscolasDTO);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEscola(@PathVariable("id") UUID id, @RequestBody CriarEscolasDTO criarEscolasDTO){
        return  escolaServices.update(criarEscolasDTO);
    }
    @GetMapping("/alunos/listar/{escolaId}")
    public ResponseEntity<?> listarAluno(@PathVariable("escolaId") UUID escola){
        return escolaServices.listarAlunos(escola);
    }
    @PutMapping("/aluno/{alunoid}/{escolaId}/add")
    public ResponseEntity<?> addAluno(
            @PathVariable("alunoid") UUID aluno,
            @PathVariable("escolaId") UUID escola){
        return escolaServices.addAluno(aluno, escola);
    }
    @PutMapping("/aluno/{alunoId}/{escolaId}/remove")
    public ResponseEntity<?> removeAluno(
            @PathVariable("alunoId") UUID alunoId,
            @PathVariable("escolaId") UUID escolaId){
        return escolaServices.removeAluno(alunoId, escolaId);
    }
    @PutMapping("/professor/{professorId}/{escolaId}/add")
    public ResponseEntity<?> addProfessor(
            @PathVariable("professorId") UUID profesorId,
            @PathVariable("escolaId") UUID escolaId,
            @RequestBody CriarEscolasDTO criarEscolasDTO
    ){
        return escolaServices.addProfessor(criarEscolasDTO);
    }
    @PutMapping("/professor/{professorId}/{escolaId}/remove")
    public ResponseEntity<?> removeProfessor(
            @PathVariable("professorId") UUID professorId,
            @PathVariable("escolaId") UUID escolaId,
            @RequestBody CriarEscolasDTO criarEscolasDTO){
        return escolaServices.removeProfessor(criarEscolasDTO);
    }
    @GetMapping("/professor/{escolaId}/listar")
    public ResponseEntity<?> listarProfessor(@RequestBody CriarEscolasDTO criarEscolasDTO){
        return escolaServices.listarProfessor(criarEscolasDTO);
    }
}

