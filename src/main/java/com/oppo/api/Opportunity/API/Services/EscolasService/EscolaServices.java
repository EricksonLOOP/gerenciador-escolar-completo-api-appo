package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.CriarTurmaDTO;
import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.CriarEscolasDTO;
import com.oppo.api.Opportunity.API.DTOs.MateriasDTO.CriarMateriasDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface EscolaServices {
    //CriarEscola e Atualizar Escola
    ResponseEntity<?> create(CriarEscolasDTO criarEscolasDTO);
    ResponseEntity<?> update(CriarEscolasDTO criarEscolasDTO);

    //Adicionar, Remover e Listar aluno na Escola
    ResponseEntity<?> addAluno(UUID idAluno, UUID idEscola);
    ResponseEntity<?> removeAluno(UUID idAluno, UUID idEscola);
    ResponseEntity<?> listarAlunos(UUID idEscola);

    //Adicionar, Remover e Listar professor na Escola
    ResponseEntity<?> addProfessor(UUID idProfessor, UUID idEscola);
    ResponseEntity<?> removeProfessor(UUID idProfessor, UUID idEscola);
    ResponseEntity<?> listarProfessor(UUID idEscola);

    //Funções da Escola sobre uma turma
    ResponseEntity<?> criarTurma(CriarTurmaDTO criarTurmaDTO, UUID idEscola);
    ResponseEntity<?> deletarTurma(UUID idTurma, UUID idEscola);
    ResponseEntity<?> listarTurma(UUID idEscola);  //Já existente
    ResponseEntity<?> addMateriaNaTurma(UUID idTurma, UUID idMateria, UUID idEscola);
    ResponseEntity<?> matricularAlunoNaTurma(UUID idTurma, UUID idAluno, UUID idEscola);
    ResponseEntity<?> removerMateriaNaTurma(UUID idTurma, UUID idMateria, UUID idEscola);
    ResponseEntity<?> listarTodasTurmas(UUID idEscola);
    //Funções da Escola sobre uma matéria
    ResponseEntity<?> criarMateria(CriarMateriasDTO criarMateriasDTO);
    ResponseEntity<?> deletarMateria(UUID idMateria, UUID idEscola);
    ResponseEntity<?> addProfessorDaMateria(UUID idMateria, UUID idProfessor);
    ResponseEntity<?> listarMaterias();
}
