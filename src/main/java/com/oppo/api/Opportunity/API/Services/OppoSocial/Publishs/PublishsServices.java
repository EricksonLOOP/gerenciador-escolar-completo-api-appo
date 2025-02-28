package com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CommentsDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.PostsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface PublishsServices {
    ResponseEntity<?> publishPost(CriarPostagemDTO criarPostagemDTO, String myToken);
    Page<CommentsDTO> getPostComments(UUID id, Pageable pageable);
    Page<PostsDTO> getPosts(Pageable pageable);

    ResponseEntity<?> addLike(UUID id, String token);
    ResponseEntity<?> addComment(UUID id, String token, String content);
}

