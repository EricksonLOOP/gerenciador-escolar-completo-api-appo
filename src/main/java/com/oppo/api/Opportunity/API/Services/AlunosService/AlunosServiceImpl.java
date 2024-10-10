package com.oppo.api.Opportunity.API.Services.AlunosService;

import com.oppo.api.Opportunity.API.DTOs.AlunoDTOs.CriarAlunoDTO;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Services.ValidacoesServices.ValidacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AlunosServiceImpl implements AlunosService{
    @Autowired
    AlunosRepository alunosRepository;
    @Autowired
    ValidacoesService validacoesService;
    @Override
    public ResponseEntity<?> criarAluno(CriarAlunoDTO alunoDTO) {
        try{
            if (!validacoesService.validarcpf(alunoDTO.cpf())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido.");
            }
            Optional<AlunosEntity> optAluno = alunosRepository.findByInformacoesPessoais_Cpf(alunoDTO.cpf());
            if (optAluno.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário com CPF %s já existe.".formatted(alunoDTO.cpf()));
            }
            AlunosEntity aluno = AlunosEntity.builder()
                    .informacoesPessoais(new InformacoesPessoaisModel(
                            alunoDTO.nome(),
                            alunoDTO.dataDeNascimento(),
                            alunoDTO.cpf(),
                            alunoDTO.rg(),
                            alunoDTO.mae(),
                            alunoDTO.pai()
                            ))
                    .contato( new ContatoModel(
                            alunoDTO.telefone(),
                            alunoDTO.email()
                    ))
                    .endereco( new EnderecoModel(
                            alunoDTO.rua(),
                            alunoDTO.numero(),
                            alunoDTO.bairro(),
                            alunoDTO.cidade(),
                            alunoDTO.estado(),
                            alunoDTO.cep()
                    ))
                    .senha(alunoDTO.senha())
                    .role(alunoDTO.role())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(alunosRepository.save(aluno));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body("Causa: %s, Mensagem: %s".formatted(e.getCause(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deletarAluno(CriarAlunoDTO alunoDTO, UUID id) {
        return null;
    }
}
