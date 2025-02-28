package com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CommentsDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.PostsDTO;
import com.oppo.api.Opportunity.API.Entitys.CommentEntity.CommentEntity;
import com.oppo.api.Opportunity.API.Entitys.PublishsEntity.PublishsEntity;
import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import com.oppo.api.Opportunity.API.Repositories.CommentsRepository.CommentsRepository;
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
    private  final CommentsRepository commentsRepository;
    @Autowired
    private  final ValidacoesService validacoesService;
    @Autowired
    private final JwtUtil jwtUtil;
    public PublishsServicesImpl(PublishsRepository publishsRepository, CommentsRepository commentsRepository, ValidacoesService validacoesService, JwtUtil jwtUtil) {
        this.publishsRepository = publishsRepository;
        this.commentsRepository = commentsRepository;
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
    @Cacheable(value = "postComments", key = "#id")
    public Page<CommentsDTO> getPostComments(UUID id, Pageable pageable) {
        try {
            Page<CommentEntity> entityCommentPage = commentsRepository.findByPublishsEntityId(id, pageable);
            return entityCommentPage.map(commentEntity -> {
                // Criando o DTO para cada comentário
                CommentsDTO newCommentDTO = CommentsDTO.builder()
                        .content(commentEntity.getContent())
                        .publishID(commentEntity.getPublishsEntity().getId())
                        .Id(commentEntity.getId())
                        .author(commentEntity.getAuthorModel())
                        .responses(commentEntity.getResponses().size())
                        .likes(commentEntity.getLikes())
                        .build();

                // Retornando o DTO
                return newCommentDTO;
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }


    @Override
    @Cacheable(value = "posts")
    public Page<PostsDTO> getPosts(Pageable pageable) {
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

    @Override
    @CacheEvict(value = "likes", key = "#id", beforeInvocation = true)
    public ResponseEntity<?> addLike(UUID id, String token) {
        try {

            Claims myUser = jwtUtil.parseJwtClaims(token);
            if (myUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inválid Token.");
            }

            Optional<PublishsEntity> myOptPublish = publishsRepository.findById(id);
            if (myOptPublish.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publicação não encontrada");
            }
            PublishsEntity myPublish = myOptPublish.get();
            if (myPublish.getLikes().contains(myUser.get("id").toString())){
                myPublish.getLikes().remove(myUser.get("id").toString());
            }else {
                myPublish.getLikes().add(myUser.get("id").toString());
            }
            return ResponseEntity.status(HttpStatus.OK).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao adicionar like na publicação Erro: "+e.getCause());
        }
    }
    @Override
    @CacheEvict(value = "postComments", key = "#id", beforeInvocation = true)
    public ResponseEntity<?> addComment(UUID id, String token, String content) {
        try {

            Claims myUser = jwtUtil.parseJwtClaims(token);
            if (myUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inválid Token.");
            }

            Optional<PublishsEntity> myOptPublish = publishsRepository.findById(id);
            if (myOptPublish.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publicação não encontrada");
            }
            PublishsEntity myPublish = myOptPublish.get();
           myPublish.getComments().add(CommentEntity.builder()
                           .authorModel(new AuthorModel(UUID.fromString(myUser.get("id").toString()), myUser.get("name").toString()))
                           .content(content)
                   .build());
           publishsRepository.save(myPublish);
            return ResponseEntity.status(HttpStatus.OK).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao adicionar like na publicação Erro: "+e.getCause());
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
                             .likes(comment.getLikes().isEmpty() ? Collections.emptyList() : comment.getLikes())
                             .content(comment.getContent())
                             .build();
                     allcomentsFromPost.add(newComment);

                 });
         return allcomentsFromPost;
     }

        return Collections.EMPTY_LIST;
    }


}
