package com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.PostsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;



public interface PublishsServices {
    ResponseEntity<?> publishPost(CriarPostagemDTO criarPostagemDTO);

    Page<PostsDTO> getpublishs(Pageable pageable);
}
