package com.oppo.api.Opportunity.API.Services.AdministradorService;

import com.oppo.api.Opportunity.API.DTOs.AdminDTOs.AdminDTO;
import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdministradorServiceImpl  implements AdministradorService{
    @Autowired
    final private AdmRepository admRepository;
    final private EscolasRepository escolasRepository;
    public AdministradorServiceImpl(AdmRepository admRepository, EscolasRepository escolasRepository) {
        this.admRepository = admRepository;
        this.escolasRepository = escolasRepository;
    }

    @Override
    public ResponseEntity<?> create(AdminDTO adminDTO) {
        try{
            Optional<AdministradorOppoEntity> optAdm = admRepository.findByInformacoesPessoais_Cpf(adminDTO.cpf());
            if (optAdm.isEmpty()){
                AdministradorOppoEntity adm = new AdministradorOppoEntity();
                adm.getInformacoesPessoais().setCpf(adminDTO.cpf());
                adm.getInformacoesPessoais().setRg(adminDTO.rg());
                adm.setRole(adminDTO.role());
                adm.setSenha(adminDTO.senha());
                adm.getInformacoesPessoais().setNome(adminDTO.nome());
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
    public List<EscolasEntity> listarEscolas() {
        try {
            List<EscolasEntity> listaEscolas = escolasRepository.findAll();
            return listaEscolas;
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
