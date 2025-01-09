package com.oppo.api.Opportunity.API.Controllers.OppoSocial.Postagens;

import com.oppo.api.Opportunity.API.DTOs.OppoSocial.PostagemDTO.CriarPostagemDTO;
import com.oppo.api.Opportunity.API.Services.OppoSocial.Publishs.PublishsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("api/social")
public class PostagemController {
    @Autowired
    private final PublishsServices publishsServices;

    public PostagemController(PublishsServices publishsServices) {
        this.publishsServices = publishsServices;
    }

    @PostMapping("/create")
    public ResponseEntity<?> criarPostagem(@RequestBody CriarPostagemDTO criarPostagemDTO){
        return publishsServices.publishPost(criarPostagemDTO);
    }
    @GetMapping("/publishes")
    public ResponseEntity<?> resgatarPostagens(){
        return publishsServices.getpublishs();
    }
}
