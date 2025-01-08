package com.oppo.api.Opportunity.API.Services.OppoManagement.AdministradorService;

import com.oppo.api.Opportunity.API.DTOs.OppoManagement.AdminDTOs.AdminDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdministradorService {
    ResponseEntity<?> create(AdminDTO adminDTO);

    ResponseEntity<?> update(UUID id, AdminDTO adminDTO);

    ResponseEntity<?> listarEscolas();

    ResponseEntity<?> getInfosEscola(UUID id);
}
