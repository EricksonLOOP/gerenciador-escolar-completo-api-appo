package com.oppo.api.Opportunity.API.Services.AdministradorService;

import com.oppo.api.Opportunity.API.DTOs.AdminDTO.AdminDTO;
import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorServiceImpl  implements AdministradorService{
    @Autowired
    final private AdmRepository admRepository;

    public AdministradorServiceImpl(AdmRepository admRepository) {
        this.admRepository = admRepository;
    }

    @Override
    public ResponseEntity<?> create(AdminDTO adminDTO) {
        try{
            Optional<AdministradorOppoEntity> optAdm = admRepository.findByCPF(adminDTO.cpf());
            if (optAdm.isEmpty()){
                AdministradorOppoEntity adm = new AdministradorOppoEntity();
                adm.setCPF(adminDTO.cpf());
                adm.setRg(adminDTO.rg());
                adm.setRole(adminDTO.role());
                adm.setSenha(adminDTO.senha());
                adm.setNome(adminDTO.nome());
                return ResponseEntity.status(HttpStatus.CREATED).body("Administrador Criado: "+admRepository.save(adm));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Administrador já existente.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
