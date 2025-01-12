package com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CommentsDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.PostsDTO;
import com.oppo.api.Opportunity.API.Entitys.PublishsEntity.PublishsEntity;
import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import com.oppo.api.Opportunity.API.Repositories.PublishsRepository.PublishsRepository;
import com.oppo.api.Opportunity.API.SecurityPaths.Auth.JwtUtil;
import com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices.ValidacoesService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PublishsServicesImpl implements PublishsServices{
    @Autowired
    private  final PublishsRepository publishsRepository;
    @Autowired
    private  final ValidacoesService validacoesService;
    @Autowired
    private final JwtUtil jwtUtil;
    public PublishsServicesImpl(PublishsRepository publishsRepository, ValidacoesService validacoesService, JwtUtil jwtUtil) {
        this.publishsRepository = publishsRepository;
        this.validacoesService = validacoesService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @CacheEvict(value = "posts", allEntries = true)
    public ResponseEntity<?> publishPost(CriarPostagemDTO criarPostagemDTO, String myToken) {
        try{
            Claims myUser = jwtUtil.parseJwtClaims(myToken);
            if (myUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inválid Token.");
            }

                PublishsEntity newPublish = PublishsEntity.builder()
                        .id(UUID.randomUUID())
                        .author(new AuthorModel(UUID.fromString(myUser.get("id").toString()), myUser.get("nome").toString()))
                        .content(criarPostagemDTO.content())
                        .createdAt(new Date())
                        .build();
                publishsRepository.save(newPublish);
                return ResponseEntity.status(HttpStatus.CREATED).body("Postagem criada com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao tentar relizar postagem. message: "+e.getCause());
        }
    }

    @Override
    @Cacheable(value = "posts")
    public Page<PostsDTO> getpublishs(Pageable pageable) {
        try{
            Page<PublishsEntity> postagens  = publishsRepository.findAll(pageable);

        return  postagens.map(post -> PostsDTO.builder()
                .likes(post.getLikes())
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .author(new AuthorModel(post.getAuthor().getAuthorID(), post.getAuthor().getAuthorName()))
                .build());
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private List<CommentsDTO> getComments(PublishsEntity post) {
        List<CommentsDTO> allcomentsFromPost = new ArrayList<>();
        if (post.getComments().size()>0){
         post.getComments().forEach(
                 comment ->{
                     CommentsDTO newComment = CommentsDTO.builder()
                             .Id(comment.getId())
                             .publishID(comment.getId())
                             .likes(comment.getLikes())
                             .content(comment.getContent())
                             .build();
                     allcomentsFromPost.add(newComment);

                 });
         return allcomentsFromPost;
     }

        return Collections.EMPTY_LIST;
    }


}
