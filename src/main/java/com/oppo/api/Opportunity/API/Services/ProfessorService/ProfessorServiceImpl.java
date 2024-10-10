package com.oppo.api.Opportunity.API.Services.ProfessorService;

import com.oppo.api.Opportunity.API.DTOs.ProfessorDTOs.CriarProfessorDTO;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.InformacoesProfissionaisModel.InformacoesProfissionaisModel;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.Services.ValidacoesServices.ValidacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private ValidacoesService validacoesService;
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
