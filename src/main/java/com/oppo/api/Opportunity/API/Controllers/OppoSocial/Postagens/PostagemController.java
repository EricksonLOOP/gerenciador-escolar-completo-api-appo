package com.oppo.api.Opportunity.API.Controllers.OppoSocial.Postagens;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.PostsDTO;
import com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs.PublishsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/social")
public class PostagemController {
    @Autowired
    private final PublishsServices publishsServices;
    @Autowired
    private PagedResourcesAssembler<PostsDTO> pagedResourcesAssembler;
    public PostagemController(PublishsServices publishsServices) {
        this.publishsServices = publishsServices;
    }

    @PostMapping("/create")
    public ResponseEntity<?> criarPostagem(@RequestBody CriarPostagemDTO criarPostagemDTO){
        return publishsServices.publishPost(criarPostagemDTO);
    }
    @GetMapping("/publishes")
    public ResponseEntity<?> resgatarPostagens(@RequestParam(value = "page", defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.status(HttpStatus.OK).body(pagedResourcesAssembler.toModel(publishsServices.getpublishs(pageable)));
    }
}
