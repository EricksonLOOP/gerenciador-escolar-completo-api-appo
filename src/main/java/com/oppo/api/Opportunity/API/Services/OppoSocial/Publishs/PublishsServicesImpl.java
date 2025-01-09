package com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.Entitys.PublishsEntity.PublishsEntity;
import com.oppo.api.Opportunity.API.Models.AuthorModel.AuthorModel;
import com.oppo.api.Opportunity.API.Repositories.PublishsRepository.PublishsRepository;
import com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices.ValidacoesService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublishsServicesImpl implements PublishsServices{
    @Autowired
    private  final PublishsRepository publishsRepository;
    @Autowired
    private  final ValidacoesService validacoesService;
    public PublishsServicesImpl(PublishsRepository publishsRepository, ValidacoesService validacoesService) {
        this.publishsRepository = publishsRepository;
        this.validacoesService = validacoesService;
    }

    @Override
    public ResponseEntity<?> publishPost(CriarPostagemDTO criarPostagemDTO) {
        try{
            if (validacoesService.verificarSeContaExistentePeloId(UUID.fromString(criarPostagemDTO.id()))){
                PublishsEntity newPublish = PublishsEntity.builder()
                        .id(UUID.randomUUID())
                        .author(new AuthorModel(UUID.fromString(criarPostagemDTO.id()), criarPostagemDTO.name()))
                        .content(criarPostagemDTO.content())
                        .createdAt(new Date())
                        .build();
                publishsRepository.save(newPublish);
                return ResponseEntity.status(HttpStatus.CREATED).body("Postagem criada com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ação inválida");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao tentar relizar postagem. message: "+e.getCause());
        }
    }

    @Override
    @Cacheable(value = "posts")
    public ResponseEntity<?> getpublishs() {
        try{
        List<PublishsEntity> publishsEntities = publishsRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(publishsEntities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar publicações");
        }
    }
}
