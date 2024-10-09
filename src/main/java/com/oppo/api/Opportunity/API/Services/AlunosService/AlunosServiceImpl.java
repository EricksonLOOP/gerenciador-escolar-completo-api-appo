package com.oppo.api.Opportunity.API.Services.AlunosService;

import com.oppo.api.Opportunity.API.DTOs.AlunoDTOs.CriarAlunoDTO;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunosServiceImpl implements AlunosService{
    @Autowired
    AlunosRepository alunosRepository;
    @Override
    public ResponseEntity<?> criarAluno(CriarAlunoDTO alunoDTO) {
        try{
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
            throw new RuntimeException();
        }
    }
}
