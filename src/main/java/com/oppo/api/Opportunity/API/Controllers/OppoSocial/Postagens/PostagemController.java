package com.oppo.api.Opportunity.API.Controllers.OppoSocial.Postagens;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/post")
public class PostagemController {
    @PostMapping("/create")
    public ResponseEntity criarPostagem(){
        return null;
    }
}
