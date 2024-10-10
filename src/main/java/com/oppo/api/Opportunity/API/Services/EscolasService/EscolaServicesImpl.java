package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.AlunoDTOs.ListarAlunosDTO;
import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.CriarEscolasDTO;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesEscola.InformacoesEscola;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.Services.ValidacoesServices.ValidacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Override
    public ResponseEntity<?> create(CriarEscolasDTO criarEscolasDTO) {
        try {
            //Verifica se o CNPJ existe
            if (!validacoesService.validarcnpj(criarEscolasDTO.cnpj())){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ não existe.");
            }
            // Verifica se a escola já existe pelo CNPJ
            Optional<EscolasEntity> encontrarEscola = escolasRepository.findByInformacoesEscola_Cnpj(criarEscolasDTO.cnpj()); // Acesse diretamente o campo cnpj
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

            // Salva a nova escola no repositório
            EscolasEntity savedEscola = escolasRepository.save(escola);
            return ResponseEntity.status(HttpStatus.CREATED).body("Escola criada com sucesso! ID: " + savedEscola.getId());

        } catch (Exception e) {
            // Aqui você pode fazer um log do erro para depuração
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a escola: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> update(CriarEscolasDTO criarEscolasDTO) {
        try {
            // Procurando a escola no repositório usando o CNPJ da escola (baseado nas informações fornecidas em 'escolasDTO')
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(criarEscolasDTO.cnpj());

            // Verificando se a escola foi encontrada no banco de dados
            if (optEscola.isPresent()) {
                // Recupera a escola encontrada
                EscolasEntity escola = optEscola.get();

                // Atualizando os dados da escola com os valores fornecidos no 'escolasDTO'
                escola.getInformacoesEscola().setNome(criarEscolasDTO.nome());   // Atualiza o nome da escola
                escola.getInformacoesEscola().setCnpj(criarEscolasDTO.cnpj());   // Atualiza o CNPJ da escola
                escola.setSenha(criarEscolasDTO.senha());                        // Atualiza a senha da escola
                escola.setRole(criarEscolasDTO.role());                          // Atualiza o papel/role da escola (por exemplo, ADMIN)

                // Salvando as mudanças no banco de dados e retornando a resposta com status OK e os dados da escola atualizados
                return ResponseEntity.status(HttpStatus.OK).body(escolasRepository.save(escola));
            }

            // Se a escola não for encontrada, retornamos um erro com status BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada.");

        } catch (Exception e) {
            // Se houver algum erro durante a operação de atualização, retornamos um erro com status INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao atualizar escola.");
        }
    }


    @Override
    public ResponseEntity<?> addAluno(UUID idAluno, UUID idEscola) {
        try {
            // Procurando a escola no repositório usando o ID da escola
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            // Procurando o aluno no repositório usando o ID do aluno
            Optional<AlunosEntity> optAluno = alunosRepository.findById(idAluno);
            // Verificando se tanto a escola quanto o aluno foram encontrados
            if (optEscola.isPresent() && optAluno.isPresent()) {
                // Recupera a escola e o aluno encontrados no banco de dados
                EscolasEntity escola = optEscola.get();
                AlunosEntity aluno = optAluno.get();
                try {
                    // Adiciona o aluno à lista de alunos da escola
                    escola.getAlunos().add(aluno);
                    // Atualiza o campo 'escola' no aluno, associando-o à escola
                    aluno.setEscola(escola);
                    // Salvando a escola e o aluno de volta ao banco de dados (não mostrado no código, mas é importante realizar a persistência)
                    // Retorna uma resposta OK com uma mensagem de sucesso
                    return ResponseEntity.status(HttpStatus.OK).body("O aluno foi adicionado na escola com sucesso!");

                } catch (Exception e) {
                    // Se houver um erro ao adicionar o aluno à escola ou a escola ao aluno, retorna erro interno
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar aluno na escola ou a escola no aluno.");
                }
            }
            // Se o aluno não for encontrado, retorna um erro com status BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.");
        } catch (Exception e) {
            // Se ocorrer algum erro durante a procura pela escola e pelo aluno, retorna um erro interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao procurar por aluno e escola. Mensage: %s".formatted(e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> removeAluno(UUID idAluno, UUID idEscola) {
        try {
            // Procurando a escola no repositório usando o ID da escola
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            // Procurando o aluno no repositório usando o ID do aluno
            Optional<AlunosEntity> optAluno = alunosRepository.findById(idAluno);
            // Verificando se tanto a escola quanto o aluno foram encontrados
            if (optEscola.isPresent() && optAluno.isPresent()) {
                // Recupera a escola e o aluno encontrados no banco de dados
                EscolasEntity escola = optEscola.get();
                AlunosEntity aluno = optAluno.get();
                try {
                    // Remove o aluno da lista de alunos da escola
                    escola.getAlunos().remove(aluno);
                    // Desassocia a escola do aluno, definindo o campo 'escola' como null
                    aluno.setEscola(null);
                    // Salvando a escola e o aluno de volta ao banco de dados (não mostrado no código, mas é importante realizar a persistência)
                    // Retorna uma resposta OK com uma mensagem de sucesso
                    return ResponseEntity.status(HttpStatus.OK).body("O aluno foi removido da escola com sucesso!");
                } catch (Exception e) {
                    // Se houver um erro ao remover o aluno da escola ou a escola do aluno, retorna erro interno
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover aluno da escola ou a escola do aluno.");
                }
            }
            // Se o aluno ou a escola não for encontrado, retorna um erro com status BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.");
        } catch (Exception e) {
            // Se ocorrer algum erro durante a procura pela escola e pelo aluno, retorna um erro interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao procurar por aluno e escola. Mensage: %s".formatted(e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> listarAlunos(UUID idEscola) {
        try {
            // Procurando a escola no repositório usando o ID da escola
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            // Verificando se a escola foi encontrada no banco de dados
            if (optEscola.isPresent()) {
                EscolasEntity escola = optEscola.get(); // Obtendo a escola da Optional
                List<ListarAlunosDTO> listarAlunosDTOS = new ArrayList<>(); // Lista para armazenar os alunos DTO
                // Iterando sobre os alunos da escola e mapeando para DTOs
                for (int i = 0; i < escola.getAlunos().size(); i++) {
                    AlunosEntity aluno = escola.getAlunos().get(i); // Obtendo o aluno da lista
                    // Criando o DTO para cada aluno com os dados necessários
                    ListarAlunosDTO alunoDTO = new ListarAlunosDTO(
                            aluno.getInformacoesPessoais().getNome(),  // Nome do aluno
                            aluno.getId(),  // ID do aluno
                            aluno.getEscola().getInformacoesEscola().getNome()  // Nome da escola do aluno
                    );
                    listarAlunosDTOS.add(alunoDTO);  // Adicionando o DTO à lista
                }
                // Retorna uma resposta OK com a lista de DTOs dos alunos
                return ResponseEntity.status(HttpStatus.OK).body(listarAlunosDTOS);
            }
            // Se a escola não for encontrada, retorna BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            // Se houver um erro durante o processamento, retorna INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro no servidor: %s".formatted(e.getMessage()));
        }
    }



    @Override
    public ResponseEntity<?> addProfessor(UUID idProfessor, UUID idEscola) {
        try {
            // Procurando a escola pelo ID fornecido
            Optional<EscolasEntity> optEscola = escolasRepository.findById(idEscola);
            // Procurando o professor pelo ID fornecido
            Optional<ProfessoresEntity> optProfessor = professorRepository.findById(idProfessor);
            // Verificando se a escola e o professor existem no banco de dados
            if (optEscola.isPresent() && optProfessor.isPresent()) {
                EscolasEntity escola = optEscola.get(); // Obtendo a escola da Optional
                ProfessoresEntity professor = optProfessor.get(); // Obtendo o professor da Optional
                // Verificando se o professor já está relacionado à escola
                if (escola.getProfessores().contains(professor)) {
                    // Verificando se o professor já está associado à escola correta
                    if (professor.getEscola() == escola) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Professor e escola já relacionados."); // Retorna erro se já estão relacionados
                    }
                    // Se o professor está em outra escola, atualiza para a nova escola
                    professor.setEscola(escola);
                } else if (professor.getEscola() != escola) {
                    // Se o professor não está na lista de professores da escola, adiciona o professor à escola
                    escola.getProfessores().add(professor);
                    // Atualiza a escola do professor
                    professor.setEscola(escola);
                }
                // Se a operação for bem-sucedida, retorna sucesso
                return ResponseEntity.status(HttpStatus.OK).body("Professor adicionado com sucesso!");

            }
            // Se a escola ou o professor não forem encontrados, retorna BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola ou Professor não encontrados");
        } catch (Exception e) {
            // Se houver um erro, retorna INTERNAL_SERVER_ERROR com a mensagem de erro
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
                        return ResponseEntity.status(HttpStatus.OK).body("Professor removido.");
                    }
                    escola.getProfessores().remove(professor);
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
    public ResponseEntity<?> listarProfessor(CriarEscolasDTO criarEscolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(criarEscolasDTO.cnpj());
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
                return  ResponseEntity.status(HttpStatus.OK).body("Lista de Professores: "+escola.getProfessores());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
