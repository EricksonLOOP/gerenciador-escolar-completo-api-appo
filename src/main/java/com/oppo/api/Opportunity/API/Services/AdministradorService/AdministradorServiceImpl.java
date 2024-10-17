package com.oppo.api.Opportunity.API.Services.AdministradorService;

import com.oppo.api.Opportunity.API.DTOs.AdminDTOs.AdminDTO;
import com.oppo.api.Opportunity.API.DTOs.AdminDTOs.AdminEscolasDTO;
import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
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
public class AdministradorServiceImpl  implements AdministradorService{
    @Autowired
    final private AdmRepository admRepository;
    @Autowired
    final private EscolasRepository escolasRepository;
    @Autowired
    final private ValidacoesService validacoesService;
    public AdministradorServiceImpl(AdmRepository admRepository, EscolasRepository escolasRepository, ValidacoesService validacoesService) {
        this.admRepository = admRepository;
        this.escolasRepository = escolasRepository;
        this.validacoesService = validacoesService;
    }

    @Override
    public ResponseEntity<?> create(AdminDTO adminDTO) {
        try{
            if (!validacoesService.validarcpf(adminDTO.cpf())){
                return ResponseEntity.badRequest().body("CPF inválido!");
            }
            Optional<AdministradorOppoEntity> optAdm = admRepository.findByInformacoesPessoais_Cpf(adminDTO.cpf());
            if (optAdm.isEmpty()){
                AdministradorOppoEntity adm = AdministradorOppoEntity.builder()
                        .informacoesPessoais(new InformacoesPessoaisModel(
                                adminDTO.nome(),
                                adminDTO.dataDeNascimento(),
                                adminDTO.cpf(),
                                adminDTO.rg(),
                                adminDTO.mae(),
                                adminDTO.pai()
                        ))
                        .senha(adminDTO.senha())
                        .role(TagsENUM.ADMIN)
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body("Administrador Criado: "+admRepository.save(adm));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Administrador já existente.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> update(UUID id, AdminDTO adminDTO) {
        try {
            Optional<AdministradorOppoEntity> administradorOppo = admRepository.findById(id);
            if (administradorOppo.isPresent()){
                AdministradorOppoEntity administradorOppoEntity = administradorOppo.get();
                administradorOppoEntity.setRole(adminDTO.role());
                return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso! " + admRepository.save(administradorOppoEntity));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não existe");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> listarEscolas() {
        try {
            List<EscolasEntity> listaEscolas = escolasRepository.findAll();
            if (listaEscolas.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body("Não encontrou escolas.");
            }
            List<AdminEscolasDTO> escolasDTOS = new ArrayList<>();
            for (int i = 0; i< listaEscolas.size(); i++){
                AdminEscolasDTO escola = new AdminEscolasDTO(
                        listaEscolas.get(i).getInformacoesEscola().getNome(),
                        listaEscolas.get(i).getId()
                );
                escolasDTOS.add(escola);
            }
            return ResponseEntity.status(HttpStatus.OK).body(escolasDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getInfosEscola(UUID id) {
       try{
           Optional<EscolasEntity> escolas = escolasRepository.findById(id);
           if(escolas.isPresent()){
               EscolasEntity escola = escolas.get();
               return ResponseEntity.status(HttpStatus.OK).body(escola);
           }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escola não encontrada.");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
       }
    }
}
