package com.oppo.api.Opportunity.API.Services.AdministradorService;

import com.oppo.api.Opportunity.API.DTOs.AdminDTO.AdminDTO;
import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AdministradorService {
    ResponseEntity<?> create(AdminDTO adminDTO);

    ResponseEntity<?> update(UUID id, AdminDTO adminDTO);
}
