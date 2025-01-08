package com.oppo.api.Opportunity.API.Controllers.OppoManagement.EscolaController;

import com.oppo.api.Opportunity.API.DTOs.CriarTurmaDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoManagement.EscolasDTOs.CriarEscolasDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoManagement.MateriasDTO.CriarMateriasDTO;
import com.oppo.api.Opportunity.API.Services.OppoManagement.EscolasService.EscolaServices;
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
    @GetMapping("/alunos/listar/{idEscola}")
    public ResponseEntity<?> listarAluno(@PathVariable("idEscola") UUID escola){
        return escolaServices.listarAlunos(escola);
    }


    @PostMapping("/{idEscola}/aluno/{idAluno}")
    public ResponseEntity<?> addAluno(
            @PathVariable("idAluno") UUID idAluno,
            @PathVariable("idEscola") UUID idEscola){
        return escolaServices.addAluno(idAluno, idEscola);
    }
    @DeleteMapping("/{idEscola}/aluno/{idAluno}")
    public ResponseEntity<?> removeAluno(
            @PathVariable("idAluno") UUID idAluno,
            @PathVariable("idEscola") UUID idEscola){
        return escolaServices.removeAluno(idAluno, idEscola);
    }
    @GetMapping("/{idEscola}/alunos/")
    public ResponseEntity<?> listarAlunos(@PathVariable("idEscola") UUID idEscola){
        return escolaServices.listarAlunos(idEscola);
    }


    @PostMapping("{idEscola}/professores/{idProfessor}")
    public ResponseEntity<?> addProfessor(
            @PathVariable("idProfessor") UUID idProfessor,
            @PathVariable("idEscola") UUID idEscola){
        return escolaServices.addProfessor(idProfessor, idEscola);
    }
    @DeleteMapping("{idEscola}/professores/{idProfessor}")
    public ResponseEntity<?> removeProfessor(
            @PathVariable("idProfessor") UUID idProfessor,
            @PathVariable("idEscola") UUID idEscola){
        return escolaServices.removeProfessor(idProfessor, idEscola);
    }
    @GetMapping("{idEscola}/professores")
    public ResponseEntity<?> listarProfessor(@PathVariable("idEscola") UUID idEscola){
        return escolaServices.listarProfessor(idEscola);
    }


    @PostMapping("/{idEscola}/turmas")
    public  ResponseEntity<?> criarTurma(@PathVariable("idEscola") UUID idEscola, CriarTurmaDTO criarTurmaDTO){
        return escolaServices.criarTurma(criarTurmaDTO, idEscola);
    }
    @PostMapping("/{idEscola}/turmas/{idTurma}/materia/{idMateria}")
    public ResponseEntity<?> addMateriaNaTurma(@PathVariable("idEscola") UUID idEscola, @PathVariable("idTurma") UUID idTurma, @PathVariable("idMateria") UUID idMateria) {
        return escolaServices.addMateriaNaTurma(idTurma, idMateria, idEscola);
    }
    @DeleteMapping("/{idEscola}/turmas/{idTurma}")
    public ResponseEntity<?> deletarTurma(@PathVariable("idEscola") UUID idEscola, @PathVariable("idTurma") UUID idTurma){
        return escolaServices.deletarTurma(idTurma,idEscola);
    }
    @DeleteMapping("/{idEscola}/turmas/{idTurma}/materias/{idMateria}")
    public ResponseEntity<?> removerMateriaNaTurma(@PathVariable("idEscola") UUID idEscola, @PathVariable("idTurma") UUID idTurma, @PathVariable("idMateria") UUID idMateria){
        return escolaServices.removerMateriaNaTurma(idEscola, idTurma, idMateria);
    }
    @GetMapping("/{idEscola}/turmas")
    public ResponseEntity<?> listarTurmas(@PathVariable("idEscola") UUID idEscola){
        return escolaServices.listarTurma(idEscola);
    }
    @PutMapping("/{idEscola}/turmas/{idTurma}/alunos/{idAluno}")
    public ResponseEntity<?> matricularAlunoNaTurma(@PathVariable("idEscola") UUID idEscola, @PathVariable("idTurma") UUID idTurma, @PathVariable("idAluno") UUID idAluno ){
        return escolaServices.matricularAlunoNaTurma(idEscola, idTurma, idAluno);
    }

    @PostMapping("/materias")
    public ResponseEntity<?> criarMateria(CriarMateriasDTO criarMateriasDTO){
        return escolaServices.criarMateria(criarMateriasDTO);
    }
    @DeleteMapping("{idEscola}/materias/{idMateria}")
    public ResponseEntity<?> deletarMateria(@PathVariable("idMateria") UUID idMateria, @PathVariable("idEscola") UUID idEscola){
        return escolaServices.deletarMateria(idMateria, idEscola);
    }
    @GetMapping("/materias")
    public ResponseEntity<?> listarMaterias(){
        return escolaServices.listarMaterias();
    }
    @PostMapping("/materias/{idMateria}/professores/{idProfessor}")
    public ResponseEntity<?> adicionarProfessorNaMateria(@PathVariable("idMateria") UUID idMateria, @PathVariable("idProfessor") UUID idProfessor){
        return escolaServices.addProfessorDaMateria(idMateria, idProfessor);
    }
}

