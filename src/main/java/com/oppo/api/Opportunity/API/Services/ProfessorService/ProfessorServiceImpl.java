package com.oppo.api.Opportunity.API.Services.ProfessorService;

import com.oppo.api.Opportunity.API.DTOs.ProfessorDTOs.CriarProfessorDTO;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.MateriasEntity.MateriasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.InformacoesProfissionaisModel.InformacoesProfissionaisModel;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.MateriasRepositories.MateriasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.Services.ValidacoesServices.ValidacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private ValidacoesService validacoesService;
    @Autowired
    private MateriasRepository materiasRepository;
    @Autowired
    private AlunosRepository alunosRepository;
    @Override
    public ResponseEntity<?> criarProfessor(CriarProfessorDTO criarProfessorDTO) {
        try {
            if (!validacoesService.validarcpf(criarProfessorDTO.cpf())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido!");
            }
            Optional<ProfessoresEntity> professor = professorRepository.findByInformacoesPessoais_Cpf(criarProfessorDTO.cpf());
            if (professor.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um professor registrado com este CPF!");
            }
            ProfessoresEntity professoresEntity = new ProfessoresEntity().builder()
                    .informacoesPessoais( new InformacoesPessoaisModel(
                            criarProfessorDTO.nome(),
                            criarProfessorDTO.dataDeNascimento(),
                            criarProfessorDTO.cpf(),
                            criarProfessorDTO.rg(),
                            criarProfessorDTO.mae(),
                            criarProfessorDTO.pai()
                    ))
                    .iformacoesProfissionais( new InformacoesProfissionaisModel(
                            criarProfessorDTO.estadoCivil(),
                            criarProfessorDTO.naturalidade(),
                            criarProfessorDTO.cargo(),
                            criarProfessorDTO.departamento(),
                            criarProfessorDTO.tipoDeContrato(),
                            criarProfessorDTO.regimeDeTrabalho()
                    ))
                    .contatoModel( new ContatoModel(
                            criarProfessorDTO.telefone(),
                            criarProfessorDTO.email()
                    ))
                    .endereco( new EnderecoModel(
                            criarProfessorDTO.rua(),
                            criarProfessorDTO.numero(),
                            criarProfessorDTO.bairro(),
                            criarProfessorDTO.cidade(),
                            criarProfessorDTO.estado(),
                            criarProfessorDTO.cep()
                    ))
                    .senha(criarProfessorDTO.senha())
                    .role(TagsENUM.PROFESSOR)
                    .build();
            return ResponseEntity.ok().body(professorRepository.save(professoresEntity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> avaliarAluno(UUID idAluno, UUID idProfessor, UUID idMateria, String nota) {
        try {
            // Buscar a matéria pelo ID
            Optional<MateriasEntity> optionalMaterias = materiasRepository.findById(idMateria);
            if (optionalMaterias.isEmpty()) {
                return ResponseEntity.badRequest().body("Matéria não encontrada.");
            }
            MateriasEntity materia = optionalMaterias.get();

            // Verificar se o aluno está inscrito na matéria
            Optional<AlunosEntity> optionalAlunosEntity = materia.getAlunos()
                    .stream()
                    .filter(aluno -> aluno.getId().equals(idAluno))
                    .findFirst();
            if (optionalAlunosEntity.isEmpty()) {
                return ResponseEntity.badRequest().body("Aluno não encontrado na matéria.");
            }
            AlunosEntity aluno = optionalAlunosEntity.get();

            // Verificar se o professor leciona essa matéria para o aluno
            Optional<MateriasEntity> materiaDoAluno = aluno.getMaterias()
                    .stream()
                    .filter(materiasEntity -> materiasEntity.getId().equals(idMateria))
                    .findFirst();

            if (materiaDoAluno.isEmpty()) {
                return ResponseEntity.badRequest().body("Matéria não encontrada para o aluno.");
            }

            Optional<ProfessoresEntity> alunoTemProfessor = materiaDoAluno.get()
                    .getProfessores()
                    .stream()
                    .filter(professoresEntity -> professoresEntity.getId().equals(idProfessor))
                    .findFirst();

            if (alunoTemProfessor.isEmpty()) {
                return ResponseEntity.badRequest().body("Professor não leciona essa matéria.");
            }

            // Validação da nota (exemplo: verificar se a nota é válida entre 0 e 10)
            try {
                double valorNota = Double.parseDouble(nota);
                if (valorNota < 0 || valorNota > 10) {
                    return ResponseEntity.badRequest().body("Nota inválida. Deve estar entre 0 e 10.");
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Nota inválida. Não é um número válido.");
            }

            // Adicionando a nota
            materiaDoAluno.get().getNota().add(nota);
            alunosRepository.save(aluno);

            return ResponseEntity.ok().body("Aluno avaliado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno.");
        }
    }


    @Override
    public ResponseEntity<?> observarAluno(UUID idAluno, UUID idProfessor) {
        return null;
    }

    @Override
    public ResponseEntity<?> diarioDeAula(UUID idProfessor) {
        return null;
    }

    @Override
    public ResponseEntity<?> realizarChamada(UUID idProfessor, UUID idTurma) {
        return null;
    }
}
