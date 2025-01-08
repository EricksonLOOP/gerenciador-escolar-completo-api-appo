package com.oppo.api.Opportunity.API.Services.OppoManagement.PubliAuthService;

import com.oppo.api.Opportunity.API.Models.CriarUsuario.CriarUsuarioModel;
import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import org.springframework.http.ResponseEntity;

public interface PubliAuthService {
    ResponseEntity<?> loginUser(LoginModel loginModel);

    ResponseEntity<?> createUser(CriarUsuarioModel criarUsuarioModel);
}
