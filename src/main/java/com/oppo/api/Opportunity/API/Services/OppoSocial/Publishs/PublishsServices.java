package com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import org.springframework.http.ResponseEntity;

public interface PublishsServices {
    ResponseEntity<?> publishPost(CriarPostagemDTO criarPostagemDTO);

    ResponseEntity<?> getpublishs();
}
