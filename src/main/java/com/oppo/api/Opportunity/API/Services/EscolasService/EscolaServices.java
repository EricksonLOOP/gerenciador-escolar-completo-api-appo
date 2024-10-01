package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface EscolaServices {
     ResponseEntity<?> create(EscolasDTO escolasDTO);
}
