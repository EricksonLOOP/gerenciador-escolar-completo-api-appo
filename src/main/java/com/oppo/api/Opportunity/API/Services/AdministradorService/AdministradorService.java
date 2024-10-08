package com.oppo.api.Opportunity.API.Services.AdministradorService;

import com.oppo.api.Opportunity.API.DTOs.AdminDTOs.AdminDTO;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AdministradorService {
    ResponseEntity<?> create(AdminDTO adminDTO);

    ResponseEntity<?> update(UUID id, AdminDTO adminDTO);

    List<EscolasEntity> listarEscolas();

    ResponseEntity<?> getInfosEscola(UUID id);
}
