package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.AlunoDTOs.ListarAlunosDTO;
import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesEscola.InformacoesEscola;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
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

    @Override
    public ResponseEntity<?> create(EscolasDTO escolasDTO) {
        try {
            //Verifica se o CNPJ existe
            if (!validacoesService.validarcnpj(escolasDTO.cnpj())){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ não existe.");
            }
            // Verifica se a escola já existe pelo CNPJ
            Optional<EscolasEntity> encontrarEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj()); // Acesse diretamente o campo cnpj
            if (encontrarEscola.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já registrado. Não é possível criar uma escola com este CNPJ");
            }

            // Cria uma nova entidade de escola a partir do DTO
            EscolasEntity escola = EscolasEntity.builder()
                    .informacoesEscola(new InformacoesEscola(
                            escolasDTO.nivelDeEnsino(),
                            escolasDTO.nivelDeEnsino(),
                            escolasDTO.capMax(),
                            escolasDTO.numFuncionarios(),
                            escolasDTO.turnos(),
                            escolasDTO.numSalas(),
                            escolasDTO.numLaboratorios(),
                            escolasDTO.numBiblioteca(),
                            escolasDTO.possuiInternet(),
                            escolasDTO.modeloEscolar(),
                            escolasDTO.nome(),
                            escolasDTO.cnpj()
                    ))
                    .contatoEscola(new ContatoModel(escolasDTO.telefone(), escolasDTO.email()))
                    .endereco(new EnderecoModel(
                            escolasDTO.rua(),
                            escolasDTO.numero(),
                            escolasDTO.bairro(),
                            escolasDTO.cidade(),
                            escolasDTO.estado(),
                            escolasDTO.cep()
                    ))
                    .role(escolasDTO.role())
                    .senha(escolasDTO.senha())
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
    public ResponseEntity<?> update(EscolasDTO escolasDTO) {
        try {
            // Procurando a escola no repositório usando o CNPJ da escola (baseado nas informações fornecidas em 'escolasDTO')
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());

            // Verificando se a escola foi encontrada no banco de dados
            if (optEscola.isPresent()) {
                // Recupera a escola encontrada
                EscolasEntity escola = optEscola.get();

                // Atualizando os dados da escola com os valores fornecidos no 'escolasDTO'
                escola.getInformacoesEscola().setNome(escolasDTO.nome());   // Atualiza o nome da escola
                escola.getInformacoesEscola().setCnpj(escolasDTO.cnpj());   // Atualiza o CNPJ da escola
                escola.setSenha(escolasDTO.senha());                        // Atualiza a senha da escola
                escola.setRole(escolasDTO.role());                          // Atualiza o papel/role da escola (por exemplo, ADMIN)

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
    public ResponseEntity<?> addProfessor(EscolasDTO escolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
            //    return  ResponseEntity.status(HttpStatus.OK).body("Professor adicionado: "+escola.getProfessores().add(escolasDTO.professoresEntity()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ResponseEntity<?> removeProfessor(EscolasDTO escolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
        //        return  ResponseEntity.status(HttpStatus.OK).body("Professor removido: "+escola.getProfessores().remove(escolasDTO.professoresEntity()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> listarProfessor(EscolasDTO escolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
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
