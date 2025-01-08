package com.oppo.api.Opportunity.API.Services.OppoManagement.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.OppoManagement.AlunoDTOs.ListarAlunosDTO;
import com.oppo.api.Opportunity.API.DTOs.CriarTurmaDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoManagement.EscolasDTOs.CriarEscolasDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoManagement.MateriasDTO.CriarMateriasDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoManagement.ProfessorDTOs.ListarProfessoresDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoManagement.TurmaDTOs.ListarTurmasDTO;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.MateriasEntity.MateriasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Entitys.TurmasEntity.TurmasEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesEscola.InformacoesEscola;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.MateriasRepositories.MateriasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices.ValidacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EscolaServicesImpl implements EscolaServices {

    @Autowired
    private EscolasRepository escolasRepository;
    @Autowired
    private ValidacoesService validacoesService;
    @Autowired
    private AlunosRepository alunosRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private MateriasRepository materiasRepository;
    @Override
    public ResponseEntity<?> create(CriarEscolasDTO criarEscolasDTO) {
        try {

            if (!validacoesService.validarcnpj(criarEscolasDTO.cnpj())){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ não existe.");
            }

            Optional<EscolasEntity> encontrarEscola = escolasRepository.findByInformacoesEscola_Cnpj(criarEscolasDTO.cnpj());
            if (encontrarEscola.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já registrado. Não é possível criar uma escola com este CNPJ");
            }

            // Cria uma nova entidade de escola a partir do DTO
            EscolasEntity escola = EscolasEntity.builder()
                    .informacoesEscola(new InformacoesEscola(
                            criarEscolasDTO.nivelDeEnsino(),
                            criarEscolasDTO.nivelDeEnsino(),
                            criarEscolasDTO.capMax(),
                            criarEscolasDTO.numFuncionarios(),
                            criarEscolasDTO.turnos(),
                            criarEscolasDTO.numSalas(),
                            criarEscolasDTO.numLaboratorios(),
                            criarEscolasDTO.numBiblioteca(),
                            criarEscolasDTO.possuiInternet(),
                            criarEscolasDTO.modeloEscolar(),
                            criarEscolasDTO.nome(),
                            criarEscolasDTO.cnpj()
                    ))
                    .contatoEscola(new ContatoModel(criarEscolasDTO.telefone(), criarEscolasDTO.email()))
                    .endereco(new EnderecoModel(
                            criarEscolasDTO.rua(),
                            criarEscolasDTO.numero(),
                            criarEscolasDTO.bairro(),
                            criarEscolasDTO.cidade(),
                            criarEscolasDTO.estado(),
                            criarEscolasDTO.cep()
                    ))
                    .senha(criarEscolasDTO.senha())
                    .role(TagsENUM.ESCOLA)
                    .build();


            EscolasEntity savedEscola = escolasRepository.save(escola);
            return ResponseEntity.status(HttpStatus.CREATED).body("Escola criada com sucesso! ID: " + savedEscola.getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a escola: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> update(CriarEscolasDTO criarEscolasDTO) {
        try {

            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(criarEscolasDTO.cnpj());


            if (optEscola.isPresent()) {

                EscolasEntity escola = optEscola.get();


                escola.getInformacoesEscola().setNome(criarEscolasDTO.nome());
                escola.getInformacoesEscola().setCnpj(criarEscolasDTO.cnpj());
                escola.setSenha(criarEscolasDTO.senha());


                return ResponseEntity.status(HttpStatus.OK).body(escolasRepository.save(escola));
            }


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao atualizar escola.");
        }
    }


    @Override
    public ResponseEntity<?> addAluno(UUID idAluno, UUID idEscola) {
        try {

            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);

            Optional<AlunosEntity> optAluno = alunosRepository.findById(idAluno);

            if (optEscola.isPresent() && optAluno.isPresent()) {
                EscolasEntity escola = optEscola.get();
                AlunosEntity aluno = optAluno.get();
                try {
                    escola.getAlunos().add(aluno);
                    aluno.setEscola(escola);
                    escolasRepository.save(escola);
                    alunosRepository.save(aluno);
                    return ResponseEntity.status(HttpStatus.OK).body("O aluno foi adicionado na escola com sucesso!");

                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar aluno na escola ou a escola no aluno.");
                }
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao procurar por aluno e escola. Mensage: %s".formatted(e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> removeAluno(UUID idAluno, UUID idEscola) {
        try {

            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            Optional<AlunosEntity> optAluno = alunosRepository.findById(idAluno);

            if (optEscola.isPresent() && optAluno.isPresent()) {

                EscolasEntity escola = optEscola.get();
                AlunosEntity aluno = optAluno.get();

                try {
                    escola.getAlunos().remove(aluno);
                    aluno.setEscola(null);
                    escolasRepository.save(escola);
                    alunosRepository.save(aluno);
                    return ResponseEntity.status(HttpStatus.OK).body("O aluno foi removido da escola com sucesso!");

                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover aluno da escola ou a escola do aluno.");
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao procurar por aluno e escola. Mensage: %s".formatted(e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> listarAlunos(UUID idEscola) {
        try {

            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);

            if (optEscola.isPresent()) {

                EscolasEntity escola = optEscola.get();
                List<ListarAlunosDTO> listarAlunosDTOS = escola.getAlunos()
                        .stream()
                        .map(alunosEntity -> new ListarAlunosDTO(
                                        alunosEntity.getInformacoesPessoais().getNome(),
                                        alunosEntity.getId()
                                        ))
                        .toList();

                return ResponseEntity.status(HttpStatus.OK).body(listarAlunosDTOS);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro no servidor: %s".formatted(e.getMessage()));
        }
    }



    @Override
    public ResponseEntity<?> addProfessor(UUID idProfessor, UUID idEscola) {
        try {

            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            Optional<ProfessoresEntity> optProfessor = professorRepository.findById(idProfessor);

            if (optEscola.isPresent() && optProfessor.isPresent()) {

                EscolasEntity escola = optEscola.get();
                ProfessoresEntity professor = optProfessor.get();

                if (escola.getProfessores().contains(professor)) {

                    if (professor.getEscola() == escola) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Professor e escola já relacionados.");
                    }
                    professor.setEscola(escola);
                    professorRepository.save(professor);
                } else if (professor.getEscola() != escola) {

                    escola.getProfessores().add(professor);
                    professor.setEscola(escola);
                    escolasRepository.save(escola);
                    professorRepository.save(professor);
                }
                return ResponseEntity.status(HttpStatus.OK).body("Professor adicionado com sucesso!");

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola ou Professor não encontrados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: %s".formatted(e.getMessage()));
        }
    }



    @Override
    public ResponseEntity<?> removeProfessor(UUID idProfessor, UUID idEscola) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            Optional<ProfessoresEntity> optProfessor = professorRepository.findById(idProfessor);

            if (optEscola.isPresent() && optProfessor.isPresent()){
                EscolasEntity escola = optEscola.get();
                ProfessoresEntity professor = optProfessor.get();
                if (escola.getProfessores().contains(professor)){
                    if (professor.getEscola() == escola){
                        escola.getProfessores().remove(professor);
                        professor.setEscola(null);
                        escolasRepository.save(escola);
                        professorRepository.save(professor);
                        return ResponseEntity.status(HttpStatus.OK).body("Professor removido.");
                    }
                    escola.getProfessores().remove(professor);
                    escolasRepository.save(escola);
                    return ResponseEntity.status(HttpStatus.OK).body("Professor removido.");
                }
              return ResponseEntity.status(HttpStatus.OK).body("O professor não esta associado a escola");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> listarProfessor(UUID idEscola) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
               List<ListarProfessoresDTO> professores = escola.getProfessores()
                       .stream()
                       .map(professoresEntity -> new ListarProfessoresDTO(
                               professoresEntity.getInformacoesPessoais().getNome(),
                               professoresEntity.getId() ))
                       .toList();


                return  ResponseEntity.status(HttpStatus.OK).body("Lista de Professores: "+escola.getProfessores());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> criarTurma(CriarTurmaDTO criarTurmaDTO, UUID idEscola) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            if (!optEscola.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada!");
            }
            EscolasEntity escola = optEscola.get();
            if (escola.getTurmas().stream().anyMatch(turmasEntity -> turmasEntity.getCodigo().equals(criarTurmaDTO.codigo())
                    && turmasEntity.getAno().equals(criarTurmaDTO.ano()))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe uma turma com est código para este ano");
            }
            TurmasEntity turma = TurmasEntity.builder()
                    .ano(criarTurmaDTO.ano())
                    .serie(criarTurmaDTO.serie())
                    .codigo(criarTurmaDTO.codigo())
                    .turno(criarTurmaDTO.turno())
                    .build();
            escola.getTurmas().add(turma);
            escolasRepository.save(escola);
            return ResponseEntity.status(HttpStatus.OK).body("Turma criada com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deletarTurma(UUID idTurma, UUID idEscola) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            if (!optEscola.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
            }

            EscolasEntity escola = optEscola.get();

            // Filtra para encontrar a turma com o ID fornecido
            Optional<TurmasEntity> optTurma = escola.getTurmas()
                    .stream()
                    .filter(turmasEntity -> turmasEntity.getId().equals(idTurma)) // Filtra pela turma correta
                    .findAny(); // Pega a primeira correspondência (caso haja)

            if (!optTurma.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Turma não encontrada na escola.");
            }

            TurmasEntity turmaRemover = optTurma.get();

            // Remover a turma dos alunos associados
            turmaRemover.getAlunos().forEach(aluno -> {
                aluno.setTurma(null);  // Define a turma do aluno como null
                alunosRepository.save(aluno);  // Persiste a mudança no banco de dados
            });

            // Remover a turma dos professores associados
            turmaRemover.getProfessoresEntities().forEach(professor -> {
                professor.getTurmas().remove(turmaRemover);
                professorRepository.save(professor);
            });

            // Remover a turma da lista de turmas da escola
            escola.getTurmas().remove(turmaRemover);

            escolasRepository.save(escola);

            return ResponseEntity.status(HttpStatus.OK).body("Turma removida com sucesso de todas as entidades!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao tentar remover a turma!");
        }
    }


    @Override
    public ResponseEntity<?> listarTurma(UUID idEscola) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            if(!optEscola.isPresent()){
            return ResponseEntity.badRequest().body("A escola não existe");
            }
            List<TurmasEntity> turmas = optEscola.get().getTurmas();
            if (turmas.isEmpty()){
                return ResponseEntity.ok().body("A escola não tem turmas presentes");
            }
            List<ListarTurmasDTO> turmasDTOS = turmas.stream()
                    .map( turmasEntity -> new ListarTurmasDTO(turmasEntity.getCodigo(), turmasEntity.getId()))
                    .toList();
            return ResponseEntity.ok().body(turmasDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno.");
        }
    }

    @Override
    public ResponseEntity<?> addMateriaNaTurma(UUID idTurma, UUID idMateria, UUID idEscola) {
        try {
            // Verifica se a escola existe
            Optional<EscolasEntity> optionalEscolas = escolasRepository.findById(idEscola);
            if (optionalEscolas.isEmpty()) {
                return ResponseEntity.badRequest().body("Escola não encontrada");
            }
            EscolasEntity escola = optionalEscolas.get();

            // Verifica se a turma existe na escola
            Optional<TurmasEntity> optTurma = escola.getTurmas()
                    .stream()
                    .filter(turmasEntity -> turmasEntity.getId().equals(idTurma))
                    .findFirst();
            if (optTurma.isEmpty()) {
                return ResponseEntity.badRequest().body("Turma não encontrada");
            }
            TurmasEntity turma = optTurma.get();

            // Verifica se a matéria existe
            Optional<MateriasEntity> optMaterias = materiasRepository.findById(idMateria);
            if (optMaterias.isEmpty()) {
                return ResponseEntity.badRequest().body("Matéria não encontrada.");
            }
            MateriasEntity materiaAdicionada = optMaterias.get();

            // Verifica se a matéria já foi adicionada à turma
            if (turma.getMaterias().contains(materiaAdicionada)) {
                return ResponseEntity.badRequest().body("Matéria já está associada a essa turma.");
            }

            // Adiciona a matéria à turma
            turma.getMaterias().add(materiaAdicionada);

            // Adiciona a matéria para todos os alunos da turma
            List<AlunosEntity> alunos = turma.getAlunos();
            for (AlunosEntity aluno : alunos) {
                if (!aluno.getMaterias().contains(materiaAdicionada)) {
                    aluno.getMaterias().add(materiaAdicionada);
                }
            }

            alunosRepository.saveAll(alunos);
            escolasRepository.save(escola);

            return ResponseEntity.ok().body("Matéria adicionada na turma com sucesso");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> matricularAlunoNaTurma(UUID idTurma, UUID idAluno, UUID idEscola) {
        try {
            // Verifica se a escola existe
            Optional<EscolasEntity> optionalEscolas = escolasRepository.findById(idEscola);
            if (optionalEscolas.isEmpty()) {
                return ResponseEntity.badRequest().body("Escola não encontrada");
            }
            EscolasEntity escola = optionalEscolas.get();

            // Verifica se a turma existe na escola
            Optional<TurmasEntity> optionalTurmas = escola.getTurmas()
                    .stream()
                    .filter(turmasEntity -> turmasEntity.getId().equals(idTurma))
                    .findFirst();
            if (optionalTurmas.isEmpty()) {
                return ResponseEntity.badRequest().body("Turma não encontrada.");
            }
            TurmasEntity turma = optionalTurmas.get();

            // Verifica se o aluno existe
            Optional<AlunosEntity> optionalAlunos = alunosRepository.findById(idAluno);
            if (optionalAlunos.isEmpty()) {
                return ResponseEntity.badRequest().body("Aluno não encontrado.");
            }
            AlunosEntity aluno = optionalAlunos.get();

            // Verifica se o aluno já está matriculado na turma
            if (turma.getAlunos().contains(aluno)) {
                return ResponseEntity.badRequest().body("Aluno já está matriculado na turma.");
            }

            // Matricula o aluno na turma
            List<MateriasEntity> materiasDaTurma = turma.getMaterias();
            turma.getAlunos().add(aluno);
            aluno.setTurma(turma);

            // Remove as matérias que o aluno tem, mas que não estão na nova turma
            List<MateriasEntity> materiasRemover = aluno.getMaterias().stream()
                    .filter(materia -> !materiasDaTurma.contains(materia))
                    .toList();
            aluno.getMaterias().removeAll(materiasRemover);

            // Adiciona as matérias da turma ao aluno, caso ele ainda não as tenha
            materiasDaTurma.forEach(materia -> {
                if (!aluno.getMaterias().contains(materia)) {
                    aluno.getMaterias().add(materia);
                }
            });

            // Salva as mudanças no banco de dados
            alunosRepository.save(aluno);
            escolasRepository.save(escola);

            return ResponseEntity.ok().body("Aluno matriculado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
    

    @Override
    public ResponseEntity<?> removerMateriaNaTurma(UUID idTurma, UUID idMateria, UUID idEscola) {
        try {
            // Verifica se a escola existe
            Optional<EscolasEntity> optionalEscolas = escolasRepository.findById(idEscola);
            if (optionalEscolas.isEmpty()) {
                return ResponseEntity.badRequest().body("A escola não está presente.");
            }
            EscolasEntity escola = optionalEscolas.get();

            // Verifica se a turma existe
            Optional<TurmasEntity> optionalTurma = escola.getTurmas().stream()
                    .filter(turma -> turma.getId().equals(idTurma))
                    .findFirst();
            if (optionalTurma.isEmpty()) {
                return ResponseEntity.badRequest().body("Turma não encontrada");
            }
            TurmasEntity turma = optionalTurma.get();

            // Verifica se a matéria existe na turma
            Optional<MateriasEntity> materiaOptional = turma.getMaterias().stream()
                    .filter(materias -> materias.getId().equals(idMateria))
                    .findFirst();

            if (materiaOptional.isPresent()) {
                MateriasEntity materia = materiaOptional.get();
                turma.getMaterias().remove(materia);

                // Remove a matéria dos alunos
                List<AlunosEntity> alunos = turma.getAlunos();
                if (!alunos.isEmpty()){
                    alunos.forEach(aluno -> aluno.getMaterias().remove(materia));
                }
                escolasRepository.save(escola);
                alunosRepository.saveAll(alunos);
                return ResponseEntity.ok().body("Matéria removida com sucesso.");
            }
            return ResponseEntity.badRequest().body("A matéria não foi encontrada na turma.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> listarTodasTurmas(UUID idEscola) {
        try {
            Optional<EscolasEntity> optionalEscolas = escolasRepository.findById(idEscola);
            if (optionalEscolas.isEmpty()){
                return ResponseEntity.badRequest().body("Escola não encontrada");
            }
            EscolasEntity escola = optionalEscolas.get();
            List<TurmasEntity> listaTurmas = escola.getTurmas();
            if (optionalEscolas.isEmpty()){
                return ResponseEntity.ok().body(listaTurmas);
            }
            List<ListarTurmasDTO> litaTurmasDTO = listaTurmas
                    .stream()
                    .map(turma -> new ListarTurmasDTO(turma.getCodigo(), turma.getId()))
                    .toList();
            return ResponseEntity.ok().body(litaTurmasDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno. %s".formatted(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> criarMateria(CriarMateriasDTO criarMateriasDTO) {
        try {
            Optional<MateriasEntity> optionalMaterias = materiasRepository.findByMateria(criarMateriasDTO.materia());
            if (optionalMaterias.isEmpty()){
                return ResponseEntity.badRequest().body("Escola não encontrada");
            }
            if (optionalMaterias.isPresent()){
                return ResponseEntity.badRequest().body("Matéria ja existe.");
            }
            MateriasEntity materia = MateriasEntity.builder()
                    .materia(criarMateriasDTO.materia())
                    .descricao(criarMateriasDTO.descricao())
                    .build();
            materiasRepository.save(materia);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno. %s".formatted(e.getMessage()));
        }
        return null;
    }

    @Override
    public ResponseEntity<?> deletarMateria(UUID idMateria, UUID idEscola) {
        try{
           //não feito ainda

        } catch (Exception e) {
            return  ResponseEntity.internalServerError().body("Erro interno");
        }
        return null;
    }

    @Override
    public ResponseEntity<?> addProfessorDaMateria(UUID idMateria, UUID idProfessor) {
        try{
            Optional<ProfessoresEntity> optionalProfessor = professorRepository.findById(idProfessor);
            if (optionalProfessor.isEmpty()){
                return ResponseEntity.badRequest().body("Professor não encontrado");
            }
            Optional<MateriasEntity> optionalMaterias = materiasRepository.findById(idMateria);
            if (optionalMaterias.isEmpty()){
                return ResponseEntity.badRequest().body("Matéria não encontrada.");
            }
            MateriasEntity materia = optionalMaterias.get();
            ProfessoresEntity professor = optionalProfessor.get();
            professor.getMaterias().add(materia);
            materia.getProfessores().add(professor);
            professorRepository.save(professor);
            materiasRepository.save(materia);
           return  ResponseEntity.ok().body("Professore matérias relacionados com sucesso");



        } catch (Exception e) {
            return  ResponseEntity.internalServerError().body("Erro interno");
        }

    }

    @Override
    public ResponseEntity<?> listarMaterias() {
        try {
            List<MateriasEntity> materiasEntities = materiasRepository.findAll();
            return ResponseEntity.ok().body(materiasEntities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno");
        }
    }
}

