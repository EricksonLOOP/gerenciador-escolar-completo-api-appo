package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesEscola.InformacoesEscola;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EscolaServicesImpl implements EscolaServices {

    @Autowired
    private EscolasRepository escolasRepository;



    @Override
    public ResponseEntity<?> create(EscolasDTO escolasDTO) {
        try {
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
        try{
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
            if (optEscola.isPresent()){
               EscolasEntity escola = optEscola.get();
               escola.getInformacoesEscola().setNome(escolasDTO.nome());
               escola.getInformacoesEscola().setCnpj(escolasDTO.cnpj());
               escola.setSenha(escolasDTO.senha());
               escola.setRole(escolasDTO.role());
               return ResponseEntity.status(HttpStatus.OK).body(escolasRepository.save(escola));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao atualizar escola.");

        }
    }

    @Override
    public ResponseEntity<?> addAluno(EscolasDTO escolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
//                return  ResponseEntity.status(HttpStatus.OK).body("Aluno adicionado: "+escola.getAlunos().add(escolasDTO.alunosEntity()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> removeAluno(EscolasDTO escolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
             //   return  ResponseEntity.status(HttpStatus.OK).body("Aluno removido: "+escola.getAlunos().remove(escolasDTO.notify()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> listarAluno(EscolasDTO escolasDTO) {
        try {
            Optional<EscolasEntity> optEscola = escolasRepository.findByInformacoesEscola_Cnpj(escolasDTO.cnpj());
            if (optEscola.isPresent()){
                EscolasEntity escola = optEscola.get();
                return  ResponseEntity.status(HttpStatus.OK).body("Lista de Alunos: "+escola.getAlunos());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada");
        } catch (Exception e) {
            throw new RuntimeException(e);
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
