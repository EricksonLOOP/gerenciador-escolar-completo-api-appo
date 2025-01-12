package com.oppo.api.Opportunity.API.Controllers.PubliAuthController;

import com.oppo.api.Opportunity.API.Models.CriarUsuario.CriarUsuarioModel;
import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import com.oppo.api.Opportunity.API.Services.OppoManagement.PubliAuthService.PubliAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:5173/")
public class PubliAuthController {
    @Autowired
    private PubliAuthService publiAuthService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginModel loginModel){
        return publiAuthService.loginUser(loginModel);
    }
    @PostMapping("/create")
    public ResponseEntity<?> loginUser(@RequestBody CriarUsuarioModel criarUsuarioModel, HttpServletRequest request){
        String myToken = request.getHeader("Authorization");
        return publiAuthService.createUser(criarUsuarioModel);
    }

}
